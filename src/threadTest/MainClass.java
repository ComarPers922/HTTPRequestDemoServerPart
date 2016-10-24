package threadTest; 

import java.util.*;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin.FakeFocusTextField;

import jdk.internal.dynalink.beans.StaticClass;

import java.awt.font.NumericShaper;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;

public class MainClass 
{
	//private static Scanner scanner;
	
	public static void main(String[] args) throws Exception
	{
		SelfAddition sa = new SelfAddition();
		sa.start();
		while(true);
	}
}

class SelfAddition extends Thread
{	
	private ServerSocket server = null;
	private Socket socket = null;
	
	public SelfAddition() throws Exception
	{
		server = new ServerSocket(4700);
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				//server = new ServerSocket(4700);
				//socket = null;
				socket = server.accept();
			}
			catch (Exception e) 
			{
				
			}
			try 
			{		
				BufferedReader  reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
				//InputStreamReader reader = new InputStreamReader(socket.getInputStream());
				while(!reader.ready());
				char [] cs = new char[socket.getInputStream().available()];
				//reader.read(cs);
				reader.read(cs);
				System.out.println(cs);
				System.out.println("宋");
				reader.read(cs);
				System.out.println(cs);
				Integer result = 3;
				PrintWriter writer = new PrintWriter(socket.getOutputStream());

				String firstLine = String.valueOf(cs).split("\n")[0];
				firstLine = firstLine.substring(0,firstLine.length());
				String[] numbers = firstLine.split("&");
				String num1 = numbers[0].split("=")[1];
				String num2 = numbers[1].split("=")[1];
				
				result = Integer.parseInt(num1)+Integer.parseInt(num2);
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("MM, dd yy HH:mm:ss");
				String responseDate = "Date:" + format.format(date);
				//String body = "<html><body><form method=\"POST\"><input type=\"text\" value =\"No Value\" name=\"text\"/><input type=\"submit\" value=\"Saysong\"></form></body></html>";
				String body = String.valueOf(result);
				int length = body.length();
				String contentLenght = "Content-Length:"+ String.valueOf(length);
				writer.println("HTTP/1.1 200 OK");	
				writer.println(responseDate);
				writer.println("Content-Type:application/x-www-form-urlencoded");
				writer.println(contentLenght);
				writer.println();
				writer.println(body);
				writer.flush();
				
				writer.close();
				reader.close();
				socket.close();
			} 
			catch (IOException e) 
			{
			} 
		}
	}
}
