package jdbc;

import java.sql.SQLException;
import java.util.Iterator;

import core.AzioneDomanda;
import model.Esame;
import model.Risposta;
import gui.panels.OutputMediator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PrelevatoreDomanda extends Database{

	private int crediti;
	private AzioneDomanda azioneDomanda;
	private int ordineRisposta;

	public PrelevatoreDomanda(AzioneDomanda azioneDomanda){

		super();
		this.azioneDomanda = azioneDomanda;
		this.ordineRisposta = ( int )( Math.random() * 2 );
	}

	public PrelevatoreDomanda() {}

	public AzioneDomanda getAzione() {

		return azioneDomanda;
	}


	public int getOrdineRisposta() {

		return ordineRisposta;
	}

	public int getCrediti(){
		return this.crediti;
	}

	public ObservableList<Esame> prelevaEsami(){

		this.creaConnessione();

		ObservableList <Esame> lista = FXCollections.observableArrayList();

		try{
			this.resultset = this.stat.executeQuery(
					 "select * "
					 + "from esame"
					 );

		    while (resultset.next()) {
		    	Esame b = new Esame(resultset.getInt("codice"), resultset.getString("nome"),
		    			resultset.getInt("crediti"), resultset.getString("domanda"),
		    			resultset.getString("rispostaEsatta"), resultset.getString("rispostaSbagliata"));
		    	lista.add(b);
		    }

		}catch(SQLException e){

		}
		finally{
			if (conn!= null) {
				 try {
					 conn.close();
				 }
				 catch (Exception e) {}
			 }
		}
		return lista;
	}

	public void inserisciEsame(Esame esame) {
		this.creaConnessione();
		try {
		   String sql = "select max(codice) as massimo from esame";

		   int cont = 0;
		   this.resultset = this.stat.executeQuery(sql);
		   this.resultset.next();
		   cont = this.resultset.getInt(1);
		   cont = cont + 1;
		   esame.setCodice(cont);

		   System.out.println(esame.getNome());
		   String sql1 = "INSERT INTO esame (codice, nome, crediti, domanda, rispostaEsatta, rispostaSbagliata )"
					+ "		 VALUES ("+cont+",'" + esame.getNome() + "', "+esame.getCrediti() +
					", '"+esame.getDomanda()+"','"+esame.getRispostaEsatta()+"','"+esame.getRispostaSbagliata()+"'" + ")";

		   stat.executeUpdate(sql1);
		}

		catch(SQLException e) {}

		finally {
			 if (conn!= null) {
				 try {
			        conn.close();
				 }
				 catch (Exception e) {}
			 }
		 }
	}

	public void elimina(int codice){

		System.out.println("Il codice da eliminare �: " + codice);

		this.creaConnessione();
		try{
			stat.executeUpdate(
					 "DELETE "
					 + "from esame "
					 + "where codice = " + codice
					 );

		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			if (conn!= null) {
				 try {
					 conn.close();
				 }
				 catch (Exception e) {}
			 }

		}
	}

	@Override
	public void query(){

		int count = 0;

		try {
			 this.resultset = this.stat.executeQuery("select count(*) from esame");
			 this.resultset.next();
			 count = this.resultset.getInt(1);
			 System.out.println("ci sono "+count + " domande");
		}
		catch (SQLException e) {
			 // In caso di errore...
		}
		finally {
			 if (conn!= null) {
				 try {
					 conn.close();
				 }
				 catch (Exception e) {}
			 }
		 }

		int rand = ( int )( Math.random() * count +1 );
		System.out.println("il random � "+rand);
		this.creaConnessione();

		try {
			this.resultset = this.stat.executeQuery(
					 "select * "
					 + "from esame"
					 + " where codice = "+ rand
					 );

			 // Scorro e mostro i risultati.
			 while (resultset.next()) {

				 Risposta ris = new Risposta();
				// System.out.println("la risposta � "+ ris.getRisposte()[0]);
				 String domanda = this.resultset.getString("domanda");
				 //System.out.println("la domanda � "+domanda);

				 this.crediti = this.resultset.getInt("crediti");
				// System.out.println("i crediti della domanda sono "+crediti);
				 //String nomeEsame = this.resultset.getString("nome");
				 //String stringaCrediti = this.resultset.getString("crediti");
				 //int crediti = Integer.parseInt(stringaCrediti);
				 azioneDomanda.setRispostaEsatta(this.resultset.getString("rispostaEsatta"));
				 azioneDomanda.setCrediti(this.crediti);
				 if( this.ordineRisposta == 0 ){
					 ris.getRisposte()[0] = this.resultset.getString("rispostaEsatta");
				 	 ris.getRisposte()[1] = this.resultset.getString("rispostaSbagliata");

//				 	System.out.println("le risposte sono "+ris.getRisposte()[0] );
//					System.out.println("le risposte sono "+ris.getRisposte()[1] );
				 }
				 else{
					 ris.getRisposte()[1] = this.resultset.getString("rispostaEsatta");
				 	 ris.getRisposte()[0] = this.resultset.getString("rispostaSbagliata");

//				 	System.out.println("le risposte sono "+ris.getRisposte()[0] );
//					System.out.println("le risposte sono "+ris.getRisposte()[1] );
				 }
				 this.azioneDomanda.setRisposte(ris);
				 this.azioneDomanda.setDomanda(domanda);

				 OutputMediator.println("domanda: " + domanda);
				 OutputMediator.print(ris.getRisposte()[0] + "    ");
				 OutputMediator.println(ris.getRisposte()[1]);
				 OutputMediator.print("\n");
			 }

		}
		catch (SQLException e) {
			 // In caso di errore...
		}
		finally {
			 if (conn!= null) {
				 try {
					 conn.close();
				 }
				 catch (Exception e) {}
			 }
		 }
	}

	public static void main(String[] args) {
		PrelevatoreDomanda database = new PrelevatoreDomanda();
		ObservableList<Esame> esami = database.prelevaEsami();

		Iterator<Esame> it = esami.iterator();
		while(it.hasNext()){
			System.out.println("Esame: " + it.next().getNome());
		}
	}
}