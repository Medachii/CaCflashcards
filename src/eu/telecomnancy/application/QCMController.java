package eu.telecomnancy.application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


public class QCMController implements Observer {

    private StackOfCards stack;

    @FXML
    private Label title;
    @FXML
    private Menu importcool;
    @FXML
    private Label description;
    @FXML
    private Button HG;
    @FXML
    private Button BG;
    @FXML
    private Button HD;
    @FXML
    private Button BD;
    @FXML
    private Label question;
    @FXML
    private Label answered;
    @FXML
    private Pane mediaContainer;
    @FXML
    private TextField fieldTime;
    @FXML 
    private Label reflexionTimeLabel;

    private Timeline timeline;
    private int timerFlag;

    public QCMController(StackOfCards stack) {
        this.stack = stack;
        this.timerFlag = 0;
        
        stack.addObserver(this);
    }

    /**
     * Initializes the view
     */
    public void initialize() {
        this.reflexionTimeLabel.setText("Temps de réflexion : "+this.stack.getTimeOfReflexion() + " secondes");
        this.update();
    }

    /**
     * Updates the view
     */
    public void update() {
        if (stack.getSceneFlag() == 5) {
            this.importcool.getItems().clear();
            ArrayList<String> list = stack.getAllStackNames();
            for (int i = 0; i < list.size(); i++) {
                MenuItem newitem = new MenuItem(list.get(i));
                newitem.setOnAction(e -> {
                    stack.importStack(newitem.getText());
                });
                importcool.getItems().add(newitem);
            }    

            this.reflexionTimeLabel.setText("Temps de réflexion : "+this.stack.getTimeOfReflexion()+" secondes");
                 
            if (mediaContainer.getChildren() instanceof MediaView) {
                ((MediaView) mediaContainer.getChildren().get(0)).getMediaPlayer().stop();
            }
            mediaContainer.getChildren().clear();
            HG.setStyle(
                    "-fx-background-color: #e8e5e7;-fx-background-radius:10;-fx-border-color: white; -fx-border-radius: 10;");
            BG.setStyle(
                    "-fx-background-color: #e8e5e7;-fx-background-radius:10;-fx-border-color: white; -fx-border-radius: 10;");
            HD.setStyle(
                    "-fx-background-color: #e8e5e7;-fx-background-radius:10;-fx-border-color: white; -fx-border-radius: 10;");
            BD.setStyle(
                    "-fx-background-color: #e8e5e7;-fx-background-radius:10;-fx-border-color: white; -fx-border-radius: 10;");
            this.title.setText(stack.getStackName());
            this.description.setText(stack.getDesc());
            int bonIndice = stack.getIndexes().get(stack.getIndex());
            this.question.setText(stack.getCards().get(bonIndice).getQuestion());

            if (stack.getCards().get(bonIndice).getMedia() != null
                    && stack.getCards().get(bonIndice).getMedia() != "") {
                mediaContainer.getChildren().clear();
                if (stack.getCards().get(bonIndice).getMedia().endsWith(".mp4")) {
                    MediaView mediaView = new MediaView();
                    mediaView.setMediaPlayer(new MediaPlayer(
                            new Media(new File(stack.getCards().get(bonIndice).getMedia()).toURI().toString())));
                    mediaView.setFitWidth(322);
                    mediaView.setFitHeight(161);
                    mediaView.setPreserveRatio(true);

                    mediaContainer.getChildren().add(mediaView);
                    // play the video
                    Button playstop = new Button("Play / Stop");
                    // put the button in 610 198
                    playstop.setLayoutX(370);
                    playstop.setLayoutY(60);

                    playstop.setOnAction(e -> {
                        if (mediaView.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
                            mediaView.getMediaPlayer().stop();
                        } else {
                            mediaView.getMediaPlayer().play();
                        }

                    });
                    mediaContainer.getChildren().add(playstop);
                    mediaView.getMediaPlayer().play();

                } else if (stack.getCards().get(bonIndice).getMedia().endsWith(".mp3")) {
                    ImageView audio = new ImageView(new Image(getClass().getResource("/audio.png").toString()));
                    audio.setFitWidth(322);
                    audio.setFitHeight(161);
                    audio.setPreserveRatio(true);
                    mediaContainer.getChildren().add(audio);
                    MediaView mediaView = new MediaView();
                    mediaView.setMediaPlayer(new MediaPlayer(
                            new Media(new File(stack.getCards().get(bonIndice).getMedia()).toURI().toString())));
                    mediaView.setFitWidth(322);
                    mediaView.setFitHeight(161);
                    mediaView.setPreserveRatio(true);
                    mediaContainer.getChildren().add(mediaView);
                    // play the sound
                    Button playstop = new Button("Play / Stop");
                    // put the button in 610 198
                    playstop.setLayoutX(370);
                    playstop.setLayoutY(60);

                    playstop.setOnAction(e -> {
                        if (mediaView.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
                            mediaView.getMediaPlayer().stop();
                        } else {
                            mediaView.getMediaPlayer().play();
                        }

                    });
                    mediaContainer.getChildren().add(playstop);
                    mediaView.getMediaPlayer().play();
                } else {
                    ImageView image = new ImageView(
                            new Image(new File(stack.getCards().get(bonIndice).getMedia()).toURI().toString()));
                    image.setFitWidth(322);
                    image.setFitHeight(161);
                    image.setPreserveRatio(true);
                    mediaContainer.getChildren().add(image);
                }
            }
            displayAnswers();
            int QCMAnswered = stack.getCards().get(bonIndice).getQCMAnswered();
            if (QCMAnswered == 1 || QCMAnswered == -1) {
                if (HG.getText().equals(stack.getCards().get(bonIndice).getAnswer())) {
                    HG.setStyle("-fx-background-color: #87f8cc;-fx-background-radius:10;");
                }
                if (BG.getText().equals(stack.getCards().get(bonIndice).getAnswer())) {
                    BG.setStyle("-fx-background-color: #87f8cc;-fx-background-radius:10;");
                }
                if (HD.getText().equals(stack.getCards().get(bonIndice).getAnswer())) {
                    HD.setStyle("-fx-background-color: #87f8cc;-fx-background-radius:10;");
                }
                if (BD.getText().equals(stack.getCards().get(bonIndice).getAnswer())) {
                    BD.setStyle("-fx-background-color: #87f8cc;-fx-background-radius:10;");
                }
            }
            if (QCMAnswered == 1) {
                this.answered.setText("Vous aviez répondu correctement à la question !");
                this.answered.setStyle("-fx-text-fill: #03ab87;");
                timeline.stop();
                timerFlag = 0;
            } else if (QCMAnswered == -1) {
                this.answered.setText("Vous aviez répondu incorrectement à la question !");
                this.answered.setStyle("-fx-text-fill: #f88787");
                timeline.stop();
                timerFlag = 0;
            } else {
                this.answered.setText("");

                if (timerFlag == 0) {
                    Timeline timeline = new Timeline();
                    this.timeline = timeline;
                    timeline.getKeyFrames()
                            .add(new KeyFrame(javafx.util.Duration.seconds(this.stack.getTimeOfReflexion()), ev -> {
                                checkAnswer();
                                this.answered.setText("Vous n'avez pas répondu à temps !");

                            }));
                    timeline.play();
                    timerFlag = 1;

                } else if (timerFlag == 1) {
                    timeline.stop();
                    timerFlag = 0;
                }
            }

        }
    }

    /**
     * Displays at most 4 answers in the QCM, using the getAnswers functions.
     */
    public void displayAnswers() {
        ArrayList<String> answers = stack.getAnswers();
        if (answers.size() == 4) {
            HG.setText(answers.get(0));
            BG.setText(answers.get(1));
            HD.setText(answers.get(2));
            BD.setText(answers.get(3));
        }
        if (answers.size() == 3) {
            HG.setText(answers.get(0));
            BG.setText(answers.get(1));
            HD.setText(answers.get(2));
            BD.setText("");
        }
        if (answers.size() == 2) {
            HG.setText(answers.get(0));
            BG.setText(answers.get(1));
            HD.setText("");
            BD.setText("");
        }
        if (answers.size() == 1) {
            HG.setText(answers.get(0));
            BG.setText("");
            HD.setText("");
            BD.setText("");
        }
    }

    /**
     * Checks if the answer is correct or not, and changes the color of the buttons
     * accordingly. The button pushed is set to green if it's a correct answer, and
     * red if it's not. Then, the correct button is set to green.
     */
    @FXML
    public void checkAnswer() {
        this.timeline.stop();
        this.timerFlag = 0;
        int indice = this.stack.getIndexes().get(this.stack.getIndex());
        Card carteAModif = this.stack.getCards().get(indice);
        String answer = carteAModif.getAnswer();
        boolean bonClick = false;

        if (HG.isArmed() && HG.getText() != "" && carteAModif.getQCMAnswered() == 0) {
            if (HG.getText().equals(answer)) {
                carteAModif.addSuccess();
                carteAModif.setQCMAnswered(1);
                HG.setStyle("-fx-background-color: #87f8cc;-fx-background-radius:10;");
            } else {
                carteAModif.addFail();
                carteAModif.setQCMAnswered(-1);
                HG.setStyle("-fx-background-color: #f88787;-fx-background-radius:10;");
            }
            bonClick = true;
        } else if (BG.isArmed() && BG.getText() != "" && carteAModif.getQCMAnswered() == 0) {
            if (BG.getText().equals(answer)) {
                carteAModif.addSuccess();
                carteAModif.setQCMAnswered(1);
                BG.setStyle("-fx-background-color: #87f8cc;-fx-background-radius:10;");
            } else {
                carteAModif.addFail();
                carteAModif.setQCMAnswered(-1);
                BG.setStyle("-fx-background-color: #f88787;-fx-background-radius:10;");
            }
            bonClick = true;
        } else if (HD.isArmed() && HD.getText() != "" && carteAModif.getQCMAnswered() == 0) {
            if (HD.getText().equals(answer)) {
                carteAModif.addSuccess();
                carteAModif.setQCMAnswered(1);
                HD.setStyle("-fx-background-color: #87f8cc;-fx-background-radius:10;");
            } else {
                carteAModif.addFail();
                carteAModif.setQCMAnswered(-1);
                HD.setStyle("-fx-background-color: #f88787;-fx-background-radius:10;");
            }
            bonClick = true;
        } else if (BD.isArmed() && BD.getText() != "" && carteAModif.getQCMAnswered() == 0) {
            if (BD.getText().equals(answer)) {
                carteAModif.addSuccess();
                carteAModif.setQCMAnswered(1);
                BD.setStyle("-fx-background-color: #87f8cc;-fx-background-radius:10;");
            } else {
                carteAModif.addFail();
                carteAModif.setQCMAnswered(-1);
                BD.setStyle("-fx-background-color: #f88787;-fx-background-radius:10;");
            }
            bonClick = true;
        } else {
            carteAModif.addFail();
            bonClick = true;
            carteAModif.setQCMAnswered(-1);
        }
        if (HG.getText().equals(answer) && bonClick) {
            HG.setStyle("-fx-background-color: #87f8cc;-fx-background-radius:10;");
        } else if (BG.getText().equals(answer) && bonClick) {
            BG.setStyle("-fx-background-color: #87f8cc;-fx-background-radius:10;");
        } else if (HD.getText().equals(answer) && bonClick) {
            HD.setStyle("-fx-background-color: #87f8cc;-fx-background-radius:10;");
        } else if (BD.getText().equals(answer) && bonClick) {
            BD.setStyle("-fx-background-color: #87f8cc;-fx-background-radius:10;");
        }
    }

    /**
     * Goes to the previous card.
     */
    @FXML
    public void QCMPrevious() {
        timeline.stop();
        timerFlag = 0;
        this.stack.previousCard();
    }

    /**
     * Goes to the next card.
     */
    @FXML
    public void QCMNext() {
        timeline.stop();
        timerFlag = 0;
        this.stack.nextCard();
    }

    /**
     * Go to the "Learning" view.
     */
    @FXML
    public void toLearn(ActionEvent event) throws Exception {
        timeline.stop();
        timerFlag = 0;
        stack.switchtoLearnView();
    }

    /**
     * Go to the "Statistics" view.
     */
    @FXML
    public void toStat(ActionEvent event) throws Exception {
        timeline.stop();
        timerFlag = 0;
        stack.switchtoStatView();
    }

    /**
     * Go to the "Create" view.
     */

    @FXML
    public void toCreate(ActionEvent event) throws Exception {
        timeline.stop();
        timerFlag = 0;
        stack.switchtoCreationView();
    }

    /**
     * Go to the "Fill in the blank" view.
     */
    @FXML
    public void toFillBlank(ActionEvent event) throws Exception {
        timeline.stop();
        timerFlag = 0;
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
     * Save the stack of cards.
     */
    @FXML
    public void save(ActionEvent event) {
        stack.save();
    }

    /**
     * Reset the session.
     */
    @FXML
    public void resetQCM() {
        timeline.stop();
        timerFlag = 0;
        this.stack.resetQCM();
    }

    @FXML 
    public void changeReflexionTime(){
        try{
        this.stack.setTimeOfReflexion(Integer.parseInt(this.fieldTime.getText()));
        
        }catch(Exception e){
            System.out.println("Erreur de saisie");
        }
    }
}
