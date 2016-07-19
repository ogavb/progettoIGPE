package gui.panels;

import core.Casella;
import javafx.scene.DepthTest;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;

public class MyPane extends FlowPane {

   private Casella c;

   public MyPane(Casella c, Image immagine) {
      this.setCasella(c);
      setBackGround(immagine);

      this.setDepthTest(DepthTest.ENABLE);
      this.setTranslateZ(0.0);

// System.out.println("La z del pannello è " + this.getTranslateZ());
   }

   public MyPane() {
   }

   public Casella getCasella() {
      return c;
   }

   public void setCasella(Casella c) {
      this.c = c;
   }

   public void setBackGround(Image image) {
      BackgroundSize bs = new BackgroundSize(80, 60, true, true, true, false);
      this.setBackground(new Background(
            new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                  BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bs)));
   }

}