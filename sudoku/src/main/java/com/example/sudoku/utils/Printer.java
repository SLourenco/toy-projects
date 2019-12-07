package com.example.sudoku.utils;

import com.example.sudoku.domain.Cell;
import com.example.sudoku.domain.Sudoku;

public class Printer {
	private Printer() {
		
	}
	
	public static String printBoard(Sudoku sudoku) {
		Cell[][] data = sudoku.getCurrentState();
		StringBuilder result = new StringBuilder();
		for(Cell[] row : data) {
			for(Cell col : row) {
				result.append(printCell(col));
				result.append(", ");
			}
			result.append("\n");
		}
		return result.toString();
	}
	
	private static String printCell(Cell cell) {
		StringBuilder strBuilder = new StringBuilder();
		if(!cell.isValid()) {
			strBuilder.append(Colors.RED.getCode());
		}
		strBuilder.append(cell.getNumber());
		strBuilder.append(Colors.RESET.getCode());
		
		return strBuilder.toString();
	}
}
