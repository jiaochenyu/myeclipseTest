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
	static int fnum;// float�����
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
			writer.println("�������������������");
			writer.flush();
			HServer.hongbao = 3;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			PrintWriter writer = null; // �� ����� ��Ϣ֪ͨ�ͻ���
			BufferedWriter writerContent = null;
			String tongzhi = "������ˣ�����������";

			String content = null; // ���տͻ��˷���ָ�� "��Ҫ�����"

			writerContent = new BufferedWriter(new OutputStreamWriter(
					client.getOutputStream()));
			writer = new PrintWriter(new OutputStreamWriter(
					client.getOutputStream()));
			writer.println(tongzhi);
			writer.flush();

			// ���ú���
			// ReSendHongbao();

			while ((content = reader.readLine()) != null) {
				// float fnum ;
				// fnum = ((new Random().nextFloat())*2)*100/100;
				if (HServer.hongbao == 1) {
					fnum = HServer.money - total;
					// System.out.println("�����ʣһ������");
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
									+ Thread.currentThread().getName() + "˵�ˣ�"
									+ content);
							writer.flush();
							// System.out.println(Thread.currentThread().getName()+"������"+"��ʣ"
							// + HServer.hongbao + "�����");
							// System.out.println(Thread.currentThread().getName()+"������"+"��ʣ"
							// + HServer.hongbao + "�����");
						}
						if (HServer.hongbao == 0 && content.equals("yes")
								&& client.equals(clienti)) {
							writer.println("����Ѿ���������ȴ����ֺ��");
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
										+ "������" + (double) fnum / 100 + "��ʣ"
										+ HServer.hongbao + "�����");

							}
							writer.println(client.getInetAddress() + "  //"
									+ Thread.currentThread().getName() + "�����ˣ�"
									+ (double) fnum / 100);
							writer.flush();
						}

					} catch (Exception e) {
						// TODO: handle exception
						HServer.clientList.remove(clienti);
					}

				}// ��ǿforѭ������

			}// while((content = reader.readLine()) != null)ѭ������

		} catch (Exception e) {
			HServer.clientList.remove(client);
		}

	}
}
