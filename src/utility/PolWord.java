package utility;

public class PolWord extends Word {
	
	private int sentVal;
	
	public PolWord(){
		super("", "");
		sentVal = 0;
	}
	
	public PolWord(String s){
		super(s, "");
		sentVal = 0;
	}
	
	public PolWord(String s, String pol){
		super(s, pol);
		sentVal = 0;
	}
	
	public PolWord(String s, String pol, int n){
		super(s, pol);
		sentVal = n;
	}
	
	public PolWord(PolWord word){
		super(word.getText(), word.getPolitic());
		sentVal = word.sentVal;
	}
	
	public int getSentValue(){ return sentVal; }
}
