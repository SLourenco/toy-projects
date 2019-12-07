package com.example.sudoku.solver;

import com.example.sudoku.domain.Cell;
import com.example.sudoku.domain.Sudoku;

class TestSolverUtils extends Sudoku {

    static final int[][] EASY_GAME_MISSING_ONE_VALUE = {
            {0,1,4,7,8,9,3,5,6},
            {7,9,5,1,6,3,8,4,2},
            {8,6,3,4,2,5,9,1,7},
            {3,4,2,6,7,1,5,9,8},
            {1,5,9,8,3,2,6,7,4},
            {6,7,8,5,9,4,2,3,1},
            {4,3,6,2,5,7,1,8,9},
            {5,2,1,9,4,8,7,6,3},
            {9,8,7,3,1,6,4,2,5}};

    static final int[][] EASY_GAME_MISSING_ANOTHER_VALUE = {
            {2,1,4,7,8,0,3,5,6},
            {7,9,5,1,6,3,8,4,2},
            {8,6,3,4,2,5,9,1,7},
            {3,4,2,6,7,1,5,9,8},
            {1,5,9,8,3,2,6,7,4},
            {6,7,8,5,9,4,2,3,1},
            {4,3,6,2,5,7,1,8,9},
            {5,2,1,9,4,8,7,6,3},
            {9,8,7,3,1,6,4,2,5}};

    static final int[][] EASY_GAME = {
            {0,0,4,0,8,0,3,0,0},
            {0,0,0,0,0,3,0,4,2},
            {8,0,0,4,0,5,9,0,7},
            {3,0,2,0,7,0,5,0,8},
            {0,5,0,0,0,0,0,7,0},
            {6,0,8,0,9,0,2,0,1},
            {4,0,6,2,0,7,0,0,9},
            {5,2,0,9,0,0,0,0,0},
            {0,0,7,0,1,0,4,0,0}};

    static final int[][] EASY_GAME_SOLVED = {
            {2,1,4,7,8,9,3,5,6},
            {7,9,5,1,6,3,8,4,2},
            {8,6,3,4,2,5,9,1,7},
            {3,4,2,6,7,1,5,9,8},
            {1,5,9,8,3,2,6,7,4},
            {6,7,8,5,9,4,2,3,1},
            {4,3,6,2,5,7,1,8,9},
            {5,2,1,9,4,8,7,6,3},
            {9,8,7,3,1,6,4,2,5}};

    TestSolverUtils(Cell[][] data) {
        super(data);
    }

    static Cell[][] createBoard(int[][] data) {
        Cell[][] board = new Cell[9][9];
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                board[row][col] = new Cell(data[row][col]);
            }
        }
        return board;
    }

    static Sudoku createSudoku(int[][] data) {
        return new TestSolverUtils(createBoard(data));
    }
}
