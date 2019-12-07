package com.example.sudoku.domain;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SudokuFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(SudokuFactory.class);
	
	private SudokuFactory() {
		
	}
	
	/**
	 * Builds a new sudoku object ready to start the game
	 * @param data starting state of the sudoku. 
	 * 	String of the type 0,1,2,3,4,5,1,... which represents the initial state.
	 * 	If the number is 0, the position is considered empty. 
	 * @return sudoku object created, with the initial state filled
	 */
	public static Sudoku createSudoku(String data) {
		return new Sudoku(parseData(data));
	}
	
	protected static Cell[][] parseData(String data) {
		if(StringUtils.isBlank(data)) {
			return new Cell[9][9];
		}
		
		Cell[][] sudoku = new Cell[9][9];
		String[] values = data.split(",");
		int row = 0;
		int col = 0;
		
		LOG.debug("Starting to build new data. Data: {}", data);
		
		for(int idx = 0; idx < values.length; idx++) {
			LOG.trace("row: {}; col: {}", row, col);
			sudoku[row][col] = new Cell(Integer.parseInt(values[idx].trim()));
			
			if(col == 8) {
				row ++;
				col = 0;
			} else {
				col ++;
			}
		}
		
		return sudoku;
	}

}
