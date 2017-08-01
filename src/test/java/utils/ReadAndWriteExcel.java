package utils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Vijayakumar_G on 01-08-2017.
 */
public class ReadAndWriteExcel
{

    private HSSFWorkbook wb;
    private HSSFSheet sh;

    public ReadAndWriteExcel(String excelPath)
    {
        try
        {
            File src=new File("P:/My Project/Selenium-1/src/test/resources/DataFiles/exceldata.xls");
            FileInputStream fis=new FileInputStream(src);
            wb=new HSSFWorkbook(fis);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public String readDataFromExcel(String sheet, int row, int col)
    {
        sh = wb.getSheet(sheet);
        String value = sh.getRow(row).getCell(col).getStringCellValue();
        if(value != null)
        {
            return value;
        }
        return null;
    }

    public void writeDataToExcel(String sheet, int row, int col, String value)
    {
        sh = wb.getSheet(sheet);
        sh.getRow(row).getCell(col).setCellValue(value);
    }
}
