package networking.panels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FinestraCreaPartita {

	private Stage stage;
	private Scene scene;
	private Pane mainPane;

	private String style   = "-fx-background-color: linear-gradient(rgb(22, 179, 184) 5%, rgb(189, 51, 42) 100%) rgb(22, 179, 184);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;-fx-font-size: 11;";
    private String styleOn = "-fx-background-color: linear-gradient(rgb(30,140,150) 10%, rgb(189, 51, 42) 100%) rgb(22, 179, 184);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;-fx-font-size: 13;";

	private TextField ipServer;
	private ComboBox<Integer> nGiocatori;
	private RadioButton selezionaIpServer;
	private Button ok;

	private String ipServ;
	private Integer numGiocatori;

	public FinestraCreaPartita(){}

	public void showAndWait(){

		mainPane = new Pane();
		mainPane.setPrefWidth(300);
		mainPane.setPrefHeight(300);

		mainPane.setStyle("-fx-background-color: A2382B;");

		ipServer = new TextField();
		ipServer.setPromptText("IP SERVER");
		ipServer.setEditable(false);
		ipServer.setTranslateX(75);
		ipServer.setTranslateY(25);

		selezionaIpServer = new RadioButton();
		selezionaIpServer.setTranslateX(250);
		selezionaIpServer.setTranslateY(25);

		selezionaIpServer.setOnAction(event -> {
			ipServer.setEditable(true);
		});

		ObservableList<Integer> items = FXCollections.observableArrayList();
		items.add(2); items.add(3); items.add(4); items.add(5); items.add(6);
		nGiocatori = new ComboBox<Integer>(items);
		nGiocatori.setPrefSize(50, 20);
		nGiocatori.setPromptText("NUMERO GIOCATORI");
		nGiocatori.setTranslateX(75);
		nGiocatori.setTranslateY(110);


		nGiocatori.setStyle(style);
		nGiocatori.setStyle(style);

		nGiocatori.setPrefSize(160, 40);
		nGiocatori.setPrefSize(160, 40);

		nGiocatori.setOnMouseEntered(event -> {
			nGiocatori.setStyle(styleOn);
	    });
		nGiocatori.setOnMouseExited(event -> {
			nGiocatori.setStyle(style);
	    });
		nGiocatori.setOnAction(event -> {
			ok.setDisable(false);
		});


		ok = new Button("OK");

		ok.setStyle(style);
		ok.setStyle(style);

		ok.setPrefSize(160, 40);
		ok.setPrefSize(160, 40);

		ok.setOnMouseEntered(event -> {
			ok.setStyle(styleOn);
	    });
		ok.setOnMouseExited(event -> {
			ok.setStyle(style);
	    });

		ok.setDisable(true);
		ok.setTranslateX(75);
		ok.setTranslateY(210);

		ok.setOnAction(event -> {
			ipServ = ipServer.getText();
			numGiocatori = nGiocatori.getSelectionModel().getSelectedItem();
			stage.close();
		});

		mainPane.getChildren().addAll(ipServer,nGiocatori,selezionaIpServer,ok);

		stage = new Stage();
		scene = new Scene(mainPane);
		stage.setScene(scene);
		stage.showAndWait();

	}

	public String getIpServer(){
		return this.ipServ;
	}

	public Integer getNumGiocatori(){
		return this.numGiocatori;
	}

}