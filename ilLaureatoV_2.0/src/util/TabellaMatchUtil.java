package util;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MatchTableView;
import networking.GestoreMatch;
import networking.Match;

public class TabellaMatchUtil {

	public static ObservableList<MatchTableView> getListaMatch(){

		List<Match> tmp = GestoreMatch.getInstance().getMatchs();

		ObservableList<MatchTableView> listaMatch = FXCollections.observableArrayList();

		for (Match m : tmp){
			MatchTableView match = new MatchTableView(m.getIdMatch(), m.getNumberPlayer(), m.size());
			listaMatch.add(match);
		}

		return FXCollections.<MatchTableView>observableArrayList(listaMatch);

	}

	public static TableColumn<MatchTableView, Integer> getColonnaIDMatch(){

		TableColumn<MatchTableView, Integer> idMatch = new TableColumn<>("IDMatch");
		idMatch.setCellValueFactory(new PropertyValueFactory<>("idMatch"));
		return idMatch;

	}

	public static TableColumn<MatchTableView, Integer> getColonnaNumeroGiocatori(){

		TableColumn<MatchTableView, Integer> numGiocatori = new TableColumn<>("NumeroGiocatori");
		numGiocatori.setCellValueFactory(new PropertyValueFactory<>("numeroGiocatori"));
		return numGiocatori;

	}

	public static TableColumn<MatchTableView, Integer> getColonnaGiocatoriCorrenti(){

		TableColumn<MatchTableView, Integer> numeroGiocatoriCorrenti = new TableColumn<>("GiocatoriCorrenti");
		numeroGiocatoriCorrenti.setCellValueFactory(new PropertyValueFactory<>("giocatoriCorrenti"));
		return numeroGiocatoriCorrenti;

	}
}
