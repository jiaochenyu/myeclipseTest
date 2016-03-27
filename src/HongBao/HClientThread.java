package HongBao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class HClientThread implements Runnable {
	Socket client = null;
	BufferedReader reader = null;

	public HClientThread(Socket client) throws IOException {
		super();
		this.client = client;
		this.reader = new BufferedReader(new InputStreamReader(
				client.getInputStream()));
	}

	@Override
	public void run() {

		try {
			// BufferedWriter writer = new BufferedWriter(new
			// OutputStreamWriter(client.getOutputStream()));
			String content = null;
			while ((content = reader.readLine()) != null) {
				System.out.println(content);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}

}
