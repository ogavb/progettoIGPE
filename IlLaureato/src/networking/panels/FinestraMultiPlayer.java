package networking.panels;

import java.util.ArrayList;
import java.util.List;

import concurrent.LockManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MatchTableView;
import networking.Client;
import networking.GestoreMatch;
import networking.RequestManagerClient;
import util.TabellaMatchUtil;

public class FinestraMultiPlayer {

   private Stage primaryStage;
   private Pane mainPane;

   private VBox boxComponenti;
   private HBox boxBottoni;
   private Button partecipaPartita;
   private Button annulla;

   private TableView<MatchTableView> tableMatchs;
   private TableColumn<MatchTableView, Integer> idMatch;
   private TableColumn<MatchTableView, Integer> numeroGiocatori;
   private TableColumn<MatchTableView, Integer> numeroGiocatoriCorrenti;

   private Client client;
   private RequestManagerClient rmc;
   private LockManager lockManager;

   private String nomeGiocatore;

   public FinestraMultiPlayer(Stage stage, Client client, String nomeGiocatore,
         RequestManagerClient rmc) {

      primaryStage = stage;

      this.client = client;

      this.rmc = rmc;

      this.nomeGiocatore = nomeGiocatore;

      this.lockManager = new LockManager();

      SchermataNuovaPartitaMultiPlayer sm = new SchermataNuovaPartitaMultiPlayer(
            true, client, rmc);
      rmc.setSchermataNuovaPartitaMultiPlayer(sm);

   }

   @Override
   protected void finalize() throws Throwable {
      // TODO Auto-generated method stub
      System.out.print("L'ho distrutto");
      super.finalize();

   }

   public void close() {
      primaryStage.close();
      System.err.println("CHIAMATO METODO CLOSE SU FINESTRA MATCH");
   }

   public String getNomeGiocatore() {
      return this.nomeGiocatore;
   }

   public Parent show(Stage stage, String[] matchs) {

      System.err.println("CHIAMATO METODO SHOW");

      this.primaryStage = stage;

      boxComponenti = new VBox(30.0);
      boxBottoni = new HBox(2.0);

      mainPane = new Pane();
      mainPane.setPrefWidth(325);
      mainPane.setPrefHeight(500);

      mainPane.setStyle("-fx-background-color: A2382B;");

      partecipaPartita = new Button("PARTECIPA");
      annulla = new Button("ANNULLA");

      String style = "-fx-background-color: linear-gradient(rgb(22, 179, 184) 5%, rgb(189, 51, 42) 100%) rgb(22, 179, 184);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;-fx-font-size: 11;";
      String styleOn = "-fx-background-color: linear-gradient(rgb(30,140,150) 10%, rgb(189, 51, 42) 100%) rgb(22, 179, 184);-fx-background-radius: 30;-fx-background-insets: 0; -fx-text-fill: white;-fx-font-size: 13;";

      partecipaPartita.setStyle(style);

      partecipaPartita.setPrefSize(160, 40);

      annulla.setStyle(style);

      annulla.setPrefSize(160, 40);

      partecipaPartita.setOnMouseEntered(event -> {
         partecipaPartita.setStyle(styleOn);
      });
      partecipaPartita.setOnMouseExited(event -> {
         partecipaPartita.setStyle(style);
      });

      annulla.setOnMouseEntered(event -> {
         annulla.setStyle(styleOn);
      });
      annulla.setOnMouseExited(event -> {
         annulla.setStyle(style);
      });

      partecipaPartita.setOnMouseReleased(event -> {

         MatchTableView match = null;

         if (tableMatchs != null)
            match = tableMatchs.getSelectionModel().getSelectedItem();

         if (match == null) {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Selezionare una partita");
            alert.showAndWait();

         }
         else {

            int idMatch = match.getIdMatch();

            client.addRequest("1##" + idMatch);

            try {
               lockManager.attendiUnoQuattro();
            }
            catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }

            this.primaryStage = ((Stage) ((Button) event.getSource()).getScene()
                  .getWindow());
            rmc.getSchermataNuovaPartitaMultiPlayer().setIsClient(true);
            Parent root = rmc.getSchermataNuovaPartitaMultiPlayer().show(
                  this.primaryStage, match.getNumeroGiocatori(), nomeGiocatore);

            client.addRequest("3##" + nomeGiocatore);

            try {
               lockManager.attendiTre();
            }
            catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
         }
      });

      primaryStage.setOnCloseRequest(event -> {
         client.addRequest("#END#");
         stage.close();
      });

      annulla.setOnMouseReleased(event -> {

         client.addRequest("#END#");
         stage.close();

      });
      createTableView();

      tableMatchs.setOnMouseReleased(event -> {
         MatchTableView match = tableMatchs.getSelectionModel()
               .getSelectedItem();
         if (match != null
               && match.getGiocatoriCorrenti() == match.getNumeroGiocatori()) {
            System.err
                  .println("MATCH TABLE VIEW: " + match.getGiocatoriCorrenti()
                        + " " + match.getNumeroGiocatori());
            partecipaPartita.setDisable(true);
         }
         else if (match != null
               && match.getGiocatoriCorrenti() < match.getNumeroGiocatori())
            partecipaPartita.setDisable(false);
      });

      boxBottoni.getChildren().add(partecipaPartita);
      boxBottoni.getChildren().add(annulla);

      boxComponenti.getChildren().add(tableMatchs);
      boxComponenti.getChildren().add(boxBottoni);
      mainPane.getChildren().add(boxComponenti);

      List<MatchTableView> tmp = new ArrayList<>();
      String match[] = null;
      for (String s : matchs) {
         match = s.split(",");
         tmp.add(new MatchTableView(Integer.parseInt(match[0]),
               Integer.parseInt(match[1]), Integer.parseInt(match[2])));

      }

      tableMatchs.getItems().clear();
      tableMatchs.getItems().addAll(tmp);

      return mainPane;

   }

   public Client getClient() {
      return this.client;
   }

   public TableView<MatchTableView> getTableMatchs() {
      return this.tableMatchs;
   }

   @SuppressWarnings("unchecked")
   private void createTableView() {
      tableMatchs = new TableView<>(TabellaMatchUtil.getListaMatch());
      idMatch = TabellaMatchUtil.getColonnaIDMatch();
      numeroGiocatori = TabellaMatchUtil.getColonnaNumeroGiocatori();
      numeroGiocatoriCorrenti = TabellaMatchUtil.getColonnaGiocatoriCorrenti();

      tableMatchs.getColumns().addAll(idMatch, numeroGiocatori,
            numeroGiocatoriCorrenti);

   }

}
