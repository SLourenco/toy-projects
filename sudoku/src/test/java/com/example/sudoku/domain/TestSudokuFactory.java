package com.example.sudoku.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestSudokuFactory {

	private static final String STATE = 
			"1,2,3,4,5,6,7,8,9," +
			"1,2,3,4,5,6,7,8,9," +
			"1,2,3,4,5,6,7,8,9," +
			"1,2,3,4,5,6,7,8,9," +
			"1,2,3,4,5,6,7,8,9," +
			"1,2,3,4,5,6,7,8,9," +
			"1,2,3,4,5,6,7,8,9," +
			"1,2,3,4,5,6,7,8,9," +
			"1,2,3,4,5,6,7,8,9";
	
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
	
	@Test
	public void ShouldCorrectlyConvertData() {
		Cell[][] sudoku = SudokuFactory.parseData(STATE);
		for(int row = 0; row < sudoku.length; row++) {
			for(int col = 0; col < sudoku[0].length; col++) {
				Assert.assertEquals("Expected the convert state to have the correct values",
						EXPECTED[row][col], sudoku[row][col].getNumber());
			}
		}
		
	}
}
