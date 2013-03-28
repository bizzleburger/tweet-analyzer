package utility;


/**This class contains a word to be insterted into the Dictionary, along with its
 * positivity rating and political leaning.
 * @author Brandon Marcellus Adams
 *
 */
public class Word implements Comparable<Word>{

	private String text;	//The text of the word
	private String politic;	//The associated party; Democrat or Republican
	
	
	public Word(){
		text = "";
		politic = "";
	}
	
	public Word(String s){
		text = s;
		politic = "";
	}
	
	public Word(String s, String pol){
		text = s;
		politic = pol;
	}
	
	public String getText(){ return text; }
	public String getPolitic(){ return politic; }
	public void setText(String t){ text=t; }
	public void setPolitic(String p){ politic=p; }

	@Override
	public int compareTo(Word w) {
		
		Word temp = (Word) w;
		
		/*if(text.equalsIgnoreCase(temp.text) && value == temp.value){
			return 0;
		}
		
		if(text.compareTo(temp.text) < 0){
			if(value < temp.value){
				return -1;
			} 
		}*/
		
		return text.compareTo(temp.text);
	}
	
	@Override
	public boolean equals(Object o){
		
		if(this == o) return true;
		if (!(o instanceof Word)) return false;
		
		Word w = (Word) o;
		//String s = w.text.replace("\\W", "");
		return text.equalsIgnoreCase(w.text);		
	}
	
	@Override
	public String toString(){
		return (politic == null ? "Text: " + text
				: "Text: " + text + ", Politic: " + politic);
	}
	
	
	
}
