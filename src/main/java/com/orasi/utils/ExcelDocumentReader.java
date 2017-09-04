package com.orasi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDocumentReader {
    private XSSFSheet excelWSheet;
    private XSSFWorkbook excelWBook;
    private XSSFCell cell;
    private String filepath;
    
    public ExcelDocumentReader(String filepath){
	this.filepath = filepath;
    }
    
    public ExcelDocumentReader(){}

    /**
     * This gets the test data from excel workbook by the sheet specified.  It returns all the data 
     * as a 2d array
     * 
     * @param	sheetName	the excel sheet
     * @version	10/16/2014
     * @author 	Justin Phlegar
     * @return 	2d array of test data
     */
    

    public Object[][] readData(String sheetName) {
    	return (readData(sheetName, -1));
    }
    
    public Object[][] readData(String filepath, String sheetName) {
	return (readData(filepath, sheetName, -1));
    }

    public Object[][] readData(String sheetName, int rowToRead) {
	return readData(filepath, sheetName, rowToRead);
    }

    public Object[][] readData(String filepath, String sheetName, int rowToRead) {

	String[][] tabArray = null;
	int totalRows =  1;

	try {

		FileInputStream excelFile = new FileInputStream(filepath);

		// Access the required test data sheet

		excelWBook = new XSSFWorkbook(excelFile);

		excelWSheet = excelWBook.getSheet(sheetName);

		int startRow = 1;

		int startCol = 0;

		int ci, cj;

		
		if (rowToRead == -1) totalRows = excelWSheet.getLastRowNum();

		// you can write a function as well to get Column count

		int totalCols = excelWSheet.getRow(startRow).getLastCellNum();

		tabArray = new String[totalRows][totalCols];

		ci = 0;

		
		cj = 0;
		
		for (int i = startRow; i <= totalRows; i++, ci++) {
			cj = 0;
			for (int j = startCol; j < totalCols; j++, cj++) {					
				tabArray[ci][cj] = getCellData(i, j);
			}
		}
		
	}catch (FileNotFoundException e) {
		System.out.println("Could not read the Excel sheet");
		e.printStackTrace();
	}catch (IOException e) {
		System.out.println("Could not read the Excel sheet");
		e.printStackTrace();
	}
	return (tabArray);
    }
    
	// This method is to read the test data from the Excel cell, in this we are
	// passing parameters as Row num and Col num
	private String getCellData(int rowNum, int colNum) {
		try {
			cell = excelWSheet.getRow(rowNum).getCell(colNum);
			String cellData = cell.getStringCellValue();
			return cellData;
		} catch (Exception e) {
			return "";
		}
	}
	
	
	public void setData(List<String> bookingLogDetails,List<String> cnfData, String filePath){
		XSSFSheet excelWSheet = null;
		XSSFWorkbook excelWBook;
		Cell cell;
		String sheetName= "BookingHistory";
		try{

			filePath=new File(getClass().getResource(filePath).getPath()).getAbsolutePath();
			FileInputStream fIn= new FileInputStream(filePath);
			boolean isSheetFound=false;
			excelWBook= new XSSFWorkbook(fIn);
			int totalSheets=excelWBook.getNumberOfSheets();
			for (int i=0;i<totalSheets;i++){
				if(excelWBook.getSheetName(i).equalsIgnoreCase(sheetName)){
					excelWSheet=excelWBook.getSheetAt(i);
					isSheetFound=true;
					break;
				}
			}


			// get the current number of rows.
			
			for(int cnfNumber=0; cnfNumber<cnfData.size();cnfNumber++){

				int rowNumber= excelWSheet.getLastRowNum()+1;
				Row row = excelWSheet.createRow(rowNumber);
				int i=0;
				for (i=0;i<bookingLogDetails.size();i++){
					cell= row.createCell(i);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(bookingLogDetails.get(i));
				}
				cell= row.createCell(i);
				cell.setCellValue(cnfData.get(cnfNumber));
			}



			FileOutputStream excelFile = new FileOutputStream(filePath);	
			excelWBook.write(excelFile);
			excelFile.close();

		}catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}

	}
	
	
	
	
}
