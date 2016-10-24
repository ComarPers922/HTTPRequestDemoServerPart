using System;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using System.IO;
using System.Text;

namespace HTTPServer
{
	class MainClass
	{
		public static void Main (string[] args)
		{
			TcpClient tcpclient = new TcpClient ();
			TcpListener listener = new TcpListener (IPAddress.Parse("127.0.0.1"),4700);
			listener.Start ();
			Thread t = new Thread (delegate ()
			{
					while(true)
					{
						tcpclient = listener.AcceptTcpClient();
						while(tcpclient.Available<=0);
						byte[] data = new byte[tcpclient.Available];
						NetworkStream ns = tcpclient.GetStream();
						ns.Read(data,0,data.Length);
						ns.Read(data,0,data.Length);
						String text = Encoding.Default.GetString(data);
						String firstLine = text.Split('\n')[0];

						String[] paras = firstLine.Split('&');
						String num1 = paras[0].Split('=')[1];
						String num2 = paras[1].Split('=')[1];

						int result = Convert.ToInt32(num1)+Convert.ToInt32(num2);

						StringBuilder sb = new StringBuilder ();

						sb.Append("HTTP/1.1 200 OK");
						DateTime time = DateTime.Now;
						sb.Append("Date:"+time.ToString()+"\n");
						sb.Append("Content-Type:application/x-www-form-urlencoded\n");
						sb.Append("Content-Length:"+result.ToString().Length+"\n");
						sb.Append("\n");
						sb.Append(result.ToString());
						var response = Encoding.Default.GetBytes(sb.ToString());
						ns.Write(response,0,response.Length);

						tcpclient.Close();
						ns.Close();
					}
			});
			t.Start ();
			Console.WriteLine ("Hello World!");
			while (true);
		}
	}
}
