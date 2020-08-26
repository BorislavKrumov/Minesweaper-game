package com.darkstyler;

import com.darkstyler.interfaces.IGameController;
import com.darkstyler.interfaces.IMinesweeperModel;
import com.darkstyler.textInterface.TUIController;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IMinesweeperModel game = new Minesweeper(); 

		IGameController ui = new TUIController(System.in, System.out, game);
		
		ui.newGame();

	}

}
