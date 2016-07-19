package core;

import java.io.Serializable;
import java.util.HashSet;


public class Giocatore implements Comparable<Object>, Serializable {

   private static final long serialVersionUID = 1L;

   private String nome;
   private int crediti;
   private Posizione pos;
   private int anniAccademici;
   private int casellePercorseInAnnoAccademico;
   private final int caselleDisponibili = 40;
   private int risultatoDado;
   private int ordineDiPartenza;
   private int color;
   private HashSet<Integer> esamiSvolti;

   public Giocatore(String nome) {

      this.nome = nome;
      this.crediti = 0;
      this.pos = new Posizione(0, 0);
      this.anniAccademici = 1;
      this.casellePercorseInAnnoAccademico = 0;
      this.risultatoDado = 0;
      this.setOrdineDiPartenza(0);
      this.color = 0;
      this.esamiSvolti = new HashSet<>();
   }

   public int compareTo(Object o) {

      if (o instanceof Giocatore) {
         Giocatore g = (Giocatore) o;
         if (this.risultatoDado < g.risultatoDado)
            return +1;
         else if (this.risultatoDado > g.risultatoDado)
            return -1;
      }
      return 0;
   }

   private void aggiornaAnniAccademici() {
      if (casellePercorseInAnnoAccademico >= caselleDisponibili) {
         casellePercorseInAnnoAccademico -= caselleDisponibili;
         anniAccademici++;

      }
   }

   public int getRisultatoDado() {

      return this.risultatoDado;
   }

   public void setRisultatoDado(int numDadi) {

      this.risultatoDado = numDadi;
   }

   public int lanciaPerOrdine() {
      risultatoDado = Dado.lanciaDadi();
      return risultatoDado;
   }

   public int lancia(int risultato) {

      risultatoDado = risultato;
      casellePercorseInAnnoAccademico += this.risultatoDado;
      System.out.println("caselle percorse dal giocatore "
            + casellePercorseInAnnoAccademico);
      aggiornaAnniAccademici();
      // OutputMediator.risultatoDado(risultatoDado);
      return risultatoDado;
   }

   public String getNome() {

      return nome;
   }

   public void setNome(String nome) {

      this.nome = nome;
   }

   public int getCrediti() {

      return crediti;
   }

   public void aggiornaCrediti(int crediti) {
      this.crediti += crediti;
   }

   public int getAnniAccademici() {

      return anniAccademici;
   }

   public void setAnniAccademici(int anniAccademici) {

      this.anniAccademici = anniAccademici;
   }

   public Posizione getPos() {

      return pos;
   }

   public void setPos(Posizione pos) {

      this.pos = pos;
   }

   public int getOrdineDiPartenza() {
      return ordineDiPartenza;
   }

   public void setOrdineDiPartenza(int ordineDiPartenza) {
      this.ordineDiPartenza = ordineDiPartenza;
   }

   public int getColor() {
      return color;
   }

   public void setColor(int color) {
      this.color = color;
   }

   public String toString() {

      StringBuffer sb = new StringBuffer();

      sb.append(this.nome + ",");
      sb.append(this.ordineDiPartenza + ",");
      sb.append(this.crediti + ",");
      sb.append(this.pos.getX() + "," + this.pos.getY() + ",");
      sb.append(this.anniAccademici + ",");
      sb.append(this.risultatoDado);

      return sb.toString();
   }
}