package gui.panels;

import java.sql.SQLException;
import java.util.HashMap;

import core.AzioneDomanda;
import core.Casella;
import core.GameManagerAstratta;
import core.Giocatore;
import core.Posizione;
import core.TavolaDiGioco;
import javafx.animation.PathTransition;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.DepthTest;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class PannelloTavola extends GridPane  implements PaneSwitcher{

	private final int NUM_COLS = 11;
	private final int  NUM_ROWS = 11;

	protected int riga;
	protected int colonna;

	private GameManagerAstratta gm;
	private TavolaDiGioco tdg;

	private String rispostaUtente;

	private MyPane[][] board = new MyPane[4][10];

	private HashMap< String, Image> mappa = new HashMap<String, Image>();

	private ImageView iv1 = new ImageView(new Image("file:giocatori/blue.gif"));
	private ImageView iv2 = new ImageView(new Image("file:giocatori/cyan.gif"));
	private ImageView iv3 = new ImageView(new Image("file:giocatori/green.gif"));
	private ImageView iv4 = new ImageView(new Image("file:giocatori/purple.gif"));
	private ImageView iv5 = new ImageView(new Image("file:giocatori/red.gif"));
	private ImageView iv6 = new ImageView(new Image("file:giocatori/yellow.gif"));

	private ImageView ricevimento = new ImageView(new Image("file:giocatori/purple.gif"));

	private ImageView n = null;

	//TUTTI I PANNELLI PER FARE LO SWITCHPANE
	private PaneLogo paneLogo;
	private PaneBiblioteca paneBiblioteca;
	private PaneDomande paneDomande;
	private PaneRicevimento paneRicevimento;
	private PaneCR paneCentroR;
	private PaneMensa paneMensa;

	private PathTransition pathTransition[];
	private int numPathTransition = 0;


	/*
	 * OVERRIDING MAKE_PANEL
   	 * Costruisce il singolo MyPane della tavola da gioco indicizzato dalla riga e dalla
	 * colonna corrente e viene ritornato
	 */

	public MyPane makePanel() {
		String nomeCasella = tdg.getCasella(riga, colonna).toString();
		MyPane p = new MyPane(tdg.getCasella(riga, colonna), mappa.get(nomeCasella));

		setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
//				System.out.println(p.getLayoutX() + " " + p.getLayoutY());
			}

		});

		if(colonna < 9 )
			colonna++;
		else
		{
			colonna = 0;
			riga++;
		}
		return p;
	}

	/*
	 * Sposta l'image view del giocatore g nella nuova posizione mediante animazioni
	 */
	public void spostaGiocatore(Giocatore g){
		//stampaCoordinateBoard();

		/*
		 * Converto la posizione reale del giocatore nella corrispondente posizione della
		 * tavola da gioco
		 */
		Posizione pnuova = convertiPosizione(g.getPos());

		/*
		 * Prendo la casella indicizzata dalla posizione pnuova
		 */
		MyPane mp = (MyPane)getNodeByRowColumnIndex(pnuova.getY(),pnuova.getX());
		//double larghezza = mp.getWidth();
		//double altezza = mp.getHeight();

		/*
		 * si imposta n alla pedina corrente del giocatore e il relativo path
		 */
		switch(g.getColor()){
		case 0:
			this.numPathTransition = 0;
			n = iv1;
			break;
		case 1:
			this.numPathTransition = 1;
			n = iv2;
			break;

		case 2:
			this.numPathTransition = 2;
		    n = iv3;
			break;

		case 3:
			this.numPathTransition = 3;
		    n = iv4;
			break;

		case 4:
			this.numPathTransition = 4;
		    n = iv5;
			break;

		case 5:
			this.numPathTransition = 5;
		    n = iv6;
			break;
		}

		/*
		 * creazione del PATH
		 */
		Path path = new Path();

		//System.out.println((n.getTranslateX()+11 ) + " " + (n.getTranslateY()+12.5 ));
    	//System.out.println((mp.getLayoutX()+11) + " " + (mp.getLayoutY()+12.5));


    	/*
    	 * se l'immagine n si trova sul lato TOP della tavola e la casella dove l'immagine
    	 * andrà a posizionarsi si trova sul lato RIGHT, allora si crea un PATH che va dalla
    	 * posizione corrente dell'immagine sul TOP alla posizione dell'ultima casella TOP e
    	 * da qui alla posizione della casella dove andrà a posizionarsi l'immagine sul lato
    	 * RIGHT.
    	 *
    	 * 											======>
    	 * Il percorso è del tipo:			    * * * * * * * * * *
														    	  *
														    	  *
														    	  *   |
														    	  *   |
														    	  *   |
														    	  *  \ /
														    	  *
														    	  *
														    	  *
														    	  *
    	 */
		//System.out.println("La translateX è: " + (n.getTranslateX()+11));
    	//System.out.println("La dimensione è: " + mp.getWidth() + " " + mp.getHeight());
		if( !(n.getTranslateX()+11 == 11 && n.getTranslateY()+12.5 == 12.5)
				&& ((n.getTranslateX()+11 >= 11 && n.getTranslateX()+11 <= (mp.getWidth()*10)+11) && (n.getTranslateY() + 12.5 == 12.5) )
				&& (mp.getLayoutY() >= mp.getHeight() ) ) {

			//System.out.println("Entrato nel basso");

			path.getElements().add(new MoveTo(n.getTranslateX()+11, n.getTranslateY()+12.5));
			path.getElements().add(new LineTo((mp.getWidth()*10)+11,12.5));
			path.getElements().add(new LineTo((mp.getWidth()*10)+11,mp.getLayoutY()+12.5));

		}

		/*
		 *
		 * se l'immagine n si trova sul lato RIGHT della tavola e la casella dove l'immagine
    	 * andrà a posizionarsi si trova sul lato BOTTOM, allora si crea un PATH che va dalla
    	 * posizione corrente dell'immagine sul RIGHT alla posizione dell'ultima casella RIGHT e
    	 * da qui alla posizione della casella dove andrà a posizionarsi l'immagine sul lato
    	 * BOTTOM.
		 *
		 * Il percorso è del tipo:
														 *
														 * 	|
														 *  |
														 *  |
														 * \ /
														 *
														 *
														 *
														 *
									   * * * * * * * * * *
												<-------

		 */

		else if( !(n.getTranslateX()+11 == 11 && n.getTranslateY()+12.5 == 12.5)
				&& (n.getTranslateX()+11 == (mp.getWidth()*10)+12)
				&& ((n.getTranslateY()+12.5 >= 12.5 && n.getTranslateY() + 12.5 < (mp.getHeight()*10)+12.5) )
				&& (mp.getLayoutX()<(mp.getWidth()*10))) {

			//System.out.println("Entrato nel girare a sinistra");

			path.getElements().add(new MoveTo(n.getTranslateX()+11, n.getTranslateY()+12.5));
			path.getElements().add(new LineTo((mp.getWidth()*10)+11,(mp.getHeight()*10)+12.5));
			path.getElements().add(new LineTo(mp.getLayoutX()+11,mp.getLayoutY()+12.5));

		}

		/*
		 *
		 *
		 * se l'immagine n si trova sul lato BOTTOM della tavola e la casella dove l'immagine
    	 * andrà a posizionarsi si trova sul lato LEFT, allora si crea un PATH che va dalla
    	 * posizione corrente dell'immagine sul BOTTOM alla posizione dell'ultima casella BOTTOM e
    	 * da qui alla posizione della casella dove andrà a posizionarsi l'immagine sul lato
    	 * LEFT.
		 *
		 * Il percorso è del tipo:
											*
											*
											*
										/ \	*
										 |	*
										 |	*
										 |	*
											*
											*
		 * 									* * * * * * * * * *
		 * 										<-------
		 *
		 */

		else if( !(n.getTranslateX()+11 == 11 && n.getTranslateY()+12.5 == 12.5)
				&& ((n.getTranslateX()+11 > 12 && n.getTranslateX()+11 <= (mp.getWidth()*10)+11) && (n.getTranslateY()+12.5 == (mp.getHeight()*10)+12.5))
				&&	((mp.getLayoutY() < mp.getHeight()*10)) ) {

			//System.out.println("Entrato in alto");

			path.getElements().add(new MoveTo(n.getTranslateX()+11, n.getTranslateY()+12.5));
			path.getElements().add(new LineTo(11,(mp.getHeight()*10)+12.5));
			path.getElements().add(new LineTo(11,mp.getLayoutY()+12.5));


		}

		/*
		 * se l'immagine n si trova sul lato LEFT della tavola e la casella dove l'immagine
    	 * andrà a posizionarsi si trova sul lato TOP, allora si crea un PATH che va dalla
    	 * posizione corrente dell'immagine sul LEFT alla posizione dell'ultima casella LEFT e
    	 * da qui alla posizione della casella dove andrà a posizionarsi l'immagine sul lato
    	 * TOP.
		 *
		 * Il percorso è del tipo:
		 *
		 *
		 * 									======>
										 * * * * * * * * * * *
										 *
									/ \	 *
									 |	 *
									 |	 *
									 |	 *
										 *
										 *
										 *
										 *
		 */

		else if( !(n.getTranslateX()+11 == 11 && n.getTranslateY()+12.5 == 12.5)
				&& ((n.getTranslateX()+11 == 12 ) && (n.getTranslateY()+12.5 > 12.5 && n.getTranslateY()+12.5 <= (mp.getHeight()*10)+12.5))
				&& ( mp.getLayoutX() > 12 ) ) {

			//System.out.println("Entrato Top");

			path.getElements().add(new MoveTo(n.getTranslateX()+11, n.getTranslateY()+12.5));
			path.getElements().add(new LineTo(11,12.5));
			path.getElements().add(new LineTo(mp.getLayoutX()+11,12.5));

		}

		/*
		 * altrimenti si crea un PATH di un solo verso:
		 *
		 * 	1) orizzontale da sinistra verso destra   =>  TOP
		 *  2) verticale dall'alto verso il basso     =>  RIGHT
		 *  3) orizzontale da destra verso sinistra   =>  BOTTOM
		 *  4) verticale dal basso verso l'ato        =>  LEFT
		 */

		else {

			//System.out.println("Entrato nel default");
			path.getElements().add(new MoveTo(n.getTranslateX()+11, n.getTranslateY()+12.5));
			path.getElements().add(new LineTo(mp.getLayoutX()+11,mp.getLayoutY()+12.5));


		}

		/*
		 * si crea il path transition e si esegue l'animazione con il metodo play
		 */

		pathTransition[this.numPathTransition] = new PathTransition(Duration.millis(1000), path, n);

		pathTransition[this.numPathTransition].setCycleCount(1);

		pathTransition[this.numPathTransition].play();

		pathTransition[this.numPathTransition].setOnFinished(event -> {

			String nomeCasella = tdg.getCasella(g.getPos().getX(), g.getPos().getY()).toString();

			Casella casellaCorrente = null;
			if(nomeCasella.equals("esame"))
				casellaCorrente = tdg.getCasella(g.getPos().getX(), g.getPos().getY());

			switch(nomeCasella){

			case "biblioteca":
				biblioteca();
				break;

			case "bookCafe":
				bookCafe();
				break;

			case "cus":
				cus();
				break;

			case "ricevimento":
				ricevimento();
				break;

			case "mensa":
				mensa();
				break;

			case "centroResidenziale":
				centroResidenziale();
				break;

			case "esame":
				AzioneDomanda tmp = (AzioneDomanda) casellaCorrente.getAzione();
				paneDomande.setTestiLabel(tmp,g);
				esame();
				paneDomande.avviaAnimazione();
				break;

			}
		});

	}

/*
		MyPane mp = (MyPane)getNodeByRowColumnIndex(pnuova.getY(),pnuova.getX());

		switch(g.getColor()){
		case 0:
			mp.getChildren().add(iv1);
			break;

		case 1:
			mp.getChildren().add(iv2);
		    break;
		case 2:
			mp.getChildren().add(iv3);
			break;
		case 3:
			mp.getChildren().add(iv4);
			break;
		case 4:
			mp.getChildren().add(iv5);
			break;
		case 5:
			mp.getChildren().add(iv6);
			break;
			}
*/

	/*
	 * Rimuove l'image view del giocatore g
	 */
	public void rimuoviGiocatore(Giocatore g){

		MyPane mp = (MyPane)getNodeByRowColumnIndex(0,0);

		switch(g.getColor()){
		case 0:
			mp.getChildren().remove(iv1);
			break;
		case 1:
			mp.getChildren().remove(iv2);
			break;
		case 2:
			mp.getChildren().remove(iv3);
			break;
		case 3:
			mp.getChildren().remove(iv4);
			break;
		case 4:
			mp.getChildren().remove(iv5);
			break;
		case 5:
			mp.getChildren().remove(iv6);
			break;
		}
	}

	/*
	 * Il metodo setCoordinatesSingleImageView prende in input un MyPane corrispondente alla
	 * casella dove risiede il giocatore e l'image view del giocatore e imposta le coordinate
	 * dell'image view.
	 */

	private void setCoordinatesSingleImageView(MyPane p, ImageView i){

		MyPane mp = null;

		/*
		 * se la casella dove risiede il giocatore si trova al TOP
		 */
		if(  ((p.getLayoutY()+12.5 == 12.5) && ((p.getLayoutX()+11 >= 11 && p.getLayoutX()+11 <= (p.getLayoutX()*10)+11) ) ) ) {

			// prendi la casella (0,0)
			mp = (MyPane) getNodeByRowColumnIndex(0, 0);

			// aggiungi l'mage view sulla casella (0,0)
			mp.getChildren().add(i);

			// dalla casella (0,0) trasli l'image view nella casella dove si trova il giocatore
			i.setTranslateX(p.getLayoutX());
			i.setTranslateY(p.getLayoutY());
			i.setDepthTest(DepthTest.ENABLE);

			i.setTranslateZ(-1);

		}

		/*
		 * se la casella dove risiede il giocatore si trova al RIGHT
		 */

		else if(  ((p.getLayoutX()+11 == (p.getLayoutX()*10)+11) && ((p.getLayoutY()+12.5 >= p.getHeight()+12.5 && p.getLayoutY()+12.5 <= (p.getHeight()*10)+12.5) ) ) ) {

			// prendi la casella (0,0)
			mp = (MyPane) getNodeByRowColumnIndex(0, 0);

			// aggiungi l'mage view sulla casella (0,0)
			mp.getChildren().add(i);

			// dalla casella (0,0) trasli l'image view nella casella dove si trova il giocatore
			i.setTranslateX(p.getLayoutX()+1);
			i.setTranslateY(p.getLayoutY());
			i.setDepthTest(DepthTest.ENABLE);

			i.setTranslateZ(-1);
		}

		/*
		 * se la casella dove risiede il giocatore si trova al BOTTOM
		 */

		else if(  ((p.getLayoutY()+12.5 == (p.getHeight()*10)+12.5) && ((p.getLayoutX()+11 >= 11 && p.getLayoutX()+11 <= (p.getWidth()*10)+11) ) ) ) {

			// prendi la casella (0,0)
			mp = (MyPane) getNodeByRowColumnIndex(0, 0);

			// aggiungi l'mage view sulla casella (0,0)
			mp.getChildren().add(i);

			// dalla casella (0,0) trasli l'image view nella casella dove si trova il giocatore
			i.setTranslateX(p.getLayoutX());
			i.setTranslateY(p.getLayoutY());
			i.setDepthTest(DepthTest.ENABLE);

			i.setTranslateZ(-1);
		}

		/*
		 * se la casella dove risiede il giocatore si trova al LEFT
		 */

		else {

			// prendi la casella (0,0)
			mp = (MyPane) getNodeByRowColumnIndex(0, 0);

			// aggiungi l'mage view sulla casella (0,0)
			mp.getChildren().add(i);

			// dalla casella (0,0) trasli l'image view nella casella dove si trova il giocatore
			i.setTranslateX(p.getLayoutX()+1);
			i.setTranslateY(p.getLayoutY());
			i.setDepthTest(DepthTest.ENABLE);

			i.setTranslateZ(-1);
		}

	}

	/*
	 * il metodo setCoordinatesImageView prende in input un indice di colonna e un indice
	 * di riga.
	 * Verifica se nella casella puntata da columnIndex e rowIndex si trovano uno o più
	 * giocatori; in caso affermativo vengono impostate le coordinate sulla tavola di gioco
	 * dell'image view del giocatore corrispondente invocando il metodo
	 * setCoordinatesSingleImageView
	 */

	public void setCoordinatesImageView(int columnIndex, int rowIndex){

		MyPane p = null;
		for(int x = 0; x < gm.getGestore().size(); x++){
			Giocatore g = gm.getGestore().getGiocatore(x);
			Posizione pos = convertiPosizione(g.getPos());
			if(pos.getY() == rowIndex && pos.getX() == columnIndex){
				p = (MyPane) getNodeByRowColumnIndex(rowIndex, columnIndex);

				switch(g.getColor()){
				case 0:

					setCoordinatesSingleImageView(p, iv1);

					break;
				case 1:

					setCoordinatesSingleImageView(p, iv2);

					break;
				case 2:

					setCoordinatesSingleImageView(p, iv3);

					break;
				case 3:

					setCoordinatesSingleImageView(p, iv4);

					break;
				case 4:

					setCoordinatesSingleImageView(p, iv5);

					break;
				case 5:

					setCoordinatesSingleImageView(p, iv6);

					break;
				}

			}
		}

	}

	private void stampaCoordinateBoard(){
		board[3][9] = (MyPane)getNodeByRowColumnIndex(0,1);
		board[3][8] = (MyPane)getNodeByRowColumnIndex(0,2);
		board[3][7] = (MyPane)getNodeByRowColumnIndex(0,3);
		board[3][6] = (MyPane)getNodeByRowColumnIndex(0,4);
		board[3][5] = (MyPane)getNodeByRowColumnIndex(0,5);
		board[3][4] = (MyPane)getNodeByRowColumnIndex(0,6);
		board[3][3] = (MyPane)getNodeByRowColumnIndex(0,7);
		board[3][2] = (MyPane)getNodeByRowColumnIndex(0,8);
		board[3][1] = (MyPane)getNodeByRowColumnIndex(0,9);
		board[3][0] = (MyPane)getNodeByRowColumnIndex(0,10);

		board[2][9] = (MyPane)getNodeByRowColumnIndex(1,10);
		board[2][8] = (MyPane)getNodeByRowColumnIndex(2,10);
		board[2][7] = (MyPane)getNodeByRowColumnIndex(3,10);
		board[2][6] = (MyPane)getNodeByRowColumnIndex(4,10);
		board[2][5] = (MyPane)getNodeByRowColumnIndex(5,10);
		board[2][4] = (MyPane)getNodeByRowColumnIndex(6,10);
		board[2][3] = (MyPane)getNodeByRowColumnIndex(7,10);
		board[2][2] = (MyPane)getNodeByRowColumnIndex(8,10);
		board[2][1] = (MyPane)getNodeByRowColumnIndex(9,10);
		board[2][0] = (MyPane)getNodeByRowColumnIndex(10,10);

		board[1][9] = (MyPane)getNodeByRowColumnIndex(10,9);
		board[1][8] = (MyPane)getNodeByRowColumnIndex(10,8);
		board[1][7] = (MyPane)getNodeByRowColumnIndex(10,7);
		board[1][6] = (MyPane)getNodeByRowColumnIndex(10,6);
		board[1][5] = (MyPane)getNodeByRowColumnIndex(10,5);
		board[1][4] = (MyPane)getNodeByRowColumnIndex(10,4);
		board[1][3] = (MyPane)getNodeByRowColumnIndex(10,3);
		board[1][2] = (MyPane)getNodeByRowColumnIndex(10,2);
		board[1][1] = (MyPane)getNodeByRowColumnIndex(10,1);
		board[1][0] = (MyPane)getNodeByRowColumnIndex(10,0);

		board[0][9] = (MyPane)getNodeByRowColumnIndex(9,0);
		board[0][8] = (MyPane)getNodeByRowColumnIndex(8,0);
		board[0][7] = (MyPane)getNodeByRowColumnIndex(7,0);
		board[0][6] = (MyPane)getNodeByRowColumnIndex(6,0);
		board[0][5] = (MyPane)getNodeByRowColumnIndex(5,0);
		board[0][4] = (MyPane)getNodeByRowColumnIndex(4,0);
		board[0][3] = (MyPane)getNodeByRowColumnIndex(3,0);
		board[0][2] = (MyPane)getNodeByRowColumnIndex(2,0);
		board[0][1] = (MyPane)getNodeByRowColumnIndex(1,0);
		board[0][0] = (MyPane)getNodeByRowColumnIndex(0,0);

		int count = 40;
		for(int i = 0; i < board.length; i++)
	        	for(int j = 0; j < board[0].length; j++)
	        		if(i==3) {}
	        			//System.out.println("Casella " + count-- + " con coordinate " + board[i][j].getLayoutX() + " " + board[i][j].getLayoutY());

	}

	/*
	 * Restituisce il MyPane indicizzato da row e column
	 */
	public Node getNodeByRowColumnIndex(final int row,final int column) {
        Node result = null;
        ObservableList<Node> childrens = getChildren();
        int i = 0;
        for(Node node : childrens) {
        	if(i == 0){
        		i++;
        		continue;
        	}
            if(getRowIndex(node) == row && getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

	private void disegnaCornici(int numCols) {
		//Top
		for ( int i = 0; i < numCols - 1; i++){
			add(makePanel(), i, 0);
		}

		//Right
		for ( int i = 0; i < numCols - 1; i++){
			add(makePanel(), numCols - 1, i);
		}

		//Bottom
		for ( int i = numCols - 1; i > 0; i--){
			add(makePanel(), i, numCols - 1);
		}

		//Left
		for ( int i = numCols - 1; i > 0; i--){
			add(makePanel(), 0, i);
		}
	}

	public MyPane getMyPane(Giocatore g){
		return (MyPane) getChildren().get(0);
	}

	/*
	 * Costruisce la tavola da gioco
	 */
	public PannelloTavola(GameManagerAstratta gm) throws SQLException {

		super();

		this.gm = gm;
		rispostaUtente = "";
		tdg = gm.getTavolaDiGioco();
		tdg.setNomeConfigurazione(gm, gm.getNomeConfigurazione());

		riga = colonna = 0;

		mappa.put("mensa", new Image("file:caselle/mensa.jpg"));
		mappa.put("biblioteca", new Image("file:caselle/biblioteca.png"));
		mappa.put("bookCafe", new Image("file:caselle/bookcafe.png"));
		mappa.put("centroResidenziale", new Image("file:caselle/centro.jpg"));
		mappa.put("cus", new Image("file:caselle/cus.jpg"));
		mappa.put("esame", new Image("file:caselle/esame.png"));
		mappa.put("ricevimento", new Image("file:caselle/ricevimento.png"));
		mappa.put("semplice", new Image("file:caselle/strada.png"));

		setGridLinesVisible(true);
//		setPrefSize(500, 200);
//		setMaxSize(500, 200);

		for (int i = 0; i < NUM_COLS; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / NUM_COLS);
            getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < NUM_ROWS; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / NUM_ROWS);
            getRowConstraints().add(rowConst);
        }

        paneLogo = new PaneLogo();
		paneBiblioteca = new PaneBiblioteca();
		paneDomande = new PaneDomande();
		paneRicevimento = new PaneRicevimento();
		paneCentroR = new PaneCR();
		paneMensa = new PaneMensa();

        //disegno le cornici
        disegnaCornici(NUM_COLS);

        this.setPrefSize(880, 660);
        this.setMaxSize(880,660);
        this.setMinSize(880, 660);


        this.pathTransition = new PathTransition[6];

    }

	public int getNUM_COLS() {
		return NUM_COLS;
	}

	public int getNUM_ROWS() {
		return NUM_ROWS;
	}

	/*
	 * Converte la posizione del giocatore in una posizione nella tavola da gioco
	 */
	private Posizione convertiPosizione(Posizione p){
		if(p.getX() == 0 && p.getY() == 0) return new Posizione(0,0);
		else if(p.getX() == 0 && p.getY() == 1) return new Posizione(1,0);
		else if(p.getX() == 0 && p.getY() == 2) return new Posizione(2,0);
		else if(p.getX() == 0 && p.getY() == 3) return new Posizione(3,0);
		else if(p.getX() == 0 && p.getY() == 4) return new Posizione(4,0);
		else if(p.getX() == 0 && p.getY() == 5) return new Posizione(5,0);
		else if(p.getX() == 0 && p.getY() == 6) return new Posizione(6,0);
		else if(p.getX() == 0 && p.getY() == 7) return new Posizione(7,0);
		else if(p.getX() == 0 && p.getY() == 8) return new Posizione(8,0);
		else if(p.getX() == 0 && p.getY() == 9) return new Posizione(9,0);

		else if(p.getX() == 1 && p.getY() == 0) return new Posizione(NUM_COLS-1,0);
		else if(p.getX() == 1 && p.getY() == 1) return new Posizione(NUM_COLS-1,1);
		else if(p.getX() == 1 && p.getY() == 2) return new Posizione(NUM_COLS-1,2);
		else if(p.getX() == 1 && p.getY() == 3) return new Posizione(NUM_COLS-1,3);
		else if(p.getX() == 1 && p.getY() == 4) return new Posizione(NUM_COLS-1,4);
		else if(p.getX() == 1 && p.getY() == 5) return new Posizione(NUM_COLS-1,5);
		else if(p.getX() == 1 && p.getY() == 6) return new Posizione(NUM_COLS-1,6);
		else if(p.getX() == 1 && p.getY() == 7) return new Posizione(NUM_COLS-1,7);
		else if(p.getX() == 1 && p.getY() == 8) return new Posizione(NUM_COLS-1,8);
		else if(p.getX() == 1 && p.getY() == 9) return new Posizione(NUM_COLS-1,9);

		else if(p.getX() == 2 && p.getY() == 0) return new Posizione(10,NUM_COLS-1);
		else if(p.getX() == 2 && p.getY() == 1) return new Posizione(9,NUM_COLS-1);
		else if(p.getX() == 2 && p.getY() == 2) return new Posizione(8,NUM_COLS-1);
		else if(p.getX() == 2 && p.getY() == 3) return new Posizione(7,NUM_COLS-1);
		else if(p.getX() == 2 && p.getY() == 4) return new Posizione(6,NUM_COLS-1);
		else if(p.getX() == 2 && p.getY() == 5) return new Posizione(5,NUM_COLS-1);
		else if(p.getX() == 2 && p.getY() == 6) return new Posizione(4,NUM_COLS-1);
		else if(p.getX() == 2 && p.getY() == 7) return new Posizione(3,NUM_COLS-1);
		else if(p.getX() == 2 && p.getY() == 8) return new Posizione(2,NUM_COLS-1);
		else if(p.getX() == 2 && p.getY() == 9) return new Posizione(1,NUM_COLS-1);

		else if(p.getX() == 3 && p.getY() == 0) return new Posizione(0,10);
		else if(p.getX() == 3 && p.getY() == 1) return new Posizione(0,9);
		else if(p.getX() == 3 && p.getY() == 2) return new Posizione(0,8);
		else if(p.getX() == 3 && p.getY() == 3) return new Posizione(0,7);
		else if(p.getX() == 3 && p.getY() == 4) return new Posizione(0,6);
		else if(p.getX() == 3 && p.getY() == 5) return new Posizione(0,5);
		else if(p.getX() == 3 && p.getY() == 6) return new Posizione(0,4);
		else if(p.getX() == 3 && p.getY() == 7) return new Posizione(0,3);
		else if(p.getX() == 3 && p.getY() == 8) return new Posizione(0,2);
		return new Posizione(0,1);
	}

	@Override
	public void logo() {
		SchermataTavolaDiGioco.switchTo(paneLogo);
	}

	@Override
	public void ricevimento() {
		SchermataTavolaDiGioco.switchTo(paneRicevimento);
	}

	@Override
	public void mensa() {
		SchermataTavolaDiGioco.switchTo(paneMensa);
	}

	@Override
	public void cus() {
		//TO-DO
		SchermataTavolaDiGioco.switchTo(paneBiblioteca);
	}

	@Override
	public void bookCafe() {
		//TO-DO
		SchermataTavolaDiGioco.switchTo(paneBiblioteca);
	}

	@Override
	public void biblioteca() {
		//TO-DO
		SchermataTavolaDiGioco.switchTo(paneBiblioteca);
	}

	@Override
	public void centroResidenziale() {
		//TO-DO fare il pannello centro residenziale
		SchermataTavolaDiGioco.switchTo(paneCentroR);
	}

	@Override
	public void esame() {
		paneDomande.resetta();
		SchermataTavolaDiGioco.switchTo(paneDomande);
	}

}