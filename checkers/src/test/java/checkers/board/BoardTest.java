package checkers.board;

import checkers.checkersGame.Player;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

@RunWith(JUnit4.class)
public class BoardTest {

    @Test
    public void testBoardCreation() {
        int[][] startingBoard = new int[8][8];

        startingBoard[0][0] = Board.PLAYER1_PIECE;
        startingBoard[2][0] = Board.PLAYER1_PIECE;
        startingBoard[4][0] = Board.PLAYER1_PIECE;
        startingBoard[6][0] = Board.PLAYER1_PIECE;
        startingBoard[1][1] = Board.PLAYER1_PIECE;
        startingBoard[3][1] = Board.PLAYER1_PIECE;
        startingBoard[5][1] = Board.PLAYER1_PIECE;
        startingBoard[7][1] = Board.PLAYER1_PIECE;
        startingBoard[0][2] = Board.PLAYER1_PIECE;
        startingBoard[2][2] = Board.PLAYER1_PIECE;
        startingBoard[4][2] = Board.PLAYER1_PIECE;
        startingBoard[6][2] = Board.PLAYER1_PIECE;

        startingBoard[1][7] = Board.PLAYER2_PIECE;
        startingBoard[3][7] = Board.PLAYER2_PIECE;
        startingBoard[5][7] = Board.PLAYER2_PIECE;
        startingBoard[7][7] = Board.PLAYER2_PIECE;
        startingBoard[0][6] = Board.PLAYER2_PIECE;
        startingBoard[2][6] = Board.PLAYER2_PIECE;
        startingBoard[4][6] = Board.PLAYER2_PIECE;
        startingBoard[6][6] = Board.PLAYER2_PIECE;
        startingBoard[1][5] = Board.PLAYER2_PIECE;
        startingBoard[3][5] = Board.PLAYER2_PIECE;
        startingBoard[5][5] = Board.PLAYER2_PIECE;
        startingBoard[7][5] = Board.PLAYER2_PIECE;

        Board board = new Board();

        List<Piece> player1Piece = board.getPieces(Player.PLAYER1);
        List<Piece> player2Piece = board.getPieces(Player.PLAYER2);

        Assert.assertEquals("Expected player 1 to have 12 pieces",
                12, player1Piece.size());
        Assert.assertEquals("Expected both players to have the same number of pieces",
                player1Piece.size(), player2Piece.size());

        Assert.assertArrayEquals("Expected starting state to be correct",
                startingBoard, board.board);
    }

    @Test
    public void testPiecesByPlayer() {
        Board board = new Board();
        board.board = new int[8][8];
        board.board[0][0] = Board.PLAYER1_PIECE;
        board.board[1][0] = Board.PLAYER1_PIECE;
        board.board[0][1] = Board.PLAYER1_PIECE;
        board.board[1][1] = Board.PLAYER1_PIECE;

        board.board[2][2] = Board.PLAYER2_PIECE;
        board.board[2][0] = Board.PLAYER2_PIECE;
        board.board[0][2] = Board.PLAYER2_PIECE;

        List<Piece> player1Pieces = board.getPieces(Player.PLAYER1);
        Assert.assertEquals("Expected number of player 1 pieces to be correct",
                4, player1Pieces.size());

        for(Piece p : player1Pieces) {
            Assert.assertTrue(
                    (p.getXCoord() == 0 && p.getYCoord()==0) ||
                            (p.getXCoord() == 1 && p.getYCoord()==0) ||
                            (p.getXCoord() == 0 && p.getYCoord()==1) ||
                            (p.getXCoord() == 1 && p.getYCoord()==1)
            );
        }

        List<Piece> player2Pieces = board.getPieces(Player.PLAYER2);
        Assert.assertEquals("Expected number of player 2 pieces to be correct",
                3, player2Pieces.size());
        for(Piece p : player2Pieces) {
            Assert.assertTrue(
                    (p.getXCoord() == 2 && p.getYCoord()==2) ||
                            (p.getXCoord() == 2 && p.getYCoord()==0) ||
                            (p.getXCoord() == 0 && p.getYCoord()==2)
            );
        }
    }
}
