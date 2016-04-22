package cli;

import cli.FileList;
import java.util.*;
import java.io.*;
import java.nio.*; 
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.lang.*;
import java.text.*;
public class Find
{
	private FileList filelist;
	private Find()
	{
		filelist = FileList.empty();
	}
	
	public static FileList in(String path, String  name) throws FileNotFoundException, SecurityException
	{
		Find f = new Find();
		FileList fl = FileList.of(path);
		List<File> afl = fl.files();
		f.findFile(afl, name);
		return f.filelist;
	}
	
	private void findFile (List<File> list, String name) throws FileNotFoundException
	{
		for (int i = 0; i < list.size(); i++)
		{
			try
			{
				if (name.equals("") || list.get(i).getName().equals(name))
				{
					this.filelist.add(list.get(i));
				}
				if (!list.get(i).isDirectory())
				{
					continue;
				}
				FileList tempfilelist = FileList.of(list.get(i).toPath().toString());
                List templist = tempfilelist.files();
                this.findFile(templist, name);
				
			}
			catch(SecurityException sec_exc)
			{	
			}
		}
	}
	
	public static void main(String[] args)
	{
		String string = System.getProperty("user.dir");
        String string2 = "";
        for (int i = 0; i < args.length; i++) 
        {
            if (args[i].charAt(0) == '-') 
            {
            	if (args[i].substring(1).equals("name")) 
               	{
                	int n = i + 1;
                    if (n >= args.length) 
                    {
                    	System.err.println("Find: missing argument to -name");
                        System.exit(1);
                        break;
                    }
                    string2 = args[i++];
                    break;
               	}
              	else 
              	{
                 	System.err.println("Find: Error: unrecognized option: " + args[i]);
                	System.exit(1);
                    break;
            	}
            }
            if (i != 0) continue;
            string = args[i];
       	}
        try 
        {
            FileList fileList = Find.in(string, string2);
            List<String> o = FileList.format((FileList)fileList, (int[])new int[]{1, 3});
            if (!o.isEmpty()) 
            {
                System.out.println(String.join((CharSequence)"\n", o));
            }
        }
        catch (FileNotFoundException | SecurityException exc) 
        {
            System.err.println("Find: " + exc.getMessage());
        }
        catch (IOException io_exc) 
        {
            System.out.println(io_exc.getMessage());
        }
	}
}