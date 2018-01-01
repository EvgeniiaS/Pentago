package model;
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
		
		int currentPlayer = (Math.random() <= 0.5) ? 1 : 2; // 1 - a human, 2 - an AI
		
		boolean isMax = (currentPlayer == 2) ? true : false;
		char AIToken = (humanToken == 'w') ? 'b' : 'w';
		
		Game g = new Game(humanToken, name, AIToken, isMax, currentPlayer);
		g.start(in);
		in.close();
	}

}
