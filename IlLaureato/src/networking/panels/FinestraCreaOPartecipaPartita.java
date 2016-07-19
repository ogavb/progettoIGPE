package networking.panels;

import java.util.Random;

import concurrent.LockManager;
import controller.MainController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import networking.Client;
import networking.MultiThreadedServer;
import networking.RequestManagerClient;

public class FinestraCreaOPartecipaPartita {

   private Stage primaryStage;
   private Scene scene;
   private Pane mainPane;

   private VBox boxButton;
   private Button creaPartita;
   private Button partecipaPartita;
   private Button TornaAlMenu;

   private Client client;
   private MultiThreadedServer server;
   private RequestManagerClient rmc;
   private LockManager lockManager;

   private String nomeGiocatore;

   public FinestraCreaOPartecipaPartita(Stage stage) {

      lockManager = new LockManager();
      nomeGiocatore = nomeRandom((int) ((Math.random() * 10) + 1));

      primaryStage = stage;
      mainPane = new Pane();
      mainPane.setPrefWidth(300);
      mainPane.setPrefHeight(150);

      boxButton = new VBox(10.0);
      creaPartita      = new Button("Crea Partita");
      partecipaPartita = new Button("Partecipa");
      TornaAlMenu      = new Button("Torna al menu");

      boxButton.getChildren().addAll(creaPartita,partecipaPartita,TornaAlMenu);
      mainPane.getChildren().add(boxButton);

      boxButton.setTranslateX(75);
      boxButton.setTranslateY(10);

      scene = new Scene(mainPane);
      scene.getStylesheets().add("css/mainCss.css");
      primaryStage.setScene(scene);
      primaryStage.show();

      creaPartita.setOnMouseReleased(event -> {

         primaryStage.hide();
         int giocatoriNum = 0;
         String numeroGiocatori = null;

         SchermataNuovaPartitaMultiPlayer sm = null;

         FinestraCreaPartita fm = new FinestraCreaPartita();
         fm.showAndWait();

         if (fm.getIpServer() == null)
            primaryStage.show();

         if (fm.getIpServer() != null) {

            if (fm.getIpServer().equals("")) {

               giocatoriNum = fm.getNumGiocatori();

               numeroGiocatori = "0##" + String.valueOf(giocatoriNum);

               server = new MultiThreadedServer(4444);
               new Thread(server).start();

               try {
                  client = new Client("127.0.0.1", 4444);
               }
               catch (Exception e) {
                  e.printStackTrace();
               }

               rmc = new RequestManagerClient(client);
               sm = new SchermataNuovaPartitaMultiPlayer(false, client, rmc);
               rmc.setSchermataNuovaPartitaMultiPlayer(sm);

               try {
                  sm.setServer(server);
               }
               catch (Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }

            }
            else {
               giocatoriNum = fm.getNumGiocatori();

               numeroGiocatori = "0##" + String.valueOf(giocatoriNum);

               try {
                  client = new Client(fm.getIpServer(), 4444);
               }
               catch (Exception e) {
                  Alert alert = new Alert(AlertType.ERROR);
                  alert.setHeaderText("Nessun server trovato");
                  alert.showAndWait();
                  return;
               }

               rmc = new RequestManagerClient(client);
               sm = new SchermataNuovaPartitaMultiPlayer(true, client, rmc);
               rmc.setSchermataNuovaPartitaMultiPlayer(sm);

            }

            client.addRequest(numeroGiocatori);

            try {
               lockManager.attendiZero();
            }
            catch (Exception e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }

            Parent root = sm.show(primaryStage, giocatoriNum, nomeGiocatore);

            client.setNomeGiocatore(nomeGiocatore);
            client.addRequest("3##" + nomeGiocatore);
            client.addRequest("10##" + nomeGiocatore);

            try {
               lockManager.attendiTre();
            }
            catch (Exception e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }

            client.addRequest("8##" + "notifica aggiunta match");

            try {
               lockManager.attendiOtto();
            }
            catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

         }

      });

      partecipaPartita.setOnMouseReleased(event -> {

         boolean ok = false;

         do {
            primaryStage.hide();
            FinestraPartecipaPartita fpp = new FinestraPartecipaPartita();

            fpp.showAndWait();

            if (fpp.getIpServer() == null)
               primaryStage.show();

            if (!fpp.getIpServer().equals("")) {
               try {
                  client = new Client(fpp.getIpServer(), 4444);
                  rmc = new RequestManagerClient(client);
                  client.addRequest("4##mostraPartite");
                  ok = true;
               }
               catch (Exception e) {
                  Alert alert = new Alert(AlertType.ERROR);
                  alert.setHeaderText("Nessun server trovato");
                  alert.showAndWait();
                  return;
               }
            }
            else {
               Alert alert = new Alert(AlertType.INFORMATION);
               alert.setHeaderText("Inserisci IP del server");
               alert.showAndWait();
            }

         } while (!ok);

         try {
            lockManager.attendiQuattro();
         }
         catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }

         client.setNomeGiocatore(nomeGiocatore);
         client.addRequest("10##" + nomeGiocatore);

         FinestraMultiPlayer fm = new FinestraMultiPlayer(primaryStage, client,
               nomeGiocatore, rmc);
         rmc.setFinsetraMultiplayer(fm);
         Parent root = fm.show(primaryStage, rmc.getMatchs());
         Scene scene = new Scene(root);
         primaryStage.setScene(scene);
         primaryStage.show();
      });

      TornaAlMenu.setOnMouseReleased(e-> {
            this.primaryStage= (Stage) ((Button) e.getSource()).getScene().getWindow();
            try {
               new MainController(stage);
            }
            catch (Exception e1) {
            }
         });
   }

   public String getNomeGiocatore() {
      return this.nomeGiocatore;
   }

   public void setNomeGiocatore(String nomeGiocatore) {
      this.nomeGiocatore = nomeGiocatore;
   }

   private String nomeRandom(int length) {
      Random rnd = new Random();
      char[] arr = new char[length];

      for (int i = 0; i < length; i++) {
         int n = rnd.nextInt(36);
         arr[i] = (char) (n < 10 ? '0' + n : 'a' + n - 10);
      }

      return new String(arr);
   }

}
