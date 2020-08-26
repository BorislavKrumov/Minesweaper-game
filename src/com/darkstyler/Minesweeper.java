package com.darkstyler;

import java.util.ArrayList;

import com.darkstyler.interfaces.IFieldObserver;
import com.darkstyler.interfaces.IGameController;
import com.darkstyler.interfaces.IMinesweeperModel;

public class Minesweeper implements IMinesweeperModel, IFieldObserver {
	private Field mineField;
	private IGameController controler;
	private ArrayList<IFieldObserver> fos;
	private int copyRow;
	private int copyCol;
    private int copyMines;
	private int moves = 0;
	public Minesweeper() {
		fos = new ArrayList<>();
		
	}
	
	public void setControler(IGameController controler){
		this.controler = controler;
	}

	@Override
	public void newGame(int row, int col, int numMines) {
		mineField = instanciateNewField(row,col,numMines);
		this.copyRow = row;
		this.copyCol = col;
		this.copyMines = numMines;
	}
	
	@Override
	public void clickCell(Coordinates c) {

		if (mineField.clickCell(c)) {
			if(moves ==0) {
				newGame(copyRow,copyCol,copyMines);
				return;
			}
			gameLose();
		}else if (fieldIsClear()){
			gameWin();
		} else {
			update();
		}
		moves++;
	}

	private Field instanciateNewField(int row, int col, int numMines) {
		Field rtn = new Field(row, col, numMines);
		rtn.regFieldObserver(this);
		return rtn;
	}
	private boolean fieldIsClear() {
		return mineField.isClear();
	}
	
	private void gameWin() {
		mineField.revealAll();
		controler.gameWin();
		
	}

	private void gameLose() {
		
		mineField.revealAll();
		controler.gameLose();
	}


	public StringBuilder printGrid() {
		
		return mineField.toGridString();
	}

	@Override
	public Field getMinefield() {
		
		return mineField;
	}

	@Override
	public void regFieldObserver(IFieldObserver fo) {
		fos.add(fo);
	}

	@Override
	public void remFieldObserver(IFieldObserver fo) {
		fos.remove(fo);
	}

	@Override
	public void update() {
		for (IFieldObserver fo : fos) {
			fo.update();
		}
	}

	
}
