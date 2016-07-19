package jdbc;

import java.sql.SQLException;
import java.util.HashSet;
import core.AzioneDomanda;
import model.Esame;
import model.Risposta;
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

	public void query2(HashSet<Integer> hashSet){

		int count = 0;
		try {
			 this.resultset = this.stat.executeQuery("select count(*) from esame");
			 this.resultset.next();
			 count = this.resultset.getInt(1);
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

		int rand = 0;
		if(hashSet.size() < count ){

		   do {
   		rand = ( int )( Math.random() * count +1 );
		   }
   		while(hashSet.contains(rand));
		}

		else {
		   rand = ( int )( Math.random() * count +1 );
		}
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
				 String domanda = this.resultset.getString("domanda");

				 this.crediti = this.resultset.getInt("crediti");
				 azioneDomanda.setRispostaEsatta(this.resultset.getString("rispostaEsatta"));
				 azioneDomanda.setCrediti(this.crediti);

				 if( this.ordineRisposta == 0 ){
					 ris.getRisposte()[0] = this.resultset.getString("rispostaEsatta");
				 	 ris.getRisposte()[1] = this.resultset.getString("rispostaSbagliata");
				 }
				 else{
					 ris.getRisposte()[1] = this.resultset.getString("rispostaEsatta");
				 	 ris.getRisposte()[0] = this.resultset.getString("rispostaSbagliata");

				 }
				 this.azioneDomanda.setRisposte(ris);
				 this.azioneDomanda.setDomanda(domanda);
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

   @Override
   public void query() {
   }

}