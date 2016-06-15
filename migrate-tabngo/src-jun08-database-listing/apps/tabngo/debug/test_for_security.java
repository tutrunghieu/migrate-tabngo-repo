package apps.tabngo.debug;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class test_for_security 
{
	public static void main(String[] args) 
	{
		String dbname = "TabnGo";
		
		MongoClient mongo = new MongoClient("103.200.20.202", 27017);
		MongoDatabase db = mongo.getDatabase(dbname);

		for(String tk: db.listCollectionNames())
		{
			//MongoCollection<Document> table = db.getCollection(tk);
			System.out.println(tk);
		}
			
		mongo.close();			
	}

}
