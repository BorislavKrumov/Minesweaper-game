package com.darkstyler.textInterface;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TUIHelper {
	private static final int DEFAULT_CONSOLESIZE = 80;	
	private final int CONSOLESIZE;
	
	private InputStream in;
	private PrintStream out;
	private Scanner sc;
	
	
	public TUIHelper() {
		this(System.in,System.out,DEFAULT_CONSOLESIZE);
	}	
	
	public TUIHelper(InputStream in, PrintStream out) {
		this(in,out,DEFAULT_CONSOLESIZE);
	}
	
	public TUIHelper(InputStream in, PrintStream out, int size) {
		this.in=in;
		this.out=out;
		this.CONSOLESIZE=size;
		sc=new Scanner(this.in);
	}

	public void printFullLine(char c) {
		for(int i = 0;i<CONSOLESIZE;i++){
			out.print(c);
		}
		out.println();
	}

	public void wrappedPrint(char c, String text) {

		String print = "";
		int whitespace = CONSOLESIZE/2-text.length()/2-1;
		
		print += c;
		print += addWhitespace(whitespace);
		print += text;
		print += addWhitespace(whitespace);
		print += c;
		
		out.println(print);
	}
	
	private String addWhitespace(int whitespace){
		String rtn="";
		for(int i=0;i<whitespace;i++){
			rtn += " ";
		}
		return rtn;
	}

	public void bottomPrint(String string) {
		
		out.println(string);
		
	}

	public String inputPrompt(String string) {
		out.print(string);
		return sc.nextLine();
	}

	public int getConsoleSize() {
		return CONSOLESIZE;
	}

	
	
	public int nextInt(int min, int max) {
		int rtn = 0;
		boolean illegalArg = true;
		String difficultyMessage =  String.join("\n"
		         , "Enter the Difficulty Level,"
		         , "Press 0 for BEGINNER (9*9 Cells and 10 mines)"
		         , "Press 1 for INTERMEDIATE (16*16 Cells and 40 mines),"
		         , "Prees 2 for ADVANCED (24*24 Cells and 99 mines),"
		         ,""
		        
		);
		do {
			try {
				rtn = Integer.parseInt(this.inputPrompt(difficultyMessage));
				if (rtn<min||rtn>max) throw new IllegalArgumentException("Please enter a number within the valid range");
				illegalArg = false;
			} catch (IllegalArgumentException e) {
				this.bottomPrint("Ivalid input, "+e.getMessage());
			}
		} while (illegalArg);
		return rtn;
	}
	public void print(StringBuilder intput) {

		out.print(intput);
		
	}
}
