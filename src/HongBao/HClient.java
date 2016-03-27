package HongBao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class HClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			// 客户端接收

			try {
				Socket client = new Socket("10.40.7.15", 40000);

				// 接收服务器发来的消息
				// 调用线程
				new Thread(new HClientThread(client)).start();

				// 发送消息到服务器 也就是将消息写入到输出流
				// 先从键盘读数据 然后在想读到的数据写入到输出流
				System.out.println("/**退出请按 '+' , 抢红包请输入  'yes'  ****/");

				BufferedReader reader = new BufferedReader(new InputStreamReader(
						System.in));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
						client.getOutputStream()));
				String content = null;
				while ((content = reader.readLine()) != null) {
					if (content.equals("+")) {
						client.close();
					} else {
						writer.write(content + "\n");
						writer.flush();
						// System.out.println("客户端输入的消息：" + content );
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
