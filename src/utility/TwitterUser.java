package utility;

import java.util.ArrayList;

public class TwitterUser {
	
	private String userName;
	private long userId;
	private ArrayList<AnalyzedTweet> tweetList;
	private Politic party;
	
	public TwitterUser(String n, long id){
		userName=n;
		userId=id;
		tweetList = new ArrayList<AnalyzedTweet>();
	}
	
	public String getUserName(){ return userName; }
	public long getUserId(){ return userId; }
	public Politic getPolitic(){ return party; }
	
	private void calculateParty(){
		
		int numRep=0;
		int numDem=0;
		
		if(tweetList.isEmpty()){
			party=Politic.NONE;
		} else if(tweetList.size()==1) {
			party=tweetList.get(0).getPolitic();
		} else{
			for(AnalyzedTweet at: tweetList){
				if(at.getPolitic()==Politic.DEMOCRAT) numDem++;
				if(at.getPolitic()==Politic.REPUBLICAN) numRep++;
			}
		
			if(numDem>numRep) party=Politic.DEMOCRAT;
			if(numDem<numRep) party=Politic.REPUBLICAN;
			//else party=Politic.NONE;
		}
	}
	
	public void addTweet(AnalyzedTweet at){
		tweetList.add(at);
		calculateParty();
	}

}
