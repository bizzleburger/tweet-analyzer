package utility;

public class DictWord extends Word {

	private double posValue;		//The relative value of the word, positive/negative
	private double negValue;					//0 means neutral, neg and pos are self explainatory, duh
	
	public DictWord(){
		super("", "");
		posValue = 0;
		negValue = 0;
	}
	
	public DictWord(String s){
		super(s, "");
		posValue = 0;
		negValue = 0;
	}
	
	public DictWord(String s, String pol){
		super(s, pol);
		posValue = 0;
		negValue = 0;
	}
	
	public DictWord(String s, String pol, double p, double n){
		super(s, pol);
		posValue = p;
		negValue = n;
	}
	
	public DictWord(DictWord word){
		super(word.getText(), word.getPolitic());
		posValue = word.posValue;
		negValue = word.negValue;
	}

	public double getPosValue() {
		// TODO Auto-generated method stub
		return posValue;
	}

	public double getNegValue() {
		// TODO Auto-generated method stub
		return negValue;
	}
	
}
