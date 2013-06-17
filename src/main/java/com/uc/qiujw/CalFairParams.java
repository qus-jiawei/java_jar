package com.uc.qiujw;

import java.util.ArrayList;
import java.util.List;

public class CalFairParams implements Run{
	
	public int run(String[] args) {
		double total = Double.valueOf(args[0]);
		double share = Double.valueOf(args[1]);
		double listTotal = 0;
		List<Double> valist = new ArrayList<Double>();
		for(String temp:args[2].split(",")){
			Double i = Double.valueOf(temp);
			valist.add(i);
			listTotal += i;
		}
		print("Num","ToTal","minShare","max","weight");
		for(int i = 0;i<valist.size();i++){
			double now = valist.get(i);
			double nowRatio = now /listTotal;
			print(i,total*nowRatio,total*share*nowRatio,total*nowRatio*(2.0-share),
					total*nowRatio*(1.0-share));
		}
		return 0;
	}
	private void print(Object... temp){
		for(int i=0;i<temp.length;i++){
			System.out.print(temp[i]);
			if( i!=temp.length-1 ) System.out.print("\t\t");
		}
		System.out.print("\n");
	}
	public String getKey() {
		return "cal";
	}
}
