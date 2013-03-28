package utility;

import java.util.StringTokenizer;

public final class People implements Comparable<People>{
	
	private String first;
	private String last;
	private String state;
	private String politic;
	
	public People(String s){
		first="";
		last=s;
		state="";
		politic="";
	}
	
	public People(String n, String s, String p){
		StringTokenizer st = new StringTokenizer(n, " ");
		last=st.nextToken();
		first=st.nextToken();
		state=s;
		politic=p;
	}
	
	

	/**
	 * @return the first
	 */
	public String getFirst() {
		return first;
	}

	/**
	 * @return the last
	 */
	public String getLast() {
		return last;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the politic
	 */
	public String getPolitic() {
		return politic;
	}

	@Override
	public int compareTo(People p1) {
		if(!first.equalsIgnoreCase(p1.first)) return first.compareToIgnoreCase(first);
		else return last.compareToIgnoreCase(p1.last);
	}
	
	@Override
	public boolean equals(Object o){
		
		if(this == o) return true;
		if (!(o instanceof People)) return false;
		
		People p = (People) o;
		
		return last.equalsIgnoreCase(p.last);
	}
	

}
