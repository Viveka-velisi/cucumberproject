package driverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import commonfunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
WebDriver driver;
String inputpath="./FileInput/DataEngineee.xlsx";
String outputpath="./FileOutput/HybridResults.xlsx";
String TCsheet="MasterTestCases";
public void startTest() throws Throwable 
{
	String Module_status="";
	String Module_New="";
	ExcelFileUtil xl=new ExcelFileUtil(inputpath);
	int rc=xl.rowcount(TCsheet);
	Reporter.log("no of rows are ::"+rc,true);
	for(int i=1;i<=rc;i++) {
		
		if(xl.getCellData(TCsheet, i, 2).equalsIgnoreCase("Y"))
		{
			
			String TCmodule=xl.getCellData(TCsheet, i, 1);
			for(int j=1;j<=xl.rowcount(TCmodule);j++) {
				String object_type=xl.getCellData(TCmodule, j, 1);
				String Locator_type=xl.getCellData(TCmodule, j, 2);
				String Locator_value=xl.getCellData(TCmodule, j, 3);
				String test_data=xl.getCellData(TCmodule, j, 4);
				try {
					if(object_type.equalsIgnoreCase("startBrowser")) {
						driver =FunctionLibrary.startBrowser();
					}
					if(object_type.equalsIgnoreCase("openUrl")) {
						FunctionLibrary.openUrl();
					}
					if(object_type.equalsIgnoreCase("waitForElement")) {
						FunctionLibrary.waitForElement(Locator_type, Locator_value, test_data);
					}
					if(object_type.equalsIgnoreCase("typeAction")) {
						FunctionLibrary.typeAction(Locator_type, Locator_value, test_data);
					}
					if(object_type.equalsIgnoreCase("ClickAction")) {
						FunctionLibrary.ClickAction(Locator_type, Locator_value);
					}
					if(object_type.equalsIgnoreCase("validateTitle")) {
						FunctionLibrary.validateTitle(test_data);
					}
					
					if(object_type.equalsIgnoreCase("dropdownlist")) {
						FunctionLibrary.dropdownlist(Locator_type, Locator_value, test_data);
					}
					if(object_type.equalsIgnoreCase("capturestock")) {
						FunctionLibrary.capturestock(Locator_type, Locator_value);
					}
					if(object_type.equalsIgnoreCase("stocktable")){
						FunctionLibrary.stocktable();
					}
					if(object_type.equalsIgnoreCase("CloseBrowser")) {
						FunctionLibrary.CloseBrowser();
					}
					if(object_type.equalsIgnoreCase("capturesupplier")) {
						FunctionLibrary.capturesupplier(Locator_type, Locator_value);
		              }
					if(object_type.equalsIgnoreCase("suppliertable")) {
						FunctionLibrary.suppliertable();
		              }
					if(object_type.equalsIgnoreCase("capturecustomernum")) {
						FunctionLibrary.capturecustomernum(Locator_type, Locator_value);
						
		              }
					if(object_type.equalsIgnoreCase("customertable")) {
						FunctionLibrary.customertable();
		              }
					
					xl.setcellData(TCmodule, j, 5, "PASS", outputpath);
					Module_status="True";
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					xl.setcellData(TCmodule, i, 5, "Fail", outputpath);
					Module_New="False";
				}
			}
			if(Module_status.equalsIgnoreCase("True")) {
				xl.setcellData(TCsheet, i, 3, "PASS",outputpath );
			}
			if(Module_New.equalsIgnoreCase("False")) {
				xl.setcellData(TCsheet, i, 3, "Fail",outputpath );
			}
			
			
			
		}else
		{
			xl.setcellData(TCsheet, i, 3, "Blocked", outputpath);
		}
		
	}
}
}
