package gui.panels;


import core.AzioneDomanda;
import core.GameManager;
import core.Giocatore;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PaneDomande  extends Pane{

	private final int NUMCREDITIPERLAUREARSI = 5;

	private VBox boxElementi;
	private HBox boxRisposte;
	private HBox boxDomanda;
	private HBox boxRisultato;

	private Label risposta1;
	private Label risposta2;

	private Label risultato;
	private Label domanda;

	private ProgressIndicator timer;
	private Timeline animazione;

	private Giocatore giocatore;
	private AzioneDomanda azione;
	private GameManager gm;

	private final double width = 500.0;
	private final double height = 300.0;


	private String frase1 = "Hai dato la risposta giusta!\nCrediti aggiornati!!!\nOra sei piu vicino alla laurea :(";
	private String frase2 = "Perfetto hai dato la risposta sbagliata!\nContinua Cosi!";
	public PaneDomande(GameManager gm) {

		this.gm = gm;
		this.setWidth(width);
		this.setHeight(height);

		setTranslateZ(-1);
		this.setStyle("-fx-background-color : #0076a3;");
		this.getStylesheets().add(this.getClass().getResource("/css/domandeCss.css").toExternalForm());

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


		boxRisultato = new HBox(20.0);
		risultato = new Label("");
		risultato.getStyleClass().add("risultato");
		boxRisultato.getChildren().addAll(risultato);


		boxDomanda.getChildren().addAll(domanda, timer);
		boxRisposte.getChildren().addAll(risposta1,risposta2);
		boxElementi.getChildren().addAll(boxDomanda,boxRisposte,risultato);

		this.getChildren().add(boxElementi);
		this.setPrefWidth(500);

		KeyValue initKeyValue = new KeyValue(timer.progressProperty(), 1);
		KeyValue endKeyValue = new KeyValue(timer.progressProperty(), 0);

		KeyFrame initFrame = new KeyFrame(Duration.ZERO,initKeyValue);
		KeyFrame endFrame = new KeyFrame(Duration.millis(30000),endKeyValue);

	    animazione = new Timeline(initFrame,endFrame);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("LAUREATO!!!!");
		alert.setContentText("Ti Sei laureato!!\nQuindi verrai escluso dall'università!!\nVERGOGNA!");

	    animazione.setOnFinished(event -> {
			azione.controllaEsitoEsame("", giocatore);
		});

		//se rispondo la prima label
		risposta1.setOnMouseReleased(event -> {
			if (azione.controllaEsitoEsame(risposta1.getText(), giocatore)) {
		       risposta1.setStyle("-fx-background-color: green;");
		       risposta2.setStyle("-fx-background-color: red;");
		       risultato.setText(frase1);
			}
			else {
				 risposta1.setStyle("-fx-background-color: red;");
			     risposta2.setStyle("-fx-background-color: green;");
			     risultato.setText(frase2);
			}
			animazione.stop();
			risposta1.setDisable(true);
			risposta2.setDisable(true);

			if ( controllaSeLaureato(giocatore) ) {
	   		      alert.showAndWait();
		       }


		});
		//se rispondo la seconda label
		risposta2.setOnMouseReleased(event -> {
			if (azione.controllaEsitoEsame(risposta2.getText(), giocatore)) {
			   risposta2.setStyle("-fx-background-color: green;");
		       risposta1.setStyle("-fx-background-color: red;");
		       risultato.setText(frase1);


			}
			else {
				 risposta2.setStyle("-fx-background-color: red;");
			     risposta1.setStyle("-fx-background-color: green;");
			     risultato.setText(frase2);
			}
			animazione.stop();
			risposta1.setDisable(true);
			risposta2.setDisable(true);

			 if ( controllaSeLaureato(giocatore) ) {
  			      alert.showAndWait();
		       }

		});
	}


	public void resetta() {
		risposta1.setDisable(false);
		risposta2.setDisable(false);
		risultato.setText("");
		risposta2.setStyle("-fx-background-color: #0076a3;");
	    risposta1.setStyle("-fx-background-color: #0076a3;");
	}


	public void avviaAnimazione() {
		animazione.playFromStart();
	}

	private boolean controllaSeLaureato(Giocatore g ) {
		if ( g.getCrediti() >= NUMCREDITIPERLAUREARSI) {
			gm.getGestore().rimuovi(g);
			return true;
		}
		return false;

	}
	public void setTestiLabel(AzioneDomanda a, Giocatore g) {
		azione = a;
		giocatore = g;
		domanda.setText(a.getDomanda());
		risposta1.setText(a.getRisposte().getRisposte()[0]);
		risposta2.setText(a.getRisposte().getRisposte()[1]);
	}

}
