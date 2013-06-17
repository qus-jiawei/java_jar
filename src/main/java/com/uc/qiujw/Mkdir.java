package com.uc.qiujw;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.util.GenericOptionsParser;

public class Mkdir implements Run{
	public int run(String[] args) {
		try {
			Mkdir.main(args);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	public String getKey() {
		return "mkdir";
	}
	
	static public void main(String[] args)throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		String mask = otherArgs[0];
		FsPermission permission = new FsPermission(mask);
		String path = otherArgs[1];
		FileSystem fs = FileSystem.get(conf);
		Path outPath = new Path(path);
		System.out.println("mask:"+mask+" path:"+path);
		fs.mkdirs(outPath, permission);
	}

	
}
