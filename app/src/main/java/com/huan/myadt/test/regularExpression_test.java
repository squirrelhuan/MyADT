package com.huan.myadt.test;

import android.test.AndroidTestCase;

public class regularExpression_test extends AndroidTestCase {

	
	public String test1(){
		String a = "Caused by: java.lang.NullPointerException: ";
		System.out.println(a);
		//System.out.println(a.matches(".*\\.(?i)Exception"));
		return a;
	}
}
