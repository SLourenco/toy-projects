package com.example.sudoku.commands;

import com.example.sudoku.domain.Sudoku;
import com.example.sudoku.domain.SudokuFactory;

class TestSudokuPlayerUtils {
    private static final String data =
            "1,2,3,4,5,6,7,8,9," +
            "9,8,7,6,5,4,3,2,1," +
            "0,0,0,0,0,0,0,0,0," +
            "0,0,0,0,0,0,0,0,0," +
            "0,0,0,0,0,0,0,0,0," +
            "0,0,0,0,0,0,0,0,0," +
            "0,0,0,0,0,0,0,0,0," +
            "0,0,0,0,0,0,0,0,0," +
            "0,0,0,0,0,0,0,0,0";

    static Sudoku createSudoku() {
        return SudokuFactory.createSudoku(data);
    }
}
