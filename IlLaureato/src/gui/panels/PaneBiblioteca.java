package gui.panels;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PaneBiblioteca extends Pane {

	public PaneBiblioteca() {

		Image image = new Image("file:icone/biblioteca.jpg");
		getChildren().add(new ImageView(image));
        setMaxSize(720, 540);

	}

}
