import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
	
	
	public static void main(String[] args) throws IOException {
		
		FileParser parser = new FileParser();
		String path = "C:\\Jamil\\AES-Tool-master\\AES-Tool-master\\AES-Application\\AES.EncryptDecrypt";
		parser.printFnames(path);
		
		
		
//		File folder = new File(path);
//		File[] listOfFiles = folder.listFiles();
//		FileParser parser = new FileParser(); 
//		
//		for (File file : listOfFiles) {
//		    if (file.isFile()) {
////		        System.out.println(file.getName());
//		    	parser.readFile(path,file);
//		    }
//		}
	}
	
	
	
	
}


