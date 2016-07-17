package networking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import concurrent.LockManager;
import core.GameManager;
import core.GameManagerAstratta;
import core.Giocatore;
import gui.panels.SchermataTavolaDiGioco;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import model.MatchTableView;
import networking.panels.FinestraMultiPlayer;
import networking.panels.SchermataNuovaPartitaMultiPlayer;

public class RequestManagerClient extends Thread{

	private FinestraMultiPlayer fm;

	private SchermataNuovaPartitaMultiPlayer sm;

	private SchermataTavolaDiGioco stg;

	private LockManager lockManager;

	private GameManagerNetwork gm;
	private String nomeConfigurazione;

	protected static Lock lock;
	protected static Condition condition;

	private boolean stopped;

	private Client client;

	private String[] matchs;

	public RequestManagerClient(Client client){

		this.client = client;

		this.lockManager = new LockManager();

		RequestManagerClient.lock = new ReentrantLock();
		RequestManagerClient.condition = lock.newCondition();

		this.stopped = false;

		this.start();

	}

	public GameManagerNetwork getGameManagerNetwork(){
		return this.gm;
	}

	public void setGameManagerNetwork(GameManagerAstratta gm){
		this.gm = (GameManagerNetwork) gm;
	}

	public FinestraMultiPlayer getFinestraMultiPlayer(){
		return this.fm;
	}

	public SchermataNuovaPartitaMultiPlayer getSchermataNuovaPartitaMultiPlayer(){
		return this.sm;
	}

	public void setFinsetraMultiplayer(FinestraMultiPlayer fm){
		this.fm = fm;
	}

	public void setSchermataNuovaPartitaMultiPlayer(SchermataNuovaPartitaMultiPlayer sm){
		this.sm = sm;
	}

	public void setNomeConfigurazione(String nomeCOnfigurazione){
		this.nomeConfigurazione = nomeCOnfigurazione;
	}

	public String[] getMatchs(){
		return this.matchs;
	}

	@Override
	public void run(){

		while(!stopped){

			lock.lock();

			String messaggio = client.popRequest();

			while(messaggio == null)
				try {
					condition.await();
					messaggio = client.popRequest();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			lock.unlock();

			if(messaggio != null){

				if(messaggio.equals("#END#") || messaggio.equals("null")){
					stopped = true;

					if(fm != null){
						System.err.println("ENTRATO NEL FM");
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								System.err.println("FM.CLOSE()");
								fm.close();
							}
						});

					}
					if( sm != null){
						System.err.println("ENTRATO NEL SM");
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								System.err.println("SM.CLOSE()");
								sm.close();
							}
						});
						lockManager.riprendiEndAll();
					}

					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							Alert alert = new Alert(AlertType.ERROR);
				    		alert.setHeaderText("Caduta connessione con il server");
				    		alert.showAndWait();
						}
					});

					System.out.println("REQUEST MANAGER CLIENT SPENTO");

				}

				else if(messaggio.equals("#END_MATCH#")){

					if( sm != null){
						System.err.println("ENTRATO NEL SM");
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								System.err.println("SM.CLOSE()");
								sm.close();
							}
						});
						lockManager.riprendiEndMatch();
					}

				}

				System.out.println();
				String r[] = messaggio.split("#");

				switch(r[0]){

				case "0":{

					lockManager.riprendiZero();
					break;
				}

				case "1":{

					switch(r[1]){
					case "0":{

						System.out.println("Registrazione effettuata");

//						lockManager.riprendi();
						break;

					}

					case "1":{

						System.out.println("Non esiste la partita");
//						lockManager.riprendi();
						break;

					}

					case "2":{

						System.out.println("Gia connesso alla partita");
//						lockManager.riprendi();
						break;

					}

					case "3":{

						System.out.println("Partita piena");
//						lockManager.riprendi();
						break;

					}

					case "4":{

						// Devo dire se non sono nella finestra FINESTRA_MULTIPLAYER
						// non devo aggiornare la tabella della partita altrimenti si

						if(fm != null){

							TableView<MatchTableView> tableMatchs = fm.getTableMatchs();


							String [] matchs = r[2].split("/");

							System.out.print("case 1#4 " + r[2]  + " ");




							List<MatchTableView> tmp = new ArrayList<>();
						    String match[] = null;
							for(String s : matchs){
								match = s.split(",");
								System.out.println("STAMPA S  "+s);
								tmp.add(new MatchTableView(Integer.parseInt(match[0]), Integer.parseInt(match[1]), Integer.parseInt(match[2])));

							}

							tableMatchs.getItems().clear();
							tableMatchs.getItems().addAll(tmp);

						}
						else
							System.out.println("Finestra Multiplayer è null");

						lockManager.riprendiUnoQuattro();

						break;

					}

					}

//					lockManager.riprendi();

					break;

				}

				case "3":{

					String [] r1 = r[1].split(",");

					sm.setNomeGiocatore(r1);

					lockManager.riprendiTre();

					break;

				}

				case "4":{

					matchs = r[1].split("/");

					for(int i = 0; i < matchs.length; i++){
						System.out.println("case 4 : " + matchs[i]);
					}

					lockManager.riprendiQuattro();

					break;
				}

				case "5":{
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							//gm = new GameManagerNetwork();

							String r1[] = r[1].split("/");
							String r2[] = r1[1].split(",");

							String r3[] = null;

							for(int i = 0; i < r2.length; i++){
								System.out.println("r2: " + r2[i]);
							}

							int numeroGiocatori = Integer.parseInt(r1[2]);

							Giocatore[] giocatori = new Giocatore[r2.length];
							for(int i = 0; i < giocatori.length; i++){
								r3 = r2[i].split("\\(");
								for(int j = 0; j < r3.length; j++){
									System.out.println("r3: " + r3[j]);
								}
								giocatori[i] = new Giocatore(r3[0]);
								giocatori[i].setColor(Integer.parseInt(r3[1]));
								giocatori[i].setOrdineDiPartenza(Integer.parseInt(r3[2]));
							}

							try {
								sm.initGameManager(giocatori, numeroGiocatori, r1[0]);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						sm.getGameManager().setOrdinaGiocatori(giocatori);
	    				//gm.decidiOrdine();

	    				try {
	    					//((Node)event.getSource()).getScene().getWindow().hide();
							stg = new SchermataTavolaDiGioco(sm.getGameManager());
						} catch (Exception e) {
							e.printStackTrace();
						}

	    				sm.getGameManager().start();
						}
					});



					lockManager.riprendiCinque();

					break;

				}

				case "6":{

					if(r.length > 1){

						String [] r1 = r[1].split(",");

						sm.setNomeGiocatore(r1);

					}

					lockManager.riprendiSei();

					break;

				}

				case "7":{

					if(fm != null){

						TableView<MatchTableView> tableMatchs = fm.getTableMatchs();

						String [] matchs = r[1].split("/");

						System.out.print("case 7 " + r[1]  + " ");

						List<MatchTableView> tmp = new ArrayList<>();
					    String match[] = null;
						for(String s : matchs){
							match = s.split(",");
							tmp.add(new MatchTableView(Integer.parseInt(match[0]), Integer.parseInt(match[1]), Integer.parseInt(match[2])));

						}

						tableMatchs.getItems().clear();
						tableMatchs.getItems().addAll(tmp);


					}

					lockManager.riprendiSette();

					break;

				}

				case "8":{

					if(fm != null){

						TableView<MatchTableView> tableMatchs = fm.getTableMatchs();


						String [] matchs = r[1].split("/");

						System.out.print("case 8# " + r[1]  + " ");




						List<MatchTableView> tmp = new ArrayList<>();
					    String match[] = null;
						for(String s : matchs){
							match = s.split(",");
							System.out.println("STAMPA S  "+s);
							tmp.add(new MatchTableView(Integer.parseInt(match[0]), Integer.parseInt(match[1]), Integer.parseInt(match[2])));

						}

						tableMatchs.getItems().clear();
						tableMatchs.getItems().addAll(tmp);

					}
					else
						System.out.println("Finestra Multiplayer è null");

					lockManager.riprendiOtto();

					break;

				}

				case "9":{

					System.err.println("ENTRATO NEL CASO 9");

					if(fm != null){
						System.err.println("ENTRATO NEL FM");
						fm.close();

					}
					else if( sm != null){
						System.err.println("ENTRATO NEL SM");
						sm.close();
					}

					break;

				}

				case "10":{

					((GameManagerNetwork) sm.getGameManager()).setYourRound(true);

					break;
				}

				case "11":{

					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							stg.getPannelloDado().animazione(Integer.parseInt(r[1]));
						}
					});

					sm.getGameManager().getGestore().next();
					break;
				}

				}

			}

		}

	}

}
