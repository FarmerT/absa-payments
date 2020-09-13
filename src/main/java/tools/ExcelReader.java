package tools;

import core.BaseClass;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;

public class ExcelReader extends BaseClass {

    public static void writeExcel(String sheetName, String[] dataToWrite) throws IOException {

        //Excel File path
        String filePath = System.getProperty("user.dir") + "\\testData.xlsx";

        //Create an object of File class to open xlsx file
        File file = new File(filePath);

        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);

        Workbook absaWorkbook = null;

        //create object of XSSFWorkbook class
        absaWorkbook = new XSSFWorkbook(inputStream);

        Sheet sheet = absaWorkbook.getSheet(sheetName);

        //Get the current count of rows in excel file
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        //Get the first row from the sheet
        Row row = sheet.getRow(0);

        //Create a new row and append it at last of sheet
        Row newRow = sheet.createRow(rowCount);

        //Create a loop over the cell of newly created Row
        for (int j = 0; j < row.getLastCellNum(); j++) {

            //Fill data in row
            Cell cell = newRow.createCell(j);

            cell.setCellValue(dataToWrite[j]);

        }

        //Close input stream
        inputStream.close();

        //Create an object of FileOutputStream class to create write data in excel file
        FileOutputStream outputStream = new FileOutputStream(file);

        //write data in the excel file
        absaWorkbook.write(outputStream);

        //close output stream
        outputStream.close();

    }

    //Function to read from excel
    public static String readExcel(String sheetName) throws IOException {

        String filePath = System.getProperty("user.dir") + "\\testData.xlsx";

        File file = new File(filePath);

        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);

        Workbook absaWorkbook = null;

        absaWorkbook = new XSSFWorkbook(inputStream);

        Sheet sheet = absaWorkbook.getSheet(sheetName);

        //Find number of rows in excel file
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        //Create a loop over all the rows of excel file to read it

        for (int i = 0; i < rowCount + 1; i++) {

            Row row = sheet.getRow(i);

            //Create a loop to print cell values in a row

            int j;

            for (j = 0; j < row.getLastCellNum(); j++) {

                //Print Excel data in console

               // System.out.print(row.getCell(j).getStringCellValue() + "|| ");
            }

            return row.getCell(j).getStringCellValue();
        }
        return null;
    }

}
