package org.nebula.mongo;

import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;
import org.nebula.util.DatabaseParams;

import com.mongodb.client.MongoCollection;

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
}
