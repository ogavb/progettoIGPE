package gui.panels;

import core.Dado;
import core.GameManager;
import core.GameManagerAstratta;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import networking.GameManagerNetwork;

public class PannelloDado extends GridPane {

	private GameManagerAstratta gm;

	private Image imgDadoUno;
	private ImageView imageViewUno;

	private int numGiocatori;
	private static String primo;

	private DadiListener eventoDado;


	public PannelloDado(Integer i1,GameManagerAstratta gm) {

		this.gm = gm;
		numGiocatori = 0;

		if (i1 != 0) primo = i1.toString(); else primo = "roll";

		setMinWidth(GridPane.USE_COMPUTED_SIZE);
		setMinHeight(GridPane.USE_COMPUTED_SIZE);
		setPrefWidth(GridPane.USE_COMPUTED_SIZE);
		setPrefHeight(GridPane.USE_COMPUTED_SIZE);
		setMaxWidth(GridPane.USE_COMPUTED_SIZE);
		setMaxHeight(GridPane.USE_COMPUTED_SIZE);

		imgDadoUno = new Image("file:dadi/" + primo + ".gif");
		imageViewUno = new ImageView(imgDadoUno);

		ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(25);
	    getColumnConstraints().addAll(column1);

		add(imageViewUno, 1, 0);
		imageViewUno.setTranslateZ(-1);
		eventoDado = new DadiListener();
		imageViewUno.addEventHandler(MouseEvent.MOUSE_RELEASED, eventoDado);
	}


	void setNumGiocatori(int numG){
		numGiocatori = numG;
	}

	void setPrimo(int risultato) {
		imgDadoUno = new Image("file:dadi/" + risultato + ".gif");
		imageViewUno = new ImageView(imgDadoUno);
		imageViewUno.addEventHandler(MouseEvent.MOUSE_RELEASED, eventoDado);
		imageViewUno.setTranslateZ(-1);
		this.getChildren().clear();
		add(imageViewUno, 1, 0);
	}

	public void animazione(int lancioCorrente){

		if(numGiocatori != 1 && numGiocatori != 0){

			setPrimo(( int )( ( Math.random() * 6 ) + 1 ));
			RotateTransition rt1 = new RotateTransition(Duration.millis(60), imageViewUno);

			rt1.setFromAngle(0.0);
			rt1.setToAngle(360.0);
			rt1.setCycleCount(2);
			rt1.setAutoReverse(true);
			rt1.setAxis(Rotate.X_AXIS);

			rt1.play();

			rt1.setOnFinished(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					setPrimo(( int )( ( Math.random() * 6 ) + 1 ));
					RotateTransition rt2 = new RotateTransition(Duration.millis(60), imageViewUno);

					rt2.setFromAngle(0.0);
					rt2.setToAngle(360.0);
					rt2.setCycleCount(2);
					rt2.setAutoReverse(true);
					rt2.setAxis(Rotate.X_AXIS);

					rt2.play();

					rt2.setOnFinished(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {

							setPrimo(( int )( ( Math.random() * 6 ) + 1 ));
							RotateTransition rt3 = new RotateTransition(Duration.millis(60), imageViewUno);

							rt3.setFromAngle(0.0);
							rt3.setToAngle(360.0);
							rt3.setCycleCount(2);
							rt3.setAutoReverse(true);
							rt3.setAxis(Rotate.Y_AXIS);

							rt3.play();

							rt3.setOnFinished(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent event) {

									setPrimo(( int )( ( Math.random() * 6 ) + 1 ));
									RotateTransition rt4 = new RotateTransition(Duration.millis(60), imageViewUno);

									rt4.setFromAngle(0.0);
									rt4.setToAngle(360.0);
									rt4.setCycleCount(2);
									rt4.setAutoReverse(true);
									rt4.setAxis(Rotate.Y_AXIS);

									rt4.play();

									rt4.setOnFinished(new EventHandler<ActionEvent>() {

										@Override
										public void handle(ActionEvent event) {

											setPrimo(( int )( ( Math.random() * 6 ) + 1 ));
											RotateTransition rt5 = new RotateTransition(Duration.millis(60), imageViewUno);

											rt5.setFromAngle(0.0);
											rt5.setToAngle(360.0);
											rt5.setCycleCount(2);
											rt5.setAutoReverse(true);
											rt5.setAxis(Rotate.Z_AXIS);

											rt5.play();

											rt5.setOnFinished(new EventHandler<ActionEvent>() {

												@Override
												public void handle(ActionEvent event) {

													setPrimo(( int )( ( Math.random() * 6 ) + 1 ));
													RotateTransition rt6 = new RotateTransition(Duration.millis(60), imageViewUno);

													rt6.setFromAngle(0.0);
													rt6.setToAngle(360.0);
													rt6.setCycleCount(2);
													rt6.setAutoReverse(true);
													rt6.setAxis(Rotate.Z_AXIS);

													rt6.play();

													rt6.setOnFinished(new EventHandler<ActionEvent>() {

														@Override
														public void handle(ActionEvent event) {

															setPrimo(lancioCorrente);

														}
													});

												}
											});

										}
									});

								}
							});


						}
					});

				}
			});

		}

	}

	private class DadiListener implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent event) {

			if(gm instanceof GameManagerNetwork && ((GameManagerNetwork)gm).isYourRound()){
				System.err.println("PUOI TIRARE IL DADO");
				int lancioCorrente = Dado.lanciaDadi();

				animazione(lancioCorrente);
				numGiocatori = gm.turnoSuccessivo(numGiocatori, lancioCorrente);
				if(numGiocatori==1)
					gm.finePartita();
			}

			else if(gm instanceof GameManager){
				int lancioCorrente = Dado.lanciaDadi();

				animazione(lancioCorrente);
				numGiocatori = gm.turnoSuccessivo(numGiocatori,lancioCorrente);
				if(numGiocatori == 1)
					gm.finePartita();
			}
		}

	}
}
