package core;

import tools.SeleniumDriver;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseClass {

    public static SeleniumDriver SeleniumDriverInstance;

    public static String TestName;


    private static String reportDirectory;
    public static void setReportDirectory (String dir){reportDirectory = dir;}
    public static String getReportDirectory(){return reportDirectory;}


    public static String getCurrentDateTime() {
        Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

        return dateFormat.format(dateNow);
    }
}
