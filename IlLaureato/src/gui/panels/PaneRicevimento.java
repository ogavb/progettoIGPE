package gui.panels;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PaneRicevimento extends Pane{

	private VBox box =new VBox(20.0);
	private TextArea text = new TextArea();


	public PaneRicevimento() {

		this.getStylesheets().add("css/textArea.css");
		this.setWidth(400.0);
		this.setHeight(470.0);

		text.setText("SEI A RICEVIMENTO!\nil prof: hai ancora molto da imparare..\nmi dispiace..stai fermo due turni!");
		text.setWrapText(true);

		Image image = new Image("file:icone/ricevimento.gif");
	    box.getChildren().addAll(text, new ImageView(image));
	    getChildren().add(box);
	}

	public static void main(String[] args) {
	}
}
