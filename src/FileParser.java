import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileParser {
	
	public ArrayList<String> filesToParser = new ArrayList<String>(); 
	public ArrayList<ClassInfo> classInfos = new ArrayList<ClassInfo>(); 
	public int DIT=0;
	
	public void printFnames(String sDir) throws IOException{
		  File[] faFiles = new File(sDir).listFiles();
		  for(File file: faFiles){
			String name = file.getName();
			String lastFourDigits = name.substring(name.length() - 2);
//			System.out.println((lastFourDigits));
		    if(lastFourDigits.equals("cs")){
		      //System.out.println(file.getAbsolutePath());
		      filesToParser.add(file.getAbsolutePath());
		    }
		    
		    if(file.isDirectory()){
		      printFnames(file.getAbsolutePath());
		    }
		  }
		  readFiles();
		  calculateClassMetrics();
		  calculateProjectMetrics();
		  printClassMetrics();
		  printProjectMetrics();
		  
		  
	}
	
	private void calculateProjectMetrics() {
		
		
	}

	private void calculateClassMetrics() throws IOException {
		calculateNOC();
		calculateCBO();
		
	}

	private void calculateCBO() throws IOException {
		// TODO Auto-generated method stub
		for (String file : filesToParser) {
			File newfile = new File(file);
			BufferedReader br = new BufferedReader(new FileReader(newfile)); 
			String st; 
			int couple = 0;  
		    while ((st = br.readLine()) != null) { 
		    	String temp[] = st.split(" ");
		    	couple += findCouple(temp); 
		    }
		    FindInfo(file).CBO = couple;
		} 
			
		
	}

	private int findCouple(String[] temp) {
		// TODO Auto-generated method stub
		int val=0;
		for(int i =0; i<temp.length; i++) {
			if(filesToParser.contains(temp[i]))
				val++;
		}
		return val;
	}

	private void calculateNOC() {
		// TODO Auto-generated method stub
		for(int i=0; i<classInfos.size(); i++) {
			if(classInfos.get(i).parent==null) continue;
			else FindInfo(classInfos.get(i).parent).NOC++;
		}
	}

	private void printClassMetrics() {
		// TODO Auto-generated method stub
		for(int i = 0; i<classInfos.size(); i++) {
			ClassInfo info = classInfos.get(i);
			if(info.TotalAttribute>0 && info.TotalMethod>0)
				  System.out.println(info.toString());
			else System.out.println("\n\nNothing to measure\n\n");
		}
	}

	private void printProjectMetrics() {
		// TODO Auto-generated method stub
		System.out.println("DIT -->"+DIT);
		
	}

	public void readFiles() throws IOException {
		
		for(int i=0; i<filesToParser.size(); i++) {
			parseFile(filesToParser.get(i));
//			if(i==1) break;
		}
		DIT = calculateDIT();
		   
	}
	
	
	public void parseFile(String FileName) throws IOException {
//		System.out.println("Parsing file "+FileName);
		ClassInfo info = new ClassInfo(FileName);
		File newfile = new File(FileName);
		BufferedReader br = new BufferedReader(new FileReader(newfile)); 
		  String st; 
		  
		  while ((st = br.readLine()) != null) { 
		  
			  // Check for methods
			  if((st.contains("public") || st.contains("private") || st.contains("protected")) && st.contains("(")) {
				  if(st.contains("public")) info.publicMethod++;
				  if(st.contains("private")) info.privateMethod++;
				  if(st.contains("protected")) info.protectedMethod++;
			  }
			  
			  
			  // Check for attributes
			  if(!st.contains("class") || st.contains("public") || st.contains("private") || st.contains("protected")) {
				  if(st.contains("public")) info.publicAttribute++;
				  if(st.contains("private")) info.privateAttribute++;
				  if(st.contains("protected")) info.protectedAttribute++;
			  }
			  
			  // Check for inheritance
			  if(st.contains(":")) {
				  String arr[] = st.split(":")[1].split(",");
				  for(int i =0; i<arr.length; i++) {
					  if(arr[i].charAt(0)!='I')
							  info.parent= arr[i];
				  }
			  }
			  
			  // Traditional metrics
			  info.LineOfCode++;
			  if(st.contains("\\")) info.LineofComment++;
			  classInfos.add(info);
		  }
		  
		  info.TotalMethod = info.publicMethod+info.privateMethod+info.protectedMethod;
		  info.TotalAttribute = info.publicAttribute+info.privateAttribute+info.protectedAttribute;
		  info.AHF = (double)info.privateAttribute/(double)info.TotalAttribute;
		  info.MHF = (double)info.privateMethod/(double)info.TotalMethod;
		  
		  
		  
	}

	private int calculateDIT() {
		int mx = 0;
		for(int i=0; i<classInfos.size(); i++) {
			ClassInfo temp = FindInfo(classInfos.get(i).parent);
		    if(temp==null) continue;  
			while(temp!=null) {
				mx++;
				temp = FindInfo(temp.parent);
			}
		}
		return mx;
	}

	@SuppressWarnings("unlikely-arg-type")
	private ClassInfo FindInfo(String parent) {
		// TODO Auto-generated method stub
		for(int i=0; i<classInfos.size(); i++)
			if(classInfos.get(i).equals(parent))
				return classInfos.get(i);
		return null;
	}
}
