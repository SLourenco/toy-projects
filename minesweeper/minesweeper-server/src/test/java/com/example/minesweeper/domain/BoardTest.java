package com.example.minesweeper.domain;


import com.example.minesweeper.util.GameFinishedException;
import com.example.minesweeper.util.InvalidMoveException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class BoardTest {

    @Test
    void canStartNewGame() {
        Map<Difficulty, Integer> difficulties = Map.of(
            Difficulty.BEGINNER, 10,
            Difficulty.INTERMEDIATE, 40,
            Difficulty.EXPERT, 100
        );

        Map<Difficulty, Pair<Integer, Integer>> boardSizes = Map.of(
                Difficulty.BEGINNER, Pair.of(10,10),
                Difficulty.INTERMEDIATE, Pair.of(20, 10),
                Difficulty.EXPERT, Pair.of(20,20)
        );

        for(Map.Entry<Difficulty, Integer> difficulty : difficulties.entrySet()) {
            Board board = new Board(difficulty.getKey());
            Assertions.assertNotNull(board, "Expected new board object to be created");
            Assertions.assertEquals(0, board.getProgress(), "Expected new board to have 0% completed");
            Assertions.assertEquals(difficulty.getValue(), board.getMissingMines(), "Expected all mines to be hidden");
            Assertions.assertNull(board.getGameResult(), "Expected game to be in progress");

            int[][] boardSize = board.getBoardState();
            Pair<Integer, Integer> expectedSizes = boardSizes.get(difficulty.getKey());
            Assertions.assertEquals(expectedSizes.getLeft(), boardSize.length, "Expected horizontal size to have the correct value");
            Assertions.assertEquals(expectedSizes.getRight(), boardSize[0].length, "Expected vertical size to have the correct value");
        }
    }

    @Test
    void canAccessBoardState() {
        Board board = new Board(Difficulty.BEGINNER);
        int[][] state = board.getBoardState();

        for(int[] row : state) {
            for(int element : row) {
                Assertions.assertEquals(-1, element, "Expected all elements to be empty");
            }
        }

        state[0][0] = 10;
        Assertions.assertNotEquals(board.getBoardState()[0][0], state[0][0],
                "Expected directly changing board game state to have no effect");
    }

    @Test
    void newGameGeneratesValidBoard() {
        for(Difficulty difficulty: Difficulty.values()) {
            Board board = new Board(difficulty);
            
            int[][] state = board.getInternalState();

            int countMines = 0;

            for(int[] row : state) {
                for(int element : row) {
                    Assertions.assertNotEquals(-1, element, "Expected all elements to be uncovered");
                    if(element == -10) {
                        ++countMines;
                    }
                }
            }
            Assertions.assertEquals(difficulty.mines, countMines, "Expected number of mines to respect difficulty (" + difficulty.name() + ")");

            for(int x=0; x < difficulty.boardSizeX; x++) {
                for(int y=0; y < difficulty.boardSizeY; y++) {
                    int neighborMines = state[x][y];
                    if(neighborMines < 0) {
                        continue;
                    }

                    int totalNeighborMines = 0;
                    totalNeighborMines += convertToMineQty(x-1, y-1, state, difficulty);
                    totalNeighborMines += convertToMineQty(x, y-1, state, difficulty);
                    totalNeighborMines += convertToMineQty(x+1, y-1, state, difficulty);
                    totalNeighborMines += convertToMineQty(x-1, y, state, difficulty);
                    totalNeighborMines += convertToMineQty(x+1, y, state, difficulty);
                    totalNeighborMines += convertToMineQty(x-1, y+1, state, difficulty);
                    totalNeighborMines += convertToMineQty(x, y+1, state, difficulty);
                    totalNeighborMines += convertToMineQty(x+1, y+1, state, difficulty);

                    Assertions.assertEquals(neighborMines, totalNeighborMines,
                            "Expected number of neighbor mines to be correct.");
                }
            }
        }
    }

    private static int convertToMineQty(int x, int y, int[][] board, Difficulty difficulty) {
        if(x < 0 || x >= difficulty.boardSizeX || y <0 || y >= difficulty.boardSizeY) {
            return 0;
        }

        return board[x][y] == -10 ? 1 : 0;
    }

    @Test
    void newGameGeneratesDifferentGames() {
        for(Difficulty difficulty: Difficulty.values()) {
            Board board1 = new Board(difficulty);
            Board board2 = new Board(difficulty);
            Board board3 = new Board(difficulty);

            int similarities12 = 0;
            int similarities13 = 0;
            int similarities23 = 0;

            for(int x=0; x < difficulty.boardSizeX; x++) {
                for(int y=0; y < difficulty.boardSizeY; y++) {
                    similarities12 += board1.getInternalState()[x][y] == board2.getInternalState()[x][y] ? 1 : 0;
                    similarities13 += board1.getInternalState()[x][y] == board3.getInternalState()[x][y] ? 1 : 0;
                    similarities23 += board2.getInternalState()[x][y] == board3.getInternalState()[x][y] ? 1 : 0;
                }
            }

            int numberOfCells = difficulty.boardSizeX * difficulty.boardSizeY;
            int threshold = (int) (0.8 * numberOfCells);
            Assertions.assertTrue(similarities12 < threshold, "Expected at least 20% differences between board 1 and 2. Instead got " + (1 - (similarities12 / numberOfCells)) * 100 + "%");
            Assertions.assertTrue(similarities13 < threshold, "Expected at least 20% differences between board 1 and 3. Instead got " + (1 - (similarities13 / numberOfCells)) * 100 + "%");
            Assertions.assertTrue(similarities23 < threshold, "Expected at least 20% differences between board 2 and 3. Instead got " + (1 - (similarities23 / numberOfCells)) * 100 + "%");
        }
    }

    @Test
    void canPlayValidMoves() {
        for(Difficulty difficulty : Difficulty.values()) {
            Board board = new Board(difficulty);

            int[][] internalState = board.getInternalState();

            for(int x=0; x < difficulty.boardSizeX; x++) {
                for(int y=0; y < difficulty.boardSizeY; y++) {
                    if(internalState[x][y] != -10) {
                        int result = board.play(x,y);
                        Assertions.assertEquals(result, internalState[x][y], "Expected play to return correct result");
                    }
                }
            }
        }
    }

    @Test
    void cannotPlayInvalidMoves() {
        Board board = new Board(Difficulty.BEGINNER);
        Assertions.assertThrows(
                InvalidMoveException.class,
                () -> board.play(-1, -1),
                "Expected exception when playing outside of board"
        );

        Assertions.assertThrows(
                InvalidMoveException.class,
                () -> board.markFlag(-1, -1),
                "Expected exception when flagging outside of board"
        );
    }

    @Test
    void canMarkFlag() {
        Board board = new Board(Difficulty.BEGINNER);
        board.markFlag(0,0);
        Assertions.assertEquals(Board.FLAG, board.getBoardState()[0][0], "Expected board to be marked with flag");
    }

    @Test
    void canUnMarkFlag() {
        Board board = new Board(Difficulty.BEGINNER);
        board.markFlag(0,0);
        board.markFlag(0,0);
        Assertions.assertEquals(-1, board.getBoardState()[0][0], "Expected board flag to be unmarked");
    }

    @Test
    void gameEndsWithLossOnMine() {
        Difficulty difficulty = Difficulty.BEGINNER;
        Board board = new Board(difficulty);

        int[][] internalState = board.getInternalState();

        Assertions.assertNull(board.getGameResult(), "Expected game to be in progress after starting");

        outerLoop: for(int x=0; x < difficulty.boardSizeX; x++) {
            for(int y=0; y < difficulty.boardSizeY; y++) {
                if(internalState[x][y] == -10) {
                    board.play(x,y);
                    break outerLoop;
                }
            }
        }

        Assertions.assertFalse(board.getGameResult(), "Expected game to be a loss after selecting a mine");

        Assertions.assertThrows(GameFinishedException.class, () -> board.play(0,0), "Expected no valid moves after game finishes");
    }

    @Test
    void gameEndsWithWinOnAllMinesDiscovered() {
        Difficulty difficulty = Difficulty.BEGINNER;
        Board board = new Board(difficulty);

        int[][] internalState = board.getInternalState();

        Assertions.assertNull(board.getGameResult(), "Expected game to be in progress after starting");

        for(int x=0; x < difficulty.boardSizeX; x++) {
            for(int y=0; y < difficulty.boardSizeY; y++) {
                if(internalState[x][y] == -10) {
                    board.markFlag(x,y);
                }
            }
        }

        Assertions.assertTrue(board.getGameResult(), "Expected game to be a win after discovering all mines");

        Assertions.assertThrows(GameFinishedException.class, () -> board.play(0,0), "Expected no valid moves after game finishes");
    }
}
