package gui.panels;



import core.AzioneDomanda;
import core.Giocatore;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PaneDomande  extends Pane{

	private VBox boxElementi;
	private HBox boxRisposte;
	private HBox boxDomanda;

	private Label risposta1;
	private Label risposta2;
	private Label domanda;

	private ProgressIndicator timer;
	private Timeline animazione;

	private Giocatore giocatore;
	private AzioneDomanda azione;

	public PaneDomande() {

		setTranslateZ(-1);
		this.setStyle("-fx-background-color : #0076a3;");
		this.getStylesheets().add(this.getClass().getResource("/css/mainCss.css").toExternalForm());

		timer = new ProgressIndicator(1);

		boxElementi = new VBox();
		boxElementi.setPrefSize(500, 300);
		boxElementi.setPadding(new Insets(10, 10,10, 10));
		boxElementi.setSpacing(20);

		boxDomanda = new HBox();
		boxDomanda.prefWidth(500);
		boxDomanda.minWidth(500);
		boxDomanda.maxWidth(500);
		boxDomanda.setPadding(new Insets(10, 10, 10, 10));
		boxDomanda.setSpacing(20);

		boxDomanda.getStyleClass().add("testoLabel");
		boxDomanda.setAlignment(Pos.CENTER);

		domanda = new Label();
		domanda.setWrapText(true);
		domanda.getStyleClass().add("testo");
		domanda.prefWidthProperty().bind(boxDomanda.widthProperty());

		boxRisposte = new HBox();
		boxRisposte.setPadding(new Insets(10, 10, 10, 10));
		boxRisposte.prefWidth(500);
		boxRisposte.minWidth(500);
		boxRisposte.maxWidth(500);
		boxRisposte.setSpacing(20);

		risposta1 = new Label();
		risposta1.setWrapText(true);
		risposta1.getStyleClass().add("testoLabel");
		risposta1.prefWidthProperty().bind(boxRisposte.widthProperty());

		risposta2 = new Label();
		risposta2.setWrapText(true);
		risposta2.getStyleClass().add("testoLabel");
		risposta2.prefWidthProperty().bind(boxRisposte.widthProperty());

		boxDomanda.getChildren().addAll(domanda, timer);
		boxRisposte.getChildren().addAll(risposta1,risposta2);
		boxElementi.getChildren().addAll(boxDomanda,boxRisposte);

		this.getChildren().add(boxElementi);
		this.setPrefWidth(500);

		KeyValue initKeyValue = new KeyValue(timer.progressProperty(), 1);
		KeyValue endKeyValue = new KeyValue(timer.progressProperty(), 0);

		KeyFrame initFrame = new KeyFrame(Duration.ZERO,initKeyValue);
		KeyFrame endFrame = new KeyFrame(Duration.millis(30000),endKeyValue);

	    animazione = new Timeline(initFrame,endFrame);

		animazione.setOnFinished(event -> {
			azione.controllaEsitoEsame("", giocatore);
		});

		risposta1.setOnMouseReleased(event -> {
			azione.controllaEsitoEsame(risposta1.getText(), giocatore);
			animazione.stop();

		});

		risposta2.setOnMouseReleased(event -> {
			azione.controllaEsitoEsame(risposta2.getText(), giocatore);
			animazione.stop();
		});

	}

	public void avviaAnimazione() {
		animazione.playFromStart();
	}

	public void setTestiLabel(AzioneDomanda a, Giocatore g) {
		azione = a;
		giocatore = g;
		domanda.setText(a.getDomanda());
		risposta1.setText(a.getRisposte().getRisposte()[0]);
		risposta2.setText(a.getRisposte().getRisposte()[1]);
	}

}
