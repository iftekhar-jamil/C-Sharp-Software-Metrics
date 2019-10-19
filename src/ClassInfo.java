import java.util.ArrayList;

public class ClassInfo {
	double MHF = 0;
	double AHF = 0;
	int TotalMethod = 0;
	int TotalAttribute = 0;
	int publicMethod, privateMethod, protectedMethod = 0;
	int publicAttribute=0, privateAttribute=0, protectedAttribute = 0;
	int HiddenMethod = 0;
	int LineOfCode = 0;
	int LineofComment = 0;
	int NOC = 0;
	int CBO = 0;
	String parent;
	
	public ClassInfo(String Filename) {
		// TODO Auto-generated constructor stub
		System.out.println("Report for File "+Filename);
		
	}
	
	public String toString() {
		return "MHF = "+MHF+"\n"
				+ "AHF = "+AHF+"\n" 
				+ "Total Method = "+ TotalMethod+"\n"
				+  "Total Attribute = "+ TotalAttribute+"\n"
				+ "Line of code " + LineOfCode+ "\n"
				+ "Line of Comment " + LineofComment+"\n"
				+ "Number of Children " + NOC+"\n"
				+  "Coupling Between Objects " + CBO+"\n";

				
	}
}		
