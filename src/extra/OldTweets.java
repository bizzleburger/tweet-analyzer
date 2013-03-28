package extra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class OldTweets {

	public static void main(String[] args){
		String dictFile = "C:/Users/Brandon/Documents/Workspace/CMSC491Twitter/src/MorgansTweetClassifications.csv";
		String hashTagFile = "C:/Users/Brandon/Documents/Workspace/CMSC491Twitter/src/HashTags.csv";
		ArrayList<String> list = new ArrayList<String>();
		
		try{
			BufferedReader br = new BufferedReader( new FileReader(dictFile));
			String strLine = "";
			StringTokenizer st = null;
			StringTokenizer st2 = null;
			
			BufferedWriter out = new BufferedWriter(new FileWriter(hashTagFile));
			
			while((strLine = br.readLine()) != null){
				st = new StringTokenizer(strLine, ",");
				st2 = new StringTokenizer(st.nextToken(), " ");
				while(st2.hasMoreTokens()){
					String tok = st2.nextToken();
					if(tok.startsWith("#"))
						tok = tok.replaceFirst("#", "");
					if(!list.contains(tok)){
						list.add(tok);
						System.out.println(tok);
						out.write(tok + "\n");
					}
					/*String tok = st2.nextToken();
					if(tok.startsWith("#")){
						tok = tok.replaceFirst("#", "");
						if(!list.contains(tok)){
							list.add(tok);
							System.out.println(tok);
							out.write(tok + "\n");
						}
					}*/
				}
			}
			out.close();
		}
		catch(Exception e){
			System.out.println("Exception while reading Twitter dictionary csv file: " + e);
			e.printStackTrace();
		}
	}
	
	
}
