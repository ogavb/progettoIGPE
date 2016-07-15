package networking;


import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.util.Pair;


public class RequestManager extends Thread {

	static RequestManager Instance;

	private Lock lock;
	private Condition condition;


	public static RequestManager getInstance() {
		if (Instance == null)
			Instance = new RequestManager();
		return Instance;
	}


	private List<Pair<Connection, String>> requestIn;

	private boolean stopped;

	private RequestManager() {

		stopped = false;
		requestIn = new CopyOnWriteArrayList<Pair<Connection, String>>();
		lock = new ReentrantLock();
		condition = lock.newCondition();
		this.start();
	}

	public void close(boolean stopped){
		lock.lock();
		this.stopped = stopped;
		condition.signalAll();
		lock.unlock();
	}

	public void addRequest(Pair<Connection, String> request) {
		lock.lock();
		requestIn.add(request);
		condition.signalAll();
		lock.unlock();
	}

	private String execute(String request) {
		// (IDRequest##body)
		System.out.println(request);

		if(request==null||request.equals("#END#")){//lopp di chiusura
			return "#END#";
		}
		else if(request.equals("#END_ALL#")){

			System.out.println("endAll");
			GestoreMatch.getInstance().notifyAll("#END#");
			Match m=GestoreMatch.getInstance().findForId(requestIn.get(0).getKey().getId());
			GestoreMatch.getInstance().removeMatch(m);
			return null;

		}
		String r[] = request.split("##");

		switch (Integer.parseInt(r[0])) {

		case 0:{//crea partita   (numPersone)
			Connection c=requestIn.get(0).getKey();
			if(c.getId()!=-1){
				return  "0#2";//già connesso su una partita
			}
			if(r.length>1){
				String s=r[1];
				int val=Integer.parseInt(s);

				int id=GestoreMatch.getInstance().getProgressiveId(val, c);
				System.out.println("Partita " + id);
				System.out.println("Matchs :");
				GestoreMatch.getInstance().stampa();
				c.setId(id);

				return "0#0";//ok

			}else{
				return "0#1";//non correttamente avviata.
			}
		}

		case 1:{//partecipa partita (idPartita)
			Connection c=requestIn.get(0).getKey();
			if(c.getId()!=-1){
				return  "1#2";//già connesso su una partita
			}
			if(r.length>1){

				int id=Integer.parseInt(r[1]);

				Match m=GestoreMatch.getInstance().findForId(id);


				if(m==null){
					return  "1#1";//non esiste la partita con id id
				}

				else if(m.addPlayer(c)){//ok

					c.setId(id);

					GestoreMatch.getInstance().notifyAll("1#4#"+GestoreMatch.getInstance().toString());

					return "1#0";//ok

				}else{

					System.out.println("Partita piena");
					return "1#3";//partita piena
				}
			}
			break;
		}
		case 2: //ping
			return "CIAODASERVER";

		case 3:
			//3##corpo    ["3"]["corpo"]
			if(r.length>1){

				Match m=GestoreMatch.getInstance().findForId(requestIn.get(0).getKey().getId());
				m.add(r[1]);
				if(m!=null){
					Iterator<String> is = m.getNamePlayers().iterator();
					StringBuilder namePlayers = new StringBuilder();

					while(is.hasNext()){
						String namePlayer = is.next();
						namePlayers.append(namePlayer+",");
					}

					m.notifyAll("3#"+namePlayers.toString());
				}

			}
			break;
		case 4:
			//4##...
			return "4#"+GestoreMatch.getInstance().toString();

		case 5:
			//5##avviaPartita
			Match m=GestoreMatch.getInstance().findForId(requestIn.get(0).getKey().getId());
			if(m!=null){
				m.notifyAll("5#" + r[1]);
			}

			break;

		case 6:
			//6##nome giocatore
			Match m1=GestoreMatch.getInstance().findForId(requestIn.get(0).getKey().getId());
			m1.remove(r[1]);
			if(m1!=null){
				Iterator<String> is = m1.getNamePlayers().iterator();
				StringBuilder namePlayers = new StringBuilder();

				while(is.hasNext()){
					String namePlayer = is.next();
					namePlayers.append(namePlayer+",");
				}

				m1.notifyAll("6#"+namePlayers.toString());
			}

			break;

		case 7:
			//7##
			Match m2=GestoreMatch.getInstance().findForId(requestIn.get(0).getKey().getId());
			m2.remove(requestIn.get(0).getKey());
			requestIn.get(0).getKey().setId(-1);
			GestoreMatch.getInstance().notifyAll("7#"+GestoreMatch.getInstance().toString());

			break;

		case 8:
			//8##
			GestoreMatch.getInstance().notifyAll("8#"+GestoreMatch.getInstance().toString());
			break;

		case 9:
			//9##
			System.err.println("ID: "+requestIn.get(0).getKey().getId());
			Match m3=GestoreMatch.getInstance().findForId(requestIn.get(0).getKey().getId());
			m3.notifyAll("#END_MATCH#");
			GestoreMatch.getInstance().removeMatch(m3);
			break;

		}// endswitch
		return null;
	}


	@Override
	public void run() {
		while (!stopped) {

			lock.lock();

			while(this.requestIn.isEmpty() && !stopped)
				try {
					condition.await();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			lock.unlock();

			if (!this.requestIn.isEmpty()) {
				String requestOut = this.execute(requestIn.get(0).getValue());
				System.out.println("reqout" + requestOut);
				if (requestOut != null) {// prende la connection e gli invia la
											// risposta



					requestIn.get(0).getKey().insertMessage(requestOut);
				}
				requestIn.remove(0);
			}
			try {
				sleep(10);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}
	}

}
