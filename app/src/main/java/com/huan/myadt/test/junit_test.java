package com.huan.myadt.test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
import org.junit.Test;*/

public class junit_test {

	//@Test
	public void test() throws IOException {
		System.out.println("111");
		String a = "Caused by: java.lang.NullPointerException: fddfdException";
		System.out.println(a);
		System.out.println(a.matches(".*.(?i)Exception"));

		System.out.println();
		String[] words = a.split("\\b");
		for(String w : words){
			System.out.println(w);
			if(w.endsWith("Exception")){
				
			}
		}
		
		Pattern pattern1 = Pattern.compile(".*(?i)Exception");
		Matcher matcher1 = pattern1.matcher(a);

		if (matcher1.find())

		{
			System.out.println(matcher1.group(0));
		}

		
		/*BufferedReader in;
		// com.huan.myadt.test.junit_test
		Pattern pattern = Pattern.compile("//(//d{3}//)//s//d{3}-//d{4}");

		in = new BufferedReader(new FileReader("D:\\phone.txt"));

		String s;

		while ((s = in.readLine()) != null)

		{

			Matcher matcher = pattern.matcher(s);

			if (matcher.find())

			{

				System.out.println(matcher.group());

			}

		}

		in.close();*/
	}
	
	//@Test
	public void test2(){
	
		String a = "Caused by: java.lang.NullPointerException: fddfdException";
		String[] words = a.split("\\b");
		for(String w : words){
			if(w.endsWith("Exception")){
				System.out.println(w);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
