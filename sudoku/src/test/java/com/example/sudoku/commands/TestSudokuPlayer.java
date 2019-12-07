package com.example.sudoku.commands;

import com.example.sudoku.domain.Sudoku;
import com.example.sudoku.utils.Printer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestSudokuPlayer {
    private static final String FILENAME = "sudoku-example.txt";
    private static final Sudoku SUDOKU = TestSudokuPlayerUtils.createSudoku();

    @Test
    public void testPuzzleCreation() {
        SudokuPlayer player = new SudokuPlayer();
        String board = player.createNewPuzzle(FILENAME);
        Assert.assertEquals("Expected board to be correctly built",
                SUDOKU, player.sudoku);
        Assert.assertTrue("Expected board to be correctly printed",
                board.contains(Printer.printBoard(SUDOKU)));
    }

    @Test
    public void testValidPlay() {
        SudokuPlayer player = new SudokuPlayer();
        player.sudoku = SUDOKU;
        player.play(8,8,9);
        Assert.assertEquals("Expect play to successfully change the board",
                9, player.sudoku.getCurrentState()[8][8].getNumber());
    }

    @Test
    public void testInvalidPlay() {
        SudokuPlayer player = new SudokuPlayer();
        player.sudoku = SUDOKU;;
        player.play(9,9,9);
        Assert.assertEquals("Expect invalid play (out of bounds) to have no effect on the board",
                0, player.sudoku.getCurrentState()[8][8].getNumber());

        player.play(8,8,10);
        Assert.assertEquals("Expect invalid play (invalid number) to have no effect on the board",
                0, player.sudoku.getCurrentState()[8][8].getNumber());

    }
}
