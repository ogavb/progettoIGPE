package core;

import gui.panels.OutputMediator;

public class AzioneRilanciaDado extends AzioneAstratta {

   public AzioneRilanciaDado(GameManagerAstratta gm) {

      super(gm);
   }

   public void esegui(Giocatore g) {

      OutputMediator.println(g.getNome() + " rilancia i dadi : ");

      if (g.getOrdineDiPartenza() == 0)
         gm.getGestore().setGiocatoreAttuale(gm.getGestore().size() - 1);
      else
         gm.getGestore().setGiocatoreAttuale(g.getOrdineDiPartenza() - 1);

   }
}
