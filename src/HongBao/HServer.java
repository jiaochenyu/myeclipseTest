package HongBao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HServer {
	static List<Socket> clientList = new ArrayList<Socket>();
	static int hongbao = 3;
	static int money = 500;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		// TODO Auto-generated method stub

		ServerSocket server = new ServerSocket(8080);

		// System.out.println("发送了第" + + "轮红包");

		while (true) {

			Socket client = server.accept();
			// System.out.println("Hserverhongbao="+hongbao);
			System.out.println("**************");
			clientList.add(client);
			System.out.println(client.getInetAddress() + "已上线");
			new Thread(new HServerThread(client)).start();

		}
	}

}
