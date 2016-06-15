package org.nebula.util;

import java.io.File;
import java.lang.reflect.Field;

import org.nebula.mongo.MongoAccess;

public class DatabaseParams
{
	public String __host;
	public String __port;
	public String __db;
	public String __out;
	public String __show;
	public String __cmp;
	public String __war;
	public String __in;
	
	public String __rfrom;
	public String __rto;
	
	public String __sfile;
	public String __tfile;
	

	public DatabaseParams(String[] args) throws Exception 
	{
		for(int n=args.length, k=1; k+1<n; )
		{
			String nk = args[k++];
			String vk = args[k++];
			
			System.out.println("Parameter [" + nk + "]=[" + vk + "]");
			
			Field fk = fieldFromSwitch(nk);
			if(fk == null)  { printHelp(); throw new Exception("Unknown switch " + nk); }
			
			fk.set(this, vk);
			
//			if(nk.equals("-h")) __host = vk;
//			else if(nk.equals("-p")) __port = vk;
//			else if(nk.equals("-db")) __dbname = vk;
//			
//			else if(nk.equals("-in")) __in = vk;			
//			else if(nk.equals("-out")) __out = vk;
//			else if(nk.equals("-war")) __war = vk;
//			else if(nk.equals("-cmp")) __cmp = vk;
//			
//			else if(nk.equals("-rfrom")) __strA = vk;			
//			else if(nk.equals("-rto")) __strB = vk;			
//			
//			else if(nk.equals("-show")) __show = vk.toLowerCase();
//			else { printHelp(); throw new Exception("Unknown switch"); }
		}
		
		return;
	}
	
	private Field fieldFromSwitch(String nk) 
	{
		nk = "_" + nk.replace("-", "_");
		
		try { return this.getClass().getField(nk); } 
		catch(Exception xp) { return null; }
	}

	private void printHelp() 
	{
		System.out.println("\t-h 12.34.56.78   to set the mongo host");		
		System.out.println("\t-p 27017   to set the mongo port");		
		System.out.println("\t-db mydatabase   to set the mongo database");		
		System.out.println("\t-out myfile.txt   to set the output file");		
		System.out.println("\t-show true   to decide whether to show the file");		
		System.out.println("\t-cmp checksum1.txt   to compare the current with the checksum");		
	}

	public String getHost()
	{
		return __host==null ? "localhost" : __host;
	}
	
	public int getPort()
	{
		return __port==null ? 27017 : Integer.parseInt(__port);
	}
	
	public String getDatabaseName(String db)
	{
		return __db==null ? db : __db;
	}

	public File getOutputFile() 
	{
		__out = __out.replace("$", MongoAccess.getDesktopFile().getAbsolutePath());
		
		return __out==null 
				? MongoAccess.getDesktopFile("out1.txt")
						: new File(__out);
	}
	
	public File getWarFile() 
	{
		__war = __war.replace("$", MongoAccess.getDesktopFile().getAbsolutePath());		
		return __war==null ? null:  new File(__war);
	}

	public File getCmpFile() 
	{
		__cmp = __cmp.replace("$", MongoAccess.getDesktopFile().getAbsolutePath());		
		return __cmp==null ? null:  new File(__cmp);
	}
	
	public boolean showResult()
	{
		if(__show == null) return false;
		
		return __show.equals("true") || __show.equals("yes"); 
	}

	public File getInputFile() 
	{
		__in = __in.replace("$", MongoAccess.getDesktopFile().getAbsolutePath());		
		return __in==null ? null:  new File(__in);
	}

	public String getReplaceFrom() 
	{
		return __rfrom;
	}

	public String getReplaceTo() 
	{
		return __rto;
	}

	public File getFile(String nk) 
	throws Exception
	{
		Field fk = fieldFromSwitch(nk);
		if(fk == null) return null;
		
		String vk = toString(fk.get(this), null);
		if(vk == null) return null;
	
		vk = vk.replace("$", MongoAccess.getDesktopFile().getAbsolutePath());
		return new File(vk);
	}

	private String toString(Object t, String dv) 
	{
		return t==null ? dv : t.toString();
	}
}
