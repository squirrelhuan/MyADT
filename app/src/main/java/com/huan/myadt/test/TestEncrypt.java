package com.huan.myadt.test;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//import org.junit.Test;

/*
 *  TestEncrypt.java
 *  Author: MKing
 *  Last Date: 2005-11-21
 *  Description: A test progrm to encrypt a string using MD5 or SHA-1,etc.
 */
 
public class TestEncrypt{
	
     public TestEncrypt() {
	}
       
     public String Encrypt(String strSrc,String encName) {
                //parameter strSrc is a string will be encrypted,
                //parameter encName is the algorithm name will be used.
                //encName dafault to "MD5"
          MessageDigest md=null;
          String strDes=null;
 
          byte[] bt=strSrc.getBytes();
          try {
              if (encName==null||encName.equals("")) {
                  encName="MD5";
              }
              md=MessageDigest.getInstance(encName);
              md.update(bt);
              strDes=bytes2Hex(md.digest());  //to HexString
          }catch (NoSuchAlgorithmException e) {
              System.out.println("Invalid algorithm.");
              return null;
          }
                return strDes;
    }
 
    public String bytes2Hex(byte[]bts) {
         String des="";
         String tmp=null;
         for (int i=0;i<bts.length;i++) {
                    tmp=(Integer.toHexString(bts[i] & 0xFF));
                    if (tmp.length()==1) {
                        des+="0";
                    }
                    des+=tmp;
         }
                return des;
    }
    //@Test
    public void main() {
        TestEncrypt te=new TestEncrypt();
        String strSrc="res/drawable-xhdpi-v4/ic_launcher.png";
        System.out.println("Source String:"+strSrc);
        System.out.println("Encrypted String:");
        System.out.println("Use Def:"+te.Encrypt(strSrc,null));
        System.out.println("Use MD5:"+te.Encrypt(strSrc,"MD5"));
        System.out.println("Use SHA:"+te.Encrypt(strSrc,"SHA-1"));
        System.out.println("Use SHA-256:"+te.Encrypt(strSrc,"SHA-256"));
    }
}