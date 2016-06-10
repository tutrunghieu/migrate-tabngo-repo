package org.nebula.mongo;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;
import org.nebula.util.DatabaseParams;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import apps.tabngo.cmd.models.MongoChecksum;

public class MongoAccess 
{
	private static ObjectMapper jsonEng = new ObjectMapper();
	
	public static Set<String> listMongoFields(MongoCollection<Document> table) 
	{
		Set<String> fields = new LinkedHashSet<String>();
		
		for(Document rk: table.find())
				fields.addAll(rk.keySet());

		return fields;
	}

	public static File getDesktopFile(String f) 
	{
		return new File(System.getProperty("user.home") + "/Desktop/" + f);
	}
	
	public static File getDesktopFile() 
	{
		return new File(System.getProperty("user.home") + "/Desktop");
	}
	
	public static int tableCounter;
	public static int objectCounter;
	public static int fieldCounter;
	public static int selectedCounter;
	public static DatabaseParams execArgs;

	public static void writeJson(Object row, PrintWriter out) 
	{
		try { jsonEng.writeValue(out, row); }
		catch(Exception xp) {}
	}
	
	public static void writeJson(Object row, File out) 
	{
		out.getParentFile().mkdirs();
		try { jsonEng.writeValue(out, row); }
		catch(Exception xp) {}
	}

	public static String writeJson(Object row) 
	{
		try { return jsonEng.writeValueAsString(row); }
		catch(Exception xp) { return null; }
	}

	public static void printCounters(PrintStream out, String mongoBase) 
	{
		out.println("database: " + mongoBase);
		out.println("tables: " + MongoAccess.tableCounter);
		out.println("objects: " + MongoAccess.objectCounter);
		out.println("links: " + MongoAccess.selectedCounter);
		
	}	
	
	public static void printCounters(PrintWriter out, String mongoBase) 
	{
		out.println("database: " + mongoBase);
		out.println("tables: " + MongoAccess.tableCounter);
		out.println("objects: " + MongoAccess.objectCounter);
		out.println("links: " + MongoAccess.selectedCounter);
		
	}

	public static<T1> T1 readJson(File f2, Class<T1> cl, T1 dv) 
	{
		try { return jsonEng.readValue(f2, cl); }
		catch(Exception xp) { return dv; }
	}

	public static void replaceValues(String host, int port, String dbname, String strA, String strB) 
	throws Exception
	{
		MongoChecksum res = new MongoChecksum();
		
		res.dbname = dbname;
		
		MongoClient mongo = new MongoClient(host, port);
		MongoDatabase db = mongo.getDatabase(dbname);

		for(String tk: db.listCollectionNames())
		{
			MongoCollection<Document> table = db.getCollection(tk);
			List<Document> items = readTable(table);
			for(Document ik: items) replaceValues(ik, strA, strB);
			
			table.drop();
			table.insertMany(items);
		}
			
		mongo.close();			
	}

	private static void replaceValues(Document obj, String strA, String strB)
	{
		for(String nk: obj.keySet())
		{
			Object vk1 = obj.get(nk);
			String vk = (vk1==null ? "" : vk1.toString());
			if( vk.startsWith(strA) )
			{
				vk = vk.replace(strA, strB);
				obj.put(nk, vk);
			}
		}
		
		return;
	}

	private static List<Document> readTable(MongoCollection<Document> table)
	{
		List<Document> res = new ArrayList<Document>();
		for(Document dk: table.find()) res.add(dk);
		return res;
	}

	public static void dump(String host, int port, String dbname, File file)
	throws Exception
	{
		ObjectMapper eng = new ObjectMapper();
		PrintWriter out = new PrintWriter(file);
		
		MongoClient mongo = new MongoClient(host, port);
		MongoDatabase db = mongo.getDatabase(dbname);
		

		for(String tk: db.listCollectionNames())
		{
			MongoCollection<Document> table = db.getCollection(tk);
			for(Document ik: table.find()) 
			{
				Map<String, Object> rk = new LinkedHashMap<String, Object>();
				rk.put("_table", tk);
				for(String nj: ik.keySet()) rk.put(nj, ik.get(nj));
				out.println( eng.writeValueAsString(rk) );
			}
		}
			
		mongo.close();		
		
		out.close();
	}
	
}
