package core;

import gui.panels.OutputMediator;

public class CasellaBookCafe extends CasellaAstratta {

   public CasellaBookCafe(AzioneAstratta azione, String nome) {

      super(azione, nome);
   }

   public void action(Giocatore g) {

      /*
       * permette al giocatore di rilanciare i dadi
       */
      OutputMediator.println("Prenditi un caffè! ");
      azione.esegui(g);
   }

}
