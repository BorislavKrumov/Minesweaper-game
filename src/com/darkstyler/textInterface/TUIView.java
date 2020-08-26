package com.darkstyler.textInterface;

import java.io.InputStream;
import java.io.PrintStream;

import com.darkstyler.Coordinates;

public class TUIView {
private TUIHelper ui;
	
	public TUIView(InputStream in, PrintStream out){
		
		ui=new TUIHelper(in, out);
	} 	 
    
	
	public int getDificulty(int maxDificulty) {
		return ui.nextInt(0, maxDificulty);
	}


	public void print(StringBuilder grid) {

		ui.print(grid);
		
	}

	public void displayLoseGameOver() {
        ui.bottomPrint("You lost!");
		
	}

	public void displayWinGameOver() {

		ui.bottomPrint("Congrats, you win!");
	}



	public void badInput(String string) {

		ui.bottomPrint("Error: " + string);
	}

	public Coordinates getLoc(int maxRow, int maxCol) {
		boolean illegalArg = true;
		int row=0;
		int col=0;
		do {
			try {
				String in = ui.inputPrompt("Enter your move , (row, column) ->");
				String[] cordinates = in.split(" ");
				row = Integer.parseInt(cordinates[0]);
				col = Integer.parseInt(cordinates[1]);
				if(row <maxRow && col < maxCol) {
					illegalArg=false;
				}
				else 
				badInput("Invalid input, try again.");
			}
			catch(IllegalArgumentException e) {
				badInput(" " +e);
			}
		}while(illegalArg);
		return new Coordinates(row, col);
		
	}

}
