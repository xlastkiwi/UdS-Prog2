package ttfe;

import java.util.Random;

public class Simulator implements SimulatorInterface{
    private int width;
    private int height;
    private int[][] board;
    private int numMoves;
    private int numPieces;
    private int points;
    private Random random;

    public Simulator(int width, int height, Random random) {
        if (width < 2 || height < 2 || random == null) {
            throw new IllegalArgumentException("Invalid board dimensions or random generator");
        }
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
        this.numMoves = 0;
        this.numPieces = 0;
        this.points = 0;
        this.random = random;
        addPiece();
        addPiece();
    }

    @Override
    public void addPiece() {
        if (!isSpaceLeft()) {
            throw new IllegalStateException("No space to add piece");
        }
        int value = random.nextDouble() < 0.9 ? 2 : 4;
        int x, y;
        do{
            x = random.nextInt(width);
            y = random.nextInt(height);
        } while (board[x][y] != 0);
        board[x][y] = value;
        numPieces++;
    }

    @Override
    public int getBoardHeight() {
        return height;
    }

    @Override
    public int getBoardWidth() {
        return width;
    }

    @Override
    public int getNumMoves() {
        return numMoves;
    }

    @Override
    public int getNumPieces() {
        return numPieces;
    }

    @Override
    public int getPieceAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Invalid coordinates");
        }
        return board[x][y];
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public boolean isMovePossible() {
        return isMovePossible(MoveDirection.NORTH) || isMovePossible(MoveDirection.SOUTH) || isMovePossible(MoveDirection.EAST) || isMovePossible(MoveDirection.WEST);
    }

    @Override
    public boolean isMovePossible(MoveDirection direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }
        switch (direction) {
            case NORTH:
                return canMoveNorth();
            case SOUTH:
                return canMoveSouth();
            case EAST:
                return canMoveEast();
            case WEST:
                return canMoveWest();
            default:
                return false;
        }
    }

    private boolean canMoveNorth() {
        for (int x = 0; x < width; x++) {
            for (int y = 1; y < height; y++) {
                if (board[x][y] != 0 && (board[x][y - 1] ==0 || board[x][y - 1] == board[x][y])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canMoveSouth() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height - 1; y++) {
                if (board[x][y] != 0 && (board[x][y + 1] == 0 || board[x][y + 1] == board[x][y])) {
                    return true;
                }
            } 
        }
        return false;
    }

    private boolean canMoveEast() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width - 1; x++) {
                if (board[x][y] != 0 && (board[x + 1][y] == 0 || board[x + 1][y] == board[x][y])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canMoveWest() {
        for (int y = 0; y < height; y++) {
            for (int x = 1; x < width; x++) {
                if (board[x][y] != 0 && (board[x - 1][y] == 0 || board[x - 1][y] == board[x][y])) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isSpaceLeft() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (board[x][y] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean performMove(MoveDirection direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }
        boolean moved = false;
        switch (direction) {
            case NORTH:
                moved = moveNorth();
                break;
            case SOUTH:
                moved = moveSouth();
                break;
            case EAST:
                moved = moveEast();
                break;
            case WEST:
                moved = moveWest();
                break;
        }
        if (moved) {
            numMoves++;
            addPiece();
        }
        return moved;
    }

    private boolean moveNorth() {
        boolean moved = false;
        for (int x = 0; x < width; x++) {
            for (int y = 1; y < height; y++) {
                if (board[x][y] != 0) {
                    int targetY = y;
                    while (targetY > 0 && board[x][targetY -1] == 0) {
                        targetY--;
                    }
                    if (targetY > 0 && board[x][targetY - 1] == board[x][y]) {
                        board[x][targetY - 1] *= 2;
                        points += board[x][targetY -1];
                        board[x][y] = 0;
                        moved = true;
                    } else if (targetY != y) {
                        board[x][targetY] = board[x][y];
                        board[x][y] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    private boolean moveSouth() {
        boolean moved = false;
        for (int x = 0; x < width; x++) {
            for (int y = height - 2; y >= 0; y--) {
                if(board[x][y] != 0) {
                    int targetY = y;
                    while (targetY < height - 1 && board[x][targetY + 1] == 0) {
                        targetY++;
                    }
                    if (targetY < height - 1 && board[x][targetY + 1] == board[x][y]) {
                        board[x][targetY + 1] *= 2;
                        points += board[x][targetY + 1];
                        board[x][y] = 0;
                        moved = true;
                    } else if (targetY != y) {
                        board[x][targetY] = board[x][y];
                        board[x][y] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    private boolean moveEast() {
        boolean moved = false;
        for (int y = 0; y < height; y++) {
            for (int x = width - 2; x >= 0; x--) {
                if (board[x][y] != 0) {
                    int targetX = x;
                    while (targetX < width - 1 && board[targetX + 1][y] == 0) {
                        targetX++;
                    }
                    if (targetX < width - 1 && board[targetX + 1][y] == board[x][y]) {
                        board[targetX + 1][y] *= 2;
                        points += board[targetX + 1][y];
                        board[x][y] = 0;
                        moved = true;
                    } else if (targetX != x) {
                        board[targetX][y] = board[x][y];
                        board[x][y] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    private boolean moveWest() {
        boolean moved = false;
        for (int y = 0; y < height; y++) {
            for (int x = 1; x < width; x++) {
                if (board[x][y] != 0) {
                    int targetX = x;
                    while (targetX > 0 && board[targetX - 1][y] == 0) {
                        targetX --;
                    }
                    if (targetX > 0 && board[targetX - 1][y] == board[x][y]) {
                        board[targetX - 1][y] *= 2;
                        points += board[targetX - 1][y];
                        board[x][y] = 0;
                        moved = true;
                    } else if (targetX != x) {
                        board[targetX][y] = board[x][y];
                        board[x][y] = 0;
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    @Override
    public void run(PlayerInterface player, UserInterface ui) {
        if (player == null || ui == null) {
            throw new IllegalArgumentException("Player and UserInterface cannot be null");
        }
        int stepCounter = 0;
        ui.updateScreen(this);
        while (isMovePossible()) {
            MoveDirection direction = player.getPlayerMove(this, ui);
            if (performMove(direction)) {
                stepCounter++;
                if (stepCounter % 10 == 0) {
                    ui.updateScreen(this);
                }
            }
        }
        ui.updateScreen(this);
        ui.showGameOverScreen(this);
    }

    @Override
    public void setPieceAt(int x, int y, int piece) {
        if (x < 0 || x >= width || y < 0 || y >= height || piece < 0) {
            throw new IllegalArgumentException("Invalid coordinates or piece value");
        }
        if (board[x][y] == 0 && piece != 0) {
            numPieces++;
        } else if (board[x][y] != 0 && piece == 0) {
            numPieces--;
        }
        board[x][y] = piece;
    }

    public Simulator(Simulator other) {
        this.width = other.width;
        this.height = other.height;
        this.board = new int[other.width][other.height];
        for (int x = 0; x < other.width; x++) {
            for (int y = 0; y < other.height; y++) {
                this.board[x][y] = other.board[x][y];
            }
        }
        this.numMoves = other.numMoves;
        this.numPieces = other.numPieces;
        this.points = other.points;
        this.random = new Random(other.random.nextLong());
    }
    
}
