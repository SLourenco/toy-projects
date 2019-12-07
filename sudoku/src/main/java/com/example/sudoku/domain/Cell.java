package com.example.sudoku.domain;

public class Cell {
	protected int number = 0;
	private boolean valid = true;
	
	public Cell(int number) {
		this.number = number;
	}
	
	public boolean isValid() {
		return valid;
	}

	protected void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public int getNumber() {
		return number;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Cell)) {
			return false;
		}

		return ((Cell)obj).number == number;
	}
}
