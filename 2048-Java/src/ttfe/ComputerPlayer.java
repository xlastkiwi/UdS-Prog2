package ttfe;

public class ComputerPlayer implements PlayerInterface{
    
    @Override
    public MoveDirection getPlayerMove(SimulatorInterface game, UserInterface ui) {
        return findBestMove(game);
    }

    private MoveDirection findBestMove(SimulatorInterface game) {
        MoveDirection[] directions = MoveDirection.values();
        MoveDirection bestMove = null;
        double highestScore = -Double.MAX_VALUE;

        for (MoveDirection direction : directions) {
            Simulator copy = new Simulator((Simulator) game);
            if (copy.performMove(direction)) {
                double score = algorithm(copy, 4, false);
                if (score > highestScore) {
                    highestScore = score;
                    bestMove = direction;
                }
            }
        }
        return bestMove;
    }

    private double algorithm(Simulator game, int depth, boolean isMaximizing) {
        if (depth == 0 || !game.isMovePossible()) {
            return evaluate(game);
        }

        if (isMaximizing) {
            double maxEval = -Double.MAX_VALUE;
            for (MoveDirection direction : MoveDirection.values()) {
                Simulator copy = new Simulator(game);
                if (copy.performMove(direction)) {
                    double eval = algorithm(copy, depth - 1, false);
                    maxEval = Math.max(maxEval, eval);
                }
            }
            return maxEval;
        } else {
            double sumEval = 0;
            int emptyTiles = 0;
            for (int x = 0; x < game.getBoardWidth(); x++) {
                for (int y = 0; y < game.getBoardHeight(); y++) {
                    if (game.getPieceAt(x, y) == 0) {
                        emptyTiles++;
                        for (int value : new int[]{2, 4}) {
                            Simulator copy = new Simulator(game);
                            copy.setPieceAt(x, y, value);
                            double eval = algorithm(copy, depth - 1, true);
                            sumEval += eval * (value == 2 ? 0.9 : 0.1);
                        }
                    }
                }
            }
            return emptyTiles == 0 ? evaluate(game) : sumEval / emptyTiles;
        }
    }

    private double evaluate(Simulator game) {
        double score = game.getPoints();
        score += getEmptyTilesWeight(game);
        score += getSmoothnessWeight(game);
        score += getMonotonicityWeight(game);
        return score;
    }

    private double getEmptyTilesWeight(Simulator game) {
        int emptyTiles = 0;
        for (int x = 0; x < game.getBoardWidth(); x++) {
            for (int y = 0; y < game.getBoardHeight(); y++) {
                if (game.getPieceAt(x, y) == 0) {
                    emptyTiles++;
                }
            }
        }
        return emptyTiles * 2.7;
    }

    private double getSmoothnessWeight(Simulator game) {
        double smoothness = 0;
        for (int x = 0; x < game.getBoardWidth(); x++) {
            for (int y = 0; y < game.getBoardHeight(); y++) {
                if (game.getPieceAt(x, y) != 0) {
                    int value = game.getPieceAt(x, y);
                    if (x + 1 < game.getBoardWidth() && game.getPieceAt(x + 1, y) != 0) {
                        smoothness -= Math.abs(value - game.getPieceAt(x + 1, y));
                    }
                    if (y + 1 < game.getBoardHeight() && game.getPieceAt(x, y + 1) != 0) {
                        smoothness -= Math.abs(value - game.getPieceAt(x, y + 1));
                    }
                }
            }
        }
        return smoothness * 0.1;
    }

    private double getMonotonicityWeight(Simulator game) {
        double[] totals = {0, 0, 0, 0};

        for (int x = 0; x < game.getBoardWidth(); x++) {
            for (int y = 0; y < game.getBoardHeight(); y++) {
                int value = game.getPieceAt(x, y);
                if (value != 0) {
                    value = (int) (Math.log(value) / Math.log(2));

                    if (x > 0) {
                        int prevValue = game.getPieceAt(x - 1, y);
                        if (prevValue != 0) {
                            prevValue = (int) (Math.log(prevValue) / Math.log(2));
                            if (prevValue > value) {
                                totals[0] += value - prevValue;
                            } else {
                                totals[1] += prevValue - value;
                            }
                        }
                    }

                    if (y > 0) {
                        int prevValue = game.getPieceAt(x, y - 1);
                        if (prevValue != 0) {
                            prevValue = (int) (Math.log(prevValue) / Math.log(2));
                            if (prevValue > value) {
                                totals[2] += value - prevValue;
                            } else {
                                totals[3] += prevValue - value;
                            }
                        }
                    }
                }
            }
        }
        return Math.max(totals[0], totals[1]) + Math.max(totals[2], totals[3]);
    }
}