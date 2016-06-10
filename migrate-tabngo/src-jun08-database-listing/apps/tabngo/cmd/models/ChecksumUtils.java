package apps.tabngo.cmd.models;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ChecksumUtils 
{
	public static boolean compare(Object x, Object y, File f) 
	throws Exception
	{
		f.getParentFile().mkdirs();
		PrintWriter out = new PrintWriter(f);
		boolean bk = ChecksumUtils.compare(x, y, out);
		out.println("FinalResult: " + bk);
		out.close();
		
		return bk;
	}
	
	public static boolean compare(Object x, Object y, PrintWriter out)
	throws Exception
	{
		for(Field fk: x.getClass().getDeclaredFields())
		if( Modifier.isPublic(fk.getModifiers() ) )
		{
			Object xk = fk.get(x);
			Object yk = fk.get(y);
			boolean bk = xk.equals(yk);
			
			out.println(bk + "|" + xk + "|" + yk);
			if(bk==false) return bk;
		}
		
		return true;
	}
}
