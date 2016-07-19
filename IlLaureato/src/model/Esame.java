package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Esame {

   private SimpleIntegerProperty codice;
   private SimpleStringProperty nome;
   private SimpleIntegerProperty crediti;
   private SimpleStringProperty domanda;
   private SimpleStringProperty rispostaEsatta;
   private SimpleStringProperty rispostaSbagliata;

   public Esame(int codice, String nome, int crediti, String domanda,
         String rispostaEsatta, String rispostaSbagliata) {

      this.codice = new SimpleIntegerProperty(codice);
      this.nome = new SimpleStringProperty(nome);
      this.crediti = new SimpleIntegerProperty(crediti);
      this.domanda = new SimpleStringProperty(domanda);
      this.rispostaEsatta = new SimpleStringProperty(rispostaEsatta);
      this.rispostaSbagliata = new SimpleStringProperty(rispostaSbagliata);

   }

   public Integer getCodice() {
      return codice.get();
   }

   public void setCodice(Integer codice) {
      this.codice.setValue(codice);
   }

   public String getNome() {
      return nome.get();
   }

   public void setNome(String nome) {
      this.nome.setValue(nome);
   }

   public Integer getCrediti() {
      return crediti.get();
   }

   public void setCrediti(Integer crediti) {
      this.crediti.setValue(crediti);
   }

   public String getDomanda() {
      return domanda.get();
   }

   public void setDomanda(String domanda) {
      this.domanda.setValue(domanda);
   }

   public String getRispostaEsatta() {
      return rispostaEsatta.get();
   }

   public void setRispostaEsatta(String rispostaEsatta) {
      this.rispostaEsatta.setValue(rispostaEsatta);
   }

   public String getRispostaSbagliata() {
      return rispostaSbagliata.get();
   }

   public void setRispostaSbagliata(String rispostaSbagliata) {
      this.rispostaSbagliata.setValue(rispostaSbagliata);
   }

}
