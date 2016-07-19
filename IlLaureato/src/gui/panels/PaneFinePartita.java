package gui.panels;

import core.Giocatore;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PaneFinePartita extends Pane {

   private Giocatore vincitore;
   private Pane pane = new Pane();
   private VBox boxLabel = new VBox(10.0);
   private Label nome, crediti, anni;

   public PaneFinePartita(Giocatore g) {

      this.getStylesheets().add("css/finePartita.css");
      this.setWidth(640.0);
      this.setHeight(480.0);

      vincitore = g;
      pane.setMinSize(640, 480);
      pane.setPrefSize(640, 480);
      pane.setMaxSize(640, 480);
      pane.setId("pannello");
      this.nome = new Label("Vincitore : " + g.getNome());
      this.crediti = new Label("Crediti  : " + g.getCrediti());
      this.anni = new Label("Anni Accademici :" + g.getAnniAccademici());
      boxLabel.getChildren().addAll(nome, crediti, anni);
      pane.getChildren().add(boxLabel);
      getChildren().add(pane);
   }

}
