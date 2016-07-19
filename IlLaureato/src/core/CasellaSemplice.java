package core;

public class CasellaSemplice extends CasellaAstratta {

   public CasellaSemplice(AzioneAstratta azione, String nome) {

      super(azione, nome);
   }

   public void action(Giocatore g) {
      azione.esegui(g);
   }

}
