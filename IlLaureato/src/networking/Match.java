package networking;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Match {
	private int idMatch;
	private int numberPlayer;
	private int size;
	private List<Connection> Players;
	private List<String> namePlayers;

	public Match(int idMatch,int numberPlayer,Connection c){
		Players=new CopyOnWriteArrayList<Connection>();
		namePlayers=new CopyOnWriteArrayList<String>();
		Players.add(c);
		this.idMatch=idMatch;
		this.numberPlayer=numberPlayer;
		this.size = Players.size();
	}

	@Override
	public String toString() {
		return ""+this.idMatch+","+this.numberPlayer+","+this.Players.size();
	}

	public List<String> getNamePlayers(){
		return namePlayers;
	}

	public void add(String namePlayer){
		namePlayers.add(namePlayer);
	}

	public void remove(String namePlayer){
		namePlayers.remove(namePlayer);
	}

	public int getIdMatch() {
		return idMatch;
	}


	public int getNumberPlayer() {
		return numberPlayer;
	}

	public int size(){
		return this.size;
	}

	public void setSize(int size){
		this.size = size;
	}

	public synchronized boolean addPlayer(Connection c){
		if(Players.size()==numberPlayer){
			return false;
		}else{
			Players.add(c);
			this.size = Players.size();
			return true;
		}

	}

	public synchronized void remove(Connection c){

		Players.remove(c);

	}
	public void notifyAll(String message){
		for(Connection c:Players){
			c.insertMessage(message);
		}
	}

	public void notifyAllExcept(String name, String request){

		if(name != null && request != null){

			for(Connection c:Players){
				if(!c.getNomeGiocatore().equals(name)){
					c.insertMessage(request);
				}
			}

		}

	}

	public void notifyToPlayerName(String name, String request){
		if(name != null && request != null){

			for(Connection c:Players){
				if(c.getNomeGiocatore().equals(name)){
					c.insertMessage(request);
					break;
				}
			}

		}
	}
}

