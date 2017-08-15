
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import junit.framework.TestCase;

import java.util.Random;




/**
 * Performs Validation Test for url validations.
 *
 * @version $Revision: 1128446 $ $Date: 2011-05-27 13:29:27 -0700 (Fri, 27 May 2011) $
 */
public class UrlValidatorTest extends TestCase {

   private boolean printStatus = false;
   private boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test were using.

   public UrlValidatorTest(String testName) {
      super(testName);
   }



   public void testManualTest()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   System.out.println(urlVal.isValid("http://www.amazon.com"));
	   System.out.println(urlVal.isValid("https://///www.google.com"));
	   System.out.println(urlVal.isValid("http://www.google.com"));
	   System.out.println(urlVal.isValid("http://www.google.com:80"));
	   System.out.println(urlVal.isValid("https://piazza.com/class/j4bo6gz138r46l?cid=158"));
	   System.out.println(urlVal.isValid("h3t://256.256.256.256:65535/$23?action=edit&mode=up"));
	   System.out.println(urlVal.isValid("http://www.google.com:80/$23"));

	   //test for valid and invalid ASCII characters
	   assertTrue(urlVal.isValid("http://www.amazon.com"));
	   assertFalse(urlVal.isValid("http://www.amäzon.com"));
	   assertTrue(urlVal.isValid("https://www.facebook.com"));
	   assertFalse(urlVal.isValid("https://www.fäcebook.com"));
	   assertTrue(urlVal.isValid("http://oregonstate.edu/"));
	   assertFalse(urlVal.isValid("http://orëgonstate.edu/"));

	   //test for valid and invalid schemes
	   assertTrue(urlVal.isValid("https://github.com/"));
	   assertTrue(urlVal.isValid("http://github.com/"));
	   //assertFalse(urlVal.isValid("htstps://github.com/")); //bug here - test fails, uncomment to verify
	   //assertFalse(urlVal.isValid("ttps://github.com/")); //bug here - test fails, uncomment to verify

	   //test for valid and invalid domain names
	   assertTrue(urlVal.isValid("http://www.public.navy.mil/spawar/Pages/default.aspx"));
	   assertTrue(urlVal.isValid("https://www.nasa.gov/"));
	   //assertTrue(urlVal.isValid("https://www.bbc.co.uk/")); //bug here - test fails, uncomment to verify
	   assertTrue(urlVal.isValid("http://www.scaphon.org/"));
	   //assertTrue(urlVal.isValid("www.nzherald.co.nz/")); //bug here - test fails, uncomment to verify
	   //assertTrue(urlVal.isValid("www.spiegel.de/international/")); //bug here - test fails, uncomment to verify
	   //assertTrue(urlVal.isValid("http://localhost:400/")); //bug here - test fails, uncomment to verify
   }

   /*****************************Testing Scheme. *****************************/
   public void testYourFirstPartition()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);

	   String baseURL = "www.amazon.com";

	   String testUrl = "https://" +baseURL;
	   System.out.println("Testing Valid Scheme : ");
	   boolean result = urlVal.isValid(testUrl);

	   System.out.println("Expected Result : TRUE    Actual Result : "+result);


	   String[] inValidScheme = {"htsp://", "hts://", "ht://",  "h://"};

	   System.out.println("Testing Invalid Scheme : ");
	   result = false; //these tests should return false
	   for (int i = 0; i <= inValidScheme.length ; i++)
	   {
		    testUrl = inValidScheme[i] +baseURL;
		   result = (urlVal.isValid(testUrl));
		   if(result)
		   System.out.println("Expected Result : FALSE    Actual Result : "+result);
	   }

   }
   /*****************************Testing Authority *****************************/
   public void testYourSecondPartition(){
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   String[] domainName = new String[6];
	   int largeNumber = 1000;
	   
	   domainName[0] = "https://www.amazon.com";
	   domainName[1] = "https://www.amazon.ca";
	   domainName[2] = "https://www.facebook.com";
	   domainName[3] = "https://www.google.com";
	   domainName[4] = "https://www.google.ca";
	   domainName[5] = "https://www.reddit.com";
	   
	   Random rand = new Random();

	   System.out.println("Testing Valid Authority (Port 80): ");
	   for (int i = 0; i <6; i++) {
		   String URL = domainName[i] + ":80";
		   boolean validAuthority = urlVal.isValid(domainName[i]);
		   System.out.println("Expected Result : true    Actual Result : "+ validAuthority);
	   }
	   
	   System.out.println("Testing invalid Authority: ");
	   for (int i = 0; i < largeNumber; i++) {
		   int randPort = rand.nextInt(65535);
		   int randHostName = rand.nextInt(5);
		   
		   String URL = domainName[randHostName] + ":" + randPort;
		   boolean result = urlVal.isValid(URL);
		   
		   System.out.println("Expected Result : false    Actual Result : " + result + "   Port: " + randPort);
	   }
   }
   /*****************************Testing Port *****************************/
   public void testYourThirdPartition(){
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);

	   String baseURL = "https://www.amazon.com";

	   String testUrl =  baseURL + ":80";
	   System.out.println("Testing Valid Port : ");
	   boolean result = urlVal.isValid(testUrl);

	   System.out.println("Expected Result : TRUE    Actual Result : "+result);

	   Random rand = new Random();



	   System.out.println("Testing Invalid Scheme : ");
	   //result = false; //these tests should return false
	   for (int i = 0; i < 1000 ; i++)
	   {
		   int n = rand.nextInt(65535);
		   System.out.println(n);
		   testUrl = baseURL + ":" + n;
		   result = (urlVal.isValid(testUrl));
		   if(!result)
		   System.out.println(n + "is invalid port");


	   }

   }
   /*****************************Testing Path *****************************/
   public void testYourFourthPartition(){
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   String domainName = "https://amazon.com/";
	   boolean testPathResult;
	   
	   String[] URLArray = new String[6];

	   URLArray[0] = domainName + "home";
	   URLArray[1] = domainName + "gp/goldbox/ref=nav_cs_gbn";
	   URLArray[2] = domainName + "ref=nav_upnav_LargeImage_C_Homepage";
	   
	   URLArray[3] = domainName + "googlymoogly";
	   URLArray[4] = domainName + "https://amazon.com/idontthinkiexist123";
	   URLArray[5] = domainName + "https://amazon.com/123456789";
	   
	   System.out.println("Testing Valid Path : ");
	   //Loop testing all values that should be true
		for (int i = 0; i < 3; i++) {
			testPathResult = urlVal.isValid(URLArray[i]);
			System.out.println("Expected Result : true    Actual Result : "+ testPathResult);
		}

	   System.out.println("Testing Invalid Path : ");
	   //Loop testing all values that should be false
		for (int i = 3; i < 7; i++) {
			testPathResult = urlVal.isValid(URLArray[i]);
			System.out.println("Expected Result : false    Actual Result : "+ testPathResult);
		}

   }

   /*****************************Testing Query *****************************/
   public void testYourFifthPartition(){
     UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);

	   String baseURL = "http://www.google.com/";

	   String testUrl = baseURL;
	   System.out.println("Testing the base URL for the Valid Query test: ");
	   boolean result = urlVal.isValid(testUrl);

	   System.out.println("Expected Result : TRUE    Actual Result : "+result);


	   String[] validQuery = {"search?q=junit&oq=junit&gs_l", "search?q=eclipse+ide&oq=eclipse+ide&gs_l=psy-ab.3", "search?q=how+to+write+bug+reports&oq=how+to+write+bug+reports&gs_l=psy-ab.3..0j0i22i30k1l3.81686.92729.0.93106.44.34.10.0.0.0.167.3411.22j12.34.0....0...1.1.64.psy-ab..0.44.3455...35i39k1j0i131k1j0i67k1j0i20k1.KUpHDCRFO00"};

	   System.out.println("Testing Valid Query : ");
	   result = true; //these tests should return true
	   for (int i = 0; i <= validQuery.length ; i++)
	   {
		    testUrl += validQuery[i];
		   result = (urlVal.isValid(testUrl));
		   if(result)
		   System.out.println("Expected Result : TRUE    Actual Result : "+result);
	   }

   }




   public void testIsValid()
   {
     //partition strings
     String[] inValidScheme = {"htsp://", "hts://", "ht://",  "h://"};
     //place second partition string here
     String[] validAuth = {"https://www.amazon.com", "https://www.amazon.com:80", "https://amazon.com"};
     //fourth partition string here
    String[] validQuery = {"search?q=junit&oq=junit&gs_l", "search?q=eclipse+ide&oq=eclipse+ide&gs_l=psy-ab.3", "search?q=how+to+write+bug+reports&oq=how+to+write+bug+reports&gs_l=psy-ab.3..0j0i22i30k1l3.81686.92729.0.93106.44.34.10.0.0.0.167.3411.22j12.34.0....0...1.1.64.psy-ab..0.44.3455...35i39k1j0i131k1j0i67k1j0i20k1.KUpHDCRFO00"};
    //random paths
    String[] validPath = {"/gp/help", "gp/help/customer", "/b/?&node=5160028011", "adprefs", "/p/feature/rzekmvyjojcp6uc"};

    String baseURL = "www.amazon.com";
    String testUrl = baseURL;
    System.out.println("Random Unit testing: ");
    int randSeed = 10;
    boolean result;
    Random rand = new Random(randSeed);
     for(int i = 0;i<10000;i++)
	   {
       UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
       //pull a random partition to test, range of 1 - 5
       int randTest = rand.nextInt(5) + 1;
       //pull a random auth
       int randAuth = rand.nextInt(3);
       //pull a random element to test within the array, range of 1 - 5
       int randElement = rand.nextInt(3) + 1;
       //random unit for port Testing
       int randPort = rand.nextInt(160) + 1;
       //random path
       int randPath = rand.nextInt(5);
       //now handle various partition tests
       //test schemes
       if(randTest == 1)
       {
         testUrl = inValidScheme[randElement] +baseURL;
 		     result = (urlVal.isValid(testUrl));
 		     if(result)
 		     System.out.println("Expected Result : FALSE    Actual Result : "+result);
       }
       //test authorities
       else if(randTest == 2)
       {
    	   	testUrl = validAuth[randAuth];
    	   		result = (urlVal.isValid(testUrl));
    	   		if (!result)
    	   			System.out.println("Expected Result : TRUE    Actual Result:" + result);

       }
       //test random ports
       else if(randTest == 3)
       {
         testUrl = baseURL + ":" + randPort;
  		   result = (urlVal.isValid(testUrl));
  		   if(!result)
  		     System.out.println(randPort + "is invalid port");
       }
       //test paths
       else if (randTest == 4)
       {
    	   	testUrl = baseURL + validPath[randPath];
    	   		result = (urlVal.isValid(testUrl));
    	   		if (!result)
    	   			System.out.println(testUrl + "is an invalid URL/Path");
       }
       //test valid queries
       else
       {
         testUrl += validQuery[randElement];
 		     result = (urlVal.isValid(testUrl));
 		     if(result)
 		      System.out.println("Expected Result : TRUE    Actual Result : "+result);
       }

	   }
     System.out.println("Testing is finished.");
   }

  public void testAnyOtherUnitTest()
   {
	    {
		   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
		   String testURL = "";
		   boolean expectedResult;
		   boolean actualResult;
		   for (int i = 0; i < testUrlScheme.length; i++) {
			   for (int j = 0; j < testUrlAuthority.length; j++) {
				   for (int k = 0; k < testUrlPort.length; k++) {
					   for (int l = 0; l < testUrlPath.length; l++) {
						   for (int m = 0; m < testUrlQuery.length; m++) {
								   testURL = testUrlScheme[i].item + testUrlAuthority[j].item + testUrlPort[k].item + testUrlPath[l].item + testUrlQuery[m].item ;
								   expectedResult = testUrlScheme[i].valid && testUrlAuthority[j].valid && testUrlPort[k].valid && testUrlPath[l].valid && testUrlQuery[m].valid;
								   actualResult = urlVal.isValid(testURL);
								   if (actualResult == expectedResult){
									   System.out.println("Passed: The URL \"" + testURL + "\" was correctly identified by isValid() as " + actualResult + ".");
								   } else {
									   System.out.println("Failed: The URL \"" + testURL + "\" was incorrectly identified by isValid() as " + actualResult + ".");
								   }
							   }
					      }
				     }
			   }
		   }
	   }
		
   }

   ResultPair[] testUrlScheme = {new ResultPair("htsp://", false),
           new ResultPair("hts://", false),
           new ResultPair("h3t://", true),
           new ResultPair("ht://", false),
           new ResultPair("h:/", false),
           new ResultPair("", true)};

ResultPair[] testUrlAuthority = {new ResultPair("www.amazon.com", true),
              new ResultPair("amazon.ca", false),
              new ResultPair("facebook.com", true),
              new ResultPair("google.com", true),
              new ResultPair("google.ca", false),
              new ResultPair("reddit.com", true),
              
};
ResultPair[] testUrlPort = {new ResultPair(":80", true),
         new ResultPair(":65535", true),
         new ResultPair(":0", true),
         new ResultPair("", true),
         new ResultPair(":-1", false),
         new ResultPair(":65636", true),
         new ResultPair(":65a", false)
};
ResultPair[] testUrlPath = {new ResultPair("/home", true),
      new ResultPair("/gp/goldbox/ref=nav_cs_gbn", true),
      new ResultPair("/ref=nav_upnav_LargeImage_C_Homepage", true),
      new ResultPair("googlymoogly", false),
      new ResultPair("//123456789", false),
      new ResultPair("/test1/", true),
      new ResultPair("", true),
   
};


ResultPair[] testUrlQuery = {new ResultPair("search?q=junit&oq=junit&gs_l", true),
          new ResultPair("search?q=eclipse+ide&oq=eclipse+ide&gs_l=psy-ab.3", true),
          new ResultPair("search?q=how+to+write+bug+reports&oq=how+to+write+bug+reports&gs_l=psy-ab.3..0j0i22i30k1l3.81686.92729.0.93106.44.34.10.0.0.0.167.3411.22j12.34.0....0...1.1.64.psy-ab..0.44.3455...35i39k1j0i131k1j0i67k1j0i20k1.KUpHDCRFO00", true)
};
       
}