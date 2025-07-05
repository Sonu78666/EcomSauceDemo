package Utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {


    private int retryCount=0;
    private final int maxRetryCount=Integer.parseInt(System.getProperty("retryCount","1"));
    @Override
    public boolean retry(ITestResult iTestResult) {
        if(System.getProperty("retry","false").equalsIgnoreCase("true")&& retryCount<maxRetryCount)
        {
           retryCount++;
           return true;
        }
        return false;
    }

}
