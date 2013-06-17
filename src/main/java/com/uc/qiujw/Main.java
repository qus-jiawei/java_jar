package com.uc.qiujw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	static public Class<Run>[] runArray = new Class[]{Mkdir.class,WordCount.class,CalFairParams.class,HiveTest.class};
	static public void main(String[] args){
		try{
			List<Run> runList = new ArrayList<Run>();
			
			for(Class<Run> c:runArray){
				runList.add((Run)c.newInstance());
			}
			String key = args[0].toLowerCase();
			System.out.println(Arrays.toString(args));
			for(Run r:runList){
				if( r.getKey().equalsIgnoreCase(key) ){
					r.run(Arrays.copyOfRange(args, 1, args.length));
					break;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	static public void init(){
		
	}
}
