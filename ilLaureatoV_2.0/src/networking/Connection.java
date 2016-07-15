package networking;

import java.net.Socket;
//import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Connection implements Runnable {

	private Socket clientSocket;
	private int Id;
	private List<String> send;

	private Lock lock;
	private Condition condition;

    //private boolean Stop=false;
    private boolean StopRecive=false;
    private boolean StopSend=false;
	Connection(Socket clientSocket){
		this.clientSocket = clientSocket;

		this.send=new CopyOnWriteArrayList<String>();
		this.Id = -1;

		this.lock = new ReentrantLock();
		this.condition = this.lock.newCondition();
		//int id=-1;
		//send.add("asadsad");
		//this.send.add("#END#");

	}
	public int getId(){
		return this.Id;
	}
	public void setId(int id){
		this.Id=id;
	}


	private Socket getSocket(){
		return this.clientSocket;
	}
	public void insertMessage(String request){
		lock.lock();
		send.add(request);
		condition.signalAll();
		lock.unlock();
	}
	Connection getThis(){
		return this;
	}
	@Override
	public void run() {

		new Thread(){
			@Override
			public void run() {
				PrintWriter out;
				try {
					out=new PrintWriter(getSocket().getOutputStream());
					try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					while(!StopSend){

						lock.lock();

						while(send.isEmpty())
							try {
								condition.await();
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						lock.unlock();

						if(!send.isEmpty()){
							String message=send.get(0);
							System.out.println("send - "+message);
							if(message.equals("#END#"))
							{
								try {
									sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

							}
							out.println(message);
							if(message.equals("#END#")){
								StopSend=true;


							}
							send.remove(0);
							out.flush();
							System.out.println("message send "+message);
						}
					}
					System.out.println("StoppedSend");
					try {
						sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					out.close();
					GestoreMatch.getInstance().removeUsers(getThis());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
        }.start();
        new Thread(){
			@Override//1socket
			public void run() {
				//PrintWriter out;
				BufferedReader in;
				try {
					in=new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
					try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					while(!StopRecive){
						String S=in.readLine();
						System.out.println("_>"+S);
						if(S!=null){
							RequestManager.getInstance().addRequest(new Pair<Connection,String>(getThis(),S));
						}
						if(S==null|| S.equals("#END#")){//TODO prova
							StopRecive=true;
							RequestManager.getInstance().addRequest(new Pair<Connection,String>(getThis(),S));
							//send.add("#END#");

						}
					}
					System.out.println("Stoppedrecive");
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }.start();




	}

}
