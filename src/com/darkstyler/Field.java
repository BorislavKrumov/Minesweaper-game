package com.darkstyler;

import java.util.ArrayList;
import java.util.List;

import com.darkstyler.cells.*;
import com.darkstyler.interfaces.IFieldObservable;
import com.darkstyler.interfaces.IFieldObserver;
import java.util.concurrent.ThreadLocalRandom;

public class Field implements IFieldObservable {
	
	private AbstractCell[][] field;
	private int sizeRow;
	private int sizeCol;
	private int mines;
	private int clues;
	private List<IFieldObserver> fos;
	Field(int row, int col, int mines){
		fos=new ArrayList<>();
		this.sizeRow=row; 
		this.sizeCol=col;		
		this.mines=mines;
		this.clues = (row*col) - mines;
		instanciateCellArray(row,col);
		setMines();
		setClues();

	}
	private void instanciateCellArray(int row, int col) {
		field=new AbstractCell[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				field[i][j] = new Clue();
			}
		}
	}

	public AbstractCell getCell(Coordinates c) {	
		return field[c.getRow()][c.getCol()];
	}
	
	public boolean clickCell(Coordinates c) {
		
		if(getCell(c).isMine()) {
			return true;					
		}
		
		else if(getCell(c).isRevealed()) {
			List<Coordinates> n = getNeighborsOfCell(c);
			if(getCell(c).getClue()!=0) {
				for(Coordinates cordinates : n) {
					if(getCell(cordinates).isHidden()) {
						if(clickCell(cordinates)) {
							return true;
						    

						}
					}
				}
			}
			return false;
		}
		else {
			
			clues --;
			getCell(c).clickCell();
			if(getCell(c).getClue()==0) {
				List <Coordinates> neighbors = getNeighborsOfCell(c);
				for(Coordinates n : neighbors) {
					if(getCell(n).isHidden()) {
						clickCell(n);
					}
				}
				
			}
			
			return false;
		}
	}
	
	public void revealAll() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				field[i][j].revealCell();
			}
		}
		updateObservers();
	}
	public StringBuilder toGridString() {
		StringBuilder rtn=new StringBuilder();
		rtn.append("Current Status of Board:");
		rtn.append('\n');
		rtn.append("   ");
		for (int i = 0; i < sizeCol; i++) {
			rtn.append(i);
			rtn.append("  ");
		}
		
		rtn.append('\n');
		
		for (int i = 0; i < field.length; i++) {
			
			rtn.append(i);
			rtn.append("  ");
			for (int j = 0; j < field[i].length; j++) {
				rtn.append(field[i][j].getChar());
				rtn.append("  ");
			}
			rtn.append("\n");
		}
				
		return rtn;
	}
	
	
	private void setMines() {
		for (int i = 0; i < this.mines; i++) {
			//get random cell
			int randRow, randCol;
			do {
				randRow = randomInt(sizeRow); randCol = randomInt(sizeCol);
			}while(getCell(new Coordinates(randRow, randCol)).isMine());
			field[randRow][randCol] = new Mine();
		}
	}
	public boolean isClear() {
		return clues==0;
	}

	private void setClues() {
		for (int i = 0; i < sizeRow; i++) {
			for (int j = 0; j < sizeCol; j++) {
				
				if(!getCell(new Coordinates(i,j)).isMine()){
					List<Coordinates> neighbors = getNeighborsOfCell(new Coordinates(i,j));
					int count=0;
					for (Coordinates c : neighbors) {
						if (getCell(c).isMine()) {
							count++;
						}
					}
					field[i][j] = new Clue(count);
				}
			}
		}
	}
	private int randomInt(int i) {
		return ThreadLocalRandom.current().nextInt(i);
	}
	private List<Coordinates> getNeighborsOfCell(Coordinates origin) {
		List<Coordinates> rtn = new ArrayList<Coordinates>();
		int row = origin.getRow(); int col = origin.getCol();
		
		if((row==0&&col==0)){ 				
			//NorthWest corner
			rtn.add(new Coordinates(row  , col+1));
			rtn.add(new Coordinates(row+1, col  ));
			rtn.add(new Coordinates(row+1, col+1));
		} else if (row==0&&col==sizeCol-1){		
			//NorthEast corner
			rtn.add(new Coordinates(row  , col-1));
			rtn.add(new Coordinates(row+1, col  ));
			rtn.add(new Coordinates(row+1, col-1));
		} else if (row==sizeRow-1&&col==0){		
			//SouthWest corner
			rtn.add(new Coordinates(row  , col+1));
			rtn.add(new Coordinates(row-1, col  ));
			rtn.add(new Coordinates(row-1, col+1));
		} else if (row==sizeRow-1&&col==sizeCol-1){	
			//SouthEast corner
			rtn.add(new Coordinates(row  , col-1));
			rtn.add(new Coordinates(row-1, col  ));
			rtn.add(new Coordinates(row-1, col-1));
		} else if (row==0){				
			//North edge
			rtn.add(new Coordinates(row  , col-1));
			rtn.add(new Coordinates(row  , col+1));
			rtn.add(new Coordinates(row+1, col  ));
			rtn.add(new Coordinates(row+1, col-1));
			rtn.add(new Coordinates(row+1, col+1));
		} else if (row==sizeRow-1){			
			//South edge
			rtn.add(new Coordinates(row  , col-1));
			rtn.add(new Coordinates(row  , col+1));
			rtn.add(new Coordinates(row-1, col  ));
			rtn.add(new Coordinates(row-1, col-1));
			rtn.add(new Coordinates(row-1, col+1));
		} else if (col==0) {				
			//West edge
			rtn.add(new Coordinates(row-1, col  ));
			rtn.add(new Coordinates(row-1, col+1));
			rtn.add(new Coordinates(row  , col+1));
			rtn.add(new Coordinates(row+1, col  ));
			rtn.add(new Coordinates(row+1, col+1));
		} else if (col==sizeCol-1){			
			//East edge
			rtn.add(new Coordinates(row-1, col  ));
			rtn.add(new Coordinates(row-1, col-1));
			rtn.add(new Coordinates(row  , col-1));
			rtn.add(new Coordinates(row+1, col  ));
			rtn.add(new Coordinates(row+1, col-1));
		} else {						
			//middle
			rtn.add(new Coordinates(row-1, col  ));
			rtn.add(new Coordinates(row-1, col-1));
			rtn.add(new Coordinates(row-1, col+1));
			rtn.add(new Coordinates(row  , col-1));
			rtn.add(new Coordinates(row  , col+1));
			rtn.add(new Coordinates(row+1, col  ));
			rtn.add(new Coordinates(row+1, col-1));
			rtn.add(new Coordinates(row+1, col+1));
		}
		return rtn;
	}
	

	@Override 
	public void remFieldObserver(IFieldObserver fo) {
		fos.remove(fo);
	}
	private void updateObservers(){
		for (IFieldObserver fo : fos) {
			fo.update();
		}
	}
	public int getMines() {
		return this.mines;
	}
	@Override
	public void regFieldObserver(IFieldObserver fo) {
		fos.add(fo);	
	}
}
