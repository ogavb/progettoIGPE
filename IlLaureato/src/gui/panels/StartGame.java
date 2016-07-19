package gui.panels;

import controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import jdbc.CreatoreTavolaDiGioco;

public class StartGame extends Application {

   // questo primaryStage deve essere l'unico Stage dell'applicazione
   @Override
   public void start(Stage primaryStage) throws Exception {

      MainController mc = new MainController(primaryStage);

      // avvio database cosi non ho problemi
      new CreatoreTavolaDiGioco();

      primaryStage.setTitle("Il Laureato");
      primaryStage.getIcons().add(new Image("data/icon.png"));
      primaryStage.setScene(mc.getScena());
      primaryStage.show();
   }

   public static void main(String[] args) {
      launch(args);
   }

}
