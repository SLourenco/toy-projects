package com.example.sudoku.solver;

import com.example.sudoku.domain.Sudoku;
import org.apache.commons.lang3.tuple.Triple;

/**
 * Interface that implements a convention on Sudoku solver strategies
 */
public interface SudokuSolver {
    /**
     * Finds the next valid move
     * @param sudoku sudoku to solve
     * @return triple indicating in order position Y, position X and valid number
     */
    Triple<Integer,Integer,Integer> findValidMove(Sudoku sudoku);
}
