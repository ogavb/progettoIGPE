package gui.editor;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyPanePalette extends ImageView{

	private String nomeCasella;
	private Image immagineCasella;

	public MyPanePalette(){}

	public MyPanePalette(String nomeCasella, Image immagineCasella) {
		super(immagineCasella);
		this.nomeCasella = nomeCasella;
		this.immagineCasella = immagineCasella;
	}

	public String getNomeCasella() {
		return nomeCasella;
	}

	public void setNomeCasella(String nomeCasella) {
		this.nomeCasella = nomeCasella;
	}

	public Image getImmagineCasella() {
		return immagineCasella;
	}

	public void setImmagineCasella(Image immagineCasella) {
		this.immagineCasella = immagineCasella;
	}

}
