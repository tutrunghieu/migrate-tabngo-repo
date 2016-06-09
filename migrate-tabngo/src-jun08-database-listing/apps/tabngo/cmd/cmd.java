package apps.tabngo.cmd;

import java.lang.reflect.Method;

public class cmd 
{
	
	public static void main(String[] args) throws Exception 
	{
		Class<?> cl = Class.forName(args[0]);
		
		Method m = cl.getMethod("main1", String[].class);
		m.invoke(null, (Object)args);
	}

}
