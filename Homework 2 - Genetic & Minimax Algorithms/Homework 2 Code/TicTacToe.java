package Homework2;

import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {


	final static int BOARD_SIZE = 3; // used to set size of board
	static char[][] board = new char[BOARD_SIZE][BOARD_SIZE]; // creates board
	
	// method for running the game
	public static void runGame() {
		boolean isHumanTurn = true; // records whose turn it is
		int winner = 0; // records whose the winner 0 = tie, 1 = user, -1 = ai
		Scanner scan = new Scanner(System.in);
		int turnCounter = board.length * board[0].length; // sets a limit on turns to know when a tie occurs
		
		while(winner == 0) { // loop that runs the game until it's over
			printBoard();
			if(isHumanTurn) {
				System.out.print("X turn\nEnter move (x y format): ");
				int xInput = scan.nextInt(); // get x input
				int yInput = scan.nextInt(); // get y input
				if((xInput > -1 && xInput < board[0].length) && (yInput > -1 && yInput < board.length) && board[xInput][yInput] == '\u0000') {
					board[xInput][yInput] = 'X'; // sets user's square to X
					isHumanTurn = false; // changes turn
					turnCounter--; // decrements a turn
				}
			} else {
				System.out.println("Opponent's turn");
				// minimax (boolean turn, int turnsLeft)
				int[] minimaxOutcome = minimax(false, turnCounter); // calls minimax Algorithm
				// Output is array of [minimax value, best xCoordinate, best yCoordinate]
				board[minimaxOutcome[1]][minimaxOutcome[2]] = 'O'; // sets ai space to O
				
//				System.out.print("O turn\nEnter move (x y format): ");
//				int xInput = scan.nextInt();
//				int yInput = scan.nextInt();
//				if((xInput > -1 && xInput < board[0].length) && (yInput > -1 && yInput < board.length) && board[xInput][yInput] == '\u0000') {
//					board[xInput][yInput] = 'O';
				
				isHumanTurn = true; // changes turn
				turnCounter--; // decrements a turn
//				}
			}
			winner = checkBoard(board); // checks if there's a winner
					
//			Counts turns and displays tie game if there's no winner
			if(turnCounter == 0) {
				printBoard();
				System.out.println("Tie game. Thanks for playing");
				scan.close();
				return;
			}
		}
		
//		Prints board and displays winner
		printBoard();
		if(winner == 1) { // user wins
			System.out.println("Congrats you won!! :)");
		} else { // ai wins
			System.out.println("You tried your best. Thanks for playing");
		}
		scan.close();
	}
	
	// minimax algorithm
	public static int[] minimax(boolean player, int turnsLeft) {
		char[][] recursiveBoard = board.clone();
		
		if(player) { // Max Value
			int[] m = {Integer.MIN_VALUE, -1, -1}; // array which stores value and the associated move
			for(int row = 0; row < recursiveBoard.length; row++) {
				for(int col = 0; col < recursiveBoard[0].length; col++) {
					if(recursiveBoard[row][col] == '\u0000') {
						recursiveBoard[row][col] = 'X';
//						System.out.println("X's turn (x: " + row + " y: " + col + ")");
//						printBoard(recursiveBoard);
						// Calls minimaxHelper which calculates the outcome value and stores the associated move
						int[] v = {minimaxHelper(recursiveBoard, !player, turnsLeft - 1), row, col}; 
						m = m[0] > v[0]? m: v; 
//						m = Math.max(m, v);
						recursiveBoard[row][col] = '\u0000';
					}
				}
			}
//			System.out.println("max: " + m);
			return m;
		} else { // Min Value
			// same as max but with flipped signs and Integer.MAX_VALUE
			int[] m = {Integer.MAX_VALUE, -1, -1};
			for(int row = 0; row < recursiveBoard.length; row++) {
				for(int col = 0; col < recursiveBoard[0].length; col++) {
					if(recursiveBoard[row][col] == '\u0000') {
						recursiveBoard[row][col] = 'O';
//						System.out.println("O's turn (x: " + row + " y: " + col + ")");
//						printBoard(recursiveBoard);
						int[] v = {minimaxHelper(recursiveBoard, !player, turnsLeft - 1), row, col};
						m = m[0] < v[0]? m: v; 
						recursiveBoard[row][col] = '\u0000';
					}
				}
			}
//			System.out.println("min: " + m);
			return m;
		}
	}
	
	// same as minimax but only 
	public static int minimaxHelper(char[][] board, boolean player, int turnsLeft) {
		if(turnsLeft == 0 || checkBoard(board) != 0) {
//			System.out.println("leaf value: " + checkBoard(board));
			return checkBoard(board);
		}
		char[][] recursiveBoard = board.clone();
		if(player) { // Max Value
			int m = Integer.MIN_VALUE;
			for(int row = 0; row < recursiveBoard.length; row++) {
				for(int col = 0; col < recursiveBoard[0].length; col++) {
					if(recursiveBoard[row][col] == '\u0000') {
						recursiveBoard[row][col] = 'X';
//						System.out.println("X's turn (x: " + row + " y: " + col + ")");
//						printBoard(recursiveBoard);

						
						int v = minimaxHelper(recursiveBoard, !player, turnsLeft - 1);
						m = Math.max(m, v);
						recursiveBoard[row][col] = '\u0000';
					}
				}
			}
//			System.out.println("max: " + m);
			return m;
		} else { // Min Value
			int m = Integer.MAX_VALUE;
			for(int row = 0; row < recursiveBoard.length; row++) {
				for(int col = 0; col < recursiveBoard[0].length; col++) {
					if(recursiveBoard[row][col] == '\u0000') {
						recursiveBoard[row][col] = 'O';
//						System.out.println("O's turn (x: " + row + " y: " + col + ")");
//						printBoard(recursiveBoard);
						int v = minimaxHelper(recursiveBoard, !player, turnsLeft - 1);
//						m = m[0] < v[0]? m: v; 
						m = Math.min(m, v);
						recursiveBoard[row][col] = '\u0000';
					}
				}
			}
//			System.out.println("min: " + m);
			return m;
		}
		

	}
	
	// prints the given board
	public static void printBoard(char[][] board) {
		String dashes = "----------------";
		dashes = dashes.repeat(board.length);
		for(int row = 0; row < board.length; row++) {
			System.out.println(dashes);
			System.out.print("|");
			for(int col = 0; col < board[0].length; col++) {
				System.out.print("\t" + board[row][col] + "\t|");
			}
			System.out.println();
		}
		System.out.println(dashes);
	}
	
	// prints the static board
	public static void printBoard() {
		String dashes = "----------------";
		dashes = dashes.repeat(board.length);
		for(int row = 0; row < board.length; row++) {
			System.out.println(dashes);
			System.out.print("|");
			for(int col = 0; col < board[0].length; col++) {
				System.out.print("\t" + board[row][col] + "\t|");
			}
			System.out.println();
		}
		System.out.println(dashes);
	}
	
	// checks for a winner
	public static int checkBoard(char[][] board) {
//		Check horizontal 3 in a row
		for(int row = 0; row < board.length; row++) {
			int count = 0;
			for(int col = 0; col < board[0].length; col++) {
				if(board[row][col] == 'X') {
					count++;
				} else if (board[row][col] == 'O'){
					count--;	
				}
			}
			if(count == board.length) {
				return 1;
			}
			if(count == -board.length) {
				return -1;
			}
		}	
		
//		Check vertical 3 in a row
		for(int col = 0; col < board[0].length; col++) {
			int count = 0;
			for(int row = 0; row < board.length; row++) {
				if(board[row][col] == 'X') {
					count++;
				} else if (board[row][col] == 'O'){
					count--;
				}
			}
			if(count == board.length) {
				return 1;
			}
			if(count == -board.length) {
				return -1;
			}
		}	
		
//		Check top left to lower right diagonal
		int count = 0;
		for(int i = 0; i < board.length; i++) {
			if(board[i][i] == 'X') {
				count++;
			} else if (board[i][i] == 'O'){
				count--;
			}
		}
		if(count == board.length) {
			return 1;
		}
		if(count == -board.length) {
			return -1;
		}
		
		count = 0;
//		Check top right to lower left diagonal
		for(int i = 0; i < board[0].length; i++) {
			if(board[i][board[0].length - i - 1] == 'X') {
				count++;
			} else if(board[i][board[0].length - i - 1] == 'O') {
				count--;
			}
		}
		if(count == board.length) {
			return 1;
		}
		if(count == -board.length) {
			return -1;
		}
		
//		0 means tie or board is not filled
		return 0;
	}
	
	
	public static void main(String[] args) {

		runGame();
	}

}
