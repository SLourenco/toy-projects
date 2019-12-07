package com.example.minesweeper.domain;

public enum Difficulty {
    BEGINNER(10, 10, 10),
    INTERMEDIATE(40, 20, 10),
    EXPERT(100, 20, 20);

    int mines;
    int boardSizeX;
    int boardSizeY;

    Difficulty(int mines, int boardSizeX, int boardSizeY) {
        this.mines = mines;
        this.boardSizeX = boardSizeX;
        this.boardSizeY = boardSizeY;
    }
}
