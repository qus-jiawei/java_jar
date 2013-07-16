package com.uc.qiujw;

import org.apache.hadoop.hdfs.DFSClient;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;


public class TestLogger implements Run {

	public int run(String[] args) {
		try{
//			org.apache.hadoop.hdfs.DFSClient
//			FileSystem fs = FileSystem.get(new Configuration());
			DFSClient.LOG.warn("warn", new Exception("warn exception"));
			DFSClient.LOG.info("info", new Exception("info exception"));
			DFSClient.LOG.info("error", new Exception("error exception"));
			
			if( DFSClient.LOG instanceof org.apache.log4j.Logger ){
				Logger log = (  org.apache.log4j.Logger )  DFSClient.LOG;
				while( log.getAllAppenders().hasMoreElements() ){
					Object o = log.getAllAppenders().nextElement();
					System.out.println( o.getClass().getName() );
				}
			}
			System.out.println( DFSClient.LOG.getClass().getName() ) ;
			
		}
		catch(Exception e){
			
		}
		return 0;
	}

	public String getKey() {
		return "testlogger";
	}
	
}
