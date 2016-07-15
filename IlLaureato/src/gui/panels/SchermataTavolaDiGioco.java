package gui.panels;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import core.GameManager;
import core.Giocatore;
import core.Stato;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

//CLASSE DEL GIOCO VERO E PROPRIO
public class SchermataTavolaDiGioco implements Observer {

	private Stage stage;
	private BorderPane mainPane;

	private ToolBar toolBar;

	private Pane centro;

	private final static double centroX = 360.0;
	private final static double centroY = 270.0;

	private String iconaNuova = "file:icone/nuovaj.png";
	private String iconaSalva= "file:icone/salva.png";
	private String iconaCarica = "file:icone/carica.png";
	private String iconaChiudi= "file:icone/chiudi.png";

	private PannelloTavola pannelloTavola;

	private BorderPane pannelloComando;

	//pannelloComando è composto da:
	private HBox[] giocatori;
	private TextArea pannelloTextArea;
	private PannelloDado pannelloDado;

	private Label[] labelCrediti;
	private Label[] labelAnniAccademici;
	private VBox[] statistiche;

	private GameManager gm;
	private int schermoGrande = 1;
	private boolean schermoIntero = true;
	private Image image;
	private static Pane group = new Pane();


	public SchermataTavolaDiGioco(GameManager gm) throws Exception {

		this.gm = gm;
		this.stage = new Stage();
		stage.setTitle("Il Laureato");

		mainPane = new BorderPane();
		mainPane.setPrefWidth(1200);
		mainPane.setPrefHeight(720);
		mainPane.setMinWidth(BorderPane.USE_PREF_SIZE);
		mainPane.setMinHeight(BorderPane.USE_PREF_SIZE);
		mainPane.setMaxWidth(BorderPane.USE_PREF_SIZE);
		mainPane.setMaxHeight(BorderPane.USE_PREF_SIZE);


		//Crea menu e la toolbar
		VBox vb = new VBox();
		vb.getChildren().add(createToolBar());

		centro = new Pane();
		image = new Image("file:icone/ilLaureato.png");

		group.setTranslateX(centroX);
		group.setTranslateY(centroY);

		centro.getChildren().add(new ImageView(image));
		centro.getChildren().add(group);
		centro.setTranslateX(90);
        centro.setTranslateY(110);

		//inizializza il pannello del gioco
		pannelloTavola = new PannelloTavola(gm);

		labelAnniAccademici = new Label[gm.getNumeroGiocatori()];
		labelCrediti = new Label[gm.getNumeroGiocatori()];

		for(int i = 0; i < gm.getNumeroGiocatori(); i++){
			labelAnniAccademici[i] = new Label();
			labelCrediti[i] = new Label("0");
		}

		statistiche = new VBox[gm.getNumeroGiocatori()];
		for(int i = 0; i < gm.getNumeroGiocatori(); i++){
			statistiche[i] = new VBox();
		}

		//inizializza il pannello comando
		initPannelloComando();

		//inizializza pannello dei giocatori
		addPlayerLabelToPane();

		mainPane.setCenter(pannelloTavola);
		mainPane.setTop(vb);
		mainPane.setRight(pannelloComando);

		gm.deleteObservers();
		gm.getGestore().deleteObservers();
		gm.addObserver(this);
		gm.getGestore().addObserver(this);

        mainPane.getChildren().add(centro);


		Scene scene = new Scene(mainPane,1200,720,true);


		scene.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		    	schermoIntero = false;
		    	if(schermoIntero){
		    		centro.setTranslateX(170);
		    		centro.setTranslateY(150);
		    	}
		    	else{
		    		schermoGrande = -schermoGrande;
		    		if(schermoGrande < 0){
		    			centro.setTranslateX(170);
		    			centro.setTranslateY(110);
		    		}
		    		else{
		    			centro.setTranslateX(90);
		    			centro.setTranslateY(110);
		    		}
		    	}

		    }
		});
		scene.heightProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
		        System.out.println("Height: " + newSceneHeight);
		    }
		});

		/*
		 * Dopo che la finestra è visualizzata scatta l'evento a cui è registrato lo stage
		 * Il metedo imposta le coordinate delle image view chiamando il metodo
		 * setCoordinatesImageView
		 */

		stage.setOnShown(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {

				//Top
				for ( int i = 0; i < pannelloTavola.getNUM_COLS()-1; i++){
					pannelloTavola.setCoordinatesImageView(i, 0);
				}

				//Right
				for ( int i = 0; i < pannelloTavola.getNUM_COLS()-1; i++){
					pannelloTavola.setCoordinatesImageView(pannelloTavola.getNUM_COLS()-1, i);
				}

				//Bottom
				for ( int i = pannelloTavola.getNUM_COLS()-1; i > 0; i--){
					pannelloTavola.setCoordinatesImageView(i, pannelloTavola.getNUM_COLS()-1);
				}

				//Left
				for ( int i = pannelloTavola.getNUM_COLS()-1; i > 0; i--){
					pannelloTavola.setCoordinatesImageView(0, i);
				}


			}

		});

		scene.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				System.out.println(((MouseEvent) event).getSceneX() + " " + ((MouseEvent) event).getSceneY());
			}
		});
		stage.setScene(scene);
		stage.setFullScreen(false);
		//stage.centerOnScreen();
		stage.show();
	}

/*
 * Inizializzazione dei pannelli
 */

	private void initPannelloComando() {
		pannelloComando = new BorderPane();
		pannelloComando.setPrefWidth(300);
		pannelloComando.setPrefHeight(715);
		pannelloComando.setMinWidth(BorderPane.USE_COMPUTED_SIZE);
		pannelloComando.setMinHeight(BorderPane.USE_COMPUTED_SIZE);
		pannelloComando.setMaxWidth(BorderPane.USE_COMPUTED_SIZE);
		pannelloComando.setMaxHeight(BorderPane.USE_COMPUTED_SIZE);

		initPannelloTextArea();
		pannelloDado = new PannelloDado(0,gm);
		pannelloDado.setNumGiocatori(gm.getNumeroGiocatori());

		pannelloComando.setCenter(pannelloTextArea);
		pannelloComando.setBottom(pannelloDado);
	}

	private void initPannelloTextArea() {
		pannelloTextArea = new TextArea();
		pannelloTextArea.setEditable(false);
		OutputMediator.setTextArea(pannelloTextArea);
	}

/*
 * Fine inizializzazione pannelli
 */


/*
 * Crezione ToolBar
 */

	private ToolBar createToolBar(){
		Button nuovaPartita = new Button();
		nuovaPartita.setGraphic(new ImageView(new Image(iconaNuova)));
		nuovaPartita.setTooltip(new Tooltip("Crea nuova partita"));
		nuovaPartita.setTranslateZ(-1);
		nuovaPartita.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				new SchermataNuovaPartita(stage);
			}
		});

		Button caricaPartita = new Button();
		caricaPartita.setGraphic(new ImageView(new Image(iconaCarica)));
		caricaPartita.setTooltip(new Tooltip("Carica partita salvata"));
		caricaPartita.setTranslateZ(-1);
		caricaPartita.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				File f = new File("."); // current directory

				FilenameFilter textFilter = new FilenameFilter() {
					public boolean accept(File dir, String name) {
						String lowercaseName = name.toLowerCase();
						if (lowercaseName.endsWith(".fap")) {
							return true;
						} else {
							return false;
						}
					}
				};

				File[] files = f.listFiles(textFilter);
				List<String> choices = new ArrayList<String>();
				for (File file : files) {
					choices.add(file.getName());
				}
				ChoiceDialog<String> dialog = new ChoiceDialog<String>(choices.get(0), choices);
				dialog.setTitle("Carica partita");
				dialog.setContentText("Seleziona la partita");

				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
				    gm.oldPartita(result.get());
				}


			}

		});

		Button salvaPartita = new Button();
		salvaPartita.setGraphic(new ImageView(new Image(iconaSalva)));
		salvaPartita.setTooltip(new Tooltip("Salva"));
		salvaPartita.setTranslateZ(-1);
		salvaPartita.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Salva partita");
				dialog.setContentText("Inserisci il nome del file:");

				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
				    gm.salvaPartita(result.get());
				}
			}

		});

		Button esci = new Button();
		esci.setGraphic(new ImageView(new Image(iconaChiudi)));
		esci.setTooltip(new Tooltip("Esci"));
		esci.setTranslateZ(-1);
		esci.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}

		});

		toolBar = new ToolBar(nuovaPartita, caricaPartita, salvaPartita, new Separator(Orientation.VERTICAL), esci, new Separator(Orientation.VERTICAL)); //, start);

		return toolBar;
	}

 /*
 * Fine creazione MenuBar e ToolBar
 */

	private void addPlayerLabelToPane() {
		int numGiocatori = gm.getNumeroGiocatori();
		//definizione array contenente i colori dei contorni dei bottoni
		ArrayList<Color> colori = new ArrayList<Color>(6);
		colori.add(Color.BLUE);
		colori.add(Color.CYAN);
		colori.add(Color.GREEN);
		colori.add(Color.PINK);
		colori.add(Color.RED);
		colori.add(Color.YELLOW);
		//definizione array contenente le immagini delle icone dei bottoni dei giocatori
		ArrayList<String> playerPictures = new ArrayList<String>(6);

		playerPictures.add("file:giocatori/blue.gif");
		playerPictures.add("file:giocatori/cyan.gif");
		playerPictures.add("file:giocatori/green.gif");
		playerPictures.add("file:giocatori/purple.gif");
		playerPictures.add("file:giocatori/red.gif");
		playerPictures.add("file:giocatori/yellow.gif");

		VBox v = new VBox();
		giocatori = new HBox[numGiocatori];

		for(int i = 0; i < numGiocatori; i++){
			giocatori[i] = new HBox();
		}

		//costruzione bottoni
        for (int i = 0; i < numGiocatori; i++)
        {
        	//GameManager.getIstance().getGestore().getGiocatore(i).setColor(i);
        	addLabel(gm.getNomeGiocatore(i), giocatori[i],
        			(String)playerPictures.get(gm.getGestore().getGiocatore(i).getColor()),
        			(Color)colori.get(gm.getGestore().getGiocatore(i).getColor()));
        	giocatori[i].getChildren().add(statistiche[i]);
        	mostraStatistiche(statistiche[i], gm.getGestore().getGiocatore(i), gm.getGestore().getGiocatore(i).getCrediti(), gm.getGestore().getGiocatore(i).getAnniAccademici());
        	statistiche[i].getChildren().add(labelCrediti[gm.getGestore().getGiocatore(i).getOrdineDiPartenza()]);
    		statistiche[i].getChildren().add(labelAnniAccademici[gm.getGestore().getGiocatore(i).getOrdineDiPartenza()]);
        	v.getChildren().add(giocatori[i]);

        }

        pannelloComando.setTop(v);

	}

	private void addLabel(String text, HBox container, String image, Color cc){
		Label label = new Label(text,new ImageView(new Image(image)));
		label.setTextFill(cc);
		label.setPrefSize(50, 27);
		label.setMinSize(50, 27);
		label.setMaxSize(50, 27);
		container.getChildren().add(label);
	}

	private void mostraStatistiche(VBox vb, Giocatore g, int crediti, int anniAccademici) {
		System.err.println("crediti del giocatore" +crediti);
		labelCrediti[g.getOrdineDiPartenza()].setText("Crediti: " + crediti);
		labelAnniAccademici[g.getOrdineDiPartenza()].setText("Anni accademici: " + anniAccademici);
	}

	@Override
	public void update(Observable o, Object arg) {
		Stato ogg = (Stato)arg;
		Integer scelta = ogg.getScelta();
		//System.out.println("Scelta " + scelta);

		switch(scelta){

		case 0:
			String[] nomiGiocatori = new String[gm.getNumeroGiocatori()];
			for(int i = 0; i < nomiGiocatori.length; i++){
				nomiGiocatori[i] = gm.getNomeGiocatore(i);
			}
			try {
				((Node)toolBar).getScene().getWindow().hide();
				new SchermataTavolaDiGioco(gm);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case 1:
			pannelloDado.setPrimo(ogg.getGiocatore().getRisultatoDado());
			break;

		case 2:
			pannelloTavola.spostaGiocatore(ogg.getGiocatore());
			group.getChildren().clear();
			break;

		case 3:
			pannelloTavola.rimuoviGiocatore(ogg.getGiocatore());
			break;

		case 5:
			mostraStatistiche(statistiche[ogg.getGiocatore().getOrdineDiPartenza()], ogg.getGiocatore(), ogg.getGiocatore().getCrediti(),  ogg.getGiocatore().getAnniAccademici());
			break;
		}
	}

	protected static void switchTo(Pane currentPane){
		group.setTranslateX(centroX-(currentPane.getWidth()/2));
		group.setTranslateY(centroY-(currentPane.getHeight()/2));
		group.getChildren().add(currentPane);

	}

}
