package com.huan.myadt.test.excel;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

//import org.junit.Test;


public class ExcelPull<T> {
	Field[] fs;
private Context context;
	
List<String> names = new ArrayList<String>();
	public List<HashMap<String, Object>> main(Context context,T t,List<String> names) {
		this.context = context;
		URL file = ExcelPull.class.getClassLoader().getResource(
				"exception.xlsx");
		//System.out.println(file.getPath());
		// 得到类对象
		/*Class userCla = t.getClass();
		fs = userCla.getDeclaredFields();*/
		this.names = names;
		return parseExcel();
	}
	
	private List<HashMap<String, Object>> parseExcel() { 
		List<HashMap<String, Object>> result = null;
        try {  
            Workbook workbook = null;  
            try {  
                System.out.println("aaa");
               // File file=new File(Environment.getExternalStorageDirectory()+File.separator+"test.xls");  
               // URL url = ExcelPull.class.getClassLoader().getResource("exception.xlsx");
               // InputStream ins = context.getResources.getResourceAsStream("exception.xls");
                AssetManager assets = null;  
                assets = context.getAssets(); 
                //打开指定资源对应的输入流  
                InputStream ins = assets  
                    .open("exception.xls");
               /* File file=new File("exception.xlsx");
                inputstreamtofile(ins, file);*/
             // File file=new File(Environment.getExternalStorageDirectory()+File.separator+"exception.xls");  
                //System.out.println(file);
                workbook = Workbook.getWorkbook(ins);  
            } catch (Exception e) {  
            	System.out.println("File not found");
                throw new Exception("File not found");  
            }  
            System.out.println("ccc");
            //得到第一张表  
            Sheet sheet = workbook.getSheet(0);  
            //列数  
            int columnCount = sheet.getColumns();  
            //行数  
            int rowCount = sheet.getRows();  
            //单元格  
            Cell cell = null;  
            System.out.println("ccc");
            result = new ArrayList<HashMap<String, Object>>();
            for (int everyRow = 0; everyRow < rowCount; everyRow++) {  
				HashMap<String, Object> rowList = new HashMap<String, Object>();
                for (int everyColumn = 0; everyColumn < columnCount; everyColumn++) {  
                    cell = sheet.getCell(everyColumn, everyRow);  
                    if (cell.getType() == CellType.NUMBER) {  
                        System.out.println("数字="+ ((NumberCell) cell).getValue());  
                    } else if (cell.getType() == CellType.DATE) {  
                        System.out.println("时间="+ ((DateCell) cell).getDate());  
                    } else {  
                        System.out.println("everyColumn="+everyColumn+",everyRow="+everyRow+  
                                           ",cell.getContents()="+ cell.getContents());  
                    }  
                    setValue(everyColumn,cell.getContents(), rowList);
                }  
                result.add(rowList);
            }  
            //关闭workbook,防止内存泄露  
            workbook.close();  
        } catch (Exception e) {  
            e.printStackTrace();
        }
		return result;  
  
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


/*
	private List<HashMap<String, Object>> readXlsx(String path)  {
		InputStream is;
		List<HashMap<String, Object>> result = null;
		try {
			is = new FileInputStream(path);
		
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		result = new ArrayList<HashMap<String, Object>>();
		// 循环每一页，并处理当前循环页
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// 处理当前页，循环读取每一行
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);

				int minColIx = xssfRow.getFirstCellNum();
				int maxColIx = xssfRow.getLastCellNum();
				//T rowList = (T) new Object();
				HashMap<String, Object> rowList = new HashMap<String, Object>();
				// 遍历该行，获取处理每一个cell元素
				for (int colIx = minColIx; colIx < maxColIx; colIx++) {
					XSSFCell cell = xssfRow.getCell(colIx);
					if (cell == null) {
						continue;
					}
					setValue(colIx,ExcelUtils.getStringVal(cell), rowList);
					// rowList.add(ExcelUtils.getStringVal(cell));
				}
				result.add(rowList);
			}
		}} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
*/
	
	public void setValue(int index,Object value, HashMap<String, Object> hashMap) throws IllegalAccessException,
			IllegalArgumentException {
		hashMap.put(names.get(index), value);
		/*Field f = fs[index];
		String type = f.getType().toString();//得到此属性的类型  
		 if (type.endsWith("String")) {  
            // System.out.println(f.getType()+"\t是String");  
            // f.set(f.getType(),value) ;        //给属性设值  
             hashMap.put(f.getName(), value);
            // System.out.println(hashMap);
          }else if(type.endsWith("int") || type.endsWith("Integer")){  
             //System.out.println(f.getType()+"\t是int");  
            // f.set(f.getType(),(int)value) ;       //给属性设值  
             hashMap.put(f.getName(), value);
          }else{  
             //System.out.println(f.getType()+"\t");  
          }  
		//f.set(f.getType(), value); // 给属性设值
*/	}
}


