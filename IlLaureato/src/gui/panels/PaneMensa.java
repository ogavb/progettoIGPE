package gui.panels;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PaneMensa extends Pane{

	private VBox box =new VBox(10.0);
	private HBox hbox =new HBox(10.0);
	private TextArea text = new TextArea();


	public PaneMensa() {
		//modificare css per renderlo piu carino
		this.getStylesheets().add("css/textArea.css");

		this.setWidth(400.0);
		this.setHeight(400.0);

		Image image1 = new Image("file:icone/mensaSpaghetti.gif");
		Image image2 = new Image("file:icone/mensaPollo.gif");
		ImageView v = new ImageView(image2);
		//translo per avere un allineamento grafico nelle immagini
		v.setTranslateY(30.0);
		text.setText("SEI A MENSA!\nGoditi il tuo pranzo e\nstai FERMO UN TURNO!");
		hbox.getChildren().addAll(new ImageView(image1),v);
	    box.getChildren().addAll(text,hbox);
	    getChildren().add(box);
	}
	//
}
