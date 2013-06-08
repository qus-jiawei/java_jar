package com.uc.qiujw;

import java.util.Arrays;

public class Main {
	static public void main(String[] args){
		try{
			if ( args[0].toLowerCase().equals("wordcount") ){
				WordCount.main(Arrays.copyOfRange(args, 1, args.length));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
