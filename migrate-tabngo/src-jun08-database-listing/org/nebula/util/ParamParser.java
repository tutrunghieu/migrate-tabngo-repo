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
			else throw new Exception("Unknown switch");
		}
		
		return;
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
