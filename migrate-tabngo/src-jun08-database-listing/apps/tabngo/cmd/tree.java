package apps.tabngo.cmd;

import java.awt.Desktop;
import java.io.PrintWriter;
import java.util.Set;

import org.bson.Document;
import org.nebula.mongo.MongoAccess;
import org.nebula.util.ParamParser;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class tree 
{
	public static void main(String[] args) throws Exception
	{
		ParamParser conf = new ParamParser(args);
		String mongoHost = conf.getHost();
		int mongoPort = conf.getPort();
		String mongoBase = conf.getDatabaseName("data-egg");
		
		MongoClient mongo = new MongoClient(mongoHost, mongoPort);
		MongoDatabase db = mongo.getDatabase(mongoBase);

		PrintWriter out = new PrintWriter(conf.getOutputFile());
		
		int ntab = 0, nrows = 0, ncols = 0;
		out.println("-- database: " + mongoBase);		
		for(String tk: db.listCollectionNames())
		{
			out.println();
			out.println("---- table: " + tk);
			MongoCollection<Document> table = db.getCollection(tk);
			
			Set<String> fields = MongoAccess.listMongoFields(table);
			
			for(String fj: fields)
			{
				out.println("------ column: " + fj);
				ncols++;
			}
			
			for(Document rj: table.find())
			{
				out.println("------ row: " + rj);
				nrows++;
			}
		} //for each table
		
		out.close();
		
		mongo.close();
		
		showFinal(mongoBase, ntab, ncols, nrows, conf);
	}


	private static void showFinal(String mongoBase, int ntab, int ncols, int nrows, ParamParser conf)
	throws Exception
	{
		System.out.println("database=" + mongoBase);
		System.out.println("ntabs=" + nrows);
		System.out.println("nrows=" + nrows);
		System.out.println("ncols=" + ncols);
	
		if( conf.showResult()) 
			Desktop.getDesktop().open(conf.getOutputFile());
	}
	
	
}
