package core;

import java.sql.SQLException;
import java.util.StringTokenizer;

import jdbc.CreatoreTavolaDiGioco;
import jdbc.ScrittoreConfigurazione;

public class TavolaDiGioco {

	private Casella[][] tavola;

	public TavolaDiGioco(GameManager gm, String nomeConfigurazione) throws SQLException{

		this.setNomeConfigurazione(gm, nomeConfigurazione);
	}

	public void setNomeConfigurazione(GameManager gm, String nomeConfigurazione) throws SQLException{

		System.out.println("La configurazione è: " + nomeConfigurazione);
		CreatoreTavolaDiGioco database = new CreatoreTavolaDiGioco();
		database.setNomeConfigurazione(nomeConfigurazione);
		database.query();

		this.tavola = new Casella[4][10];
		String configurazione = database.getConfigurazioneTavola();
		StringTokenizer st = new StringTokenizer(configurazione);
		String parola = null;
		for( int x = 0, i = 0, j = 0; x < configurazione.length(); x++, j++ ){
			if( st.hasMoreTokens() ){
				parola = st.nextToken();
				switch(parola){

				case "Biblioteca":
					this.tavola[i][j] = new CasellaBiblioteca(new AzioneMovimento(gm, true),"biblioteca");
					break;

				case "BookCafe":
					this.tavola[i][j] = new CasellaBookCafe(new AzioneRilanciaDado(gm),"bookCafe");
					break;

				case "CentroResidenziale":
					this.tavola[i][j] = new CasellaCentroResidenziale(new AzioneFermaTurno(gm, 2),"centroResidenziale");
					break;

				case "Cus":
					this.tavola[i][j] = new CasellaCus(new AzioneNulla(gm),"cus");
					break;

				case "Esame":
					this.tavola[i][j] = new CasellaEsame( new AzioneDomanda( gm ), "esame" );
					break;

				case "Mensa":
					this.tavola[i][j] = new CasellaMensa(new AzioneNulla(gm),"mensa");
					break;

				case "Ricevimento":
					this.tavola[i][j] = new CasellaRicevimento(new AzioneFermaTurno(gm, 2),"ricevimento");
					break;

				case "Semplice":
					this.tavola[i][j] = new CasellaSemplice(new AzioneNulla(gm),"semplice");
					break;

				default:
						System.out.println("Input errato!");
				}
				if( j == this.tavola[0].length - 1 ){

					i++;
					j = -1;
				}
			}
		}

	}

	public Casella[][] getTavola(){

		return this.tavola;
	}

	public void setTavola( Casella[][] tavola ){

		this.tavola = tavola;
	}

	public void write( String configurazione ){

		new ScrittoreConfigurazione(configurazione).query();
	}

	public Casella getCasella( int i, int j ){

		return this.tavola[i][j];
	}

	public void setCasella( int i, int j, Casella c ){

		this.tavola[i][j] = c;
	}

	public String toString(){

		StringBuffer sb = new StringBuffer();

		for( int i = 0; i < this.tavola.length; i++ ){
			for( int j = 0; j < this.tavola[0].length; j++ ){
				sb.append( this.tavola[i][j].toString() + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}


}
