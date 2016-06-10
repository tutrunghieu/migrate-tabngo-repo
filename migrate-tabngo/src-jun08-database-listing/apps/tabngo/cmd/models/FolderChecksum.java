package apps.tabngo.cmd.models;

import java.io.File;
import java.util.Stack;

import org.nebula.mongo.MongoAccess;

public class FolderChecksum {

	public String fileCounter;
	public String folderCounter;
	public String byteCounter;

	public static FolderChecksum fromJson(File f2) 
	{
		return MongoAccess.readJson(f2, 
				FolderChecksum.class, new FolderChecksum());
	}
	

	public static FolderChecksum fromFolder(File f1) 
	{
		Stack<File> todo = new Stack<File>();
		todo.add(f1);
		
		int files = 0, folders = 0, bytes = 0;
		
		while(!todo.isEmpty())
		{
			File[] tfiles = todo.pop().listFiles();
			if(tfiles == null) continue;
			
			for(File fj: tfiles)
			if(fj.isDirectory()) { folders++; todo.add(fj); }
			else { files++; bytes += fj.length(); }
		}
		
		FolderChecksum res = new FolderChecksum();
		res.folderCounter = files + "";
		res.fileCounter = folders + "";
		res.byteCounter = bytes + "";
		return res;
	}
	
}
