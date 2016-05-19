package gui.panels;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceltaEditor {

	private Stage stage;
	private Scene scene;

	private Pane pane;
	private ImageView editorDomande;
	private ImageView editorTavola;
	private ImageView indietro;

	public SceltaEditor(Stage stage) {

		this.stage = stage;

		pane = new Pane();
		pane.setPrefSize(640,400);

		editorDomande = new ImageView(new Image("file:icone/editorDomande.png"));
		editorTavola = new ImageView(new Image("file:icone/editor.png"));
		indietro = new ImageView(new Image("file:icone/tornaAlmenu.png"));

		indietro.setTranslateX(20);
		indietro.setTranslateY(6);

		editorDomande.setTranslateX(10);
		editorDomande.setTranslateY(20);

		editorTavola.setTranslateX(30);
		editorTavola.setTranslateY(120);

		pane.getChildren().add(indietro);
		pane.getChildren().add(editorDomande);
		pane.getChildren().add(editorTavola);

		scene = new Scene(pane);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();

	}


}
