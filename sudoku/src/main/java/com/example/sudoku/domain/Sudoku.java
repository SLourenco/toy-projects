package com.example.sudoku.domain;

import org.springframework.util.Assert;

import com.example.sudoku.utils.ArrayOperations;

public class Sudoku {
	
	private Cell[][] data;
	
	protected Sudoku(Cell[][] data) {
		Assert.notNull(data, "Puzzle cannot be null");
		Assert.notEmpty(data, "Puzzle cannot be completly empty");
		this.data = ArrayOperations.deepClone(data);
		validate();
	}

	public Sudoku(Sudoku sudoku) {
		this(sudoku.data);
	}
	
	/**
	 * @return copy of the current board state
	 */
	public Cell[][] getCurrentState() {
		return ArrayOperations.deepClone(data);
	}
	
	/**
	 * Places a number at a position specified
	 * @param x x axis position
	 * @param y y axis position
	 * @param number number to play
	 * @return resulting state of the board
	 */
	public Cell[][] play(int x, int y, int number) {
		Assert.isTrue(x < 9 && x >= 0, "Invalid position X");
		Assert.isTrue(y < 9 && y >= 0, "Invalid position Y");
		Assert.isTrue(number <= 9 && number > 0, "Invalid number");
		
		data[x][y] = new Cell(number);
		validate();
		return getCurrentState();
	}
	
	public boolean validate() {
		boolean isBoardValid = true;
		for(int row = 0; row < 9; row++) {
			for(int col = 0; col < 9; col++) {
				boolean isValid = isValidEntry(row, col, data[row][col]);
				data[row][col].setValid(isValid);
				isBoardValid &= isValid;
			}
		}
		return isBoardValid;
	}

	private boolean isValidEntry(int row, int col, Cell cell) {
		if(cell.number == 0) return true;

		// valid in row
		for(int r = 0; r < 9; r++) {
			if (row == r) continue;

			if(data[r][col].number == cell.number) {
				return false;
			}
		}

		// valid in column
		for(int c = 0; c < 9; c++) {
			if(col == c) continue;

			if(data[row][c].number == cell.number) {
				return false;
			}
		}

		// valid in group
		int group_row = row / 3;
		int group_col = col / 3;
		for(int gr = group_row*3; gr < (group_row+1)*3; gr++) {
			for(int gc = group_col*3; gc < (group_col+1)*3; gc++) {
				if(gr == row && gc == col) continue;

				if(data[gr][gc].number == cell.number) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Sudoku)) {
			return false;
		}

		Cell[][] dataToCompare = ((Sudoku)obj).data;
		for(int row = 0; row < dataToCompare.length; row++) {
			for(int col = 0; col < dataToCompare[row].length; col++) {
				if(!dataToCompare[row][col].equals(data[row][col])) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean isComplete() {
		for( Cell[] row : data) {
			for( Cell col : row) {
				if(col.getNumber() == 0) {
					return false;
				}
			}
		}
		return true;
	}
}
