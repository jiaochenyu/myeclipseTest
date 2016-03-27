package HongBao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class HServerThread implements Runnable {
	Socket client = null;
	BufferedReader reader = null;
	static int fnum;// float随机数
	static int shengyu = HServer.money;
	static int count;
	static int total;

	// float random = new Random().nextFloat();

	long begin;
	long end;

	public HServerThread(Socket client) throws IOException {
		super();
		this.client = client;
		this.reader = new BufferedReader(new InputStreamReader(
				client.getInputStream()));
		// System.out.println("hongbao="+HServer.hongbao);
	}

	public void ReSendHongbao() throws InterruptedException, IOException {
		while (true) {
			PrintWriter writer = null;
			wait(5000);
			writer = new PrintWriter(new OutputStreamWriter(
					client.getOutputStream()));
			writer.println("发红包啦，快来抢啊！");
			writer.flush();
			HServer.hongbao = 3;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			PrintWriter writer = null; // 将 发红包 消息通知客户端
			BufferedWriter writerContent = null;
			String tongzhi = "发红包了，快来抢啊！";

			String content = null; // 接收客户端发送指令 "我要抢红包"

			writerContent = new BufferedWriter(new OutputStreamWriter(
					client.getOutputStream()));
			writer = new PrintWriter(new OutputStreamWriter(
					client.getOutputStream()));
			writer.println(tongzhi);
			writer.flush();

			// 调用函数
			// ReSendHongbao();

			while ((content = reader.readLine()) != null) {
				// float fnum ;
				// fnum = ((new Random().nextFloat())*2)*100/100;
				if (HServer.hongbao == 1) {
					fnum = HServer.money - total;
					// System.out.println("红包就剩一个啦！");
				} else {
					count = shengyu;
					fnum = new Random().nextInt(shengyu);
					shengyu = count - fnum;
					total = total + fnum;

				}
				// System.out.println("cout::"+count+"fnum::"+fnum+"shengyu::"+shengyu);
				for (Socket clienti : HServer.clientList) {
					try {

						writer = new PrintWriter(new OutputStreamWriter(
								clienti.getOutputStream()));

						if (content.equals("yes") == false) {
							writer.println(client.getInetAddress() + "  //"
									+ Thread.currentThread().getName() + "说了："
									+ content);
							writer.flush();
							// System.out.println(Thread.currentThread().getName()+"抢到了"+"还剩"
							// + HServer.hongbao + "个红包");
							// System.out.println(Thread.currentThread().getName()+"抢到了"+"还剩"
							// + HServer.hongbao + "个红包");
						}
						if (HServer.hongbao == 0 && content.equals("yes")
								&& client.equals(clienti)) {
							writer.println("红包已经被抢完请等待下轮红包");
							writer.flush();
						}
						if (content.equals("yes") && HServer.hongbao != 0) {
							// fnum = (float)Math.random();
							/*
							 * if (HServer.hongbao == 1) { HServer.fnum =
							 * HServer.money - HServer.count; }else {
							 * HServer.fnum = ((new
							 * Random().nextFloat())*(HServer.shengyu))*100/100;
							 * HServer.count = +HServer.fnum; HServer.shengyu =
							 * -HServer.fnum; }
							 */
							if (client.equals(clienti)) {
								HServer.hongbao--;
								System.out.println(client.getInetAddress()
										+ Thread.currentThread().getName()
										+ "抢到了" + (double) fnum / 100 + "还剩"
										+ HServer.hongbao + "个红包");

							}
							writer.println(client.getInetAddress() + "  //"
									+ Thread.currentThread().getName() + "抢到了："
									+ (double) fnum / 100);
							writer.flush();
						}

					} catch (Exception e) {
						// TODO: handle exception
						HServer.clientList.remove(clienti);
					}

				}// 增强for循环结束

			}// while((content = reader.readLine()) != null)循环结束

		} catch (Exception e) {
			HServer.clientList.remove(client);
		}

	}
}
