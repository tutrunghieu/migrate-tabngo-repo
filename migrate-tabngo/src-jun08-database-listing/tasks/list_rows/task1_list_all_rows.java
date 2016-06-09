package tasks.list_rows;

import java.io.File;
import java.io.PrintWriter;
import java.util.Set;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class task1_list_all_rows 
{
	public static void main(String[] args) throws Exception
	{
//		MongoFrame f = new MongoFrame();
//		f.setFormValues("localhost", 27017, "data-trenzi105");		
		
		String mongoHost = "localhost";
		int mongoPort = 27017;
		String mongoBase = "data-trenzi105";
		
		MongoClient mongo = new MongoClient(mongoHost, mongoPort);
		MongoDatabase db = mongo.getDatabase(mongoBase);

		File f = MongoAccess.getDesktopFile("out1.txt");
		PrintWriter out = new PrintWriter(f);
		
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
			} //for each field
			
			for(Document rj: table.find())
			{
				out.println("------ row: " + rj);
			} //for each field
			
			
		} //for each table
		
		out.close();
		
		mongo.close();
			
//		
//		MongoCollection<Document> t = db.getCollection(table);
//		
//		HtmlWriter out = new HtmlWriter(file);
//		
//		out.h1(table + " (" + t.count() + ")");
//		out.table();
	}

}
