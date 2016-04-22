/*
	This is a Java program that I've made that is intended to be run through a command line prompt. It mimics the Unix Echo command and incorporates several command line options. 
	-n : doesn't output on the same line
	-e : allows for backslash characters to be interpreted.
	\n : new line
	\t : tab
	
*/


import java.util.Scanner;
public class Echo
{
        public static void main(String[] args)
        {
            // The boolean and loop keep the program running.
            boolean repeat = true;
            while (repeat == true)
            {
                //The lines in this part take your text and break it down into separate strings contained in a string array.
                String ui;
                String uifinal;
                ui = askUser();
                String[] uiarray = ui.split(" (?=(([^'\"]*['\"]){2})*[^'\"]*$)");
                //The boolean values are used to check which command line options are present.
                boolean n = checkForN(uiarray);
                boolean e = checkForE(uiarray);
                // The uifinal variable makes the necessary adjustments to the string based on which command line options (\n and \t) are present.
                uifinal = adjustUserInput(uiarray,e);
                // The conditionals determine how the text should be printed based on the -n option.
                if (n == true)
                {
                    System.out.print(uifinal);
                }
                else
                {
                    System.out.println(uifinal);
                }
                //And then the program repeats.
            }
        }

        public static String askUser()
        {
            String userinput;
            Scanner scan = new Scanner(System.in);
            System.out.print("[user@notnotbc.org]$ java Echo ");
            userinput = scan.nextLine();
            return userinput;
        }

        public static String adjustUserInput(String[] stringarr, boolean eoption)
        {
            if (eoption)
            {
                String temp = stringarr[stringarr.length - 1];
                temp = temp.replaceAll("\\\\t","\t");
                temp = temp.replaceAll("\\\\n","\n");
                return temp;
            }
            return stringarr[stringarr.length - 1];
        }

        public static boolean checkForN(String[] stringarr)
        {
            for (int i = 0; i < stringarr.length; i++)
            {
                if (stringarr[i].equals("-n"))
                {
                    return true;
                }
            }
            return false;
        }

        public static boolean checkForE(String[] stringarr)
        {
            for (int i = 0; i < stringarr.length; i++)
            {
                if (stringarr[i].equals("-e"))
                {
                    return true;
                }
            }
            return false;
        }
}
