package com.darkstyler.interfaces;

import com.darkstyler.Field;
import com.darkstyler.Coordinates;

public interface IMinesweeperModel extends IFieldObservable {

	public void newGame(int row, int col, int numMines);
		
	public void clickCell(Coordinates c);

	public Field getMinefield();
	
	public void setControler(IGameController controler);

	public StringBuilder printGrid();

}
