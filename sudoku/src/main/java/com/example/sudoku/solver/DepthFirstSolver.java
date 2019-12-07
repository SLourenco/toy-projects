package com.example.sudoku.solver;

import com.example.sudoku.domain.Cell;
import com.example.sudoku.domain.Sudoku;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a Depth First Search (DFS) brute force strategy for a Sudoku Solver
 * Algorithm:
 *  -- If puzzle filled - no valid moves
 *  -- Find empty cell; if no more empty cells, board is solved. Select first used move and return it
 *  ---- List valid moves for cell
 *  ------ Select not attempted move and execute it
 *  ------ Validate board, if invalid, discard move and repeat; if valid, find new empty cell and repeat
 *
 */
public class DepthFirstSolver implements SudokuSolver {

    @Override
    public Triple<Integer, Integer, Integer> findValidMove(Sudoku sudoku) {
        if(sudoku.isComplete()) {
            return null;
        }
        Pair<Integer,Integer> emptyCell = findEmptyCell(sudoku.getCurrentState());
        List<Triple<Integer,Integer,Integer>> possibleMoves = findPossibleMoves(emptyCell);

        for(Triple<Integer,Integer,Integer> move : possibleMoves) {
            Sudoku testSudoku = new Sudoku(sudoku);
            boolean validNextMove = solveNextMove(testSudoku, move);
            if(validNextMove) {
                return Triple.of(move.getLeft(),move.getMiddle(),move.getRight());
            }
        }

        return null; // no valid moves possible
    }

    private boolean solveNextMove(Sudoku sudoku, Triple<Integer,Integer,Integer> move) {
        sudoku.play(move.getLeft(), move.getMiddle(), move.getRight());
        if(sudoku.validate())
        {
            Pair<Integer,Integer> emptyCell = findEmptyCell(sudoku.getCurrentState());
            if(emptyCell==null) {
                return true;
            }

            List<Triple<Integer,Integer,Integer>> possibleMoves = findPossibleMoves(emptyCell);
            for(Triple<Integer,Integer,Integer> nextMove : possibleMoves) {
                Sudoku testSudoku = new Sudoku(sudoku);
                boolean validNextMove = solveNextMove(testSudoku, nextMove);
                if(validNextMove) {
                    return true;
                }
            }
        }

        return false;
    }

    Pair<Integer, Integer> findEmptyCell(Cell[][] board) {
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[0].length; col++) {
               if(board[row][col].getNumber() == 0) {
                   return Pair.of(row, col);
               }
            }
        }

        return null;
    }

    List<Triple<Integer, Integer, Integer>> findPossibleMoves(Pair<Integer, Integer> emptyCell) {
        int[] validNumbers = {1,2,3,4,5,6,7,8,9};
        List<Triple<Integer, Integer, Integer>> possibleMoves = new ArrayList<>();
        for(int number : validNumbers) {
            possibleMoves.add(Triple.of(emptyCell.getLeft(), emptyCell.getRight(), number));
        }
        return possibleMoves;
    }
}
