package gui.panels;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PaneCR extends Pane {

   private VBox vbox = new VBox(10.0);
   private HBox hbox = new HBox();
   private TextArea text = new TextArea();

   public PaneCR() {
      this.getStylesheets().add("css/textArea.css");

      this.setWidth(400.0);
      this.setHeight(400.0);

      text.setText("Sei al centro residenziale! "
            + "\nti aspettano file interminabili.." + "\nstai fermo 3 turni!!");
      hbox.getChildren().add(new ImageView(new Image("file:icone/rabbia.gif")));
      vbox.getChildren().addAll(text, hbox);
      getChildren().add(vbox);
   }
}