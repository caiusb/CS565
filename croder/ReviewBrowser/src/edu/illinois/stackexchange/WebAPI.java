package edu.illinois.stackexchange;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.DeathByCaptcha.Captcha;
import com.DeathByCaptcha.Client;
import com.DeathByCaptcha.SocketClient;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class WebAPI implements WebApiInterface {

	WebClient webClient ;
	HtmlPage page;
	
	public WebAPI()
	{
		webClient = new WebClient();
		page=null;
		webClient.getOptions().setJavaScriptEnabled(false);
	}

	public static void main(String[] args)
	{
		WebAPI test= new WebAPI();
		test.login("semih.okur", "Ker32nel");
		
		String title = "Database Design process when using Hibernate";
		String content= "My question is, when using ORM frameworks like Hibernate, does it makes sense for the App Developer to work with the person doing the database design?";
		test.postQuestion(title, content, null);
	}
	
	public void login(String email, String pass) {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		try {
			page = webClient
					.getPage("http://codereview.stackexchange.com/users/login");

			((HtmlTextInput) page.getElementById("openid_identifier"))
					.setValueAttribute("https://www.google.com/accounts/o8/id");
			
			page = ((HtmlElement) page.getElementById("submit-button")).click();
			
			((HtmlTextInput) page.getElementById("Email"))
					.setValueAttribute(email);
			((HtmlPasswordInput) page.getElementById("Passwd"))
					.setValueAttribute(pass);
			page = ((HtmlElement) page.getElementById("signIn")).click();

			System.out.println(page.getWebResponse().getContentAsString()
					.contains("user244"));

		} catch (FailingHttpStatusCodeException e1) {
			System.out.println("FailingHttpStatusCodeException thrown:"
					+ e1.getMessage());
			e1.printStackTrace();

		} catch (MalformedURLException e1) {
			System.out.println("MalformedURLException thrown:"
					+ e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.println("IOException thrown:" + e1.getMessage());
			e1.printStackTrace();
		} catch (Exception e) {
			System.out.println("General exception thrown:" + e.getMessage());
			e.printStackTrace();
		}
	}

	public String postQuestion(String title, String content, List<String> tags) {

		try {
			page = webClient.getPage("http://codereview.stackexchange.com/questions/ask");
			
	
			((HtmlTextInput) page.getElementById("title"))
					.setValueAttribute(title);
			((HtmlTextArea) page.getElementById("wmd-input"))
					.setTextContent(content);
			
			
            HtmlTextInput textField3 = (HtmlTextInput) page.getElementById("tagnames");
            
            int i=0;
            StringBuilder str= new StringBuilder();
            for (String tag : tags) {
				if(i==0)
					str.append(tag);
				else
					str.append(","+tag);
				i++;
			}
            textField3.setValueAttribute(str.toString());
			
			page = ((HtmlElement) page.getElementById("submit-button")).click();
			System.out.println(page.getFullyQualifiedUrl(""));
			System.out.println(page.getWebResponse().getContentAsString());
			
			String urlCaptcha= page.getWebResponse().getContentAsString().split("iframe src=\"")[1].split("\"")[0];
			
			HtmlPage captchaPage= webClient.getPage(urlCaptcha);
			
			
			Client client = (Client)(new SocketClient("kernel32","ker32nel"));
	        client.isVerbose = true;
	        
	        Captcha captcha = null;
	        
	        String resultPageSource="";
	        while(!resultPageSource.contains("<textarea"))
	        {
	            try {
	            	String imgUrl= "http://www.google.com/recaptcha/api/"+ captchaPage.getWebResponse().getContentAsString().split("src=\"")[1].split("\"")[0];
	    			
	                captcha = client.decode(downloadImage(new URL(imgUrl)), 120);
	            } catch (IOException e) {
	                System.out.println("Failed uploading CAPTCHA");
	            }
	            if (null == captcha) {
	                System.out.println("Failed CAPTCHA");
	            }
	            
	            System.out.println(captcha.text);
	            ((HtmlInput) captchaPage.getElementById("recaptcha_response_field")).setValueAttribute(captcha.text);
	            captchaPage = ((HtmlElement)captchaPage.getElementByName("submit")).click();
	            resultPageSource=captchaPage.getWebResponse().getContentAsString();
	            
	        }
            System.out.println(captchaPage.getFullyQualifiedUrl(""));
            String confirmationCode= resultPageSource.split("<textarea")[1].split(">")[1].split("<")[0];
			
            ((HtmlTextArea) page.getElementByName("recaptcha_challenge_field")).setTextContent(confirmationCode);
            
            page= ((HtmlElement)page.getElementById("btn-captcha")).click();
            
            System.out.println( page.getFullyQualifiedUrl(""));
            return page.getFullyQualifiedUrl("").toString();


		} catch (FailingHttpStatusCodeException e1) {
			System.out.println("FailingHttpStatusCodeException thrown:"
					+ e1.getMessage());
			e1.printStackTrace();

		} catch (MalformedURLException e1) {
			System.out.println("MalformedURLException thrown:"
					+ e1.getMessage());
			e1.printStackTrace();

		} catch (IOException e1) {
			System.out.println("IOException thrown:" + e1.getMessage());
			e1.printStackTrace();

		} catch (Exception e) {
			System.out.println("General exception thrown:" + e.getMessage());
			e.printStackTrace();

		}
		
		System.err.println(title + "\n" + content);
		
		return "";
	}
	
	public String[] parseQuestion(String url) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost httpost = new HttpPost(url);

			HttpResponse response= client.execute(httpost);
			
			String sourceCode = EntityUtils.toString(response.getEntity());
			
			String[] answers= sourceCode.split("<td class=\"answercell\">");
			
			String[] replies = new String[answers.length-1];
			
			for(int i=0; i<replies.length; i++)
			{
				replies[i]= answers[i+1].split("<table class=\"fw\">")[0];
			}
			

			return replies;
			
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
		
	}
	
	public byte[] downloadImage(URL url)
	{
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		InputStream is = null;
		try {
		  is = url.openStream ();
		  byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
		  int n;

		  while ( (n = is.read(byteChunk)) > 0 ) {
		    bais.write(byteChunk, 0, n);
		  }
		  is.close();
		}
		catch (IOException e) {
		  System.err.printf ("Failed while reading bytes from %s: %s", url.toExternalForm(), e.getMessage());
		  e.printStackTrace ();
		  // Perform any other exception handling that's appropriate.
		}
		finally {
			return bais.toByteArray();
		}
	}
	

}

