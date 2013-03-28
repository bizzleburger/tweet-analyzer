package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import twitter4j.Status;



/**This class is used to contain the words used to search and determine political leanings.
 * 
 * @author Brandon Marcellus Adams
 *
 */
public class Dictionary {

	private ArrayList<DictWord> dictionary;
	private ArrayList<PolWord> polWords;
	private ArrayList<Word> negWords;
	final String dictFile = "twitter_sentiment_list - Sheet 1.csv";
	final String peopleFile = "Senators and Congressmen.csv";
	final String wordsFile = "words-and-affiliations.csv";
	final String negWordsFile = "negation-words.csv";
	
	private Dictionary(){
		dictionary = new ArrayList<DictWord>();
		polWords = new ArrayList<PolWord>();
		negWords = new ArrayList<Word>();
		readWords();
	}
	
	public static Dictionary getInstance(){
		return new Dictionary();
	}
	
	
	
	private void readWords(){
		try{		
		
		BufferedReader br = new BufferedReader( new FileReader(dictFile));
		String strLine = "";
		StringTokenizer st = null;
		
		try{
			
			while( (strLine = br.readLine()) != null){
				st = new StringTokenizer(strLine, ",");
				DictWord tWord = new DictWord(st.nextToken(), null, 
						Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
				dictionary.add(tWord);  
			}
			
		}
		catch(Exception e){
			System.out.println("Exception while reading Twitter dictionary csv file: " + e);
			e.printStackTrace();
		}
		try{
			
			br = new BufferedReader( new FileReader(negWordsFile));
			strLine = "";
			st = null;
			//StringTokenizer tempSt;
			
			while( (strLine = br.readLine()) != null){
				//st = new StringTokenizer(strLine, ",");
				Word tWord = new Word(strLine);;
				negWords.add(tWord);
			}
			
		}
		catch(Exception e){
			System.out.println("Exception while negation words csv file: " + e);
			e.printStackTrace();
		}
		
		try{
			br = new BufferedReader( new FileReader(wordsFile));
			strLine = "";
			st = null;
			
			while( (strLine = br.readLine()) != null){
				st = new StringTokenizer(strLine, ",");
				PolWord tWord = new PolWord(st.nextToken(), st.nextToken(),
						Integer.parseInt(st.nextToken()));
				polWords.add(tWord);  
			}
			
		}
		catch(Exception e){
			System.out.println("Exception while reading political words csv file: " + e);
			e.printStackTrace();
		}
		
		Collections.sort(dictionary);
		Collections.sort(negWords);
		Collections.sort(polWords);
		
		}
		catch(Exception e){
			System.out.println("Exception while opening csv file: " + e);
			e.printStackTrace();
		}
	}

	/**
	 * @return the dictionary
	 */
	public ArrayList<DictWord> getDictionary() {
		return dictionary;
	}

	/**
	 * @return the people
	 */
	public ArrayList<Word> getNegWords() {
		return negWords;
	}

	/**
	 * @return the polWords
	 */
	public ArrayList<PolWord> getPolWords() {
		return polWords;
	}
}
