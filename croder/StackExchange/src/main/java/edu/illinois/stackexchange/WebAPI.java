package edu.illinois.stackexchange;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class WebAPI implements WebApiInterface {

	WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
	HtmlPage page = null;

	public void main(String[] args)
	{
		
		String[] list= parseQuestion("http://codereview.stackexchange.com/questions/2711/linked-list-loop-detection-in-javainterview-question");
		for(int i=0; i< list.length; i++)
		{
			System.out.println(list[i]);
			System.out.println("---------");
		}
	}
	
	
	public void login(String email, String pass) {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		try {
			page = webClient
					.getPage("http://codereview.stackexchange.com/users/login");

			((HtmlTextInput) page.getElementById("openid_identifier"))
					.setValueAttribute("https://www.google.com/accounts/o8/id");
			;
			page = ((HtmlElement) page.getElementById("submit-button")).click();
			;
			((HtmlTextInput) page.getElementById("Email"))
					.setValueAttribute(email);
			((HtmlPasswordInput) page.getElementById("Passwd"))
					.setValueAttribute(pass);
			page = ((HtmlElement) page.getElementById("signIn")).click();

			System.out.println(page.getWebResponse().getContentAsString()
					.contains("kernel32"));

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

	public String postQuestion(String title, String content) {

		try {
			page = webClient.getPage("http://codereview.stackexchange.com/questions/ask");
			((HtmlTextInput) page.getElementById("title"))
					.setValueAttribute(title);
			((HtmlTextArea) page.getElementById("wmd-input"))
					.setTextContent(content);
			page = ((HtmlElement) page.getElementById("submit-button")).click();
			
			return page.getBaseURI();

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
	

}

