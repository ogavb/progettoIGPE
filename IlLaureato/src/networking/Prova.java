package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class Prova {

	public static void main(String[] args) throws UnknownHostException, IOException {

		MultiThreadedServer server = new MultiThreadedServer(4444);
		new Thread(server).start();
		Client client = new Client("127.0.0.1",4444);
		RequestManagerClient rmc = new RequestManagerClient(client);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String messaggio = br.readLine();

		while(!messaggio.equals("-1")){

			client.addRequest(messaggio);
			messaggio = br.readLine();

		}

		server.stop();

	}

}
