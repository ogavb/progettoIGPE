package gui.editor;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Optional;

import gui.panels.SceltaEditor;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
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
	private Scene scene;

	private BorderPane mainPane;
	private VBox paletteCaselle;

	//il salva e annulla sono
	private Button salva;
	private Button annulla;
	private Button indietro;


	public EditorTavolaDiGioco(Stage stage) throws SQLException {
		super(11,11);

		this.stage = stage;

	    salva   = new Button("Salva");
	    annulla = new Button("Annulla");
	    indietro = new Button("Menu");

	    salva.setId("btn_etg");
	    annulla.setId("btn_etg");
	    indietro.setId("btn_etg");

	    //all'inizio li disabilito
	    salva.setDisable(true);
	    annulla.setDisable(true);

	    //EVENTI BOTTONI
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

	    indietro.setOnMouseReleased(e-> {
	    	this.stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
	    	new SceltaEditor(stage);
	    });
	    // FINE EVENTI BOTTONI

		this.mainPane = new BorderPane();
		this.paletteCaselle = new VBox();

		mainPane.setPrefWidth(1200);
		mainPane.setPrefHeight(720);

		setStyle("-fx-background-color: DAE6F3;");

		mainPane.setTop(new ImageView(new Image("file:icone/editor.png")));
		BorderPane.setAlignment(mainPane.getTop(), Pos.CENTER);

		mainPane.setCenter(this);

		paletteCaselle.getChildren().add(new ImageView(new Image("file:icone/scegli.png")));
		paletteCaselle.getChildren().add(addFlowPane());
		mainPane.setRight(paletteCaselle);

	    scene = new Scene(mainPane);
	    scene.getStylesheets().add("css/mainCss.css");

		this.stage.setScene(scene);
	    this.stage.centerOnScreen();
		this.stage.setResizable(true);
		this.stage.show();

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
        flow.getChildren().add(indietro);

	    return flow;
	}

	@Override
	protected void setBottoni(boolean b) {
		salva.setDisable(b);
		annulla.setDisable(b);
	}


}
