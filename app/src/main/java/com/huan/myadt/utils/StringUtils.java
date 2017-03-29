package com.huan.myadt.utils;

import java.io.BufferedReader;

public class StringUtils {
	
	public static String readLine(int lineNumber,BufferedReader reader)throws Exception{
	       String line="";
	       int i=0;
	       while(i<lineNumber){
	           line=reader.readLine();
	           i++;
	       }
	       return line;
	   }
}
