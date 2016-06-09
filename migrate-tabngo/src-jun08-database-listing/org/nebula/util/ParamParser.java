package org.nebula.util;

import java.io.File;

import org.nebula.mongo.MongoAccess;

public class ParamParser
{
	private String __host;
	private String __port;
	private String __dbname;
	private String __out;
	private String __show;

	public ParamParser(String[] args) throws Exception 
	{
		for(int n=args.length, k=1; k+1<n; )
		{
			String nk = args[k++];
			String vk = args[k++];
			
			System.out.println("Parameter [" + nk + "]=[" + vk + "]");
			
			if(nk.equals("-h")) __host = vk;
			else if(nk.equals("-p")) __port = vk;
			else if(nk.equals("-db")) __dbname = vk;			
			else if(nk.equals("-out")) __out = vk;			
			else if(nk.equals("-show")) __show = vk.toLowerCase();
			else { printHelp(); throw new Exception("Unknown switch"); }
		}
		
		return;
	}
	
	private void printHelp() 
	{
		System.out.println("\t-h 12.34.56.78   to set the mongo host");		
		System.out.println("\t-p 27017   to set the mongo port");		
		System.out.println("\t-db mydatabase   to set the mongo database");		
		System.out.println("\t-out myfile.txt   to set the output file");		
		System.out.println("\t-show true   to decide whether to show the file");		
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
		return __dbname==null ? db : __dbname;
	}

	public File getOutputFile() 
	{
		return __out==null 
				? MongoAccess.getDesktopFile("out1.txt")
						: new File(__out);
	}
	
	public boolean showResult()
	{
		if(__show == null) return false;
		
		return __show.equals("true") || __show.equals("yes"); 
	}

}
