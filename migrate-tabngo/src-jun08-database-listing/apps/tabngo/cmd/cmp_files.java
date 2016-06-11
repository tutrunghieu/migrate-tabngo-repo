package apps.tabngo.cmd;

import java.io.File;

import org.nebula.mongo.ChecksumUtils;
import org.nebula.util.TargetController;

public class cmp_files  extends TargetController
{
	private File out;

	@Override
	public void processRequest() throws Exception 
	{
		File f1 = super.printParam("Source file: ", this.mongoArgs.getFile("-sfile") );
		File f2 = super.printParam("Target file: ", this.mongoArgs.getFile("-tfile") );
		out = super.printParam("Output file: ", this.mongoArgs.getFile("-out") );
		
		ChecksumUtils.compareFiles(f1,  f2, out);
	}

	@Override
	public void printFooter() throws Exception 
	{
		if(out.exists()) 
			super.showFileIf(out);		
	}
}
