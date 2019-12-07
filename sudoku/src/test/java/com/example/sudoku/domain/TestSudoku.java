package com.example.sudoku.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestSudoku {
	
	private static final int[][] EXPECTED = {
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9}};

	private static final int[][] IMCOMPLETE = {
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,0,6,7,8,9}};

	private static final int[][] INVALID_IN_GROUP = {
			{1,6,4,7,8,2,3,9,5},
			{7,9,5,1,6,3,8,4,2},
			{8,1,3,4,2,5,9,6,7},
			{3,4,2,6,7,9,5,1,8},
			{2,5,9,8,3,1,6,7,4},
			{6,7,8,3,9,4,2,5,1},
			{4,3,6,2,5,7,1,8,9},
			{5,2,1,9,4,8,7,3,6},
			{9,8,7,5,1,6,4,2,3}};

	static final int[][] GAME_VALID = {
			{2,1,4,7,8,9,3,5,6},
			{7,9,5,1,6,3,8,4,2},
			{8,6,3,4,2,5,9,1,7},
			{3,4,2,6,7,1,5,9,8},
			{1,5,9,8,3,2,6,7,4},
			{6,7,8,5,9,4,2,3,1},
			{4,3,6,2,5,7,1,8,9},
			{5,2,1,9,4,8,7,6,3},
			{9,8,7,3,1,6,4,2,5}};

	private static final Cell[][] STATE = createBoard(EXPECTED);
	private static final Cell[][] STATE_INCOMPLETE = createBoard(IMCOMPLETE);
	private static final Cell[][] STATE_INVALID_GROUP = createBoard(INVALID_IN_GROUP);
	private static final Cell[][] STATE_VALID = createBoard(GAME_VALID);
	
	private static Cell[][] createBoard(int[][] data) {
		Cell[][] board = new Cell[9][9];
		for(int row = 0; row < 9; row++) {
			for(int col = 0; col < 9; col++) {
				board[row][col] = new Cell(data[row][col]);
			}
		}
		return board;
	}
	
	@Test
	public void SudokuStateShouldNotBeChangedDirectly() {
		Cell[][] state = STATE.clone();
		Sudoku sudoku = new Sudoku(state);
		state[0][0].number = state[0][0].number + 1;
		Assert.assertNotEquals(
				"Expected the state could not be changed by changing the initial data",
				state[0][0], sudoku.getCurrentState()[0][0].number);
		
		state = sudoku.getCurrentState();
		state[1][1].number = state[1][1].number + 1;
		Assert.assertNotEquals(
				"Expected the state could not be changed by changing the current puzzle state",
				state[1][1], sudoku.getCurrentState()[1][1].number);
	}
	
	@Test
	public void ValidMovesShouldUpdateBoard() {
		Sudoku sudoku = new Sudoku(STATE);
		sudoku.play(0, 0, 9);
		Assert.assertEquals("Expected the value of the cell choosen to be updated",
				9, sudoku.getCurrentState()[0][0].number);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void InvalidMovesShouldThrowException() {
		Sudoku sudoku = new Sudoku(STATE);
		sudoku.play(9, 0, 8);
	}

	@Test
	public void invalidBoardShouldBeMarkedInvalid() {
		Sudoku sudoku = new Sudoku(STATE);
		for(int row = 0; row < 9; row++) {
			for(int col = 0; col < 9; col++) {
				Assert.assertFalse("Expected all cells to be invalid",
						sudoku.getCurrentState()[0][0].isValid());
			}
		}
		Assert.assertFalse("Validate method should return false",
				sudoku.validate());
	}

	@Test
	public void invalidBoardInGroup() {
		Sudoku sudoku = new Sudoku(STATE_INVALID_GROUP);

		Assert.assertFalse("Validate method should return false",
				sudoku.validate());
	}

	@Test
	public void validBoard() {
		Sudoku sudoku = new Sudoku(STATE_VALID);

		Assert.assertTrue("Validate method should return true for valid board",
				sudoku.validate());
	}

	@Test
	public void testIsComplete() {
		Sudoku complete = new Sudoku(STATE);
		Assert.assertTrue("Complete board should be identified as complete",
				complete.isComplete());

		Sudoku incomplete = new Sudoku(STATE_INCOMPLETE);
		Assert.assertFalse("Incomplete board should be identified as incomplete",
				incomplete.isComplete());
	}
}
