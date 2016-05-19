package gui.panels;

import java.sql.SQLException;

import core.Giocatore;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class TavolaGridPane extends GridPane{

	private int NUM_COLS;
	private int  NUM_ROWS;

	protected int riga;
	protected int colonna;

	public TavolaGridPane(int NUM_COLS, int NUM_ROWS) throws SQLException {

		super();

		riga = colonna = 0;
		this.setNUM_COLS(NUM_COLS);
		this.setNUM_ROWS(NUM_ROWS);

		setGridLinesVisible(true);;
		setPrefSize(500, 200);
		setMaxSize(500, 200);

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

        //disegno le cornici
        disegnaCornici(NUM_COLS);

        this.setPrefSize(600, 400);

	}



	/*
	 * Costruisce un MyPane vuoto
	 */
	public MyPane makePanelEmpty() {
		int i = (int) (Math.random()*5)+1;
		MyPane p = new MyPane();

		setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				System.out.println(event.getX() + " " + event.getY());
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
			add(makePanelEmpty(), i, 0);
		}

		//Right
		for ( int i = 0; i < numCols - 1; i++){
			add(makePanelEmpty(), numCols - 1, i);
		}

		//Bottom
		for ( int i = numCols - 1; i > 0; i--){
			add(makePanelEmpty(), i, numCols - 1);
		}

		//Left
		for ( int i = numCols - 1; i > 0; i--){
			add(makePanelEmpty(), 0, i);
		}
	}

	public MyPane getMyPane(Giocatore g){
		return (MyPane) getChildren().get(0);
	}



	public int getNUM_COLS() {
		return NUM_COLS;
	}



	public void setNUM_COLS(int nUM_COLS) {
		NUM_COLS = nUM_COLS;
	}



	public int getNUM_ROWS() {
		return NUM_ROWS;
	}



	public void setNUM_ROWS(int nUM_ROWS) {
		NUM_ROWS = nUM_ROWS;
	}

}
