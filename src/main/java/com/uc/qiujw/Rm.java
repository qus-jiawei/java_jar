package com.uc.qiujw;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Rm implements Run{

	public int run(String[] args) {
		try{
			System.out.println(args[0]+" from "+args[1]+" to "+args[2]);
			int begin = Integer.valueOf(args[1]);
			int end = Integer.valueOf(args[2]);
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(conf);
			
			for ( int i = begin; i <= end; i++){
				String after = null;
				if( i <=1000 ){
					after = String.format("%04d", i);
				}
				else{
					after = Integer.toString(i);
				}
				String path = args[0]+"_"+after;
				System.out.println("delete "+path );
				fs.delete(new Path(path), true);
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return "rm";
	}

}
