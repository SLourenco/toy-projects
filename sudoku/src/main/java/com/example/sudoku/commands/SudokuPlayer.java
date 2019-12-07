package com.example.sudoku.commands;

import com.example.sudoku.solver.DepthFirstSolver;
import com.example.sudoku.solver.SudokuSolver;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.example.sudoku.domain.Sudoku;
import com.example.sudoku.domain.SudokuFactory;
import com.example.sudoku.utils.FileLoader;
import com.example.sudoku.utils.Printer;

@ShellComponent
public class SudokuPlayer {
	private static final Logger LOG = LoggerFactory.getLogger(SudokuPlayer.class);
	protected Sudoku sudoku;

    @ShellMethod("Create a new Sudoku Puzzle from a file")
    public String createNewPuzzle(
        @ShellOption String filePathname) {
    	
        String data = new FileLoader().loadFile(filePathname);

        if(StringUtils.isBlank(data)) {
        	return "NO PUZZLE FOUND!!";
		}

        try {
			sudoku = SudokuFactory.createSudoku(data);
		} catch (Exception ex) {
        	return "PUZZLE IS INVALID!!";
		}

        return Printer.printBoard(sudoku) + " \n \n Ready to Start ?";
    }

    @ShellMethod("Place a number")
    public String play(
		@ShellOption int posX,
		@ShellOption int posY,
		@ShellOption int number) {
    	
    	try {
    		sudoku.play(posX, posY, number);
    	} catch (Exception ex) {
    		LOG.trace("Invalid play {} at {},{}. Ex: ", number, posX, posY, ex);
    		return "Invalid play for " + number + " at: (" + posX + "," + posY + ")";
		}
    	
    	return Printer.printBoard(sudoku);
    }

    @ShellMethod("Suggest a move")
	public String suggestMove() {
		SudokuSolver solver = new DepthFirstSolver();
		Triple<Integer, Integer, Integer> move = solver.findValidMove(sudoku);
		return "Valid move at (" + move.getLeft() +","+ move.getMiddle()+"): " + move.getRight();
	}
}