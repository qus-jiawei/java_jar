package com.uc.qiujw;

import java.util.Arrays;

public class Main {
	static public void main(String[] args){
		try{
			System.out.println(Arrays.toString(args));
			if ( args[0].toLowerCase().equals("wordcount") ){
				WordCount.main(Arrays.copyOfRange(args, 1, args.length));
			}
			if ( args[0].toLowerCase().equals("mkdir") ){
				Mkdir.main(Arrays.copyOfRange(args, 1, args.length));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
