package gui.editor;

import model.Esame;
import util.TabellaEsameUtil;

import java.util.Arrays;

import gui.panels.SceltaEditor;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdbc.PrelevatoreDomanda;

public class EditorDomande {

	private Stage stage;
	private Scene scene;
	private Pane mainPane;

	private ImageView editorDomande = new ImageView(new Image("file:icone/editorDomande.png"));
	private ImageView nomeEsame = new ImageView(new Image("file:icone/nomeEsame.png"));
	private ImageView crediti = new ImageView(new Image("file:icone/crediti.png"));
	private ImageView domanda = new ImageView(new Image("file:icone/domanda.png"));
	private ImageView rispostaEsatta = new ImageView(new Image("file:icone/rispostaEsatta.png"));
	private ImageView rispostaSbagliata = new ImageView(new Image("file:icone/rispostaSbagliata.png"));

	private Button indietro;
	private Button aggiungi;
	private Button rimuovi;
	private Button annulla;

	private TextField nomeEsameTf;
	private TextField creditiTf;
	private TextField domandaTf;
	private TextField rispostaEsattaTf;
	private TextField rispostaSbagliataTf;

	private TableView<Esame> tableView;
	private TableColumn<Esame, String> nomeEsameTc;
	private TableColumn<Esame, Integer> creditiEsameTc;
	private TableColumn<Esame, String> domandaEsameTc;
	private TableColumn<Esame, String> rispostaEsattaEsameTc;
	private TableColumn<Esame, String> rispostaSbagliataEsameTc;

	public EditorDomande(Stage stage) {
		this.stage = stage;

		mainPane = new Pane();
		mainPane.setPrefWidth(1200);
		mainPane.setPrefHeight(720);

		indietro = new Button("Torna al menu");
		aggiungi = new Button("Aggiungi");
		rimuovi = new Button("Rimuovi");
	    annulla = new Button("Annulla");

		//applico effetto a tutti i componenti
		//e setto le posizioni a mano ( x,y)
		applicaEffetto(indietro);
		indietro.setTranslateX(20);
		indietro.setTranslateY(6);

		applicaEffetto(editorDomande);
		editorDomande.setTranslateX(300);
		editorDomande.setTranslateY(6);

		applicaEffetto(nomeEsame);
		nomeEsame.setTranslateX(20);
		nomeEsame.setTranslateY(170);

		applicaEffetto(crediti);
		crediti.setTranslateX(20);
		crediti.setTranslateY(270);

		applicaEffetto(domanda);
		domanda.setTranslateX(20);
		domanda.setTranslateY(370);

		applicaEffetto(rispostaEsatta);
		rispostaEsatta.setTranslateX(20);
		rispostaEsatta.setTranslateY(470);

		applicaEffetto(rispostaSbagliata);
		rispostaSbagliata.setTranslateX(20);
		rispostaSbagliata.setTranslateY(570);

		//TEXTFIELD
		nomeEsameTf = new TextField();
		nomeEsameTf.setPromptText("nome esame");
		applicaEffetto(nomeEsameTf);
		nomeEsameTf.setTranslateX(500);
		nomeEsameTf.setTranslateY(180);

		creditiTf = new TextField();
		creditiTf.setPromptText("crediti");
		applicaEffetto(creditiTf);
		creditiTf.setTranslateX(500);
		creditiTf.setTranslateY(280);

		domandaTf = new TextField();
		domandaTf.setPromptText("domanda");
		applicaEffetto(domandaTf);
		domandaTf.setTranslateX(500);
		domandaTf.setTranslateY(380);

		rispostaEsattaTf = new TextField();
		rispostaEsattaTf.setPromptText("risposta esatta");
		applicaEffetto(rispostaEsattaTf);
		rispostaEsattaTf.setTranslateX(500);
		rispostaEsattaTf.setTranslateY(480);

		rispostaSbagliataTf = new TextField();
		rispostaSbagliataTf.setPromptText("risposta sbagliata");
		applicaEffetto(rispostaSbagliataTf);
		rispostaSbagliataTf.setTranslateX(500);
		rispostaSbagliataTf.setTranslateY(580);
		//FINE TEXTFIELD

	    applicaEffetto(aggiungi);
	    applicaEffetto(rimuovi);
	    applicaEffetto(annulla);

	    // fine applico effetto componenti


	    //AGGIUNGO GLI EVENTI AI BOTTONI
	    indietro.setOnMouseReleased(e-> {
			this.stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
			new SceltaEditor(stage);
		});

	    aggiungi.setOnMouseReleased(event -> {
	    	String nome = nomeEsameTf.getText();
	    	int crediti = Integer.parseInt(creditiTf.getText());
	    	String domanda = domandaTf.getText();
	    	String rispostaEsatta = rispostaEsattaTf.getText();
	    	String rispoSbagliata = rispostaSbagliataTf.getText();

	    	nomeEsameTf.clear();
	    	creditiTf.clear();
	    	domandaTf.clear();
	    	rispostaEsattaTf.clear();
	    	rispostaSbagliataTf.clear();

	    	PrelevatoreDomanda database = new PrelevatoreDomanda();
	    	Esame esame = new Esame(1,nome,crediti,domanda,rispostaEsatta,rispoSbagliata);
	    	database.inserisciEsame(esame);

	    	tableView.getItems().add(esame);
	    });

	    rimuovi.setOnMouseReleased(event -> {
	    	PrelevatoreDomanda database = new PrelevatoreDomanda();
	    	TableViewSelectionModel<Esame> tsm = tableView.getSelectionModel();

	    	if (tsm.isEmpty()) {
	    		System.out.println("Please select a row to delete.");
	    		return;
	    	}

	    	ObservableList<Integer> lista = tsm.getSelectedIndices();
	    	Integer[] indiciSelezionati = new Integer[lista.size()];
	    	indiciSelezionati = lista.toArray(indiciSelezionati);

	    	Arrays.sort(indiciSelezionati);

	    	System.out.println(tsm.getSelectedItem().getCodice());
	    	database.elimina(tsm.getSelectedItem().getCodice());

	    	for(int i = 0; i <= indiciSelezionati.length - 1; i++) {
	    		tsm.clearSelection(indiciSelezionati[i].intValue());
	    		tableView.getItems().remove(indiciSelezionati[i].intValue());
	    	}

	    });

	    annulla.setOnMouseReleased(event -> {
	    	nomeEsameTf.setText("");
	    	creditiTf.setText("");
	    	domandaTf.setText("");
	    	rispostaEsattaTf.setText("");
	    	rispostaSbagliataTf.setText("");
	    });

	    //FINE EVENTI BOTTONI

	    aggiungi.setTranslateX(375);
	    aggiungi.setTranslateY(670);

	    rimuovi.setTranslateX(535);
	    rimuovi.setTranslateY(670);

	    annulla.setTranslateX(695);
	    annulla.setTranslateY(670);

	    createTableView();
	    applicaEffetto(tableView);
	    tableView.setTranslateX(700);
	    tableView.setTranslateY(180);

	    mainPane.getChildren().add(indietro);
		mainPane.getChildren().add(editorDomande);

		mainPane.getChildren().add(nomeEsame);
		mainPane.getChildren().add(crediti);
		mainPane.getChildren().add(domanda);
		mainPane.getChildren().add(rispostaEsatta);
		mainPane.getChildren().add(rispostaSbagliata);

		mainPane.getChildren().add(nomeEsameTf);
		mainPane.getChildren().add(creditiTf);
		mainPane.getChildren().add(domandaTf);
		mainPane.getChildren().add(rispostaEsattaTf);
		mainPane.getChildren().add(rispostaSbagliataTf);
	    mainPane.getChildren().add(aggiungi);
	    mainPane.getChildren().add(rimuovi);
	    mainPane.getChildren().add(annulla);
	    mainPane.getChildren().add(tableView);

	    scene = new Scene(mainPane);
		scene.getStylesheets().add("css/editor.css");
		this.stage.setScene(scene);
		this.stage.setResizable(false);
		this.stage.centerOnScreen();

		this.stage.show();

	}


	@SuppressWarnings("unchecked")
	private void createTableView(){
		tableView                = new TableView<Esame>(TabellaEsameUtil.getListaEsami());
	    nomeEsameTc 			 = TabellaEsameUtil.getColonnaNome();
	    creditiEsameTc			 = TabellaEsameUtil.getColonnaCrediti();
	    domandaEsameTc 			 = TabellaEsameUtil.getColonnaDomanda();
	    rispostaEsattaEsameTc    = TabellaEsameUtil.getColonnaRispostaEsatta();
	    rispostaSbagliataEsameTc = TabellaEsameUtil.getColonnaRispostaSbagliata();

	    tableView.getColumns().addAll(nomeEsameTc,creditiEsameTc, domandaEsameTc,
				rispostaEsattaEsameTc, rispostaSbagliataEsameTc);

	}

	private void applicaEffetto(Node node) {
		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(0);
		dropShadow.setOffsetY(0);
		dropShadow.setRadius(0);
		dropShadow.setColor(Color.BLUE);
		node.setEffect(dropShadow);
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		timeline.getKeyFrames().addAll
		(new KeyFrame(Duration.ZERO, new KeyValue(dropShadow.radiusProperty(), 0)),
		new KeyFrame(new Duration(500), new KeyValue(dropShadow.radiusProperty(), 10)));
		timeline.playFrom(Duration.ZERO);

		node.setOnMouseEntered(new EventHandler<MouseEvent>(){

			public void handle(MouseEvent me){
				timeline.playFrom(Duration.ZERO);
			}
		});
		node.setOnMouseExited(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent me){
				timeline.jumpTo(Duration.ZERO);
			}
		});
	}
}
