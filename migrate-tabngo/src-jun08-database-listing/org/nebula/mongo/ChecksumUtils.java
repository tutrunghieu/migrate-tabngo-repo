package org.nebula.mongo;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ChecksumUtils 
{
	public static boolean compareFiles(File f1, File f2, File f) 
	throws Exception
	{
		f.getParentFile().mkdirs();
		PrintWriter out = new PrintWriter(f);
		boolean bk = ChecksumUtils.compareFiles(f1, f2, out);
		out.println("FinalResult: " + bk);
		out.close();
		
		return bk;
	}
	
	public static boolean compareFiles(File f1, File f2, PrintWriter out)
	throws Exception
	{
		{
			boolean bk = ( f1.length() == f2.length() );
			out.println(bk + "|" + f1.length() + "|" + f2.length());
			if(bk == false) return bk;
		}
		
		Map<Object, Integer> r1 = readBytes(f1);
		Map<Object, Integer> r2 = readBytes(f2);
		
		Set<Object> keys = new TreeSet<Object>();
		keys.addAll(r1.keySet());
		keys.addAll(r2.keySet());
		
		for(Object nk: keys)
		{
			Integer c1 = r1.get(nk);
			Integer c2 = r2.get(nk);
			boolean bk = ( c1 == c2 );
			out.println(bk + "|" + c1 + "|" + c2);			
			if(bk == false) return bk;
		}
		
		return true;
	}

	public static Map<Object, Integer> readBytes(File f1) 
	throws Exception
	{
		Map<Object, Integer> res = new TreeMap<Object, Integer>();
		
	    FileReader r1 = new FileReader(f1);
	    
	    while (true)
	    {
	    	int bk = r1.read();
	    	if(bk == -1) break;
	    	
	    	Integer ck = res.get(bk);
	    	res.put(bk, (ck==null ? 1 : ck+1) ); 
	    }
	
		r1.close();
	
	    return res;
	}

	public static boolean compareObjects(Object x, Object y, File f) 
	throws Exception
	{
		f.getParentFile().mkdirs();
		PrintWriter out = new PrintWriter(f);
		boolean bk = ChecksumUtils.compareObjects(x, y, out);
		out.println("FinalResult: " + bk);
		out.close();
		
		return bk;
	}
	
	public static boolean compareObjects(Object x, Object y, PrintWriter out)
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
