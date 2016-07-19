package gui.panels;

import java.io.File;
import java.sql.SQLException;

import controller.MainController;
import core.GameManager;
import core.Giocatore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jdbc.CreatoreTavolaDiGioco;

// SCHERMATA IN CUI SI SCELGONO NOMI GIOCATORI QUANTI CHE TAVOLA ECC
public class SchermataNuovaPartita {

   private Stage stage;
   private Scene scene;
   private BorderPane mainPane;

   private GridPane paneCenter;
   private VBox boxTop;
   private HBox paneBottom;

   private Button conferma;
   private Button tornaAlMenu;
   private ComboBox<String> configurazioni;
   private String[] ColorPlayer = { "blue", "cyan", "green", "purple", "red",
         "yellow" };

   private GameManager gm;
// variabili per la musica
// private String path;
// private Media media;
// private MediaPlayer mediaPlayer;
// private MediaView mediaView;

   public SchermataNuovaPartita(Stage stage) {

      this.stage = stage;
      stage.setTitle("Nuova Partita");

// path = "C:/Users/Cosimo/Desktop/MUSICA/ragazzi.mp3";
// media = new Media(new File(path).toURI().toString());
// mediaPlayer = new MediaPlayer(media);
// mediaPlayer.setAutoPlay(true);
// mediaView = new MediaView(mediaPlayer);

      mainPane = new BorderPane();
      mainPane.setPrefWidth(600);
      mainPane.setPrefHeight(500);
      mainPane.setMinWidth(BorderPane.USE_PREF_SIZE);
      mainPane.setMinHeight(BorderPane.USE_PREF_SIZE);
      mainPane.setMaxWidth(BorderPane.USE_PREF_SIZE);
      mainPane.setMaxHeight(BorderPane.USE_PREF_SIZE);

      // stile per avere i bordi forse lo togliamo
      mainPane.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
            + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
            + "-fx-border-radius: 5;" + "-fx-border-color: rgb(0,128,192);");

      drawSelectPlayer();

      mainPane.setTop(boxTop);
      mainPane.setCenter(paneCenter);
      mainPane.setBottom(paneBottom);

      scene = new Scene(mainPane);
      scene.getStylesheets().add("css/nuovaPartita.css");
      this.stage.setScene(scene);
      this.stage.centerOnScreen();
      this.stage.show();

   }

   private void drawSelectPlayer() {

      final int nGioc = 6;
      Label labelNumero = new Label("Numero giocatori: ");
      labelNumero.setFont(new Font(16));
      ObservableList<Integer> items = FXCollections.observableArrayList();
      items.add(2);
      items.add(3);
      items.add(4);
      items.add(5);
      items.add(6);
      ComboBox<Integer> nGiocatori = new ComboBox<>(items);
      nGiocatori.setValue(items.get(0));

      nGiocatori.setPrefSize(50, 20);

      boxTop = new VBox(10.0);
      HBox hBoxTop = new HBox(260.0);
      hBoxTop.getChildren().addAll(labelNumero, nGiocatori);

      boxTop.getChildren().addAll(hBoxTop,
            new Separator(Orientation.HORIZONTAL));

      final Label[] lblNomeGiocatore = new Label[nGioc];
      final TextField[] txtNomeGiocatore = new TextField[nGioc];

      paneCenter = new GridPane();
      paneCenter.setPrefSize(20, 10);
      paneCenter.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

      ColumnConstraints column1 = new ColumnConstraints();
      column1.setPercentWidth(50);
      ColumnConstraints column2 = new ColumnConstraints();
      column2.setPercentWidth(50);
      paneCenter.getColumnConstraints().addAll(column1, column2);

      for (int j = 0; j < nGioc; j++) {
         lblNomeGiocatore[j] = new Label("Giocatore " + (j + 1), new ImageView(
               new Image("file:giocatori/" + ColorPlayer[j] + ".gif")));
         txtNomeGiocatore[j] = new TextField();
         txtNomeGiocatore[j].setText("nome" + j);
         GridPane.setConstraints(lblNomeGiocatore[j], 0, j);
         GridPane.setConstraints(txtNomeGiocatore[j], 1, j);
         paneCenter.setVgap(25);
         paneCenter.getChildren().addAll(lblNomeGiocatore[j],
               txtNomeGiocatore[j]);
      }

      for (int k = 2; k < nGioc; k++) {
         lblNomeGiocatore[k].setDisable(true);
         txtNomeGiocatore[k].setDisable(true);
      }

      nGiocatori.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
            for (int k = 0; k < nGioc; k++) {
               if (k < nGiocatori.getValue()) {
                  lblNomeGiocatore[k].setDisable(false);
                  txtNomeGiocatore[k].setDisable(false);
               }
               else {
                  lblNomeGiocatore[k].setDisable(true);
                  txtNomeGiocatore[k].setDisable(true);
               }
            }
         }
      });

      tornaAlMenu = new Button("Torna al Menu");
      conferma = new Button("Conferma");
      paneBottom = new HBox(10.0);
      paneBottom.setAlignment(Pos.BOTTOM_RIGHT);
      ObservableList<String> listaConfigurazioni;
      try {
         listaConfigurazioni = new CreatoreTavolaDiGioco()
               .prelevaNomiConfigurazioni();
         configurazioni = new ComboBox<>(listaConfigurazioni);
         configurazioni.setValue(listaConfigurazioni.get(0));
      }
      catch (SQLException e1) {
         e1.printStackTrace();
      }

      paneBottom.getChildren().add(configurazioni);
      paneBottom.getChildren().add(conferma);
      paneBottom.getChildren().add(tornaAlMenu);

      // AZIONI DEI BOTTONI
      conferma.setOnAction(new EventHandler<ActionEvent>() {

         @Override
         public void handle(ActionEvent event) {

            boolean errorFlag, errorDuplicate;
            Giocatore[] giocatori;
            giocatori = new Giocatore[nGiocatori.getValue()];
            errorFlag = false;
            errorDuplicate = false;

            for (int k = 0; k < nGiocatori.getValue(); k++) {
               // il metodo trim() di String ritorna una stringa rimuovendo
               // gli spazi ( se presenti ), all'inizio e alla fine della
               // stringa
               if (txtNomeGiocatore[k].getText().trim().equalsIgnoreCase(""))
                  errorFlag = true;
               giocatori[k] = new Giocatore(txtNomeGiocatore[k].getText());
               giocatori[k].setColor(k);

               // controllo duplicati
               for (int j = (k + 1); j < nGiocatori.getValue(); j++)
                  if (txtNomeGiocatore[k].getText().toLowerCase()
                        .equals(txtNomeGiocatore[j].getText().toLowerCase()))
                     errorDuplicate = true;
            }

            if (errorFlag == true) {

               Alert alert = new Alert(AlertType.ERROR);
               alert.setTitle("Errore");
               alert.setContentText("Attenzione: inserire tutti i giocatori!");

               alert.showAndWait();

            }
            // uso flag duplicate
            else if (errorDuplicate == true) {

               Alert alert = new Alert(AlertType.ERROR);
               alert.setTitle("Errore");
               alert.setContentText(
                     "Attenzione: I giocatori devono avere nomi differenti!");

               alert.showAndWait();

            }
            else {
               try {

                  initGameManager(giocatori, giocatori.length);
               }
               catch (SQLException e) {
                  e.printStackTrace();
               }

               gm.decidiOrdine();

               try {
                  ((Node) event.getSource()).getScene().getWindow().hide();
                  new SchermataTavolaDiGioco(gm);
               }
               catch (Exception e) {
                  e.printStackTrace();
               }

               gm.start();

            }
         }

      });

      tornaAlMenu.setOnMouseReleased(e -> {
         this.stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
         try {
            new MainController(stage);
         }
         catch (Exception e1) {
         }
      });

   }

   private void initGameManager(Giocatore[] nomiGiocatori, int numeroGiocatori)
         throws SQLException {

      this.gm = new GameManager();
      String nomeConfigurazione = (String) configurazioni.getSelectionModel()
            .getSelectedItem();
      gm.init(nomiGiocatori, numeroGiocatori, nomeConfigurazione);

   }

}
