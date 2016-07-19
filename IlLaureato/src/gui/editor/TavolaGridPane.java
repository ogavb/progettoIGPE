package gui.editor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import core.Giocatore;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public abstract class TavolaGridPane extends GridPane {

   private int NUM_COLS;
   private int NUM_ROWS;


   protected HashMap<Integer, MyPaneTavola> lista = new HashMap<>();

   protected String nomeCasellaSelezionata;
   protected Image immagineCasellaSelezionata;

   public TavolaGridPane(int NUM_COLS, int NUM_ROWS) throws SQLException {

      super();

      this.setNUM_COLS(NUM_COLS);
      this.setNUM_ROWS(NUM_ROWS);

      setGridLinesVisible(true);

      costruisciCelle(NUM_COLS, NUM_ROWS);

      // disegno le cornici
      disegnaCornici(NUM_COLS);

      this.setPrefSize(880, 660);
      this.setMaxSize(880, 660);
      this.setMinSize(880, 660);

   }

   protected abstract void setBottoni(boolean b);

   private void costruisciCelle(int NUM_COLS, int NUM_ROWS) {
      for (int i = 0; i < NUM_COLS; i++) {
         ColumnConstraints colConst = new ColumnConstraints();
         colConst.setPercentWidth(100.0 / NUM_COLS);
         getColumnConstraints().add(colConst);
      }
      for (int i = 0; i < NUM_ROWS; i++) {
         RowConstraints rowConst = new RowConstraints();
         rowConst.setPercentHeight(100.0 / NUM_ROWS);
         getRowConstraints().add(rowConst);
      }
   }

   public String getNomeCasellaSelezionata() {
      return this.nomeCasellaSelezionata;
   }

   /*
    * Costruisce un MyPaneTavola vuoto
    */
   public MyPaneTavola makePanel(int indice) {
      MyPaneTavola p = new MyPaneTavola();
      p.setIndice(indice);

      p.setOnMouseClicked(new EventHandler<MouseEvent>() {

         @Override
         public void handle(MouseEvent event) {
            p.setBackGround(immagineCasellaSelezionata);
            p.setNomeCasella(nomeCasellaSelezionata);
            System.err.println("CASELLA SELEZIONTA IN EDITOR: " + p.getNomeCasella());
            lista.put(p.getIndice(), p);

            if (controllaCondizione()) {

               setBottoni(false);
            }
            else
               setBottoni(true);

         }

      });

      return p;
   }

   private boolean controllaCondizione() {
      int cont = 0;

      Iterator<Entry<Integer, MyPaneTavola>> it = lista.entrySet().iterator();


      while (it.hasNext()) {
        Map.Entry<Integer,MyPaneTavola> entry = (Map.Entry<Integer, MyPaneTavola>)it.next();

        if(entry.getValue().getNomeCasella().equals("Esame")){
           cont++;
        }

     }

      return cont >= 4 && lista.size() == 40;
   }

   private void disegnaCornici(int numCols) {
      // Top
      for (int i = 0; i < numCols - 1; i++) {
         add(makePanel(i), i, 0);
      }

      // Right
      for (int i = 0; i < numCols - 1; i++) {
         add(makePanel(numCols+i), numCols - 1, i);
      }

      // Bottom
      for (int i = numCols - 1, v = 21; i > 0; i--, v++) {
         add(makePanel(v), i, numCols - 1);
      }

      // Left
      for (int i = numCols - 1, v = 32; i > 0; i--, v++) {
         add(makePanel(v), 0, i);
      }
   }

   public MyPanePalette getMyPane(Giocatore g) {
      return (MyPanePalette) getChildren().get(0);
   }

   public int getNUM_COLS() {
      return NUM_COLS;
   }

   public void setNUM_COLS(int nUM_COLS) {
      NUM_COLS = nUM_COLS;
   }

   public int getNUM_ROWS() {
      return NUM_ROWS;
   }

   public void setNUM_ROWS(int nUM_ROWS) {
      NUM_ROWS = nUM_ROWS;
   }

}
