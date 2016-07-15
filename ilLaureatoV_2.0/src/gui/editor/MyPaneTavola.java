package gui.editor;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;

public class MyPaneTavola extends FlowPane{

	private String nomeCasella;

	public MyPaneTavola(String nomeCasella,Image immagineCasella) {
		this.setNomeCasella(nomeCasella);
		this.setBackGround(immagineCasella);
	}

	public MyPaneTavola(){}

	public void setBackGround(Image image){
		BackgroundSize bs = new BackgroundSize(80, 60, true, true, true, false);
		this.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bs)));
	}



	public String getNomeCasella() {
		return nomeCasella;
	}

	public void setNomeCasella(String nomeCasella) {
		this.nomeCasella = nomeCasella;
	}

}
