package core;

import gui.panels.OutputMediator;

public class CasellaRicevimento extends CasellaAstratta {

   public CasellaRicevimento(AzioneAstratta azione, String nome) {

      super(azione, nome);

   }

   public void action(Giocatore g) {

      /*
       * blocca il giocatore 1 o 2 turni
       */
      OutputMediator.println("Sei a ricevimento! ");
      azione.esegui(g);

   }

}
