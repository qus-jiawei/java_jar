package com.uc.qiujw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.jdbc.HiveDriver;
import org.apache.hadoop.util.GenericOptionsParser;

public class HiveTest implements Run {
	private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";

	public int run(String[] args) {
		try {
			Configuration conf = new Configuration();
			GenericOptionsParser parser = new GenericOptionsParser(conf, args);
			String[] otherArgs = parser.getRemainingArgs();
			findAndPrint(conf,"mapred.job.queue.name");
			findAndPrint(conf,"my.hive.host");
			findAndPrint(conf,"my.hive.port");
			findAndPrint(conf,"my.hive.file");
			findAndPrint(conf,"mapred.job.queue.name");
			findAndPrint(conf,"mapreduce.job.queue.name");
			String queue = conf.get("mapred.job.queue.name");
//			if ( queue==null || queue.length() <1 ){
//				queue = conf.get("mapreduce.job.queue.name");
//			}
			
//			set mapred.job.queue.name=queue1;
//			findAndPrint(conf,"my.hive.file");
			String host = conf.get("my.hive.host");
//			HiveDriver.class.
//			String host = "platform31";
			Class.forName(driverName);
			String sql = "";
//			String filepath =  conf.get("my.hive.file");
			String port = conf.get("my.hive.port");
			Connection con = DriverManager.getConnection(
					"jdbc:hive://"+host+":"+port+"/default", "", "");
			Statement stmt = con.createStatement();
			String tableName = "testHiveDriverTable";
//			stmt.executeQuery("drop table " + tableName);
//			ResultSet res = stmt.executeQuery("create table " + tableName
//					+ " (a string, b string, c string)");
			// show tables 
			
			//set queue
			sql = "set mapred.job.queue.name="+queue;
			System.out.println(sql);
			stmt.executeQuery(sql);
			
			sql = "show tables '" + tableName + "'";
			System.out.println("Running: " + sql);
			ResultSet res = stmt.executeQuery(sql);
			if (res.next()) {
				System.out.println(res.getString(1));
			}
			// describe table 
			sql = "describe " + tableName;
			System.out.println("Running: " + sql);
			res = stmt.executeQuery(sql);
			while (res.next()) {
				System.out.println(res.getString(1) + "\t" + res.getString(2));
			}

			// load data into table // NOTE: filepath has to be local to the
			// hive server // NOTE: /tmp/a.txt is a ctrl-A separated file with
			// two fields per line String filepath = "/tmp/a.txt";
//			sql = "load data inpath '" + filepath + "' into table "
//					+ tableName;
//			System.out.println("Running: " + sql);
//			res = stmt.executeQuery(sql);

			// select * query
			sql = "select * from " + tableName + " limit 10";
			System.out.println("Running: " + sql);
			res = stmt.executeQuery(sql);
			while (res.next()) {
				System.out.println(String.valueOf(res.getString(1)));
			}

			// regular hive query 
			sql = "select count(1) from " + tableName+" where a='a' limit 1 ";
			System.out.println("Running: " + sql);
			res = stmt.executeQuery(sql);
			while (res.next()) {
				System.out.println(res.getString(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String getKey() {
		return "hive";
	}
	public void findAndPrint(Configuration conf,String key){
		System.out.println(key+":"+conf.get(key));	
	}
}
