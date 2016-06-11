package org.nebula.util;

import apps.tabngo.cmd.cmd;

public class CommandTarget 
{
	public static TargetController findTargetController(String url) 
	throws Exception
	{
		url = cmd.class.getPackage().getName() + "." + url.replace('-', '_');
		System.out.println("Executing " + url);
		
		Class<?> cl = Class.forName(url);
		return (TargetController) cl.newInstance();
	}

	public static String[] debugParams(String args) 
	{
		return args.split("\\s+");
	}

}
