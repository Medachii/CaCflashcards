package eu.telecomnancy.application;

import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.layout.Pane;
import java.util.ArrayList;


/**
 * Controller for the "Creating and modifying" part.
 */
public class CreateController implements Observer {

    private StackOfCards stack;

    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private TextField question;
    @FXML
    private Button delete;
    @FXML
    private TextField answer;
    @FXML
    private TextField newtitle;
    @FXML
    private TextArea newdesc;
    @FXML
    private Label notif;
    @FXML
    private Label nbPage;
    @FXML
    private Pane mediaContainer;
    @FXML
    private Menu menuPage;
    @FXML
    private Menu importcool;

    public CreateController(StackOfCards pile) {
        this.stack = pile;
        this.stack.addObserver(this);
    }

    /**
     * Initializes the view.
     */
    public void initialize() {
        this.update();
    }

    /**
     * Updates the view.
     */
    public void update() {
        if (stack.getSceneFlag() == 2) {
            if (mediaContainer.getChildren() instanceof MediaView) {
                ((MediaView) mediaContainer.getChildren().get(0)).getMediaPlayer().stop();
            }
            mediaContainer.getChildren().clear();
            ImageView imageSuppr = new ImageView(new Image(getClass().getResource("/croix.png").toString()));
            imageSuppr.setFitWidth(20);
            imageSuppr.setFitHeight(20);
            this.delete.setGraphic(imageSuppr);
            this.menuPage.getItems().clear();
            for (int i = 0; i < stack.getCards().size(); i++) {
                MenuItem newitem = new MenuItem((i + 1) + "");
                newitem.setOnAction(e -> {
                    stack.setCurrentCard(Integer.parseInt(newitem.getText()) - 1);
                    notif.setText("");
                });
                menuPage.getItems().add(newitem);
            }
            this.importcool.getItems().clear();
            ArrayList<String> list = stack.getAllStackNames();
            for (int i = 0; i < list.size(); i++) {
                MenuItem newitem = new MenuItem(list.get(i));
                newitem.setOnAction(e -> {
                    stack.importStack(newitem.getText());
                    notif.setText("");
                });
                importcool.getItems().add(newitem);
            }

            // we check if the extension is a video or an image

            if (stack.getCurrentCard().getMedia() != null && stack.getCurrentCard().getMedia().length() > 0) {
                if (stack.getCurrentCard().getMedia().endsWith(".mp4")) {
                    MediaView mediaView = new MediaView();

                    mediaView.setMediaPlayer(
                            new MediaPlayer(new Media(new File(stack.getCurrentCard().getMedia()).toURI().toString())));
                    mediaView.setFitWidth(257);
                    mediaView.setFitHeight(181);
                    mediaView.setPreserveRatio(true);
                    mediaContainer.getChildren().add(mediaView);
                    // play the video
                    // create a button to play the video
                    Button playstop = new Button("Play / Stop");
                    // put the button in 610 198
                    playstop.setLayoutX(230);
                    playstop.setLayoutY(60);

                    playstop.setOnAction(e -> {
                        if (mediaView.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
                            mediaView.getMediaPlayer().stop();
                        } else {
                            mediaView.getMediaPlayer().play();
                        }

                    });
                    mediaContainer.getChildren().add(playstop);

                } else if (stack.getCurrentCard().getMedia().endsWith(".mp3")) {
                    ImageView audio = new ImageView(new Image(getClass().getResource("/audio.png").toString()));
                    audio.setFitWidth(257);
                    audio.setFitHeight(181);
                    audio.setPreserveRatio(true);
                    mediaContainer.getChildren().add(audio);
                    MediaView mediaView = new MediaView();
                    mediaView.setMediaPlayer(
                            new MediaPlayer(new Media(new File(stack.getCurrentCard().getMedia()).toURI().toString())));
                    mediaView.setFitWidth(257);
                    mediaView.setFitHeight(181);
                    mediaContainer.getChildren().add(mediaView);
                    // play the sound
                    mediaView.getMediaPlayer().play();
                    Button playstop = new Button("Play / Stop");
                    // put the button in 610 198
                    playstop.setLayoutX(230);
                    playstop.setLayoutY(60);

                    playstop.setOnAction(e -> {
                        if (mediaView.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
                            mediaView.getMediaPlayer().stop();
                        } else {
                            mediaView.getMediaPlayer().play();
                        }
                    });
                    mediaContainer.getChildren().add(playstop);
                } else {
                    ImageView image = new ImageView(
                            new Image(new File(stack.getCurrentCard().getMedia()).toURI().toString()));
                    image.setFitWidth(257);
                    image.setFitHeight(181);
                    image.setPreserveRatio(true);
                    mediaContainer.getChildren().add(image);
                }
            }
            /*
             * else{
             * ImageView image = new ImageView(new
             * Image(getClass().getResource("/image.png").toString()));
             * image.setFitWidth(400);
             * image.setFitHeight(300);
             * mediaContainer.getChildren().add(image);
             * }
             */

            title.setText(stack.getStackName());
            this.question.setText(stack.getCurrentCard().getQuestion());
            this.answer.setText(stack.getCurrentCard().getAnswer());
            description.setText(stack.getDesc());
            this.nbPage.setText((stack.getIndex() + 1) + "/" + stack.getCards().size());

        }

    }

    /**
     * Creates a new stack.
     */
    @FXML
    public void createstack(ActionEvent event) {
        stack.setStackName("Nouvelle Pile");
        stack.setDesc("Nouvelle Pile");
        Stack<Card> cards = new Stack<Card>();
        cards.push(new Card());
        stack.setCards(cards);
        this.notif.setText("Nouvelle pile créée");
        stack.setCurrentCard(0);
    }

    /**
     * Deletes the stack file chosen by the user.
     */
    @FXML
    public void deletestack(ActionEvent event) {
        String homepath = System.getProperty("user.home");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");
        File d = new File(homepath + "/codingandchill/data/");
        fileChooser.setInitialDirectory(d);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichier JSON", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            file.delete();
            this.notif.setText("Pile supprimée");
        } else {
            this.notif.setText("Aucune pile trouvée");
        }
    }

    /**
     * Changes the question of the current card.
     */
    @FXML
    public void changeQuestion(ActionEvent event) {
        stack.getCurrentCard().setQuestion(question.getText());
        this.notif.setText("Question modifiée");
    }

    /**
     * Changes the answer of the current card.
     */
    @FXML
    public void changeAnswer(ActionEvent event) {
        stack.getCurrentCard().setAnswer(answer.getText());
        this.notif.setText("Réponse modifiée");

    }

    /**
     * Changes the current card for the next one.
     */
    @FXML
    public void nextCard(ActionEvent event) {
        stack.nextCard();
        this.notif.setText("");
    }

    /**
     * Changes the current card for the previous one.
     */
    @FXML
    public void prevCard(ActionEvent event) {
        stack.previousCard();
        this.notif.setText("");
    }

    /**
     * Adds a card to the stack of cards.
     */
    @FXML
    public void addCard(ActionEvent event) {
        stack.addCard();
        this.notif.setText("Carte ajoutée");
    }

    /**
     * Deletes the current card.
     */
    @FXML
    public void deleteCard(ActionEvent event) {
        stack.deleteCard(stack.getIndex());
        this.notif.setText("Carte supprimée");
    }

    /**
     * Saves the stack of cards.
     */
    @FXML
    public void save(ActionEvent event) {
        stack.save();
        this.notif.setText("Pile sauvegardée");
    }

    /**
     * Go to the "Learning" view.
     */
    @FXML
    public void toLearn(ActionEvent event) throws Exception {
        stack.switchtoLearnView();
    }

    /**
     * Go to the "Statistics" view.
     */
    @FXML
    public void toStat(ActionEvent event) throws Exception {
        stack.switchtoStatView();
    }

    /**
     * Go to the "QCM" view.
     */

    @FXML
    public void toQCM(ActionEvent event) throws Exception {
        stack.switchtoQCMView();
    }

    /**
     * Go to the "Fill in the blank" view.
     */
    @FXML
    public void toFillBlank(ActionEvent event) throws Exception {
        stack.switchtoFillBlankView();
    }

    /**
     * Exit the application.
     */
    @FXML
    public void exitApp(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Change the title of the stack of cards.
     */
    @FXML
    public void changeTitle(ActionEvent event) {

        if (newtitle.getText().equals("")) {
            this.notif.setText("Entrez un titre valide");
        }
        if (newtitle.getText().contains(".")) {
            this.notif.setText("Le titre ne peut pas contenir de points");
        } else {
            stack.setStackName(newtitle.getText());
            this.notif.setText("Titre modifié");
        }

    }

    /**
     * Change the description of the stack of cards.
     */
    @FXML
    public void changeDesc(ActionEvent event) {
        stack.setDesc(newdesc.getText());
        this.notif.setText("Description modifiée");
    }

    /**
     * Change the media of the current card.
     */
    @FXML
    public void setCardMedia() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Médias", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.mp3", "*.mp4")

        );
        File file = fileChooser.showOpenDialog(null);

        // get the file name and extension

        if (file != null) {
            // get only the filename with the extension

            stack.getCurrentCard().setMedia(file.getAbsolutePath());
            this.notif.setText("Média ajouté");
        } else {
            this.notif.setText("Aucun média trouvé");
        }
        this.stack.notifyObserver();
    }

    /**
     * Deletes the media of the current card.
     */
    @FXML
    public void deleteCardMedia() {
        stack.getCurrentCard().setMedia(null);
        this.notif.setText("Média supprimé");
        this.stack.notifyObserver();
    }
}
