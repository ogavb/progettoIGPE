package core;

import gui.panels.OutputMediator;

public class CasellaBiblioteca extends CasellaAstratta {

   public CasellaBiblioteca(AzioneAstratta azione, String nome) {

      super(azione, nome);
   }

   public void action(Giocatore g) {

      /*
       * fa avanzare il giocatore di tot caselle
       */
      OutputMediator.println("Sei in biblioteca! ");
      azione.esegui(g);
   }

}
