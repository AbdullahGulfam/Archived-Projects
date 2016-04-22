import java.util.Scanner;

public class PigLatin
{
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter: ");
		String string = scanner.nextLine();
		System.out.println("Translation: " + translate(string));	
	}
	
	public static String translate(String s)
	{
		String[] words = s.split(" ");
		String translation = "";
		for (int i = 0; i < words.length; i++)
		{
			translation += pigWord(words[i]) + " ";
		}
		return translation;
	}
	
	public static String pigWord(String s)
	{
		String vowels = "aeiou";
		String startsc = startSpecialCharacters(s);
		String endsc = endSpecialCharacters(s);
		s = s.replace(startsc,"");
		s = s.replace(endsc,"");
		int vowelindex = firstVowel(s);
		boolean conditionmet = false; 
		String temp = s;
		
		if (vowelindex == 0)
		{
			for (int i = 0; i < vowels.length(); i++)
			{
				if (s.substring(s.length() - 1).equalsIgnoreCase(vowels.substring(i,i+1)) && s.substring(s.length() - 1) != "y")
				{
					temp += "yay";
					conditionmet = true;
				}
			}
			if (conditionmet == false)
			{
				temp += "ay";
			}
		}
		else if (vowelindex != 0)
		{
			temp = temp.substring(vowelindex,s.length()) + temp.substring(0,vowelindex).toLowerCase() + "ay";
		}
		
		if (s.substring(0,1).equals(s.substring(0,1).toUpperCase()))
		{
			temp = temp.substring(0,1).toUpperCase() + temp.substring(1);
		}
		
		return startsc + temp + endsc;
	}

	public static boolean isAlphabetical(char c)
	{
		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
		{
			return true;
		} 
		return false;
	}
	
	public static int firstVowel(String s)
	{
		String vowels = "aeiou";
		int index = 0;
		for (int i = s.length() - 1; i >= 0; i--)
		{
			for (int j = 0; j < vowels.length(); j++)
			{
				if (s.substring(i,i+1).equalsIgnoreCase(vowels.substring(j,j+1)))
				{
					index = i;
				}
			}
		}
		return index;
	}
	
	public static String startSpecialCharacters(String s)
	{
		String start = "";
		for (int i = 0; i < s.length(); i++)
		{
			if (!isAlphabetical(s.charAt(i)))
			{
				start += s.substring(i,i+1);
			}
			
			else
			{
				return start;
			}
		}
		return start;
	}
	
	public static String endSpecialCharacters(String s)
	{
		String end = "";
		for (int i = s.length() - 1; i >= 0; i--)
		{
			if (isAlphabetical(s.charAt(i)))
			{
				end += s.substring(i+1);
				return end;
			}
		}
		return end;
	}
}