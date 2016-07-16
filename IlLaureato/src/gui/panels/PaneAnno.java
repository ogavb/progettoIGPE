package gui.panels;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PaneAnno extends Pane {

	private VBox boxElementi;
	private HBox boxFuochi;
	private TextArea frase;

	public PaneAnno() {
		this.setWidth (400.0);
		this.setHeight(400.0);
		this.getStylesheets().add("css/textArea.css");

		boxElementi = new VBox(20.0);
		boxFuochi   = new HBox(20.0);

		frase = new TextArea();
		frase.setText("Complimenti hai passato un altro anno\n SENZA LAUREARTI..AUGURI!!!");

		boxFuochi.getChildren().add(new ImageView(new Image("file:icone/fuochi.gif")));
		boxFuochi.getChildren().add(new ImageView(new Image("file:icone/fuochi3.gif")));

		boxElementi.getChildren().addAll(boxFuochi,frase);
		getChildren().add(boxElementi);
	}

}
