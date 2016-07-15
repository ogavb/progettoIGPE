package jdbc;

import java.sql.SQLException;

public class ScrittoreConfigurazione extends Database {

	private String configurazioneDaScrivere;

	public ScrittoreConfigurazione( String configurazioneDaScrivere ){

		super();
		this.configurazioneDaScrivere = configurazioneDaScrivere;

	}

	@Override
	public void query() {

		try {

			String sql = "select count(*) from tavoladigioco";

			int cont = 0;
			this.resultset = this.stat.executeQuery(sql);
			this.resultset.next();
			cont = this.resultset.getInt(1);
			cont = cont + 1;

			String sql1 = "INSERT INTO tavoladigioco (codice, nome, configurazione) VALUES (" + cont + ", '" + this.nomeConfigurazione + "', '" + this.configurazioneDaScrivere + "'" + ")";
			this.stat.execute(sql1);

		 } catch (SQLException e) {
			 e.printStackTrace();
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

}
