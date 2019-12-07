package com.example.minesweeper.domain;

import com.example.minesweeper.util.GameFinishedException;
import com.example.minesweeper.util.InvalidMoveException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
    public static final int MINE = -10;
    public static final int FLAG = -5;

    private int missingMines;
    private final int[][] boardState;
    private final int[][] internalState;
    private Boolean status;

    public Board(Difficulty difficulty) {
        this.missingMines = difficulty.mines;
        boardState = new int[difficulty.boardSizeX][difficulty.boardSizeY];
        Arrays.stream(boardState).forEach(
            row -> Arrays.fill(row, -1)
        );
        internalState = generateBoard(difficulty);
    }

    public int getProgress() {
        return 0;
    }

    public int getMissingMines() {
        return missingMines;
    }

    public Boolean getGameResult() {
        return status;
    }

    public int[][] getBoardState() {
        return Arrays.stream(boardState).map(int[]::clone).toArray(int[][]::new);
    }

    protected int[][] getInternalState() {
        return Arrays.stream(internalState).map(int[]::clone).toArray(int[][]::new);
    }

    public int play(int x, int y) {
        if(status!=null) {
            throw new GameFinishedException();
        }

        if(x < 0 || y < 0 || x > boardState.length || y > boardState[0].length) {
            throw new InvalidMoveException(x, y);
        }

        int result = internalState[x][y];
        boardState[x][y] = result;

        if(result == MINE) {
            status = false;
        }

        return result;
    }

    public void markFlag(int x, int y) {
        if(status!=null) {
            throw new GameFinishedException();
        }

        if(x < 0 || y < 0 || x > boardState.length || y > boardState[0].length) {
            throw new InvalidMoveException(x, y);
        }

        if(boardState[x][y] == FLAG) {
            if(internalState[x][y] == MINE) {
                ++missingMines;
            }
            boardState[x][y] = -1;
            
        } else {
            if(internalState[x][y] == MINE) {
                --missingMines;

                if(missingMines <= 0) {
                    status = true;
                }
            }

            boardState[x][y] = FLAG;
        }
    }

    private static int[][] generateBoard(Difficulty difficulty) {
        int[][] board = new int[difficulty.boardSizeX][difficulty.boardSizeY];

        List<String> used = new ArrayList<>();
        Random random = new Random();

        for(int idx=0; idx < difficulty.mines; idx++) {
            Pair<Integer, Integer> mine = generateUniqueMine(difficulty, used, random);
            board[mine.getLeft()][mine.getRight()] = MINE;
        }


        for(int x = 0; x < difficulty.boardSizeX; x++) {
            for(int y = 0; y < difficulty.boardSizeY; y++) {
                board[x][y] = countNeighborMines(x,y,board,difficulty);
            }
        }

        return board;
    }

    private static int countNeighborMines(int x, int y, int[][] board, Difficulty difficulty) {
        if(board[x][y] == MINE) {
            return MINE;
        }

        int totalNeighborMines = 0;
        totalNeighborMines += convertToMineQty(x-1, y-1, board, difficulty);
        totalNeighborMines += convertToMineQty(x, y-1, board, difficulty);
        totalNeighborMines += convertToMineQty(x+1, y-1, board, difficulty);
        totalNeighborMines += convertToMineQty(x-1, y, board, difficulty);
        totalNeighborMines += convertToMineQty(x+1, y, board, difficulty);
        totalNeighborMines += convertToMineQty(x-1, y+1, board, difficulty);
        totalNeighborMines += convertToMineQty(x, y+1, board, difficulty);
        totalNeighborMines += convertToMineQty(x+1, y+1, board, difficulty);

        return totalNeighborMines;
    }

    private static int convertToMineQty(int x, int y, int[][] board, Difficulty difficulty) {
        if(x < 0 || x >= difficulty.boardSizeX || y <0 || y >= difficulty.boardSizeY) {
            return 0;
        }

        return board[x][y] == MINE ? 1 : 0;
    }

    private static Pair<Integer, Integer> generateUniqueMine(Difficulty difficulty, List<String> used, Random random) {
        int mineX = random.nextInt(difficulty.boardSizeX);
        int mineY = random.nextInt(difficulty.boardSizeY);

        String mineCode = mineX + "," + mineY;

        if(used.contains(mineCode)) {
            return generateUniqueMine(difficulty, used, random);
        }

        used.add(mineCode);
        return Pair.of(mineX, mineY);
    }
}
