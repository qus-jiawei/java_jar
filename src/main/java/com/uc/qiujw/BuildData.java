package com.uc.qiujw;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuildData {
	static public void main(String[] args){
//		int lines=Integer.valueOf(args[0]);
//		for (int i = 0;i <= lines; i++){
//			String line = getword(3)+' '+getword(3)+' '+getword(2);
//			System.out.println(line);
//		}
		findStat();
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
	private static final Pattern PROCFS_STAT_FILE_FORMAT = Pattern .compile(
		    "^([0-9-]+)\\s([^\\s]+)\\s[^\\s]\\s([0-9-]+)\\s([0-9-]+)\\s([0-9-]+)\\s" +
		    "([0-9-]+\\s){7}([0-9]+)\\s([0-9]+)\\s([0-9-]+\\s){7}([0-9]+)\\s([0-9]+)" +
		    "(\\s[0-9-]+){15}");
	
	static public void findStat(){
	      String str = "4058 (java) S 3897 4058 4058 0 -1 4202496 46948 1011 1 0 1136 514 0 0 20 0 28 0 25985859 1674297344 62961 18446744073709551615 1073741824 1073778416 140735463811792 140735463802928 257630961837 0 4 3 16800972 18446744073709551615 0 0 17 6 0 0 0 0 0";
	      Matcher m = PROCFS_STAT_FILE_FORMAT.matcher(str);
	      boolean mat = m.find();
	      if (mat) {
	        // Set (name) (ppid) (pgrpId) (session) (utime) (stime) (vsize) (rss)
	        System.out.println("name:"+m.group(2));
	        System.out.println("ppid:"+m.group(3));
	        System.out.println("pgrpId:"+m.group(4));
	        System.out.println("session:"+m.group(5));
	        System.out.println("utime:"+m.group(7));
	        System.out.println("stime:"+m.group(8));
	        System.out.println("vsize:"+m.group(10));
	        System.out.println("rss:"+m.group(11));
	      }
	}
}
