package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import gui.editor.EditorDomande;
import gui.editor.EditorTavolaDiGioco;
import gui.panels.SceltaEditor;
import gui.panels.SchermataNuovaPartita;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainController extends Pane implements Initializable{
    final private String IMG_FOLDER = "img/";
    final private String IMG_EXTENTION = ".png";

    private Stage stage;

    @FXML private ImageView background;
    @FXML private Group rectangleGroup;
    @FXML private Rectangle rectangle;
    @FXML private ImageView title;

    @FXML private ImageView newGameContainer;
    @FXML private ImageView loadGameContainer;
    @FXML private ImageView onlineGameContainer;
    @FXML private ImageView editorContainer;
    @FXML private ImageView optionContainer;
    @FXML private ImageView creditsContainer;
    @FXML private ImageView exitContainer;

    private Image newGame;
    private Image loadGame;
    private Image onlineGame;
    private Image editor;
    private Image option;
    private Image credits;
    private Image exit;

    private Image newGameEffect;
    private Image loadGameEffect;
    private Image onlineGameEffect;
    private Image editorEffect;
    private Image optionEffect;
    private Image creditsEffect;
    private Image exitEffect;

    public MainController(Stage stage) {
    	this.stage = stage;

    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
    	fxmlLoader.setRoot(this);
    	fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        background.setPreserveRatio(true);
        stage.heightProperty().addListener((event) -> {

        });
        background.fitWidthProperty().bind(stage.widthProperty());
        background.fitHeightProperty().bind(stage.heightProperty());

//        rectangleGroup.setClip(new Rectangle());

   //     title.setLayoutX(value);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newGame = new Image(IMG_FOLDER + "Nuova Partita" + IMG_EXTENTION);
        newGameEffect = new Image(IMG_FOLDER + "Nuova Partita Effect" + IMG_EXTENTION);
        loadGame = new Image(IMG_FOLDER + "Carica Partita" + IMG_EXTENTION);
        loadGameEffect = new Image(IMG_FOLDER + "Carica Partita Effect" + IMG_EXTENTION);
        onlineGame = new Image(IMG_FOLDER + "Partita Online" + IMG_EXTENTION);
        onlineGameEffect = new Image(IMG_FOLDER + "Partita Online Effect" + IMG_EXTENTION);
        editor = new Image(IMG_FOLDER + "Editor" + IMG_EXTENTION);
        editorEffect = new Image(IMG_FOLDER + "Editor Effect" + IMG_EXTENTION);
        option = new Image(IMG_FOLDER + "Opzioni" + IMG_EXTENTION);
        optionEffect = new Image(IMG_FOLDER + "Opzioni Effect" + IMG_EXTENTION);
        credits = new Image(IMG_FOLDER + "Crediti" + IMG_EXTENTION);
        creditsEffect = new Image(IMG_FOLDER + "Crediti Effect" + IMG_EXTENTION);
        exit = new Image(IMG_FOLDER + "Esci" + IMG_EXTENTION);
        exitEffect = new Image(IMG_FOLDER + "Esci Effect" + IMG_EXTENTION);
    }

    @FXML
    private void newGame(MouseEvent e) {

    	System.out.println("Sono entrato in nuova partita");
		new SchermataNuovaPartita(stage);
    }

    @FXML
    private void loadGame(MouseEvent e){
        //TODO
    }

    @FXML
    private void onlineGame(MouseEvent e){
        //TODO
    }

    @FXML
    private void editor(MouseEvent e) throws SQLException{
    	new EditorDomande();
    }

    @FXML
    private void option(MouseEvent e){
    	//TODO

        Stage stage = (Stage)((HBox)e.getSource()).getScene().getWindow();

        if(stage.isFullScreen()){
            stage.setFullScreen(false);
        }
        else{
            stage.setFullScreen(true);
        }
    }

    @FXML
    private void credits(MouseEvent e){
        //TODO
    }

    @FXML
    private void exit(MouseEvent e){
        System.exit(0);
    }

    @FXML private void enterMouse(MouseEvent e){
        HBox h = ((HBox)e.getSource());
        h.setPadding(new Insets(0, 0, 0, 20));

        ImageView iv = (ImageView)h.getChildrenUnmodifiable().get(0);

        switch (iv.getId()) {
        case "newGameContainer":
            iv.setImage(newGameEffect);
            break;
        case "loadGameContainer":
            iv.setImage(loadGameEffect);
            break;
        case "onlineGameContainer":
            iv.setImage(onlineGameEffect);
            break;
        case "editorContainer":
            iv.setImage(editorEffect);
            break;
        case "optionContainer":
            iv.setImage(optionEffect);
            break;
        case "creditsContainer":
            iv.setImage(creditsEffect);
            break;
        case "exitContainer":
            iv.setImage(exitEffect);
            break;

        default:
            System.out.println("Problema switch enterMouse");
            break;
        }
    }

    @FXML private void exitMouse(MouseEvent e){
        HBox h = ((HBox)e.getSource());
        h.setPadding(new Insets(0, 0, 0, 0));

        ImageView iv = (ImageView)h.getChildrenUnmodifiable().get(0);

        switch (iv.getId()) {
        case "newGameContainer":
            iv.setImage(newGame);
            break;
        case "loadGameContainer":
            iv.setImage(loadGame);
            break;
        case "onlineGameContainer":
            iv.setImage(onlineGame);
            break;
        case "editorContainer":
            iv.setImage(editor);
            break;
        case "optionContainer":
            iv.setImage(option);
            break;
        case "creditsContainer":
            iv.setImage(credits);
            break;
        case "exitContainer":
            iv.setImage(exit);
            break;

        default:
            System.out.println("Problema switch exitMouse");
            break;
        }
    }

    @FXML
    private void pressMouse(MouseEvent e){
        //TODO
    }

    @FXML
    private void releaseMouse(MouseEvent e){
        //TODO
    }

    @FXML
    private void onKeyTyped(KeyEvent k){
        //TODO
    }

}