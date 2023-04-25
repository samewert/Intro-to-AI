package Homework2;

import java.util.Arrays;
import java.util.Scanner;

public class TicTacToePruningPseudoCoded {

	final static int BOARD_SIZE = 3;
	static char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
	
	
	public static void runGame() {
		boolean isHumanTurn = true;
		int winner = 0;
		Scanner scan = new Scanner(System.in);
		int turnCounter = board.length * board[0].length;
		
		while(winner == 0) {
			printBoard();
			if(isHumanTurn) {
//				AI vs AI
//				System.out.println("Human's turn");int[] minimaxOutcome = minimax(true, turnCounter, Integer.MIN_VALUE, Integer.MAX_VALUE);board[minimaxOutcome[1]][minimaxOutcome[2]] = 'X';isHumanTurn = false;turnCounter--;
				System.out.print("X turn\nEnter move (x y format): ");
				int xInput = scan.nextInt();
				int yInput = scan.nextInt();
				if((xInput > -1 && xInput < board[0].length) && (yInput > -1 && yInput < board.length) && board[xInput][yInput] == '\u0000') {
					board[xInput][yInput] = 'X';
					isHumanTurn = false;
					turnCounter--;
				}
			} else {
				System.out.println("Opponent's turn");
				// minimax (boolean turn, int turnsLeft)
				int[] minimaxOutcome = minimax(false, turnCounter, Integer.MIN_VALUE, Integer.MAX_VALUE);
				// Output is array of [minimax value, best xCoordinate, best yCoordinate]
				board[minimaxOutcome[1]][minimaxOutcome[2]] = 'O';
				
//				System.out.print("O turn\nEnter move (x y format): ");
//				int xInput = scan.nextInt();
//				int yInput = scan.nextInt();
//				if((xInput > -1 && xInput < board[0].length) && (yInput > -1 && yInput < board.length) && board[xInput][yInput] == '\u0000') {
//					board[xInput][yInput] = 'O';
				
				isHumanTurn = true;
				turnCounter--;
//				}
			}
			winner = checkBoard(board);
					
//			Counts turns and displays tie game if there's no winner
			if(turnCounter == 0 && winner == 0) {
				printBoard();
				System.out.println("Tie game. Thanks for playing");
				scan.close();
				return;
			}
		}
		
//		Prints board and displays winner
		printBoard();
		if(winner == 1) {
			System.out.println("Congrats you won!! :)");
		} else {
			System.out.println("You tried your best. Thanks for playing");
		}
		scan.close();
	}
	
	
	public static int[] minimax(boolean player, int turnsLeft, int alpha, int beta) {
		char[][] recursiveBoard = board.clone();
		
		if(player) { // Max Value
			int[] v = {Integer.MIN_VALUE, -1, -1};
			for(int row = 0; row < recursiveBoard.length; row++) {
				for(int col = 0; col < recursiveBoard[0].length; col++) {
					if(recursiveBoard[row][col] == '\u0000') {
						recursiveBoard[row][col] = 'X';
//						System.out.println("X's turn (x: " + row + " y: " + col + ")");
//						printBoard(recursiveBoard);
						int[] m = {minimaxHelper(recursiveBoard, !player, turnsLeft - 1,
								alpha, beta), row, col};
						recursiveBoard[row][col] = '\u0000';
						v = v[0] >= m[0]? v: m;
						if(v[0] >= beta) {
							return v;
						}
						alpha = Math.max(alpha, v[0]);
					}
				}
			}
//			System.out.println("max: " + m);
			return v;
		} else { // Min Value
			int[] v = {Integer.MAX_VALUE, -1, -1};
			for(int row = 0; row < recursiveBoard.length; row++) {
				for(int col = 0; col < recursiveBoard[0].length; col++) {
					if(recursiveBoard[row][col] == '\u0000') {
						recursiveBoard[row][col] = 'O';
//						System.out.println("O's turn (x: " + row + " y: " + col + ")");
//						printBoard(recursiveBoard);
						int[] m = {minimaxHelper(recursiveBoard, !player, turnsLeft - 1, 
								alpha, beta), row, col};
						recursiveBoard[row][col] = '\u0000';
						v = v[0] <= m[0]? v: m;
						if(v[0] <= alpha) {
							return v;
						}
						beta = Math.min(beta, v[0]);
						
					}
				}
			}
//			System.out.println("min: " + m);
			return v;
		}
	}
	
	
	public static int minimaxHelper(char[][] board, boolean player, int turnsLeft, int alpha, int beta) {
		if(turnsLeft == 0 || checkBoard(board) != 0) {
//			System.out.println("leaf value: " + checkBoard(board));
			return checkBoard(board);
		}
		char[][] recursiveBoard = board.clone();
		if(player) { // Max Value
			int v = Integer.MIN_VALUE;
			for(int row = 0; row < recursiveBoard.length; row++) {
				for(int col = 0; col < recursiveBoard[0].length; col++) {
					if(recursiveBoard[row][col] == '\u0000') {
						recursiveBoard[row][col] = 'X';
//						System.out.println("X's turn (x: " + row + " y: " + col + ")");
//						printBoard(recursiveBoard);
						v = Math.max(v, minimaxHelper(recursiveBoard, !player, turnsLeft - 1, alpha, beta));
						recursiveBoard[row][col] = '\u0000';
						if(v >= beta) {
							return v;
						}
						alpha = Math.max(alpha, v);
					}
				}
			}
//			System.out.println("max: " + m);
			return v;
		} else { // Min Value
			int v = Integer.MAX_VALUE;
			for(int row = 0; row < recursiveBoard.length; row++) {
				for(int col = 0; col < recursiveBoard[0].length; col++) {
					if(recursiveBoard[row][col] == '\u0000') {
						recursiveBoard[row][col] = 'O';
//						System.out.println("O's turn (x: " + row + " y: " + col + ")");
//						printBoard(recursiveBoard);
						v = Math.min(v, minimaxHelper(recursiveBoard, !player, turnsLeft - 1, alpha, beta));
						recursiveBoard[row][col] = '\u0000';
						if(v <= alpha) {
							return v;
						}
						beta = Math.min(beta, v);						
					}
				}
			}
//			System.out.println("min: " + m);
			return v;
		}
		

	}
	
//	TODO delete this method?
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
		
//		System.out.println(checkBoard(board));
//		System.out.println(Arrays.toString(minimax(true, 3)));
		runGame();
	}

}
