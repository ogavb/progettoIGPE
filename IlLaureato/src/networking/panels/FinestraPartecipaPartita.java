package networking.panels;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FinestraPartecipaPartita {

	private Stage stage;
	private Scene scene;
	private Pane mainPane;

	private String style   = "-fx-background-color: linear-gradient(rgb(22, 179, 184) 5%, rgb(189, 51, 42) 100%) rgb(22, 179, 184);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;-fx-font-size: 11;";
    private String styleOn = "-fx-background-color: linear-gradient(rgb(30,140,150) 10%, rgb(189, 51, 42) 100%) rgb(22, 179, 184);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;-fx-font-size: 13;";

	private TextField ipServer;
	private Button ok;

	private String ipServ;

	public FinestraPartecipaPartita(){}

	public void showAndWait(){

		mainPane = new Pane();
		mainPane.setPrefWidth(300);
		mainPane.setPrefHeight(300);

		mainPane.setStyle("-fx-background-color: A2382B;");

		ipServer = new TextField();
		ipServer.setPromptText("IP SERVER");
		ipServer.setTranslateX(75);
		ipServer.setTranslateY(25);


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

		ok.setTranslateX(75);
		ok.setTranslateY(210);

		ok.setOnAction(event -> {
			ipServ = ipServer.getText();
			stage.close();
		});

		mainPane.getChildren().addAll(ipServer,ok);

		stage = new Stage();
		scene = new Scene(mainPane);
		stage.setScene(scene);
		stage.showAndWait();

	}

	public String getIpServer(){
		return this.ipServ;
	}

}
