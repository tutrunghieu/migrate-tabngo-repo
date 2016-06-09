package apps.tabngo.migrate;

import java.io.File;
import java.io.PrintWriter;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class task2_list_values_having_http 
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

		File f = MongoAccess.getDesktopFile("out1-value-http.txt");
		PrintWriter out = new PrintWriter(f);
		
		out.println("- database: " + mongoBase);		
		for(String tk: db.listCollectionNames())
		{
			out.println();
			out.println("-- table: " + tk);
			MongoCollection<Document> table = db.getCollection(tk);
			
			for(Document rj: table.find())
			{
				out.println("--- row: " + rj.get("_id"));
				for(String fj: rj.keySet())
				{
					Object vj1 = rj.get(fj);
					String vj = (vj1==null ? "" : vj1.toString());
					
					if(vj.startsWith("http://") || vj.startsWith("https://"))
						out.println("----- " + fj + ": " + vj);
				} //for each field
			}
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
