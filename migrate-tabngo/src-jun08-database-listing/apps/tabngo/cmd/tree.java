package apps.tabngo.cmd;

import java.awt.Desktop;
import java.io.PrintWriter;
import java.util.Set;

import org.bson.Document;
import org.nebula.mongo.MongoAccess;
import org.nebula.util.DatabaseParams;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class tree 
{
	public static void main1(String[] args) throws Exception
	{
		DatabaseParams conf = new DatabaseParams(args);
		String mongoHost = conf.getHost();
		int mongoPort = conf.getPort();
		String mongoBase = conf.getDatabaseName("data-egg");
		
		printTree(conf, mongoHost, mongoPort, mongoBase);
		showFinal(mongoBase, conf);
	}


	private static void printTree(DatabaseParams conf,
			String mongoHost, int mongoPort, String mongoBase) 
	throws Exception
	{
		
		MongoClient mongo = new MongoClient(mongoHost, mongoPort);
		MongoDatabase db = mongo.getDatabase(mongoBase);

		PrintWriter out = new PrintWriter(conf.getOutputFile());
		
		out.println("--database: " + mongoBase);		
		for(String tk: db.listCollectionNames())
		{
			MongoAccess.tableCounter++;
			
			out.println("----table: " + tk);
			MongoCollection<Document> table = db.getCollection(tk);
			
			Set<String> fields = MongoAccess.listMongoFields(table);
			
			for(String fj: fields)
			{
				MongoAccess.fieldCounter++;
				out.println("------column: " + fj);
			}
			
			for(Document rj: table.find())
			{
				MongoAccess.objectCounter++;
				out.println("------row: " +  MongoAccess.writeJson(rj) );
			}
		} //for each table
		
		out.close();
		
		mongo.close();		
	}


	private static void showFinal(String mongoBase, DatabaseParams conf)
	throws Exception
	{
		System.out.println("database: " + mongoBase);
		System.out.println("tables: " + MongoAccess.tableCounter);
		System.out.println("objects: " + MongoAccess.objectCounter);
		System.out.println("fields: " + MongoAccess.fieldCounter);
	
		if( conf.showResult()) 
			Desktop.getDesktop().open(conf.getOutputFile());
		
		System.out.println("See result at: " 
				+ conf.getOutputFile().getAbsolutePath() );		
	}
	
	
}
