package com.darkstyler.textInterface;

import java.io.InputStream;
import java.io.PrintStream;

import com.darkstyler.interfaces.IGameController;
import com.darkstyler.interfaces.IMinesweeperModel;
public class TUIController implements IGameController{
	private IMinesweeperModel game;
	private TUIView ui;
	private boolean playing;
	private int x,y,mine;
	public TUIController(InputStream in, PrintStream out, IMinesweeperModel game) {
		this.ui = new TUIView(in, out);
		this.game = game;
		this.game.setControler(this);
	}


	@Override	
	public void newGame(){
		this.setDificulty();
		this.startGame();
	}


	@Override
	public void gameWin() {
		
		
		displayGrid();
		ui.displayWinGameOver();
		playing = false;
	}

	@Override
	public void gameLose() {
		
		
		displayGrid();
		ui.displayLoseGameOver();
		playing = false;
	}

   private void setDificulty() {
	   
	   int dificulty;
	   dificulty=ui.getDificulty(2);
	   if(dificulty == 0) {
		   
		   x=9;
		   y=9;
		   mine = 10;
		   game.newGame(x, y, mine);
	   }
	   if(dificulty == 1) {
		   x=16;
		   y=16;
		   mine=40;
		   game.newGame(x, y, mine);
	   }
	   if(dificulty == 2) {
		   x=24;
		   y=24;
		   mine = 99;
		   game.newGame(x, y, mine);
	   }
	   
   }
	private void startGame() {
		playing = true;
		while (playing){
			displayGrid();			
			game.clickCell(ui.getLoc(x,y));
		}
	}


	private void displayGrid() {
	
		ui.print(game.printGrid());
	}
}
