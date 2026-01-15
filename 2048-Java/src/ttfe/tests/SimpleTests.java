package ttfe.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import ttfe.MoveDirection;
import ttfe.SimulatorInterface;
import ttfe.TTFEFactory;

/**
 * This class provides a very simple example of how to write tests for this project.
 * You can implement your own tests within this class or any other class within this package.
 * Tests in other packages will not be run and considered for completion of the project.
 */
public class SimpleTests {

	private SimulatorInterface game;

	@Before
	public void setUp() {
		game = TTFEFactory.createSimulator(4, 4, new Random(0));
	}
	
	@Test
	public void testInitialGamePoints() {
		assertEquals("The initial game did not have zero points", 0,
				game.getPoints());
	}
	
	@Test
	public void testInitialBoardHeight() {
		assertTrue("The initial game board did not have correct height",
				4 == game.getBoardHeight());
	}


	@Test
	public void testInitialBoardWidth() {
		assertTrue("The initial game board did not have correct width", 4 == game.getBoardWidth());
	}

	@Test
	public void testAddPiece() {
		int initialPieces = game.getNumPieces();
		game.addPiece();
		assertEquals("Piece was not added correctly", initialPieces + 1, game.getNumPieces());
	}

	@Test
	public void testGetPieceAt() {
		game.setPieceAt(1, 1, 2);
		assertEquals("The value at (1, 1) was not correct", 2, game.getPieceAt(1, 1));
	}

	@Test
	public void testSetPieceAt() {
		game.setPieceAt(2, 2, 4);
		assertEquals("The value at (2, 2) was not correct", 4, game.getPieceAt(2, 2));
		game.setPieceAt(2, 2, 0);
		assertEquals("The value at (2, 2) was not cleared", 0, game.getPieceAt(2, 2));
	}

	@Test
	public void testIsMovePossible() {
		assertTrue("There should be a possible nive at the start", game.isMovePossible(null));
	}

	@Test
	public void testIsSpaceLeft() {
		assertTrue("There should be space left on the board", game.isSpaceLeft());
	}

	@Test
	public void testPerformMove() {
		game.setPieceAt(0, 0, 2);
		game.setPieceAt(0, 1, 2);
		boolean moveSuccessful = game.performMove(MoveDirection.EAST);
		assertTrue("Move should be successful", moveSuccessful);
		assertEquals("The value at (0, 3) should be 4", 4, game.getPieceAt(0, 3));
	}

	@Test
	public void testWrongAddPiece1() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				game.setPieceAt(i, j, 2);
			}
		}

		assertThrows(IllegalStateException.class, () -> {game.addPiece();});
	}

	@Test
	public void testGetNumMoves() {
		assertEquals("Initial number of moves should be 0", 0, game.getNumMoves());
		game.setPieceAt(0, 0, 2);
		game.setPieceAt(0, 1, 2);
		game.performMove(MoveDirection.EAST);
		assertEquals("Number of moves should be 1 after one move",  1, game.getNumMoves());
	}

	@Test
	public void testIsSpaceLeft2() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				game.setPieceAt(i, j, 2);
			}
		}
		assertTrue("There should be no space left", !game.isSpaceLeft());
	}

	@Test
	public void testMovePossible2() {
		game.setPieceAt(0, 0, 2);
		game.setPieceAt(0, 1, 4);
		game.setPieceAt(0, 2, 2);
		game.setPieceAt(0, 3, 4);
		game.setPieceAt(1, 0, 4);
		game.setPieceAt(1, 1, 2);
		game.setPieceAt(1, 2, 4);
		game.setPieceAt(1, 3, 2);
		game.setPieceAt(2, 0, 2);
		game.setPieceAt(2, 1, 4);
		game.setPieceAt(2, 2, 2);
		game.setPieceAt(2, 3, 4);
		game.setPieceAt(3, 0, 4);
		game.setPieceAt(3, 1, 2);
		game.setPieceAt(3, 2, 4);
		game.setPieceAt(3, 3, 2);
		assertTrue("There should be no possible moves", !game.isMovePossible(MoveDirection.NORTH));
		assertTrue("There should be no possible moves", !game.isMovePossible(MoveDirection.EAST));
		assertTrue("There should be no possible moves", !game.isMovePossible(MoveDirection.SOUTH));
		assertTrue("There should be no possible moves", !game.isMovePossible(MoveDirection.WEST));
		assertTrue("There should be no possible moves", !game.isMovePossible(null));

		game.setPieceAt(0, 0, 2);
		game.setPieceAt(0, 1, 2);
		game.setPieceAt(0, 2, 4);
		game.setPieceAt(0, 3, 4);
		assertTrue("Move should be possible", game.isMovePossible(MoveDirection.EAST));
		assertTrue("Move should be possible", game.isMovePossible(MoveDirection.WEST));
	}

	@Test
	public void testWrongPerformMove1() {
		game.setPieceAt(0, 0, 2);
		game.setPieceAt(0, 1, 4);
		game.setPieceAt(0, 2, 2);
		game.setPieceAt(0, 3, 4);
		game.setPieceAt(1, 0, 4);
		game.setPieceAt(1, 1, 2);
		game.setPieceAt(1, 2, 4);
		game.setPieceAt(1, 3, 2);
		game.setPieceAt(2, 0, 2);
		game.setPieceAt(2, 1, 4);
		game.setPieceAt(2, 2, 2);
		game.setPieceAt(2, 3, 4);
		game.setPieceAt(3, 0, 4);
		game.setPieceAt(3, 1, 2);
		game.setPieceAt(3, 2, 4);
		game.setPieceAt(3, 3, 2);
		assertTrue("Move should not be successful", !game.performMove(MoveDirection.NORTH));
		assertTrue("Move should not be successful", !game.performMove(MoveDirection.EAST));
		assertTrue("Move should not be successful", !game.performMove(MoveDirection.WEST));
		assertTrue("Move should not be successful", !game.performMove(MoveDirection.SOUTH));
	}

	@Test
	public void testWrongPerformMove2() {
		game.setPieceAt(0, 0, 2);
		game.setPieceAt(0, 1, 2);
		assertTrue("Move should be successful", game.performMove(MoveDirection.EAST));
		assertEquals("The value at (0, 3) should be 4", game.getPieceAt(0, 3));
		assertEquals("The value at (0, 0) should be 0", game.getPieceAt(0, 0));
		assertEquals("The value at (0, 1) should be 0", game.getPieceAt(0, 1));
	}

	@Test
	public void testWrongPerformMove3() {
		game.setPieceAt(1, 0, 2);
		game.setPieceAt(1, 1, 2);
		game.setPieceAt(1, 2, 2);
		game.setPieceAt(1, 3, 2);
		assertTrue("Move should be successful", game.performMove(MoveDirection.EAST));
		assertEquals("The value at (1,3) should be 4", game.getPieceAt(1,3));
		assertEquals("The value at (1,2) should be 4", game.getPieceAt(1, 2));
		assertEquals("The value at (1,0) should be 0", game.getPieceAt(1, 0));
		assertEquals("The value at (1,1) should be 0", game.getPieceAt(1, 1));
	}

	@Test
	public void testWrongPoints() {
		assertEquals("Initial points should be 0", 0, game.getPoints());
		game.setPieceAt(0, 0, 2);
		game.setPieceAt(0, 1, 2);
		game.performMove(MoveDirection.EAST);
		assertEquals("Points should be 4 after one merge", 4, game.getPoints());
	}

	@Test
	public void testNSMoves() {
		game.setPieceAt(0, 0, 2);
		game.setPieceAt(1, 0, 2);
		game.setPieceAt(2, 0, 4);
		game.setPieceAt(3, 0, 4);
		assertTrue("Move North should be possible", game.isMovePossible(MoveDirection.NORTH));
		assertTrue("Move South should be possible", game.isMovePossible(MoveDirection.SOUTH));
		assertTrue("Move East should not be possible", !game.isMovePossible(MoveDirection.EAST));
		assertTrue("Move West should not be possible", !game.isMovePossible(MoveDirection.WEST));
	}

	@Test
	public void testEWMoves() {
		game.setPieceAt(0, 0, 2);
		game.setPieceAt(0, 1, 2);
		game.setPieceAt(0, 2, 4);
		game.setPieceAt(0, 3, 4);
		assertTrue("Move East should be possible", game.isMovePossible(MoveDirection.EAST));
		assertTrue("Move West should be possible", game.isMovePossible(MoveDirection.WEST));
		assertTrue("Move North should not be possible", !game.isMovePossible(MoveDirection.NORTH));
		assertTrue("Move South should not be possible", !game.isMovePossible(MoveDirection.SOUTH));
	}

	@Test
	public void testTLCorner() {
		game.setPieceAt(0, 0, 2);
		game.setPieceAt(0, 1, 2);
		game.setPieceAt(1, 0, 4);
		assertTrue(game.isMovePossible(MoveDirection.EAST));
		assertTrue(game.isMovePossible(MoveDirection.SOUTH));
		assertTrue(game.isMovePossible(MoveDirection.NORTH));
		assertTrue(game.isMovePossible(MoveDirection.WEST));
	}

	@Test
	public void testTRCorner() {
		game.setPieceAt(0, 3, 2);
		game.setPieceAt(0, 2, 2);
		game.setPieceAt(1, 3, 4);
		assertTrue(game.isMovePossible(MoveDirection.WEST));
		assertTrue(game.isMovePossible(MoveDirection.SOUTH));
		assertTrue(game.isMovePossible(MoveDirection.NORTH));
		assertTrue(game.isMovePossible(MoveDirection.EAST));
	}@Test
	public void testBLCorner() {
		game.setPieceAt(3, 0, 2);
		game.setPieceAt(3, 1, 2);
		game.setPieceAt(2, 0, 4);
		assertTrue(game.isMovePossible(MoveDirection.EAST));
		assertTrue(game.isMovePossible(MoveDirection.NORTH));
		assertTrue(game.isMovePossible(MoveDirection.SOUTH));
		assertTrue(game.isMovePossible(MoveDirection.WEST));
	}@Test
	public void testBRCorner() {
		game.setPieceAt(3, 3, 2);
		game.setPieceAt(3, 2, 2);
		game.setPieceAt(2, 3, 4);
		assertTrue(game.isMovePossible(MoveDirection.WEST));
		assertTrue(game.isMovePossible(MoveDirection.NORTH));
		assertTrue(game.isMovePossible(MoveDirection.SOUTH));
		assertTrue(game.isMovePossible(MoveDirection.EAST));
	}


}