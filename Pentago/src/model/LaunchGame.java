package model;
// test
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LaunchGame {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter your name: ");
		String name = in.nextLine();
		
		char humanToken;
		do {
			System.out.println("Choose token color (B or W): ");
			String color = in.nextLine().toLowerCase();
			humanToken = color.charAt(0);
		} while (humanToken != 'b' && humanToken != 'w'); 
		
		int currentPlayer = (Math.random() <= 0.5) ? 1 : 2; // 1 - a human, 2 - AI
		
		String AIMinOrMax;
		if (currentPlayer == 2) {
			AIMinOrMax = "max";
		} else {
			AIMinOrMax = "min";
		}
		
		Game g = new Game(humanToken, AIMinOrMax);
		
		FileWriter fw = null;
		try {
			fw = new FileWriter("Output.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		do {			
			printGame(name, humanToken, currentPlayer, g, fw);
			if (currentPlayer == 1) {
				System.out.println("Your next move: ");
				String nextMove = in.nextLine();
				g.move(nextMove, humanToken);
			} else {
				System.out.println("AI moving...\n");
				g.AIMovePruning();
			}
			currentPlayer = changeCurrentPlayer(currentPlayer);
		} while (!g.isOver());
		System.out.println();
		System.out.println("-------------------");
		System.out.println("-------------------");
		System.out.println("Final state");
		
		try {
			fw.write("-------------------\n");
			fw.write("-------------------\n");
			fw.write("Final state:\n");
			fw.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		printGame(name, humanToken, currentPlayer, g, fw);
		System.out.println("A winner is " + g.getWinner().toUpperCase());
		try {
			fw.write("A winner is " + g.getWinner().toUpperCase());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		in.close();
	}
	
	public static int changeCurrentPlayer(int currentMaxPlayer) {
		if(currentMaxPlayer == 1) {
			return 2;
		} else {
			return 1;
		}
	}
	
	public static void printGame(String name, char humanToken, int currentPlayer, Game g, FileWriter fw) {
		char AIToken;
		if (humanToken == 'w') AIToken = 'b';
		else AIToken = 'w';
		
		System.out.println("Player 1: " + name + " (" + Character.toUpperCase(humanToken) + ")");
		System.out.println("Player 2: AI" + " (" + Character.toUpperCase(AIToken) + ")");
		
		try {
			fw.write("Player 1: " + name + " (" + Character.toUpperCase(humanToken) + ")\n");
			fw.write("Player 2: AI" + " (" + Character.toUpperCase(AIToken) + ")\n");
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (currentPlayer == 1) {
			System.out.println("Player to move next: 1");
			try {
				fw.write("Player to move next: 1\n");
				fw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {			
			System.out.println("Player to move next: 2");
			try {
				fw.write("Player to move next: 2\n");
				fw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		g.printGame(fw);		
	}

}
