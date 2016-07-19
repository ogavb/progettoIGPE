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

	private TextField ipServer;
	private Button ok;

	private String ipServ;

	public FinestraPartecipaPartita(){}

	public void showAndWait(){


		mainPane = new Pane();
		mainPane.setPrefWidth(300);
		mainPane.setPrefHeight(300);

		ipServer = new TextField();
		ipServer.setPromptText("IP SERVER");
		ipServer.setTranslateX(75);
		ipServer.setTranslateY(25);


		ok = new Button("OK");

		ok.setTranslateX(75);
		ok.setTranslateY(210);

		ok.setOnAction(event -> {
			ipServ = ipServer.getText();
			stage.close();
		});

		mainPane.getChildren().addAll(ipServer,ok);

		stage = new Stage();
		scene = new Scene(mainPane);
		scene.getStylesheets().add("css/mainCss.css");
		stage.setScene(scene);
		stage.showAndWait();

	}

	public String getIpServer(){
		return this.ipServ;
	}

}
