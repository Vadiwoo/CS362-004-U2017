import junit.framework.TestCase;
import java.util.Random;
import java.io.*;

public class UrlValidatorRandomTester extends TestCase{
	
	   public void testManualTest() throws FileNotFoundException
	   {
		   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
		   //System.out.println(urlVal.isValid("http://www.amazon.com"));
		   
		   PrintWriter outFile = new PrintWriter("urlRandomResults.txt");
		   outFile.println("URL Random Testing Started.....");
		   outFile.println();
		   Random rand = new Random();
		   int randomScheme, randomAuthority, randomPort, randomPath, randomQuery; 
		   
			   String testURL = "";
			   boolean expectedResult;
			   boolean actualResult;
			   int passedTest = 0;
			   int failedTest = 0;
			   for (int i = 0; i< 1000; i++)
			    {
				   randomScheme = rand.nextInt(testUrlScheme.length);
				   System.out.println(" randomScheme is" + randomScheme);
				   randomAuthority = rand.nextInt(testUrlAuthority.length);
				   randomPort = rand.nextInt(testUrlPort.length);
				   randomPath = rand.nextInt(testUrlPath.length);
				   randomQuery = rand.nextInt(testUrlQuery.length);
				   
 									   testURL = testUrlScheme[randomScheme].item + testUrlAuthority[randomAuthority].item + testUrlPort[randomPort].item + testUrlPath[randomPath].item + testUrlQuery[randomQuery].item ;
 									   outFile.println("URL: " + testURL);
 									   expectedResult = testUrlScheme[randomScheme].valid && testUrlAuthority[randomAuthority].valid && testUrlPort[randomPort].valid && testUrlPath[randomPath].valid && testUrlQuery[randomQuery].valid;
									   actualResult = urlVal.isValid(testURL);
									   if (actualResult == expectedResult){
										  passedTest++;
										   System.out.println("Passed: The URL \"" + testURL + "\" was correctly identified by isValid() as " + actualResult + ".");
										   outFile.println("Passed: The URL \"" + testURL + "\" was correctly identified by isValid() as " + actualResult + ".");
									   } else {
										   failedTest++;
										   System.out.println("Failed: The URL \"" + testURL + "\" was incorrectly identified by isValid() as " + actualResult + ".");
										   outFile.println("Failed: The URL \"" + testURL + "\" was incorrectly identified by isValid() as " + actualResult + ".");
											  
									   }
									   outFile.println();
									
								   }
			   outFile.println(" " +passedTest  + " Test Passed");
			   outFile.println(" " +failedTest  + " Test Failed");
			   	
				}
					   
				   
	   
	   ResultPair[] testUrlScheme = {new ResultPair("http://", true),
			   new ResultPair("ftp://", true),
               new ResultPair("h3t://", true),
               new ResultPair("3ht://", false),
               new ResultPair("http:/", false),
               new ResultPair("http:", false),
               new ResultPair("http/", false),
               new ResultPair("://", false),
               new ResultPair("", true)};

	   ResultPair[] testUrlAuthority = {new ResultPair("www.google.com", true),
               new ResultPair("go.com", true),
               new ResultPair("go.au", true),
               new ResultPair("0.0.0.0", true),
               new ResultPair("255.255.255.255", true),
               new ResultPair("256.256.256.256", false),
               new ResultPair("255.com", true),
               new ResultPair("1.2.3.4.5", false),
               new ResultPair("1.2.3.4.", false),
               new ResultPair("1.2.3", false),
               new ResultPair(".1.2.3.4", false),
               new ResultPair("go.a", false),
               new ResultPair("go.a1a", false),
               new ResultPair("go.cc", true),
               new ResultPair("go.1aa", false),
               new ResultPair("aaa.", false),
               new ResultPair(".aaa", false),
               new ResultPair("aaa", false),
               new ResultPair("", false)
};
	   ResultPair[] testUrlPort = {new ResultPair(":80", true),
               new ResultPair(":65535", true),
               new ResultPair(":0", true),
               new ResultPair("", true),
               new ResultPair(":-1", false),
               new ResultPair(":65636", true),
               new ResultPair(":65a", false)
};
	   ResultPair[] testUrlPath = {new ResultPair("/test1", true),
               new ResultPair("/t123", true),
               new ResultPair("/$23", true),
               new ResultPair("/..", false),
               new ResultPair("/../", false),
               new ResultPair("/test1/", true),
               new ResultPair("", true),
               new ResultPair("/test1/file", true),
               new ResultPair("/..//file", false),
               new ResultPair("/test1//file", false)
};
//Test allow2slash, noFragment
	   ResultPair[] testUrlPathOptions = {new ResultPair("/test1", true),
               new ResultPair("/t123", true),
               new ResultPair("/$23", true),
               new ResultPair("/..", false),
               new ResultPair("/../", false),
               new ResultPair("/test1/", true),
               new ResultPair("/#", false),
               new ResultPair("", true),
               new ResultPair("/test1/file", true),
               new ResultPair("/t123/file", true),
               new ResultPair("/$23/file", true),
               new ResultPair("/../file", false),
               new ResultPair("/..//file", false),
               new ResultPair("/test1//file", true),
               new ResultPair("/#/file", false)
};

	   ResultPair[] testUrlQuery = {new ResultPair("?action=view", true),
               new ResultPair("?action=edit&mode=up", true),
               new ResultPair("", true)
};
}
