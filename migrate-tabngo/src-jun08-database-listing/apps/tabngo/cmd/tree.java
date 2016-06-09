package apps.tabngo.cmd;

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
		
		out.println("-- database: " + mongoBase);		
		for(String tk: db.listCollectionNames())
		{
			out.println();
			out.println("---- table: " + tk);
			MongoCollection<Document> table = db.getCollection(tk);
			
			Set<String> fields = MongoAccess.listMongoFields(table);
			
			for(String fj: fields)
				out.println("------ column: " + fj);
			
			for(Document rj: table.find())
				out.println("------ row: " + rj);
		} //for each table
		
		out.close();
		
		mongo.close();
	}

}
