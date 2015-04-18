package q8.test.obj;

import static org.junit.Assert.*;

import org.junit.Test;

import q8.obj.ChessBoard;

public class ChessBoardTest {

	@Test
	public void queensInSameRowCheck_4queens_QueenInSameRowByLeft() {

		ChessBoard theBoard = new ChessBoard(8);
		theBoard.putQueen(0,0);
		theBoard.putQueen(1,1);
		theBoard.putQueen(3,4);
		
		boolean result = theBoard.queensInSameRowCheck(3,5);
		assertEquals(true, result);
	}
	
	@Test
	public void queensInLeftDiagonals_4queens_QueenInDiagonalByUp() {
		ChessBoard theBoard = new ChessBoard(8);
		theBoard.putQueen(0,0);
		theBoard.putQueen(1,1);
		theBoard.putQueen(3,4);
		
		boolean result1 = theBoard.queensInDiagonalsOnLeft(2,5);
		boolean result2 = theBoard.queensInDiagonalsOnLeft(4,5);
		
		assertEquals(true, result1 & result2);
	}

	@Test
	public void boardFinalCheck_4queens_FinalCheckOfCompleteResult() {
		ChessBoard theBoard = new ChessBoard(4);
		theBoard.putQueen(0, 0);
		theBoard.putQueen(1, 1);
		theBoard.putQueen(1, 2);
		theBoard.putQueen(3, 3);
		boolean thisMustBeFalse = theBoard.boardFinalCheck();
		
		theBoard = new ChessBoard(4);
		theBoard.putQueen(0, 0);
		theBoard.putQueen(2, 2);
		boolean thisAlsoFalse = theBoard.boardFinalCheck();
		
		theBoard = new ChessBoard(4);
		theBoard.putQueen(1, 0);
		theBoard.putQueen(3, 1);
		theBoard.putQueen(0, 2);
		theBoard.putQueen(2, 3);
		boolean thisShouldBeTrue = theBoard.boardFinalCheck();
		
		assertEquals(true, !thisMustBeFalse & !thisAlsoFalse & thisShouldBeTrue);
	}
}
