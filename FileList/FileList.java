/*
	This is a Java program the emulates the Unix 'ls' command. It is intended to run from a command line prompt. It displays files and directories in the root of the Java program, and their associated information. There a several options thats can be used:
	no options : all files shown execpt hidden files
	-A : show all files, including hidden files
	-l : show extended information
	-c : display canonicalized path from root
	
	These options can be used separately, together, or not at all. For example:
		java cli.FileList
		java cli.FileList -A
		java cli.FileList -l
		java cli.FileList -c
		java cli.FileList -Alc
		...
		And so on.
	
	You can add a file name or directory name after the options to show information specific to that file or directory. For example:
		java cli.FileList a_dir
		java cli.FileList -A a_dir
		java cli.FileList -l a_dir
		java cli.FileList -c file.txt
		java cli.FileList -Alc file.txt
		...
		And so on.   
*/ 

package cli;

import java.util.*;
import java.io.*;
import java.nio.*; 
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.lang.*;
import java.text.*;

public class FileList
{
	private List<File> filelist = new ArrayList<File>();
	public static final int ALL = 1;
	public static final int EXTENDED = 2;
	public static final int CANONICAL = 3;
	
	private FileList(List<File> list)
	{
		this.filelist = list;
	}
	
	public static FileList of(String str) throws FileNotFoundException, SecurityException
	{
		ArrayList<File> tempArrayList = new ArrayList<File>();
		File tempFile = null;
		Path tempPath = Paths.get(str, new String[0]);
		tempFile = FileList.checkPath(tempPath);
		
		if (tempFile == null)
		{
			tempFile = File.listRoots()[0];
		}
		if (!tempFile.isDirectory())
		{
			tempArrayList.add(tempFile);
		}
		else
		{
			for (int i = 0; i < tempFile.listFiles().length; i++)
			{
				tempArrayList.add(tempFile.listFiles()[i]);
			}
		}
		return new FileList(tempArrayList);
	}
	
	public static FileList empty()
	{
		return new FileList(new ArrayList<File>()); 	
	}
	
	public List<File> files()
	{
		ArrayList<File> tempArrayList = new ArrayList<File>();
		for (int i = 0; i < this.filelist.size(); i++)
		{
			tempArrayList.add(this.filelist.get(i));
		}
		return tempArrayList;
	}
	
	public boolean contains(File f)
	{
		for (int i = 0; i < this.filelist.size(); i++)
		{
			if (this.filelist.get(i).equals(f))
			{
				return true;
			}
		}
		return false;
	}
	
	public void add(File f) throws FileNotFoundException, SecurityException
	{
		if (this.contains(f))
		{
			return;
		}
		f = FileList.checkPath(f.toPath());
		this.filelist.add(f);
	}
	
	private static List<File> formatA(List<File> l) throws IOException
	{
		ArrayList<File> tempArrayList = new ArrayList<File>();
		for (int i = 0; i < l.size(); i++)
		{
			if (l.get(i).isHidden())
			{
				continue;
			}
			tempArrayList.add(l.get(i));
			
		}
		return tempArrayList;
	}
	
	private static List<String> formatC(List<File> l,boolean bC) throws IOException
	{
		ArrayList<String> tempArrayList = new ArrayList<String>();
		for (int i = 0; i < l.size(); i++)
		{
			String str;
			if (bC)
			{
				str = l.get(i).getCanonicalPath().toString();
			}
			else
			{
				str = l.get(i).getName();
			}
			if (l.get(i).isDirectory())
			{
				str += "/";
			}
			tempArrayList.add(str);
		}
		return tempArrayList;
	}
	
	public static List<String> format(FileList fl, int ... options) throws IOException 
	{
		List<File> list = fl.files();
        Collections.sort(list);
        boolean intarr[] = new boolean[4];
        for (int o : options)
        {
        	if (o == ALL)
        	{
        		intarr[ALL] = true;
        	}
        	else if (o == EXTENDED)
        	{
        		intarr[EXTENDED] = true;
        	}
        	else if (o == CANONICAL)
        	{
        		intarr[CANONICAL] = true;
        	}
        }
        ArrayList<String> tempArrayList = new ArrayList<String>();
        if (!intarr[ALL])
        {
        	for (int i = list.size()-1; i >= 0; i--)
        	{
        		if (list.get(i).isHidden())
        		{
        			list.remove(i);
        		}
        	}
        }
        ArrayList<String> tempArrayList2 = new ArrayList<String>();
        if (intarr[EXTENDED])
        {
        	for(File f : list)
        	{
        		tempArrayList2 = fileInfo(f,intarr[CANONICAL]);
        		String t = "";
        		for (String s : tempArrayList2)
        		{	
        			t += s + " ";
        		}
        		tempArrayList.add(t);
        	}
        }
        else
        {
			for (int i=0; i<list.size(); i++) 
			{
				String str;
				if (intarr[CANONICAL])
				{
					str=list.get(i).getCanonicalPath().toString();
				}
				else
				{
					str=list.get(i).getName();
				}
				if (list.get(i).isDirectory())
				{
					str += "/";
				}
				tempArrayList.add(str);
			}
        }
		return tempArrayList;
	}
	
	private static ArrayList<String> fileInfo(File file, boolean bC) throws IOException 
	{
        String str;
        ArrayList<String> li = new ArrayList<String>();
        PosixFileAttributes posixFileAttributes = (PosixFileAttributes)Files.readAttributes(file.toPath(), PosixFileAttributes.class, new LinkOption[0]);
        Set<PosixFilePermission> set = posixFileAttributes.permissions();
        String s2 = PosixFilePermissions.toString(set);
        s2 = file.isDirectory() ? "d".concat(s2) : "-".concat(s2);
        li.add(s2);
        li.add(posixFileAttributes.owner().toString());
        li.add(posixFileAttributes.group().toString());
        li.add(Long.toString(posixFileAttributes.size()));
        li.add(posixFileAttributes.lastModifiedTime().toString());
        str = bC ? file.getCanonicalPath() : file.getName();
        if (file.isDirectory())
            str = str.concat("/");
        li.add(str);
        return li;
    }

	
	public static File checkPath (Path p) throws FileNotFoundException, SecurityException
	{
		File tempFile = null;
		for (int i = 0; i < p.getNameCount(); i++)
		{
			String tempStr;
			if (p.isAbsolute())
			{
				tempStr = "/" + p.subpath(0, i + 1).toString();	
			}
			else 
			{
				tempStr = p.subpath(0, i + 1).toString();
			}
			
			tempFile = new File(tempStr);
			if (!tempFile.exists()) 
			{
                throw new FileNotFoundException("cannot access " + tempFile.toPath() + ": No such file or directory");
            }
            
            if (!tempFile.isDirectory()) 
            {
            	continue;
            }
            
            if (!tempFile.canRead()) 
            {
                throw new SecurityException("cannot open directory " + tempFile.toPath() + ": Permission denied");
            }
            
            if (tempFile.canExecute())
            { 
            	continue;	
           	}
           	throw new SecurityException("cannot open directory " + tempFile.toPath() + ": Permission denied");
        }
        return tempFile;	
	}
	
	public static void main(String[] args)
	{
		FileList fl = null;
		String str = System.getProperty("user.dir");
		int[] intarr = new int[3];
		int num = 0;
		for (int i = 0; i < args.length; i++)
		{
			if (args[i].charAt(0) == '-')
			{
				for (int j = 1; j < args[i].length(); j++)
				{
					if (args[i].charAt(j) == 'A') 
					{
						intarr[num++] = ALL;
					}
					else if (args[i].charAt(j) == 'l')
					{
						intarr[num++] = EXTENDED;
					}   
					else if (args[i].charAt(j) == 'c')
					{
						intarr[num++] = CANONICAL;
					}	 
                    else 
                    {
                    	System.err.println("FileList: Error: unrecognized option: " + args[i].charAt(j));
                    	System.exit(1);
                    }
				}
				continue;
			}
			str = args[i];
			break;
			
		}
		try 
		{
            fl = FileList.of(str);
            List<String> list = FileList.format(fl, intarr);
            System.out.println(String.join((CharSequence)"\n", list));
        }
        catch (FileNotFoundException | SecurityException e) 
        {
            System.err.println("FileList: " + e.getMessage());
        }
        catch (Exception e) 
        {
            e.printStackTrace(System.err);
        }
	}
} 
