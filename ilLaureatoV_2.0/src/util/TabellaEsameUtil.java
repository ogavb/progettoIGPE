package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import jdbc.PrelevatoreDomanda;
import model.Esame;

public class TabellaEsameUtil {

	public static ObservableList<Esame> getListaEsami(){

		PrelevatoreDomanda database = new PrelevatoreDomanda();
		ObservableList<Esame> listaEsami = database.prelevaEsami();

		return FXCollections.<Esame>observableArrayList(listaEsami);

	}

	public static TableColumn<Esame, String> getColonnaNome(){

		TableColumn<Esame, String> nomeEsame = new TableColumn<>("Nome");
		nomeEsame.setCellValueFactory(new PropertyValueFactory<>("nome"));
		return nomeEsame;

	}

	public static TableColumn<Esame, Integer> getColonnaCrediti(){

		TableColumn<Esame, Integer> creditiEsame = new TableColumn<>("Crediti");
		creditiEsame.setCellValueFactory(new PropertyValueFactory<>("crediti"));
		return creditiEsame;

	}

	public static TableColumn<Esame, String> getColonnaDomanda(){

		TableColumn<Esame, String> domandaEsame = new TableColumn<>("Domanda");
		domandaEsame.setCellValueFactory(new PropertyValueFactory<>("domanda"));
		return domandaEsame;

	}

	public static TableColumn<Esame, String> getColonnaRispostaEsatta(){

		TableColumn<Esame, String> rispostaEsattaEsame = new TableColumn<>("RispostaEsatta");
		rispostaEsattaEsame.setCellValueFactory(new PropertyValueFactory<>("rispostaEsatta"));
		return rispostaEsattaEsame;

	}

	public static TableColumn<Esame, String> getColonnaRispostaSbagliata(){

		TableColumn<Esame, String> rispostaSbagliataEsame = new TableColumn<>("RispostaSbagliata");
		rispostaSbagliataEsame.setCellValueFactory(new PropertyValueFactory<>("rispostaSbagliata"));
		return rispostaSbagliataEsame;

	}

}
