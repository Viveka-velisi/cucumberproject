package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.microsoft.schemas.office.visio.x2012.main.CellType;

public class ExcelFileUtil {

	
	XSSFWorkbook wb;
	public ExcelFileUtil(String Excelpath) throws Throwable 
	{
		FileInputStream fi=new FileInputStream(Excelpath);
		wb=new XSSFWorkbook(fi);
	}
	
	public int rowcount(String Sheetname)
	{
		return wb.getSheet(Sheetname).getLastRowNum();
		}
	
	public String getCellData(String sheetname,int row,int column)
	{
	String data="";
	if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==org.apache.poi.ss.usermodel.CellType.NUMERIC)
	{
		int celldata=(int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
		data=String.valueOf(celldata);
	}
	else {
		data =wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
	}
	return data;
	}
	
	public void setcellData(String sheetname,int row,int column,String status,String WriteExcel) throws Throwable
	{
		XSSFSheet ws=wb.getSheet(sheetname);
		XSSFRow rowNum=ws.getRow(row);
		XSSFCell Cell=rowNum.createCell(column);
		Cell.setCellValue(status);
		if(status.equalsIgnoreCase("pass"))
		{
			XSSFCellStyle style=wb.createCellStyle();
			XSSFFont font=wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
			
		}
		else if(status.equalsIgnoreCase("fail"))
		{
			XSSFCellStyle style=wb.createCellStyle();
			XSSFFont font=wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("Blocked")) {
			XSSFCellStyle style=wb.createCellStyle();
			XSSFFont font=wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		FileOutputStream fo =new FileOutputStream(WriteExcel);
		wb.write(fo);
	}
	
	
}
