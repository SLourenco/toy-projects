package com.example.sudoku.utils;

import java.util.Arrays;

import com.example.sudoku.domain.Cell;

public class ArrayOperations {

	private ArrayOperations() {
		
	}
	
	public static Cell[][] deepClone(Cell[][] data) {
		return Arrays.stream(data).map(Cell[]::clone).toArray(Cell[][]::new);
	}
}
