package com.example.sudoku.utils;

public enum Colors {
	RESET("\u001B[0m"),
	RED("\u001B[31m"),
	GREEN("\u001B[32m");
	
	private String code;
	
	private Colors(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
