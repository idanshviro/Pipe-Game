package cacheManager;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import solver.Solution;

public class File implements CacheManager{
//    public static HashMap<String, String> solutionHmap = new HashMap<String, String>(); //// TODO CHECK IF WE NEED IT


   
	@Override
	public void save(String problem, Solution sol) throws FileNotFoundException {
		if (sol == null) {
			try (PrintWriter out = new PrintWriter(problem.hashCode()+".txt")) {
				out.print("null");
				out.close();
			}
		}
		else {
			try (PrintWriter out = new PrintWriter(problem.hashCode()+".txt")) {
				for(int i=0;i<sol.getActionList().size();i++) {
					String str = (sol.getActionList().get(i)).toString();
					if(i==sol.getActionList().size()-1) { 
						out.print(str);
					}
					else{out.println(str);}
				}
				out.close();
			}
		}
	}
	
	
  @Override      
	public boolean exists(String problem) {
		java.io.File f = new java.io.File(problem.hashCode() + ".txt");	
		return f.isFile(); //isFile checks ifExists()&&ifNormalFile()
	}
    
    @Override
	public String load(String problem) {
		 Scanner scanner = null;
		try {
			scanner = new Scanner( new java.io.File(problem.hashCode()+".txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String text = scanner.useDelimiter("\\A").next();
		scanner.close(); // Put this call in a finally block
		return text;
	}	
}
