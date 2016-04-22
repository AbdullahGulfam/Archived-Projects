//Abdullah Gulfam
import java.util.Scanner;
public class Guessing_Game
{
	public static int totalguess, replay;
	
	
	public static void main(String[] args)
	{	
		do
		{
			Scanner in = new Scanner(System.in);
			System.out.println("1. Beginner Level (numbers between 1 and 10)\n" + "2. Intermediate Level (numbers between 1 and 100)\n" + "3. Advanced Level (numbers between 1 and 1,000)\n" + "4. Expert Level (numbers between 1 and 10,000)\n" + "Type the number corresponding to the level you want to play:");
			int diff = in.nextInt();
			System.out.println(); 
						
			if (diff < 1 || diff > 4)
			{	
				do
				{	
					System.out.println("1. Beginner Level (numbers between 1 and 10)\n" + "2. Intermediate Level (numbers between 1 and 100)\n" + "3. Advanced Level (numbers between 1 and 1,000)\n" + "4. Expert Level (numbers between 1 and 10,000)\n" + "The number you entered cannot be accepted. Type the number corresponding to the level you want to play:");
					diff = in.nextInt();
					System.out.println();	
				}
				while (diff < 1 || diff > 4);
			}
				
			int rannum = (int)(Math.random() * Math.pow(10, diff) + 1);
			
			System.out.println("Enter your guess:");
			int guess = in.nextInt();
			do
			{
				if (guess > rannum)
				{
					totalguess++;
					System.out.println("Your guess was too high! Enter your new guess:");
					guess = in.nextInt();	
				}
				if (guess < rannum)
				{
					totalguess++;
					System.out.println("Your guess was too low! Enter your new guess:");
					guess = in.nextInt();
				}
			}
			while(guess != rannum);
					
			if (guess == rannum)
			{
				totalguess++;
				System.out.println("Congratulations! You guessed the correct number! It took you " + totalguess + " guesses.");
				do 
				{ 
                	System.out.println("Enter '0' to play again. Enter '1' to quit.");
               	 	replay = in.nextInt();
               		if (replay != 0 && replay != 1) 
               		{
                    	System.out.println("Input not recognized.");
                	}
				}
            	while (replay != 0 && replay != 1);
			}
		}
		while (replay == 0);
	}	
}
