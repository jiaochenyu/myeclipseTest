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
		
			// �ͻ��˽���

			try {
				Socket client = new Socket("10.40.7.15", 40000);

				// ���շ�������������Ϣ
				// �����߳�
				new Thread(new HClientThread(client)).start();

				// ������Ϣ�������� Ҳ���ǽ���Ϣд�뵽�����
				// �ȴӼ��̶����� Ȼ���������������д�뵽�����
				System.out.println("/**�˳��밴 '+' , �����������  'yes'  ****/");

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
						// System.out.println("�ͻ����������Ϣ��" + content );
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
