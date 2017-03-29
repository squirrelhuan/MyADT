package com.huan.myadt.test.image;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.huan.myadt.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;


public class ZipImage {
	
	public Bitmap imageZoom(Context context) { 
		Drawable drawable = context.getResources().getDrawable(R.drawable.test);
		System.out.println("a");
		Bitmap bitMap = ((BitmapDrawable)drawable).getBitmap();
		/*InputStream ins= ZipImage.class.getClassLoader().getResourceAsStream("exception.xlsx");
		File file = new File();
		//InputStream ins = (new AnnexXML()).getClass().getResourceAsStream("../../annexInternet.xml");
		 ///  File f=new File("annexInternet.xml");
		 inputstreamtofile(ins, f);*/
		 
        //图片允许最大空间   单位：KB 
        double maxSize = 8.81; 
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）   
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos); 
        byte[] b = baos.toByteArray(); 
        int length = b.length;
        bitMap.compress(Bitmap.CompressFormat.JPEG, (int)maxSize/length, baos); 
		Toast.makeText(context, "size:"+bitMap.getRowBytes() * bitMap.getHeight()/1024, Toast.LENGTH_SHORT).show();
        //将字节换成KB 
        double mid = length/1024; 
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩 
        if (mid > maxSize) { 
                //获取bitmap大小 是允许最大大小的多少倍 
                double i = mid / maxSize; 
                //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小） 
                bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i), 
                                bitMap.getHeight() / Math.sqrt(i)); 
        }
		return bitMap; 
	}
	
	public static void inputstreamtofile(InputStream ins,File file) {
	  try {
	   OutputStream os = new FileOutputStream(file);
	   int bytesRead = 0;
	   byte[] buffer = new byte[8192];
	   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
	    os.write(buffer, 0, bytesRead);
	   }
	   os.close();
	   ins.close();
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 }

	/**  
	  * 保存文件  
	  * @param bm  
	  * @param fileName  
	  * @throws IOException  
	  */    
	 public void saveFile(Bitmap bm, String fileName) throws IOException {   
	  //String path = getSDPath() +"/revoeye/";       
	     File dirFile = new File(fileName);    
	     if(!dirFile.exists()){    
	         dirFile.mkdir();    
	     }    
	     File myCaptureFile = new File(fileName);    
	     BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));    
	     bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);    
	     bos.flush();    
	     bos.close();    
	 }  
	
        /***
         * 图片的缩放方法
         *
         * @param bgimage
         *            ：源图片资源
         * @param newWidth
         *            ：缩放后宽度
         * @param newHeight
         *            ：缩放后高度
         * @return
         */ 
        public static Bitmap zoomImage(Bitmap bgimage, double newWidth, 
                        double newHeight) { 
                // 获取这个图片的宽和高 
                float width = bgimage.getWidth(); 
                float height = bgimage.getHeight(); 
                // 创建操作图片用的matrix对象 
                Matrix matrix = new Matrix(); 
                // 计算宽高缩放率 
                float scaleWidth = ((float) newWidth) / width; 
                float scaleHeight = ((float) newHeight) / height; 
                // 缩放图片动作 
                matrix.postScale(scaleWidth, scaleHeight); 
                Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, 
                                (int) height, matrix, true); 
                return bitmap; 
        } 
}
