package com.example.minesweeper.util;

public class InvalidMoveException extends RuntimeException {
    private int x;
    private int y;

    public InvalidMoveException(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
