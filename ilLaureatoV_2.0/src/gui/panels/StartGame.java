package gui.panels;

import controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import jdbc.CreatoreTavolaDiGioco;

public class StartGame extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		MainController mc = new MainController(primaryStage);
		new CreatoreTavolaDiGioco();
		Scene scene = new Scene(mc);
		scene.getStylesheets().add(getClass().getResource("/css/mainCss.css").toExternalForm());
		primaryStage.setTitle("Il Laureato");
		primaryStage.getIcons().add(new Image("data/icon.png"));
		primaryStage.setScene(scene);
		primaryStage.show();

		ImageView iv = new ImageView(new Image("data/unical.jpg"));
	}



	public static void main(String[] args) {
		launch(args);
	}

}
