package com.huan.myadt.test.image;

public class FileSize {

	
	/*public void zipFile(File imageFile,String path_new){
		long size = imageFile.getTotalSpace();
        double scale = 1.0d ;
        if(size >= 200*1024){
            if(size > 0){
                scale = (200*1024f) / size  ;
            }
        }
		 try {
	            //added by chenshun 2016-3-22 注释掉之前长宽的方式，改用大小
//	            Thumbnails.of(filePathName).size(width, height).toFile(thumbnailFilePathName);
	            if(imageFile.getTotalSpace() < 8.08*1024){
	                Thumbnails.of(imageFile.getAbsoluteFile().toString()).scale(1f).outputFormat("png").toFile(path_new);
	            }else{
	                Thumbnails.of(imageFile.getAbsoluteFile()).scale(1f).outputQuality(scale).outputFormat("jpg").toFile(path_new);
	            }
	            
	        } catch (Exception e1) {
	        	e1.printStackTrace();
	        }
	}*/
}
