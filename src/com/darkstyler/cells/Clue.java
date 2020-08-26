package com.darkstyler.cells;

public class Clue extends AbstractCell {
private final int CLUE;
	
	public Clue(){
		this.CLUE=0;
	}
	
	public Clue(int clue){
		this.CLUE=clue;
	}

	@Override
	public boolean isMine() {
		
		return false;
	}

	@Override
	public char getSymbol() {
		
		if (this.CLUE==0) return ' ';
		else return Integer.toString(CLUE).charAt(0);
	}

	@Override
	public int getClue() {
		return CLUE;
	}
}
