package com.uc.qiujw;

public class Main {
	static public void main(String[] args){
		try{
			if ( args[0].toLowerCase().equals("wordcount") ){
				WordCount.main(args);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
