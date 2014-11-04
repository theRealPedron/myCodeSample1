import org.junit.Test;

import junit.framework.TestCase;



public class test_Board extends TestCase{

	@Test
	public void test_goal_board(){
		int blocks[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
		Board board = new Board(blocks);
		assertTrue(board.hamming() == 0);
		assertTrue(board.manhattan() == 0);
		assertTrue(board.isGoal());
		assertTrue(board.equals(new Board(blocks)));
	}
	@Test
	public void test_Hamming_Distance(){
		int blocks1[][] = {{8,8,8,8},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
		Board board1 = new Board(blocks1);
		assertTrue("hamming distance should be 4, but is: " + board1.hamming(),board1.hamming() == 4);
	}
	@Test
	public void test_Manhattan_Distance(){
		int blocks1[][] = {{8,8,8,8},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
		Board board1 = new Board(blocks1);
		assertTrue("manhattan distance should be 10, but is: " + board1.manhattan(),board1.manhattan() == 10);
	}
	@Test
	public void test_isGoal_board(){
		int blocks1[][] = {{8,8,8,8},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
		Board board1 = new Board(blocks1);
		assertTrue(!board1.isGoal());
	}
	public void test_equals(){
		int blocks1[][] = {{8,8,8,8},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
		int blocks[][] = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};

		Board board = new Board(blocks);
		Board board1 = new Board(blocks1);
		assertTrue(board1.equals(new Board(blocks1)));	
		assertTrue(board.equals(new Board(blocks)));
		assertTrue(!board.equals(board1));
	}
}
