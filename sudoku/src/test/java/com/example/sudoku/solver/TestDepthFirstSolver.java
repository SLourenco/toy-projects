package com.example.sudoku.solver;

import com.example.sudoku.domain.Cell;
import com.example.sudoku.domain.Sudoku;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class TestDepthFirstSolver {

    @Test
    public void completePuzzle() {
        DepthFirstSolver solver = new DepthFirstSolver();
        Sudoku solvedBoard = TestSolverUtils.createSudoku(TestSolverUtils.EASY_GAME_SOLVED);
        Triple<Integer, Integer, Integer> move = solver.findValidMove(solvedBoard);
        Assert.assertNull("No valid moves should exist for a valid board",
                move);
    }

    @Test
    public void incompletePuzzleReturnsMove() {
        DepthFirstSolver solver = new DepthFirstSolver();
        Sudoku board = TestSolverUtils.createSudoku(TestSolverUtils.EASY_GAME);
        Triple<Integer, Integer, Integer> move = solver.findValidMove(board);
        Assert.assertNotNull("Move should be returned for incomplete puzzle",
                move);
        Assert.assertTrue("Move should be in board",
                move.getLeft() < 9 && move.getLeft() >= 0 && move.getMiddle() <9 && move.getMiddle() >= 0);
        Assert.assertTrue("Move should have valid number",
                move.getRight() < 9 && move.getRight() >= 0);
    }

    @Test
    public void findEmptyCell() {
        DepthFirstSolver solver = new DepthFirstSolver();

        List<Pair<Integer, Integer>> coordinates = new ArrayList<>();
        coordinates.add(Pair.of(0,0));
        coordinates.add(Pair.of(5,2));
        coordinates.add(Pair.of(7,3));

        for(Pair<Integer,Integer> coord : coordinates) {
            Cell[][] board = TestSolverUtils.createBoard(TestSolverUtils.EASY_GAME_SOLVED);
            board[coord.getLeft()][coord.getRight()] = new Cell(0);
            Pair<Integer, Integer> emptyCell = solver.findEmptyCell(board);
            Assert.assertEquals("Expected empty cell to be the only one possible (Y)",
                    (int) coord.getLeft(), (int) emptyCell.getLeft());
            Assert.assertEquals("Expected empty cell to be the only one possible (X)",
                    (int) coord.getRight(), (int) emptyCell.getRight());
        }
    }

    @Test
    public void testSinglePossibilityValidMove() {
        DepthFirstSolver solver = new DepthFirstSolver();
        Sudoku board = TestSolverUtils.createSudoku(TestSolverUtils.EASY_GAME_MISSING_ONE_VALUE);
        Sudoku solvedBoard = TestSolverUtils.createSudoku(TestSolverUtils.EASY_GAME_SOLVED);
        Triple<Integer, Integer, Integer> move = solver.findValidMove(board);
        Assert.assertEquals("Valid move is in first cell (Y)",
                0, (int) move.getLeft());
        Assert.assertEquals("Valid move is in first cell (X)",
                0, (int) move.getMiddle());
        Assert.assertEquals("Valid move has correct number",
                solvedBoard.getCurrentState()[0][0].getNumber(), (int) move.getRight());
    }

    @Test
    public void testValidMove() {
        DepthFirstSolver solver = new DepthFirstSolver();
        Sudoku board = TestSolverUtils.createSudoku(TestSolverUtils.EASY_GAME);
        Sudoku solvedBoard = TestSolverUtils.createSudoku(TestSolverUtils.EASY_GAME_SOLVED);
        Triple<Integer, Integer, Integer> move = solver.findValidMove(board);
        Assert.assertEquals("Valid move is new move",
                0, board.getCurrentState()[move.getLeft()][move.getMiddle()].getNumber());
        Assert.assertEquals("Valid move is valid",
                solvedBoard.getCurrentState()[move.getLeft()][move.getMiddle()].getNumber(), (int)move.getRight());
    }
}
