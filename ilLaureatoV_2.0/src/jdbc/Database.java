package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public abstract class Database {

	protected String Driver;
	protected String db_url;
	protected Connection conn;
	protected Statement  stat;
	protected ResultSet resultset;

	protected String nomeConfigurazione;


	public Database(){

		this.creaConnessione();

	}

	protected void creaConnessione(){

		this.Driver = "org.sqlite.JDBC";
		this.db_url = "jdbc:sqlite:illaureato.db";

		try {
			 // Carico il driver.
			 Class.forName(this.Driver);
		 } catch (ClassNotFoundException e1) {
			 // Il driver non può essere caricato.
			 System.out.println("Driver non trovato...");
			 e1.printStackTrace();
			 System.exit(1);
		 }

		try {
			this.conn = DriverManager.getConnection(this.db_url);
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		 // Ottengo lo Statement per interagire con il database.
		 try {
			this.stat = this.conn.createStatement();
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

	}

	public String getNomeConfigurazione() {
		return nomeConfigurazione;
	}

	public void setNomeConfigurazione(String nomeConfigurazione) {
		this.nomeConfigurazione = nomeConfigurazione;
	}

	public abstract void query();

}
