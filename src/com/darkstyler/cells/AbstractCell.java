package com.darkstyler.cells;

public abstract class AbstractCell {
public enum CellState
{
	HIDDEN,REVEALED;
}
private CellState state;
public AbstractCell() {
	setState(CellState.HIDDEN);
}
public CellState getState() {
	return state;
}
public void setState(CellState state) {
	this.state = state;
}

public char getChar() {
	if (state==CellState.HIDDEN)
		return '-';
	else
		return this.getSymbol();
}
public void clickCell(){
		setState(CellState.REVEALED);
}

/*
 * end of game reveal
 */
public void revealCell() {

	this.setState(CellState.REVEALED);
	
}

public boolean isHidden() {
	return state==CellState.HIDDEN;
}


public boolean isRevealed() {
	return state==CellState.REVEALED;
}

abstract public boolean isMine();

abstract public char getSymbol();
abstract public int getClue();
}
