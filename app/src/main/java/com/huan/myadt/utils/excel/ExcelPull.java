package com.huan.myadt.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;


public class ExcelPull {

	private void parseExcel() {  
        try {  
            Workbook workbook = null;  
            try {  
               // File file=new File(Environment.getExternalStorageDirectory()+File.separator+"test.xls");  
                URL url = ExcelPull.class.getClassLoader().getResource("exception.xlsx");
                File file=new File(url.toString());
                workbook = Workbook.getWorkbook(file);  
            } catch (Exception e) {  
                throw new Exception("File not found");  
            }  
            //得到第一张表  
            Sheet sheet = workbook.getSheet(0);  
            //列数  
            int columnCount = sheet.getColumns();  
            //行数  
            int rowCount = sheet.getRows();  
            //单元格  
            Cell cell = null;  
            for (int everyRow = 0; everyRow < rowCount; everyRow++) {  
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
                }  
            }  
            //关闭workbook,防止内存泄露  
            workbook.close();  
        } catch (Exception e) {  
  
        }  
  
    }  
	
/*	public static void main(String[] args) throws Exception {
		URL file = ExcelPull.class.getClassLoader().getResource("exception.xlsx");
		System.out.println(file.getPath());
		readXlsx(file.getPath());
	}

	public static List<List<String>> readXlsORXlxs(String path)
			throws Exception {
		System.out.println(path);
		int length = path.split("\\.").length-1;
		System.out.println(length);
		String type = path.split("\\.")[length];
		switch (type) {
		case "xls":
			return readXls(path);
		case "xlsx":
			return readXlsx(path);
		default:
			return null;
		}
	}

	private static List<List<String>> readXls(String path) throws Exception {
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<List<String>> result = new ArrayList<List<String>>();
		// 循环每一页，并处理当前循环页
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// 处理当前页，循环读取每一行
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);

				int minColIx = hssfRow.getFirstCellNum();
				int maxColIx = hssfRow.getLastCellNum();
				List<String> rowList = new ArrayList<String>();
				// 遍历该行，获取处理每一个cell元素
				for (int colIx = minColIx; colIx < maxColIx; colIx++) {
					HSSFCell cell = hssfRow.getCell(colIx);
					if (cell == null) {
						continue;
					}
					rowList.add(ExcelUtils.getStringVal(cell));
				}
				result.add(rowList);
			}
		}
		return result;
	}

	private static List<List<String>> readXlsx(String path) throws Exception {
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<List<String>> result = new ArrayList<List<String>>();
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
				List<String> rowList = new ArrayList<String>();
				// 遍历该行，获取处理每一个cell元素
				for (int colIx = minColIx; colIx < maxColIx; colIx++) {
					XSSFCell cell = xssfRow.getCell(colIx);
					if (cell == null) {
						continue;
					}
					rowList.add(ExcelUtils.getStringVal(cell));
				}
				result.add(rowList);
			}
		}
		System.out.println(result);
		return result;
	}*/
}
