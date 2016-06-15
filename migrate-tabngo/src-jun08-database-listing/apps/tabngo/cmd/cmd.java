package apps.tabngo.cmd;

import org.nebula.util.CommandTarget;
import org.nebula.util.DatabaseParams;
import org.nebula.util.TargetController;

public class cmd extends CommandTarget
{
//	args = "war-sum  -show true   -war $/migrate-tabngo.jar    -out  $/out-check1.txt".split("\\s+"); 
//	args = "war-sumtar -show true   -war $/migrate-tabngo.jar   -cmp $/out-check1.txt     -out  $/out-done1.txt".split("\\s+"); 
//	args = "folder-sum -show true   -in c:/opt/apps   -out  $/out-check2.txt".split("\\s+"); 
//	args = "folder-sumtar -show true   -in c:/opt/apps   -cmp $/out-check2.txt    -out  $/out-done2.txt".split("\\s+"); 
//	args = "mongo-sum -show true  -db data-trenzi105   -out $/out-check3.txt".split("\\s+");
//	args = "mongo-sumtar -show true  -db data-trenzi105   -cmp $/out-check3.txt -out $/out-done3.txt".split("\\s+");
//	args = ("mongo-dsum -show true  -db TabnGo -out $/out-dump.txt  "
//			+ "-rfrom http://192.168.100.60:7070/ "
//			+ "-rto http://hoang.dinh.hung.com:7070/").split("\\s+");

//	args = CommandTarget.debugParams("cmp-files "
//	+ "-show true "
//	+ "-out $/out1.txt "
//	+ "-sfile $/migrate-tabngo.jar "
//	+ "-tfile $/migrate-tabngo.jar");
	
	public static void main(String[] args) throws Exception 
	{
//		args = CommandTarget.debugParams("mongo-replace -show true -db TabnGo  -out $/out-dump.txt "
//				+ "-rfrom http://103.200.20.192:7070/ "
//				+ "-rto http://103.200.20.202:7070/");		
		
		TargetController tar = findTargetController(args[0]);
		
		tar.mongoArgs = new DatabaseParams(args);
		tar.printHeader();
		tar.processRequest();
		tar.printFooter();
	}


}
