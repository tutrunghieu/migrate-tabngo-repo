package org.nebula.util;

import java.awt.Desktop;
import java.io.File;

public abstract class TargetController 
{
	public String mongoHost;
	public int mongoPort;
	public String mongoBase;
	public DatabaseParams mongoArgs;
	
	public abstract void processRequest() throws Exception;

	public void showFileIf(File f3)
	throws Exception
	{
		if(this.mongoArgs.showResult()) 
			Desktop.getDesktop().open(f3);		
	}

	public<T1> T1 printParam(String msg, T1 val) 
	{
		System.out.println(msg + val);
		return val;
	}
	
}
