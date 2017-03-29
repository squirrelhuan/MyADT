package com.huan.aidl;
import com.huan.bean.MyLogBean;
//MyADT_AIDL
interface MyAdtHelper {

	//boolean sendMessage(String logtext);
	boolean sendMessage(in MyLogBean myLogBean);
}