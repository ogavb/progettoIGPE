package gui.editor;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Optional;

import gui.panels.SchermataNuovaPartita;
import gui.panels.SchermataTavolaDiGioco;
import gui.panels.StartGame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdbc.ScrittoreConfigurazione;

public class EditorTavolaDiGioco extends TavolaGridPane{

	private Stage stage;

	private BorderPane mainPane;
	private VBox paletteCaselle;

	private Button salva;
	private Button annulla;


	public EditorTavolaDiGioco() throws SQLException {
		super(11,11);


	    salva   = new Button("SALVA");
	    annulla = new Button("ANNULLA");
	    String style   = "-fx-background-color: linear-gradient(rgb(22, 179, 184) 5%, rgb(189, 51, 42) 100%) rgb(22, 179, 184);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;-fx-font-size: 11;";
	    String styleOn = "-fx-background-color: linear-gradient(rgb(30,140,150) 10%, rgb(189, 51, 42) 100%) rgb(22, 179, 184);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;-fx-font-size: 13;";

	    salva.setStyle(style);
	    annulla.setStyle(style);

	    salva.setPrefSize(80, 40);
	    annulla.setPrefSize(80,40);

	    salva.setDisable(true);
	    annulla.setDisable(true);

	    salva.setOnMouseEntered(event -> {
	    	salva.setStyle(styleOn);
	    });
	    salva.setOnMouseExited(event -> {
	    	salva.setStyle(style);
	    });

	    annulla.setOnMouseEntered(event -> {
	    	annulla.setStyle(styleOn);
	    });
	    annulla.setOnMouseExited(event -> {
	    	annulla.setStyle(style);
	    });

	    salva.setOnMouseReleased(event -> {

	    	TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("Salva Configurazione");
			dialog.setContentText("Nome della\n configurazione creata:");

			StringBuffer sb = new StringBuffer();
			String nomeConfigurazione = null;

	        Optional<String> result = dialog.showAndWait();
		    if (result.isPresent()) {
				nomeConfigurazione = result.get();

				Iterator<MyPaneTavola> it = lista.iterator();
		    	while(it.hasNext()){
		    		sb.append(it.next().getNomeCasella() +" ");
		    	}

				ScrittoreConfigurazione sc = new ScrittoreConfigurazione(sb.toString());
		    	sc.setNomeConfigurazione(nomeConfigurazione);
		    	sc.query();
			}
		});


		this.mainPane = new BorderPane();
		this.paletteCaselle = new VBox();

		mainPane.setPrefWidth(1200);
		mainPane.setPrefHeight(720);

		mainPane.setStyle("-fx-background-color: A2382B;");
		setStyle("-fx-background-color: DAE6F3;");


		mainPane.setTop(new ImageView(new Image("file:icone/editor.png")));
		BorderPane.setAlignment(mainPane.getTop(), Pos.CENTER);

		ImageView indietro = new ImageView(new Image("file:icone/tornaAlmenu.png"));
		indietro.setTranslateX(20);
		indietro.setTranslateY(6);

		mainPane.getChildren().add(indietro);
		mainPane.setCenter(this);

		paletteCaselle.getChildren().add(new ImageView(new Image("file:icone/scegli.png")));
		paletteCaselle.getChildren().add(addFlowPane());
		mainPane.setRight(paletteCaselle);

		Scene scene = new Scene(mainPane);
		stage = new Stage();
		stage.setScene(scene);
		stage.show();

	}

	private boolean controllaAlmenoQuattroEsami() {
		Iterator<MyPaneTavola> it = lista.iterator();

		int count = 0;

		while(it.hasNext()){
			MyPaneTavola mt = it.next();

			if(mt.getNomeCasella().equals("Esame")){
				count++;
			}
		}

		return count >= 4;

	}
	private FlowPane addFlowPane() {
	    FlowPane flow = new FlowPane();
	    flow.setTranslateY(10);
	    flow.setPadding(new Insets(5, 0, 5, 0));
	    flow.setVgap(4);
	    flow.setHgap(4);
	    flow.setPrefWrapLength(170); // preferred width allows for two columns

	    String immagineMensa = "file:caselle/mensa.jpg";
	    String immagineCus = "file:caselle/cus.jpg";
	    String immagineEsame = "file:caselle/esame.png";
	    String immagineBiblioteca = "file:caselle/biblioteca.png";
	    String immagineBookCafe = "file:caselle/mensa.png";
	    String immagineSemplice = "file:caselle/strada.png";
	    String immagineCentroR = "file:caselle/centro.jpg";
	    String immagineRicevimento = "file:caselle/centro.jpg";

	    MyPanePalette pages[] = new MyPanePalette[8];
	    pages[0] = new MyPanePalette("Biblioteca", new Image(immagineBiblioteca));
	    pages[1] = new MyPanePalette("BookCafe", new Image(immagineSemplice));
	    pages[2] = new MyPanePalette("CentroResidenziale", new Image(immagineCentroR));
	    pages[3] = new MyPanePalette("Cus", new Image(immagineCus));
	    pages[4] = new MyPanePalette("Esame", new Image(immagineEsame));
	    pages[5] = new MyPanePalette("Mensa", new Image(immagineMensa));
	    pages[6] = new MyPanePalette("Ricevimento", new Image(immagineRicevimento));
	    pages[7] = new MyPanePalette("Semplice", new Image(immagineSemplice));

	    for (int i=0; i<8; i++) {
	        flow.getChildren().add(pages[i]);

	        MyPanePalette mpe = pages[i];
	        mpe.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					nomeCasellaSelezionata = mpe.getNomeCasella();
					immagineCasellaSelezionata = mpe.getImmagineCasella();
				}
			});
	    }
	    flow.getChildren().add(salva);
        flow.getChildren().add(annulla);

	    return flow;
	}

	@Override
	protected void setBottoni(boolean b) {
		salva.setDisable(b);
		annulla.setDisable(b);
	}


}
