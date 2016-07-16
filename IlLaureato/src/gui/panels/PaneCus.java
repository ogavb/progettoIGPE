package gui.panels;


import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PaneCus extends Pane {

	private VBox vbox =new VBox(10.0);
	private HBox hbox =new HBox(10.0);
	private TextArea text = new TextArea();

	public PaneCus() {

		this.getStylesheets().add("css/textArea.css");

		this.setWidth(400.0);
		this.setHeight(400.0);

		text.setText("SEI AL CUS!Goditi il tuo pranzo e\nstai FERMO UN TURNO!");
		hbox.getChildren().add(new ImageView(new Image("file:icone/palestra1.gif")));
		hbox.getChildren().add(new ImageView(new Image("file:icone/palestra1.gif")));
	    vbox.getChildren().addAll(text,hbox);
	    getChildren().add(vbox);



	}

}
