package core;

public interface Casella {

   public void action(Giocatore g);

   public void setAzione(AzioneAstratta a);

   public AzioneAstratta getAzione();
}
