package com.orasi.utils;



import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.xpath.operations.String;

import java.io.*;


/**
 * Created by Adam on 12/22/2015.
 * This class works with XLS and XLSX files.
 * It uses the Apache POI Project: poi.apache.org
 */
public class Excel {

    //Variables
    private String filePath;
    private String sheetName;
    private Workbook wb;
    private Sheet sh;
    private FileOutputStream fileOut = null;
    private FileInputStream inputStream = null;
    private OPCPackage opc = null;
    private File inputFile = null;
    public int ParameterRow = 1;
    public int KeyColumn = 1;

    //Opens a workbook while using the first sheet (Usually Sheet1)
    public Excel(String filePath){
        this.filePath = filePath;
        GetWorkBook();
        //setting default sheet.
        sh = wb.getSheetAt(0);
    }

    //Opens a workbook while opening a specific sheet within a workbook
    public Excel(String filePath, String sheetName){
        this.filePath = filePath;
        this.sheetName = sheetName;
        GetWorkBook();
        SetSheet(sheetName);
    }

    //Creates a workbook and sheet
    public Excel(String filePath, String sheetName, boolean CreateNewWorkbook){
        this.filePath = filePath;
        this.sheetName = sheetName;

        if (filePath.toUpperCase().indexOf(".XLSX") > 0) {
            wb = new XSSFWorkbook(); //XLSX
        }
        else {
            wb = new HSSFWorkbook(); //XLS
        }
        Sheet sheet1 = wb.createSheet(sheetName);
        fileOut = null;
        try {
            fileOut = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GetWorkBook();
        SetSheet(sheetName);
    }

    public void SaveWorkbook(){
        // Write the output to a file
        try {
            fileOut = new FileOutputStream(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
        
    private void GetWorkBook(){
        inputFile = new File(filePath);
        try {
            inputStream = new FileInputStream(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            opc = OPCPackage.open(inputStream);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wb = WorkbookFactory.create(opc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    private Cell CreateCell(int cellrow, int cellcol) {
        Row row = sh.createRow((short)cellrow - 1);
        Cell cell = row.createCell(cellcol - 1);
        return cell;
    }

    private Cell GetCell(int cellrow, int cellcol){ //accepts row and column numbers, starting at 1
        CellReference cellReference = new CellReference(cellrow,cellcol);
        Cell cell = null;
        Row row = sh.getRow(cellReference.getRow() - 1);

        if (row == null) {
            cell = CreateCell(cellrow, cellcol);
        }
        else {
            cell = row.getCell(cellReference.getCol() - 1);
        }

        if (cell == null) {
            cell = CreateCell(cellrow, cellcol);
        }

        return cell;
    };

    private void SetSheet(String SheetName){
       sh = wb.getSheet(SheetName);
    };

    public String GetCellString (int cellrow, int cellcol){
        Cell tmpcl = GetCell(cellrow, cellcol);
        tmpcl.setCellType(Cell.CELL_TYPE_STRING);
        return tmpcl.getStringCellValue();
    }

    public double GetCellDouble (int cellrow, int cellcol){
        return GetCell(cellrow, cellcol).getNumericCellValue();
    }

    public void SetCellString(int cellrow, int cellcol, String SetToString){
        GetCell(cellrow, cellcol).setCellValue(SetToString);
    }

    public void SetCellDouble(int cellrow, int cellcol, double SetToDouble){
        GetCell(cellrow, cellcol).setCellValue(SetToDouble);
    }

    private Cell FindInRange(String Range, String SearchText){

        boolean foundVal = false;
        String[] parts = Range.split(":");
        CellReference startCR = new CellReference(parts[0]);
        CellReference endCR = new CellReference(parts[1]);
        int starRow = startCR.getRow() + 1;
        int startCol = startCR.getCol() + 1;
        int endRow = endCR.getRow() + 2;
        int endCol = endCR.getCol() + 2;
        int foundRow = 0;
        int foundCol = 0;

        for (int r = 1; r < endRow; r++) {
            if (foundVal) {
                break;
            }
            for (int c = 1; c < endCol; c++) {
                if (SearchText.equals(GetCellString(r, c))) {

                    foundVal = true;
                    foundRow = r;
                    foundCol = c;
                    break;
                }
            }
        }

        if (foundVal){
            return GetCell(foundRow,foundCol);
        }
        return null;
    }

    public int FindRow(String Range, String SearchText){
        Cell cl = FindInRange(Range, SearchText);
        return cl.getRowIndex() + 1;
    }

    public int FindColumn(String Range, String SearchText){
        Cell cl = FindInRange(Range, SearchText);
        return cl.getColumnIndex() + 1;
    }

    public int Find (String Range, String SearchText, ReturnType returnType){

        int foundOn = 0;

        switch (returnType) {
            case ROW:
                foundOn = FindRow(Range,SearchText);
                break;

            case COLUMN:
                foundOn = FindColumn(Range,SearchText);
                break;
        }
        return foundOn;
    }

    public enum ReturnType {
        ROW, COLUMN
    }


    //ToDo Create method to: Return range of values
    //ToDo Create method to: Find first empty row
    //ToDo Create method to: Find first empty column
    //ToDo Create method to: Get column by name
    //ToDo Create method to: Compare Columns
    //ToDo Create method to: Compare Row
    //ToDo Create method to: Compare Sheet
    //ToDo Create method to: Get Sheet Names
    //ToDo Create method to: Color Row
    //ToDo Create method to: Color Column
    //ToDo Create method to: Color Cell

}