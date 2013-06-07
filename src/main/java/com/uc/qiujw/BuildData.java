package com.uc.qiujw;

import java.util.Random;

public class BuildData {
	static public void main(String[] args){
		int lines=Integer.valueOf(args[0]);
		for (int i = 0;i <= lines; i++){
			String line = getword(3)+' '+getword(3)+' '+getword(2);
			System.out.println(line);
		}
	}
	static Random random = new Random();
	static String getword(int length){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<length;i++){
			int t1 = random.nextInt(26);
			sb.append((char)('a'+t1));	
		}
		return sb.toString();
	}
}
