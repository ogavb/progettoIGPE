package gui.panels;

import controller.MainController;
import gui.editor.EditorDomande;
import gui.editor.EditorTavolaDiGioco;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceltaEditor {

   private Stage stage;
   private Scene scene;

   private Pane pane;
   private VBox box;
   private Button editorDomande;
   private Button editorTavola;
   private Button indietro;

   public SceltaEditor(Stage stage) {
      this.stage = stage;

      pane = new Pane();
      pane.setPrefSize(450, 210);

      this.box = new VBox(30.0);

      editorDomande = new Button("editor Domande");
      editorTavola = new Button("editor Tavola");
      indietro = new Button("Torna al menu");

      // EVENTI DEI BOTTONI
      editorDomande.setOnMouseReleased(e -> {
         this.stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
         new EditorDomande(stage);
      });

      indietro.setOnMouseReleased(e -> {
         this.stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
         try {
            new MainController(stage);
         }
         catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
      });

      editorTavola.setOnMouseReleased(e -> {
         this.stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
         try {
            new EditorTavolaDiGioco(stage);
         }
         catch (Exception e1) {
            System.err.println("eccezione");
         }
      });
      // FINE EVENTI BOTTONI

      box.getChildren().add(editorDomande);
      box.getChildren().add(editorTavola);
      box.getChildren().add(indietro);

      box.setTranslateX(150.0);
      box.setTranslateY(30.0);
      pane.getChildren().add(box);

      scene = new Scene(pane);
      // applico i css
      scene.getStylesheets().add("css/mainCss.css");

      this.stage.setScene(scene);
      this.stage.setResizable(false);
      this.stage.show();
   }
}
