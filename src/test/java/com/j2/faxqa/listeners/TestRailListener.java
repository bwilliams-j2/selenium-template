package com.j2.faxqa.listeners;


import com.j2.faxqa.config.testrail_commons.Common;
import com.j2.faxqa.listeners.testrail.TestRail;
import com.j2.faxqa.listeners.testrail.TestRailAPI;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestRailListener extends TestListenerAdapter {
    Boolean debugInfo = true;

    public void onTestStart(ITestResult arg0) {
        String timeStamp = new SimpleDateFormat("dd/MM/yyy HH:mm:ss").format(Calendar.getInstance().getTime());

        ITestNGMethod m = arg0.getMethod();

        // get test method name
        String methodName = m.getMethodName();
        // get test browser name
        String browserName = m.getXmlTest().getParameter("browserName");

        System.out.println("");
        System.out.println("===============================================");

        if (browserName == null)
        {
            System.out.println("Test started - " + methodName + " at " + timeStamp);
        }
        else
        {
            System.out.println("Test started - " + methodName + " on browser " + browserName + " at " + timeStamp);
        }
        System.out.println("===============================================");
        System.out.println("");
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("");
        System.out.println("===============================================");

        // if you have chosen to record test results to TestRail
        if (Common.postResultsToTestRail) {
            ITestNGMethod m = result.getMethod();
            // get the method name for the test
            String methodName = m.getMethodName();
            // get the browser name for the test
            String browserName = m.getXmlTest().getParameter("browserName");

            // get the TestRail Test Run Id from the TestNG.xml file
            int testRailTestRunId = 0;
            String testRailTestRunIdStr = m.getXmlTest().getParameter("testRailTestRunId");
            // if the Test Run id parameter exists in the xml then store it as an int
            if (testRailTestRunIdStr != null && !testRailTestRunIdStr.isEmpty()) {
                testRailTestRunId = Integer.parseInt(testRailTestRunIdStr);
            }

            // get the TestRail Test Case id from the Test annonation description value
            int[] testCaseIds = returnTestCaseId(result);

            // if the Test Case Id and Test Run Id have both been set then try to add the test result to TestRail
            if (testCaseIds != null && testRailTestRunId != 0) {
                // the comment that will be added to the Test Run result
                Object commentFromTest = result.getTestContext().getAttribute("Comment");
                if (debugInfo)
                    System.out.println("Comment attribute = " + commentFromTest);
                String additionalComment;
                if (commentFromTest == null) {
                    additionalComment = "";
                } else {
                    additionalComment = "\n\nAdditional info - " + commentFromTest.toString();
                }

                String testRailComment = "Automated TestNG Test - PASS; \n\nTest method name = " + methodName + "\nBrowser name = " + browserName + additionalComment;
                System.out.println(testRailComment);

                try {
                    // add the result to TestRail
                    for (int testCaseId : testCaseIds) {
                        System.out.println(testCaseId);
                        TestRailAPI.addResult(1, testRailComment, testRailTestRunId, testCaseId);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                // if the Test Case Id or Test Run Id have not been set correctly record a message to the log
                System.out.println("Test results are not being recorded to TestRail for test " + methodName + " on browser " + browserName);
            }
        }
        System.out.println("===============================================");
    }


    // runs any time a test fails
    @Override
    public void onTestFailure(ITestResult failingTest) {
        System.out.println("");
        System.out.println("===============================================");

        // store the failure exception
        String errorMessage = failingTest.getThrowable().toString();
        System.out.println("\n\n\n\n\n\n\n" + errorMessage + "\n\n\n\n\n\n TEST" + "\n\n\n");

        // if you have chosen to record test results to TestRail
        if (Common.postResultsToTestRail) {
            ITestNGMethod m = failingTest.getMethod();
            System.out.println("Failing test: " + m);

            // store the test method name
            String methodName = m.getMethodName();
            System.out.println("Method name: " + methodName);

            // store the test browser name
            String browserName = System.getProperty("browser");
            System.out.println(browserName);

            // get the TestRail Test Run Id from the TestNG.xml file
            int testRailTestRunId = 0;
            String testRailTestRunIdStr = m.getXmlTest().getParameter("testRailTestRunId");
            System.out.println("TestRail Test Run ID String: " + testRailTestRunIdStr);

            // if the Test Run id parameter exists in the xml then store it as an int
            if (testRailTestRunIdStr != null && !testRailTestRunIdStr.isEmpty()) {
                testRailTestRunId = Integer.parseInt(testRailTestRunIdStr);
                System.out.println("Test Rail Test Run ID: " + testRailTestRunId);
            }

            // get the TestRail Test Case id from the Test annonation description value
            int[] testCaseIds = returnTestCaseId(failingTest);
            System.out.println("TestCaseIDs: " + testCaseIds);

            // if the Test Case Id and Test Run Id have both been set then try to add the test result to TestRail
            if (testCaseIds != null && testRailTestRunId != 0) {
                // the comment that will be added to the Test Run result
                Object commentFromTest = failingTest.getTestContext().getAttribute("Comment");
                if (debugInfo)
                    System.out.println("Comment attribute = " + commentFromTest);

                String additionalComment;
                if (commentFromTest == null) {
                    additionalComment = "";
                }
                else {
                    additionalComment = "\nAdditional info - " + commentFromTest.toString();
                }

                String testRailComment = "Automated TestNG Test - FAIL\n\nTest method name = " + methodName + "\nBrowser name = " + browserName + "\n\nFailure Exception = " + errorMessage + additionalComment;

                try {
                    // add the result to TestRail
                    for (int testCaseId : testCaseIds)
                    {
                        System.out.println(testCaseId);
                        TestRailAPI.addResult(5, testRailComment, testRailTestRunId, testCaseId);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                // if the Test Case Id or Test Run Id have not been set correctly record a message to the log
                System.out.println("Test results are not being recorded to TestRail for test " + methodName + " on browser " + browserName);
            }
        }
        System.out.println("===============================================");
    }

    public void onTestSkipped(ITestResult arg0)
    {
    }

    public void onFinish(ITestContext arg0) { }

    public void onStart(ITestContext arg0) { }

    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) { }

    public int[] returnTestCaseId(ITestResult result)
    {
        ITestNGMethod testNGMethod = result.getMethod();
        Method method = testNGMethod.getConstructorOrMethod().getMethod();
        TestRail testRailAnnotation = method.getAnnotation(TestRail.class);
        int[] testCaseIds;

        try
        {
            testCaseIds = testRailAnnotation.testCaseId();
        }
        catch (Exception ex)
        {
            return null;
        }
        return testCaseIds;
    }


}