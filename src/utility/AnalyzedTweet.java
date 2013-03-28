package utility;

public final class AnalyzedTweet {

	private long userID;
	private String userName;
	private String text;
	private Politic pol;
	private double sentVal;
	
	public AnalyzedTweet(long userID, String uName, String text, Politic p, double val){
		this.userID = userID;
		userName = uName;
		this.text = text;
		pol = p;
		sentVal = val;
	}
	
	/**
	 * 
	 * @return the userID
	 */
	public long getuserID(){ return userID; }
	/**
	 * 
	 * @return the userName
	 */
	public String getUserName(){ return userName; }
	/**
	 * 
	 * @return the text
	 */
	public String getTweetText(){ return text; }
	/**
	 * 
	 * @return the pol
	 */
	public Politic getPolitic(){ return pol; }
	/**
	 * 
	 * @return the sentVal
	 */
	public double getSentiment(){ return sentVal; }
	
	@Override
	public String toString(){
		return (sentVal>.5 ? userName + "," + text + "," + pol + "," + "Positive"
				: userName + "," + text + "," + pol + "," + "Negative"); 
	}
	
}
