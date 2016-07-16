package networking.panels;

import java.util.Random;

import concurrent.LockManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import networking.Client;
import networking.MultiThreadedServer;
import networking.RequestManagerClient;

public class FinestraCreaOPartecipaPartita {

	private Stage primaryStage;

	private Pane mainPane;

	private Button creaPartita;
	private Button partecipaPartita;

	private Client client;
	private MultiThreadedServer server;
	private RequestManagerClient rmc;
	private LockManager lockManager;

	private String nomeGiocatore;

	public FinestraCreaOPartecipaPartita() {

		lockManager = new LockManager();

		nomeGiocatore = nomeRandom(( int )( ( Math.random() * 10 ) + 1 ));

		primaryStage = new Stage();

		mainPane = new Pane();
		mainPane.setPrefWidth(300);
		mainPane.setPrefHeight(150);

		mainPane.setStyle("-fx-background-color: A2382B;");

		creaPartita = new Button("CREA PARTITA");
		partecipaPartita = new Button("PARTECIPA");

		String style   = "-fx-background-color: linear-gradient(rgb(22, 179, 184) 5%, rgb(189, 51, 42) 100%) rgb(22, 179, 184);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;-fx-font-size: 11;";
	    String styleOn = "-fx-background-color: linear-gradient(rgb(30,140,150) 10%, rgb(189, 51, 42) 100%) rgb(22, 179, 184);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;-fx-font-size: 13;";

	    creaPartita.setStyle(style);
		partecipaPartita.setStyle(style);

		creaPartita.setPrefSize(160, 40);
		partecipaPartita.setPrefSize(160, 40);

		creaPartita.setOnMouseEntered(event -> {
			creaPartita.setStyle(styleOn);
	    });
		creaPartita.setOnMouseExited(event -> {
			creaPartita.setStyle(style);
	    });

		partecipaPartita.setOnMouseEntered(event -> {
			partecipaPartita.setStyle(styleOn);
	    });
		partecipaPartita.setOnMouseExited(event -> {
			partecipaPartita.setStyle(style);
	    });

		creaPartita.setOnMouseReleased(event -> {

			int giocatoriNum = 0;
			String numeroGiocatori = null;

			SchermataNuovaPartitaMultiPlayer sm = null;

			FinestraCreaPartita fm = new FinestraCreaPartita();
			fm.showAndWait();

			if(fm.getIpServer() != null){

				if(fm.getIpServer().equals("")){

					giocatoriNum = fm.getNumGiocatori();

					numeroGiocatori = "0##" + String.valueOf(giocatoriNum);

					server = new MultiThreadedServer(4444);
					new Thread(server).start();

					try {
						client = new Client("127.0.0.1",4444);
					} catch (Exception e) {
						e.printStackTrace();
					}

					rmc = new RequestManagerClient(client);
					sm = new SchermataNuovaPartitaMultiPlayer(false,client,rmc);
					rmc.setSchermataNuovaPartitaMultiPlayer(sm);

					try {
						sm.setServer(server);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				else{

					giocatoriNum = fm.getNumGiocatori();

					numeroGiocatori = "0##" + String.valueOf(giocatoriNum);

					try {
						client = new Client(fm.getIpServer(),4444);
					} catch (Exception e) {
						Alert alert = new Alert(AlertType.ERROR);
			    		alert.setHeaderText("Nessun server trovato");
			    		alert.showAndWait();
			    		return;
					}

					rmc = new RequestManagerClient(client);
					sm = new SchermataNuovaPartitaMultiPlayer(true,client,rmc);
					rmc.setSchermataNuovaPartitaMultiPlayer(sm);

				}

				client.addRequest(numeroGiocatori);

				try {
					lockManager.attendiZero();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				primaryStage = ((Stage)((Button)event.getSource()).getScene().getWindow());
				primaryStage.setTitle("PROVA");
				System.err.println("STAGE " + primaryStage.getTitle());
				Parent root = sm.show(primaryStage,giocatoriNum,nomeGiocatore);

				client.setNomeGiocatore(nomeGiocatore);
				client.addRequest("3##"+nomeGiocatore);

				try {
					lockManager.attendiTre();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				client.addRequest("8##"+"notifica aggiunta match");

				try {
					lockManager.attendiOtto();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			    Scene scene = new Scene(root);
			    primaryStage.setScene(scene);
			    primaryStage.show();

		}

		});

		partecipaPartita.setOnMouseReleased(event -> {

			boolean ok = false;

			do{

				FinestraPartecipaPartita fpp = new FinestraPartecipaPartita();

				fpp.showAndWait();

				if(fpp.getIpServer() == null) return;

				if(!fpp.getIpServer().equals("")){
					try {
						client = new Client(fpp.getIpServer(),4444);
						rmc = new RequestManagerClient(client);
						client.addRequest("4##mostraPartite");
						ok = true;
					} catch (Exception e) {
						Alert alert = new Alert(AlertType.ERROR);
			    		alert.setHeaderText("Nessun server trovato");
			    		alert.showAndWait();
			    		return;
					}
				}
				else{
					Alert alert = new Alert(AlertType.INFORMATION);
		    		alert.setHeaderText("Inserisci IP del server");
		    		alert.showAndWait();
				}

			}while(!ok);

			try {
				lockManager.attendiQuattro();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			primaryStage = ((Stage)((Button)event.getSource()).getScene().getWindow());
			FinestraMultiPlayer fm = new FinestraMultiPlayer(client,nomeGiocatore,rmc);
			rmc.setFinsetraMultiplayer(fm);
	    	Parent root = fm.show(primaryStage,rmc.getMatchs());
	    	Scene scene = new Scene(root);
	    	primaryStage.setScene(scene);
	    	primaryStage.show();

		});



		creaPartita.setTranslateX(75);
		creaPartita.setTranslateY(25);

		partecipaPartita.setTranslateX(75);
		partecipaPartita.setTranslateY(75);

		mainPane.getChildren().add(creaPartita);
	    mainPane.getChildren().add(partecipaPartita);

	    Scene scene = new Scene(mainPane);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public String getNomeGiocatore(){
		return this.nomeGiocatore;
	}

	public void setNomeGiocatore(String nomeGiocatore){
		this.nomeGiocatore = nomeGiocatore;
	}

	private String nomeRandom (int length) {
		Random rnd = new Random ();
		char[] arr = new char[length];

		for (int i=0; i<length; i++) {
			int n = rnd.nextInt (36);
			arr[i] = (char) (n < 10 ? '0'+n : 'a'+n-10);
		}

		return new String (arr);
	}

}
