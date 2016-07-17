package networking.panels;

import java.sql.SQLException;
import java.util.Iterator;

import javax.activity.InvalidActivityException;

import concurrent.LockManager;
import core.GameManagerAstratta;
import core.Giocatore;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import jdbc.CreatoreTavolaDiGioco;
import networking.Client;
import networking.GameManagerNetwork;
import networking.MultiThreadedServer;
import networking.RequestManagerClient;

public class SchermataNuovaPartitaMultiPlayer {

	private Stage primaryStage;
	private BorderPane mainPane;

	private final String style   = "-fx-background-color: linear-gradient(rgb(22, 179, 184) 5%, rgb(189, 51, 42) 100%) rgb(22, 179, 184);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;-fx-font-size: 11;";
    private final String styleOn = "-fx-background-color: linear-gradient(rgb(30,140,150) 10%, rgb(189, 51, 42) 100%) rgb(22, 179, 184);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;-fx-font-size: 13;";

	private GridPane paneCenter;
	private BorderPane paneTop;
	private HBox paneBottom;

	private Button conferma;
	private Button esci;
	private ComboBox<String> configurazioni;
	private Label[] lblNomeGiocatore;
    private TextField[] txtNomeGiocatore;
	private String[] ColorPlayer = {"blue", "cyan", "green", "purple", "red", "yellow"};

	private boolean isClient;
	private int numGiocatori;

	private Client client;
	private MultiThreadedServer server;
	private RequestManagerClient rmc;
	private LockManager lockManager;

	private String nomeGiocatore;

	private GameManagerAstratta gm;
	private Giocatore[] giocatori;



	public SchermataNuovaPartitaMultiPlayer(boolean isClient,Client client,RequestManagerClient rmc) {

		this.isClient = isClient;
		this.rmc = rmc;
		this.client = client;
		this.lockManager = new LockManager();

	}

	public Giocatore[] getGiocatori(){
		return this.giocatori;
	}

	public GameManagerAstratta getGameManager(){
		return this.gm;
	}

	public void setIsClient(boolean isClient){
		this.isClient = isClient;
	}

	public void close(){
		try{
			if(primaryStage != null)
				primaryStage.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.err.println("CHIAMATO METODO CLOSE DI NUOVA PARTITA");
	}

	public Parent show(Stage stage,int numGiocatori,String nomeGiocatore){

		this.nomeGiocatore = nomeGiocatore;

		this.primaryStage = stage;
		System.err.println("HO CREATO");

		stage.setTitle("Nuova Partita");

		mainPane = new BorderPane();
		mainPane.setPrefWidth(400);
		mainPane.setPrefHeight(400);
		mainPane.setMinWidth(BorderPane.USE_PREF_SIZE);
		mainPane.setMinHeight(BorderPane.USE_PREF_SIZE);
		mainPane.setMaxWidth(BorderPane.USE_PREF_SIZE);
		mainPane.setMaxHeight(BorderPane.USE_PREF_SIZE);

		drawSelectPlayer(numGiocatori);


		mainPane.setTop(paneTop);
		mainPane.setCenter(paneCenter);
		mainPane.setBottom(paneBottom);

		return mainPane;

	}

	public void setServer(MultiThreadedServer server) throws InvalidActivityException {

		if(isClient){
			throw new InvalidActivityException("Is only client");
		}
		else{
			this.server = server;
		}


	}

	public void setNomeGiocatore(String [] nomiGiocatori){

		for (int j=0; j < txtNomeGiocatore.length; j++)
			txtNomeGiocatore[j].setText("");

		for (int j=0; j < nomiGiocatori.length; j++)
			txtNomeGiocatore[j].setText(nomiGiocatori[j]);

	}

	private void drawSelectPlayer(int nGioc) {

		this.numGiocatori = nGioc;

		paneTop = new BorderPane();
		paneTop.setStyle("-fx-background-color: A2382B;");

		lblNomeGiocatore = new Label[nGioc];
        txtNomeGiocatore = new TextField[nGioc];

        paneCenter = new GridPane();
        paneCenter.setPrefSize(20, 10);
        paneCenter.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        paneCenter.setStyle("-fx-background-color: A2382B;");

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        paneCenter.getColumnConstraints().addAll(column1, column2);

        for (int j=0; j < nGioc; j++){
        	lblNomeGiocatore[j] = new Label("Giocatore " + (j+1), new ImageView(new Image("file:giocatori/" + ColorPlayer[j] + ".gif")));
        	lblNomeGiocatore[j].setStyle(style);
        	lblNomeGiocatore[j].setStyle(style);
        	txtNomeGiocatore[j] = new TextField();
        	txtNomeGiocatore[j].setEditable(false);
        	GridPane.setConstraints(lblNomeGiocatore[j], 0, j);
        	GridPane.setConstraints(txtNomeGiocatore[j], 1, j);
        	paneCenter.setVgap(25);
        	paneCenter.getChildren().addAll(lblNomeGiocatore[j], txtNomeGiocatore[j]);
        }

        conferma = new Button("CONFERMA");
        esci = new Button("ESCI");

        conferma.setStyle(style);
        conferma.setStyle(style);

        conferma.setPrefSize(160, 40);
        conferma.setPrefSize(160, 40);

        conferma.setOnMouseEntered(event -> {
        	conferma.setStyle(styleOn);
	    });
        conferma.setOnMouseExited(event -> {
        	conferma.setStyle(style);
	    });

        esci.setStyle(style);
        esci.setStyle(style);

        esci.setPrefSize(160, 40);
        esci.setPrefSize(160, 40);

        esci.setOnMouseEntered(event -> {
        	esci.setStyle(styleOn);
	    });
        esci.setOnMouseExited(event -> {
        	esci.setStyle(style);
	    });

        paneBottom = new HBox();
        paneBottom.setStyle("-fx-background-color: A2382B;");
        paneBottom.setAlignment(Pos.BOTTOM_RIGHT);
        ObservableList<String> listaConfigurazioni;
        try {
        	listaConfigurazioni= new CreatoreTavolaDiGioco().prelevaNomiConfigurazioni();
			configurazioni = new ComboBox<>(listaConfigurazioni);
			configurazioni.setValue(listaConfigurazioni.get(0));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

        configurazioni.setStyle(style);
        configurazioni.setStyle(style);

        configurazioni.setPrefSize(160, 40);
        configurazioni.setPrefSize(160, 40);

        configurazioni.setOnMouseEntered(event -> {
        	configurazioni.setStyle(styleOn);
	    });
        configurazioni.setOnMouseExited(event -> {
        	configurazioni.setStyle(style);
	    });

        if(isClient && rmc.getFinestraMultiPlayer()!=null){

        	conferma.setDisable(true);
        	configurazioni.setDisable(true);

        }

        paneBottom.getChildren().add(configurazioni);
        paneBottom.getChildren().add(conferma);
        paneBottom.getChildren().add(esci);

        primaryStage.setOnCloseRequest(event -> {

        	//client partecipante
			if(isClient && rmc.getFinestraMultiPlayer()!=null){
				client.addRequest("6##"+nomeGiocatore);

				try {
					lockManager.attendiSei();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				client.addRequest("7##aggiorna rimozione giocatore finestra dei match");

				try {
					lockManager.attendiSette();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				client.addRequest("4##mostraPartite");

				try {
					lockManager.attendiQuattro();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				primaryStage = ((Stage)((Button)event.getSource()).getScene().getWindow());
				System.err.println("Stmp "+primaryStage.getTitle());
				for(int i = 0; i < rmc.getMatchs().length; i++){
					System.err.println("Cosimo: " + rmc.getMatchs()[i]);
				}

				Parent root = rmc.getFinestraMultiPlayer().show(primaryStage,rmc.getMatchs());
				Scene scene = new Scene(root);
		    	primaryStage.setScene(scene);
		    	primaryStage.show();
			}
				//CLIENT CHE HA CREATO LA PARTITA SU UN SERVER PRESENTE
			else if(isClient){
				System.err.println("CLIENT CHE HA CREATO LA PARTITA SU UN SERVER PRESENTE");
				client.addRequest("9##");

				try {
					lockManager.attendiEndMatch();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			else{

					System.err.println("ENTRATO NELL' ELSE NON e' UN CLIENT");
					client.addRequest("#END_ALL#");

					try {
						lockManager.attendiEndAll();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.err.println("MI SONO SBLOCCATO DAL LOCK");

					if(server != null){
						server.stop();
					}

				}

        });

        esci.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				//client partecipante
				if(isClient && rmc.getFinestraMultiPlayer()!=null){
					client.addRequest("6##"+nomeGiocatore);

					try {
						lockManager.attendiSei();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					client.addRequest("7##aggiorna rimozione giocatore finestra dei match");

					try {
						lockManager.attendiSette();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					client.addRequest("4##mostraPartite");

					try {
						lockManager.attendiQuattro();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					primaryStage = ((Stage)((Button)event.getSource()).getScene().getWindow());
					System.err.println("Stmp "+primaryStage.getTitle());
					for(int i = 0; i < rmc.getMatchs().length; i++){
						System.err.println("Cosimo: " + rmc.getMatchs()[i]);
					}

					Parent root = rmc.getFinestraMultiPlayer().show(primaryStage,rmc.getMatchs());
					Scene scene = new Scene(root);
			    	primaryStage.setScene(scene);
			    	primaryStage.show();
				}
					//CLIENT CHE HA CREATO LA PARTITA SU UN SERVER PRESENTE
				else if(isClient){
					System.err.println("CLIENT CHE HA CREATO LA PARTITA SU UN SERVER PRESENTE");
					client.addRequest("9##");

					try {
						lockManager.attendiEndMatch();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				else{

						System.err.println("ENTRATO NELL' ELSE NON e' UN CLIENT");
						client.addRequest("#END_ALL#");

						try {
							lockManager.attendiEndAll();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.err.println("MI SONO SBLOCCATO DAL LOCK");

						if(server != null){
							server.stop();
						}

					}

			}

        });

        conferma.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				boolean errorFlag;
    			giocatori = new Giocatore[numGiocatori];
    			errorFlag = false;

    			for (int k = 0; k < numGiocatori; k++)
    			{
    				// il metodo trim() di String ritorna una stringa rimuovendo
    				// gli spazi ( se presenti ), all'inizio e alla fine della stringa

    				if (txtNomeGiocatore[k].getText().trim().equalsIgnoreCase(""))
    					errorFlag = true;

    				giocatori[k] = new Giocatore(txtNomeGiocatore[k].getText());
    				giocatori[k].setColor(k);

    			}

    			if (errorFlag == true){

    				Alert alert = new Alert(AlertType.ERROR);
    				alert.setTitle("Errore");
    				alert.setContentText("Attenzione: inserire tutti i giocatori!");

    				alert.showAndWait();

    		 	}

    			else
    		 	{

    				String nomeConfigurazione = ((String)configurazioni.getSelectionModel().getSelectedItem());
    				String numeroGiocatori = String.valueOf(giocatori.length);
    				StringBuilder nomiGiocatori = new StringBuilder();

    				try {
						initGameManager(giocatori, giocatori.length, nomeConfigurazione);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

    				rmc.setGameManagerNetwork(gm);

    				gm.decidiOrdine();

    				for(Giocatore g :  gm.getGiocatori()){
    					System.err.println("Ordine giocatore " + g.getOrdineDiPartenza());
    				}


    				for(int i = 0; i < giocatori.length; i++){
    					if(i == giocatori.length - 1){
    						giocatori[i] = gm.getGiocatore(i);
    						nomiGiocatori.append(giocatori[i].getNome() + "(" + giocatori[i].getColor() + "(" + giocatori[i].getOrdineDiPartenza());
    					}
    					else{
    						giocatori[i] = gm.getGiocatore(i);
    						nomiGiocatori.append(giocatori[i].getNome() + "(" + giocatori[i].getColor() + "(" + giocatori[i].getOrdineDiPartenza() + ",");
    					}
    				}

    				System.err.println("GIOCATORI: " + nomiGiocatori.toString());

    				client.addRequest("5##" + nomeConfigurazione + "/" + nomiGiocatori.toString() + "/" + numeroGiocatori);

    				try {
    					lockManager.attendiCinque();
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}

    				client.addRequest("13##"+giocatori[0].getNome()+"##10");



    			}
			}

        });

	}

	public void initGameManager(Giocatore[] nomiGiocatori, int numeroGiocatori, String nomeConfigurazione) throws SQLException{

		this.gm = new GameManagerNetwork();
		((GameManagerNetwork)this.gm).setClient(client);
		this.gm = gm.init(nomiGiocatori, numeroGiocatori, nomeConfigurazione);

	}

}
