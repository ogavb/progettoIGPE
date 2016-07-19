package gui.editor;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import gui.panels.SceltaEditor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdbc.ScrittoreConfigurazione;

public class EditorTavolaDiGioco extends TavolaGridPane {

   private Stage stage;
   private Scene scene;

   private BorderPane mainPane;
   private Pane centro;
   private VBox paletteCaselle;

   private Button salva;
   private Button indietro;

   private boolean schermoIntero=false;

   public EditorTavolaDiGioco(Stage stage) throws SQLException {
      super(11, 11);

      this.stage = stage;
      this.centro = new Pane();

      this.centro.getChildren().add(new ImageView("file:icone/ilLaureato.png"));
      this.centro.setTranslateX(146); //233 121
      this.centro.setTranslateY(118);

//       addListener(new ChangeListener<Number>() {
//         @Override
//         public void changed(ObservableValue<? extends Number> observableValue,
//               Number oldSceneWidth, Number newSceneWidth) {
//               System.err.println("entrato nel listener");
//               if(schermoIntero) {
//                  centro.setTranslateX(233);
//                  centro.setTranslateY(121);
//                  schermoIntero = false;
//               }
//               else {
//                  centro.setTranslateX(146);
//                  centro.setTranslateY(118);
//                  schermoIntero = true;
//               }
//         }});

      this.setOnMouseClicked(e -> {
         System.err.println("X: "+e.getSceneX());
         System.err.println("Y: "+e.getSceneY());
      });

      salva = new Button("Salva");
      indietro = new Button("Menu");

      salva.setId("btn_etg");
      indietro.setId("btn_etg");

      // all'inizio disabilito il salvataggio
      salva.setDisable(true);

      // EVENTI BOTTONI
      salva.setOnMouseReleased(event -> {

         TextInputDialog dialog = new TextInputDialog("");
         dialog.setTitle("Salva Configurazione");
         dialog.setContentText("Nome della\n configurazione creata:");

         StringBuffer sb = new StringBuffer();
         String nomeConfigurazione = null;

         Optional<String> result = dialog.showAndWait();
         if (result.isPresent()) {
            nomeConfigurazione = result.get();

            Iterator<Entry<Integer, MyPaneTavola>> it = lista.entrySet().iterator();


            while (it.hasNext()) {
              Map.Entry<Integer,MyPaneTavola> entry = (Map.Entry<Integer, MyPaneTavola>)it.next();

              String nomeCasella = entry.getValue().getNomeCasella();
              sb.append(nomeCasella + " ");
              System.err.println("CONFIGURAZIONE: " + nomeCasella + ",");
           }

            ScrittoreConfigurazione sc = new ScrittoreConfigurazione(
                  sb.toString());
            sc.setNomeConfigurazione(nomeConfigurazione);
            sc.query();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Operazione Riuscita!");
            alert.setHeaderText(
                  "Salvataggio tavola di gioco\navvenuto con successo!");
            alert.showAndWait();
         }
      });

      indietro.setOnMouseReleased(e -> {
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

      paletteCaselle.getChildren()
            .add(new ImageView(new Image("file:icone/scegli.png")));
      paletteCaselle.getChildren().add(addFlowPane());
      mainPane.setRight(paletteCaselle);

      scene = new Scene(mainPane);
      scene.getStylesheets().add("css/mainCss.css");

      mainPane.getChildren().add(centro);
      this.stage.setScene(scene);
      this.stage.centerOnScreen();
      this.stage.setResizable(true);
      this.stage.show();

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
      String immagineBookCafe = "file:caselle/bookcafe.png";
      String immagineSemplice = "file:caselle/strada.png";
      String immagineCentroR = "file:caselle/centro.jpg";
      String immagineRicevimento = "file:caselle/ricevimentop.png";

      MyPanePalette pages[] = new MyPanePalette[8];
      pages[0] = new MyPanePalette("Biblioteca", new Image(immagineBiblioteca));
      pages[1] = new MyPanePalette("BookCafe", new Image(immagineBookCafe));
      pages[2] = new MyPanePalette("CentroResidenziale",
            new Image(immagineCentroR));
      pages[3] = new MyPanePalette("Cus", new Image(immagineCus));
      pages[4] = new MyPanePalette("Esame", new Image(immagineEsame));
      pages[5] = new MyPanePalette("Mensa", new Image(immagineMensa));
      pages[6] = new MyPanePalette("Ricevimento",
            new Image(immagineRicevimento));
      pages[7] = new MyPanePalette("Semplice", new Image(immagineSemplice));

      for (int i = 0; i < 8; i++) {
         flow.getChildren().add(pages[i]);

         MyPanePalette mpe = pages[i];
         mpe.addEventHandler(MouseEvent.MOUSE_RELEASED,
               new EventHandler<Event>() {
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

   protected void setBottoni(boolean b) {
      salva.setDisable(b);
   }

}
