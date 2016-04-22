import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class PigLatinRunnerFromFile
{
	public static void main(String[] args) throws IOException
	{
		File input = new File("english.txt");
		if (!input.exists())
		{
			System.out.println("english.txt does not exist.");
			System.exit(1);
		}
		Scanner scanner = new Scanner(input);
		
		File output = new File("piglatin.txt");
		if (output.exists())
		{
			System.out.println("piglatin.txt already exists.");
			System.exit(2);
		}
		PrintWriter pw = new PrintWriter(output);
		
		String lineOfText;
		while (scanner.hasNextLine())
		{
			lineOfText = scanner.nextLine();
			pw.println(PigLatin.translate(lineOfText));
		}
		scanner.close();
		pw.close();
	}
}