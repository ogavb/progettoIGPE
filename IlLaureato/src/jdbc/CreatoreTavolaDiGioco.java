package jdbc;

import java.io.File;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreatoreTavolaDiGioco extends Database {

	private String configurazioneTavola;


	public CreatoreTavolaDiGioco() throws SQLException{

		super();
		File database = new File("illaureato.db");

		if(database.length() == 0) {
			stat.executeUpdate("create table tavoladigioco (codice, nome, configurazione)");
			stat.executeUpdate("create table esame (codice, nome, crediti, domanda, rispostaEsatta, rispostaSbagliata)");
			stat.executeUpdate("insert into tavoladigioco (codice, nome, configurazione) VALUES (1,'Default','Semplice Esame Biblioteca Semplice Semplice BookCafe Semplice Ricevimento Esame Semplice Semplice Biblioteca Semplice Ricevimento CentroResidenziale Semplice BookCafe Cus Semplice Esame Semplice Biblioteca Semplice Semplice BookCafe Ricevimento Semplice Mensa Semplice Cus Semplice BookCafe Semplice Semplice Semplice Ricevimento Semplice Mensa Semplice Semplice')");
//			stat.executeUpdate("insert into esame (codice, nome, crediti, domanda, rispostaEsatta, rispostaSbagliata) VALUES (1, 'storia', 5, 'quando è scoppiata la prima guerra mondiale in Italia?', '23 maggio 1915', '28 luglio 1914')");
			stat.executeUpdate("insert into esame (codice, nome, crediti, domanda, rispostaEsatta, rispostaSbagliata) VALUES (1, 'inglese', 5, 'come si dice bello in inglese?', 'beautiful', 'good')");
//			stat.executeUpdate("insert into esame (codice, nome, crediti, domanda, rispostaEsatta, rispostaSbagliata) VALUES (1, 'informatica', 10, 'cosa è il mouse?', 'hardware', 'software')");
//			stat.executeUpdate("insert into esame (codice, nome, crediti, domanda, rispostaEsatta, rispostaSbagliata) VALUES (2, 'informatica', 10, 'cosa è google chrome?', 'broswer', 'programma musicale')");
//			stat.executeUpdate("insert into esame (codice, nome, crediti, domanda, rispostaEsatta, rispostaSbagliata) VALUES (3, 'informatica', 10, 'come si chiama il programa di video scrittura di microsoft?', 'Word', 'Excel')");
		}
	}

	public ObservableList<String> prelevaNomiConfigurazioni() {
		ObservableList<String> configurazioni = FXCollections.observableArrayList();
		try {
			this.resultset = this.stat.executeQuery(
					 "select nome "
					 + "from tavoladigioco "
					 );

			while(this.resultset.next()) {
				configurazioni.add(resultset.getString("nome"));
			}

		 } catch (SQLException e) {
		 } finally {
			 if (conn!= null) {
				 try {
					 conn.close();
				 } catch (Exception e) {
				 }
			 }
		 }
		 return configurazioni;
	}


	@Override
	public void query() {
		if (nomeConfigurazione == null)
			nomeConfigurazione = "Default";
		try {

			this.resultset = this.stat.executeQuery(
					 "select * "
					 + "from tavoladigioco "
					 + "where nome = " + "'" + nomeConfigurazione + "'"
					 );

			this.resultset.next();


			this.setConfigurazioneTavola(this.resultset.getString("configurazione"));


		 } catch (SQLException e) {
			 // In caso di errore...
		 } finally {
			 if (conn!= null) {
				 try {
					 conn.close();
				 } catch (Exception e) {
				 }
			 }
		 }

	}

	public String getConfigurazioneTavola() {
		return configurazioneTavola;
	}

	public void setConfigurazioneTavola(String configurazioneTavola) {
		this.configurazioneTavola = configurazioneTavola;
	}

}
