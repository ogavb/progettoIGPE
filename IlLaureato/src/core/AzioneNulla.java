package core;

import networking.GameManagerNetwork;

public class AzioneNulla extends AzioneAstratta {

   public AzioneNulla(GameManagerAstratta gm) {

      super(gm);
   }

   public void esegui(Giocatore g) {
      if (gm instanceof GameManagerNetwork
            && !((GameManagerNetwork) gm).isRequestActive())
         ((GameManagerNetwork) gm).setYourRound(false);
   }

}
