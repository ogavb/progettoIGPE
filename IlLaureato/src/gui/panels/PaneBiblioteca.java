package gui.panels;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PaneBiblioteca extends Pane {

   private VBox box = new VBox(10.0);
   private HBox hbox = new HBox(10.0);
   private TextArea text = new TextArea();

   public PaneBiblioteca() {

      this.getStylesheets().add("css/textArea.css");
      this.setWidth(440.0);
      this.setHeight(500.0);

      Image image1 = new Image("file:icone/bibliotecaL.gif");
      Image image2 = new Image("file:icone/bibliotecaLibro.gif");
      ImageView v = new ImageView(image1);
      // translo per avere un allineamento grafico nelle immagini
      v.setTranslateY(130.0);
      text.setText(
            "OPS! purtroppo sei in biblioteca ma..\nper tua fortuna non hai imparato molto!!\n"
                  + "BEN FATTO! Rilancia il dado!");
      hbox.getChildren().addAll(new ImageView(image2), v);
      box.getChildren().addAll(text, hbox);
      getChildren().add(box);

   }

}
