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
   
	   
	   
	   
	   
	   
	   
   }
   /*****************************Testing Authority *****************************/
   public void testYourThirdPartition(){
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
		
	   String baseURL = "https://www.amazon.com";
	 
	   String testUrl =  baseURL + ":80";
	   System.out.println("Testing Valid Scheme : ");
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
   
	   
	   
	   
	   
	   
	   
   }
   
   /*****************************Testing Query *****************************/
   public void testYourFifthPartition(){
	   
   
   }
   
   
   
   
   public void testIsValid()
   {
	   for(int i = 0;i<10000;i++)
	   {
		   
	   }
   }
   
   public void testAnyOtherUnitTest()
   {
		
   }

 
       
}




