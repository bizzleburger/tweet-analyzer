package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.*;


import twitter4j.FilterQuery;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.*;
import utility.*;

public class TweetAnalyzer {

	private Logger logger;
	private Dictionary dictionary;
	private Twitter twitter;
	private TwitterStream stream;
	private ArrayList<AnalyzedTweet> storedTweets;
	private final String analyzedTweets = "AnalyzedTweets.csv";
	
	public TweetAnalyzer(){
		
		dictionary = Dictionary.getInstance();
		logger = Logger.getLogger(TweetAnalyzer.class.getName());
		try {
			FileHandler fh = new FileHandler(analyzedTweets);
			logger.addHandler(fh);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("Ij1qPiOgE1bIY5oSoLOC2Q")
		  .setOAuthConsumerSecret("vppVynOTVDNFOSN982UQ330zERd7sSMDRQ5ZZYBiTw")
		  .setOAuthAccessToken("85486630-F4KKMR7zxmenjWlkEkhMPMMujohTzx47pI2y4BJ8")
		  .setOAuthAccessTokenSecret("BFDJyCBocXYmIudKSRCyMy3oYpqbWxKMEeMGvCbvc");
		
		twitter = new TwitterFactory(cb.build()).getInstance();
		//twitter = new TwitterFactory().getInstance();
		
		stream = new TwitterStreamFactory().getInstance();
		
		stream.setOAuthConsumer("Ij1qPiOgE1bIY5oSoLOC2Q", 
				"vppVynOTVDNFOSN982UQ330zERd7sSMDRQ5ZZYBiTw");
		try {
			stream.setOAuthAccessToken(twitter.getOAuthAccessToken());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		storedTweets = new ArrayList<AnalyzedTweet>();
	}
	
	private void fetchQueryTweets(){
		Query query = new Query();
		query.setQuery("Republican OR Democrat OR Obama OR Romney OR GOP2012 OR GOP");
		
		try{
			QueryResult result = twitter.search(query);
			//System.out.println("Count: " + result.getTweets().size());
			for (Tweet tweet : result.getTweets()){					
				User u = twitter.showUser(tweet.getFromUserId());
				//System.out.println(u.getScreenName());
				TwitterUser tu = new TwitterUser(u.getScreenName(), u.getId());
				
				for(Status s: twitter.getUserTimeline(tu.getUserName(), new Paging(1, 50))){
					StringTokenizer st = new StringTokenizer(s.getText());
					AnalyzedTweet at = analyzeTweet(s.getId(), s.getUser().getScreenName(),
							s.getText(), st);
					if(at.getSentiment()!=.5 && at.getPolitic()!=Politic.NONE){
						tu.addTweet(at);
						storedTweets.add(at);
						logger.log(Level.ALL, at.toString());
						System.out.println(at.toString());
					}
				}
				System.out.println(tu.getUserName() + "," + tu.getPolitic());
				//StringTokenizer st = new StringTokenizer(tweet.getText());
				//StringTokenizer stu= new StringTokenizer(u.getDescription());
				//AnalyzedTweet at = analyzeTweet(tweet.getId(), tweet.getFromUser(),
				//		tweet.getText(), st);
			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void fetchTweets(){
		
		String[] track = { "Obama", "Republican", "Democrat", "GOP", "GOP2012", "Romney" };
		FilterQuery fQuery = new FilterQuery()
								.track(track);
		
		//BufferedWriter out=null;
		
		//StatusStream results;
		
		StatusListener listener = new StatusListener() {
			
			@Override
			public void onException(Exception ex) { }
			
			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) { }
			
			@Override
			public void onStatus(Status status) {
				try{
					//out = new BufferedWriter(new FileWriter(new File(analyzedTweets)));
					//System.out.println("Tweet: " + status.getText());
					User u = status.getUser();
					StringTokenizer st = new StringTokenizer(status.getText());
					//System.out.println(u.getDescription());
					//StringTokenizer stu= new StringTokenizer(u.getDescription());
					AnalyzedTweet at = analyzeTweet(status.getId(), status.getUser().getScreenName(),
							status.getText(), st);
					if(at.getSentiment()!=.5 && at.getPolitic()!=Politic.NONE){
						storedTweets.add(at);
						logger.log(Level.ALL, at.toString());
						//out.append(at.toString() + "\n");
						//out.close();
						System.out.println(at.toString());
					}
				} catch (Exception e) { e.printStackTrace(); }
			}
			
			@Override
			public void onScrubGeo(long userId, long upToStatusId) { }
			
			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) { }
		};		
		
		stream.addListener(listener);
		
		System.out.println("--------------Aggragated user tweets below---------------");
		fetchQueryTweets();	
		//stream.sample();
		System.out.println("--------------Streaming tweets below---------------");
		stream.filter(fQuery);
		//results.next(listener);
	}
	
	public AnalyzedTweet analyzeTweet(long id, String uName, String text, StringTokenizer tweet){ 
		//StringTokenizer uDesc){
		
		
		//StringTokenizer tweet = new StringTokenizer(status.getText());
		/**
		 * (e^(sum each word(sad - happy)) + 1)^(-1)
		 */
		
		double sum=0;
		int numDem=0;
		int numRep=0;
		int neg=0;
		
		ArrayList<DictWord> dict = dictionary.getDictionary();
		ArrayList<PolWord> polWords = dictionary.getPolWords();
		ArrayList<Word> negWords = dictionary.getNegWords();
		//ArrayList<People> people = dictionary.getPeople();
		
		while(tweet.hasMoreTokens()){
			//String s = tweet.nextToken().replace("\\W", "");
			String s = tweet.nextToken().replace("#", "")
										.replace(",", "");
			if(s.contains("\\w")){
				
			}
			DictWord tempD = new DictWord(s);
			PolWord tempP = new PolWord(s);
			
			//People tempP = new People(s);
			
			//This is the calculation of the sentiment value
			
			if(dict.contains(tempD)){
				DictWord w = dict.get(dict.indexOf(tempD));
				//System.out.println("\t" + w.toString());
				double exp = w.getNegValue() - w.getPosValue();
				sum +=exp;
			}
			
			if(negWords.contains(tempD)) { 
				//System.out.println(tempD);
				neg++;
			}
			
			//This is where the calculation of the political value happens
			
			if(polWords.contains(tempP)){
				PolWord w = polWords.get(polWords.indexOf(tempP));
				//System.out.println("\t" + w.getText());
				if(w.getPolitic().equalsIgnoreCase("R")) numRep+=w.getSentValue();
				if(w.getPolitic().equalsIgnoreCase("D")) numDem+=w.getSentValue();
			}
		}
		
		double total = Math.pow((Math.exp(sum) + 1),-1);
		if(neg%2==1) total = 1 - total;
		//System.out.println("\t\tTotal val for tweet: " + total);
		
		/**
		 * This is where the political stuff will go.
		 */
		
		AnalyzedTweet at=null;
		text = text.replace(",", "");
		
		if(numDem>numRep && total>.5) at = new AnalyzedTweet(id, uName, text, Politic.DEMOCRAT, total);
		else if(numDem>numRep && total<.5) at = new AnalyzedTweet(id, uName, text, Politic.REPUBLICAN, total);
		else if(numDem<numRep && total>.5) at = new AnalyzedTweet(id, uName, text, Politic.REPUBLICAN, total);
		else if(numDem<numRep && total<.5) at = new AnalyzedTweet(id, uName, text, Politic.DEMOCRAT, total);
		else at = new AnalyzedTweet(id, uName, text, Politic.NONE, total);
		return at;
	}
	
	public static void main(String[] args){
		TweetAnalyzer test = new TweetAnalyzer();
		test.fetchTweets();
		
		String dictFile = "MorgansTweetClassifications.csv";
		
		try{
			BufferedReader br = new BufferedReader( new FileReader(dictFile));
			String strLine = "";
			StringTokenizer st = null;
			StringTokenizer st2 = null;
			
			/*while((strLine = br.readLine()) != null){
				st = new StringTokenizer(strLine, ",");
				String text = st.nextToken();
				//String pol = st.nextToken();
				st2 = new StringTokenizer(text, " ");
				/*while(st2.hasMoreTokens()){
					String tok = st2.nextToken();
					if(tok.startsWith("#"))
						tok = tok.replaceFirst("#", "");
				}
				AnalyzedTweet at = test.analyzeTweet(1, "MorgansTweet", strLine, st2);
				if(at.getSentiment()!=.5 && at.getPolitic()!=Politic.NONE){
					//logger.log(Level.ALL, at.toString());
					//out.append(at.toString() + "\n");
					//out.close();
					//System.out.println(at.toString() + "," );
				}
			}*/
		}
		catch(Exception e){
			System.out.println("Exception while reading Morgan's csv file: " + e);
			e.printStackTrace();
		}

	}
	
}
