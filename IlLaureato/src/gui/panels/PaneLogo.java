package gui.panels;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PaneLogo extends Pane {

   public PaneLogo() {
      Image image = new Image("file:icone/ilLaureato.png");
      getChildren().add(new ImageView(image));
      setMaxSize(720, 540);

   }

}
