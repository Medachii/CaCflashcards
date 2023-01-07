package eu.telecomnancy.application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.FileChooser;
import java.util.Collections;
import java.util.ArrayList;

/**
 * Controller for the "Learning" part.
 */
public class LearnController implements Observer, Initializable {

    StackOfCards stack;

    @FXML
    private Menu importcool;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label question;
    @FXML
    private Button bad;
    @FXML
    private Button good;
    @FXML
    private Pane mediaContainer;

    private boolean isQuestion = true;
    // private Timeline timeline;
    // private int timerFlag;

    LearnController(StackOfCards pile) {
        this.stack = pile;
        // this.timerFlag = 0;
        stack.addObserver(this);
    }

    /**
     * Initialize the view.
     */
    public void initialize(URL url, ResourceBundle resources) {
        this.update();
    }

    /**
     * Update the view when the model is changed.
     */
    public void update() {
        if (stack.getSceneFlag() == 1) {
            this.importcool.getItems().clear();
            ArrayList<String> list = stack.getAllStackNames();
            for (int i = 0; i < list.size(); i++) {
                MenuItem newitem = new MenuItem(list.get(i));
                newitem.setOnAction(e -> {
                    stack.importStack(newitem.getText());
                });
                importcool.getItems().add(newitem);
            }

            this.title.setText(stack.getStackName());
            this.description.setText(stack.getDesc());
            if (mediaContainer.getChildren() instanceof MediaView) {
                System.out.println("stop");
                ((MediaView) mediaContainer.getChildren().get(0)).getMediaPlayer().stop();
            }
            mediaContainer.getChildren().clear();
            if (stack.getIndex() < 0) {
                this.question.setText(stack.getCurrentCard().getQuestion());
                this.isQuestion = true;
                bad.setDisable(true);
                good.setDisable(true);
            } else {
                int bonIndice = stack.getIndexes().get(stack.getIndex());
                this.question.setText(stack.getCards().get(bonIndice).getQuestion());
                this.isQuestion = true;

                if (stack.getCards().get(bonIndice).getMedia() != null
                        && !stack.getCards().get(bonIndice).getMedia().equals("")) {
                    if (stack.getCards().get(bonIndice).getMedia().endsWith(".mp4")) {
                        MediaView mediaView = new MediaView();
                        mediaView.setMediaPlayer(new MediaPlayer(
                                new Media(new File(stack.getCards().get(bonIndice).getMedia()).toURI().toString())));
                        mediaView.setFitWidth(285);
                        mediaView.setFitHeight(200);
                        mediaView.setPreserveRatio(true);
                        mediaContainer.getChildren().add(mediaView);
                        // play the video
                        Button playstop = new Button("Play / Stop");
                        // put the button in 610 198
                        playstop.setLayoutX(610);
                        playstop.setLayoutY(198);

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
                        audio.setFitWidth(285);
                        audio.setFitHeight(200);
                        audio.setPreserveRatio(true);
                        mediaContainer.getChildren().add(audio);
                        MediaView mediaView = new MediaView();
                        mediaView.setMediaPlayer(new MediaPlayer(
                                new Media(new File(stack.getCards().get(bonIndice).getMedia()).toURI().toString())));
                        mediaView.setFitWidth(285);
                        mediaView.setFitHeight(200);
                        mediaContainer.getChildren().add(mediaView);
                        // play the sound
                        Button playstop = new Button("Play / Stop");
                        // put the button in 610 198
                        playstop.setLayoutX(610);
                        playstop.setLayoutY(198);

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
                        image.setFitWidth(285);
                        image.setFitHeight(200);
                        image.setPreserveRatio(true);
                        mediaContainer.getChildren().add(image);
                    }
                }
            }

            /*
             * if (timerFlag==0){
             * Timeline timeline = new Timeline();
             * this.timeline=timeline;
             * timeline.getKeyFrames().add(new
             * KeyFrame(javafx.util.Duration.seconds(this.stack.getTimeOfReflexion()), new
             * KeyValue(question.textProperty(), stack.getCurrentCard().getAnswer())));
             * //change the text to the answer after 5 seconds
             * timeline.play();
             * timerFlag = 1;
             * 
             * //when timer is over
             * timeline.setOnFinished((ActionEvent event) -> {
             * //turn();
             * System.out.println("Fin du timer");
             * timerFlag = 0;
             * });
             * }
             */

        }
    }

    /**
     * Turn the card
     */
    @FXML
    public void turn() {

        /*
         * if (this.timerFlag == 1){ //if timer is running
         * this.timeline.stop(); //stop timer
         * this.timerFlag = 0; //reset timer flag
         * 
         * }
         */
        if (isQuestion) {
            if (this.stack.getIndex() < 0) {
                this.question.setText(stack.getCurrentCard().getAnswer());
                this.isQuestion = false;
            } else {
                int bonIndice = stack.getIndexes().get(stack.getIndex());
                this.question.setText(stack.getCards().get(bonIndice).getAnswer());
                this.isQuestion = false;
            }

        } else {
            if (this.stack.getIndex() < 0) {
                this.question.setText(stack.getCurrentCard().getQuestion());
                this.isQuestion = true;
            } else {
                int bonIndice = stack.getIndexes().get(stack.getIndex());
                this.question.setText(stack.getCards().get(bonIndice).getQuestion());
                this.isQuestion = true;
            }
        }
    }

    /**
     * Tells the model that the user knows the answer
     */
    @FXML
    public void goodAnswer(ActionEvent event) {
        /*
         * if (this.timerFlag == 1){ //if timer is running
         * this.timeline.stop(); //stop timer
         * this.timerFlag = 0; //reset timer flag
         * 
         * }
         */
        stack.goodAnswer();
    }

    /**
     * Tells the model that the user doesn't know the answer
     */
    @FXML
    public void badAnswer(ActionEvent event) {
        /*
         * if (this.timerFlag == 1){ //if timer is running
         * this.timeline.stop(); //stop timer
         * this.timerFlag = 0; //reset timer flag
         * 
         * }
         */
        stack.badAnswer();
    }

    /**
     * Goes to the "Create" view
     */
    @FXML
    public void toCreate(ActionEvent event) throws Exception {
        /*
         * if (this.timerFlag == 1){ //if timer is running
         * this.timeline.stop(); //stop timer
         * this.timerFlag = 0; //reset timer flag
         * 
         * }
         */
        stack.switchtoCreationView();
    }

    /**
     * Goes to the "Stat" view
     */
    @FXML
    public void toStat(ActionEvent event) throws Exception {
        /*
         * if (this.timerFlag == 1){ //if timer is running
         * this.timeline.stop(); //stop timer
         * this.timerFlag = 0; //reset timer flag
         * 
         * }
         */
        stack.switchtoStatView();
    }

    /**
     * Goes to the "FillBlank" view
     */
    @FXML
    public void toFillBlank(ActionEvent event) throws Exception {
        stack.switchtoFillBlankView();
    }

    /**
     * Goes to the "QCM" view
     */
    @FXML
    public void toQCM(ActionEvent event) throws Exception {
        /*
         * if (this.timerFlag == 1){ //if timer is running
         * this.timeline.stop(); //stop timer
         * this.timerFlag = 0; //reset timer flag
         * 
         * }
         */
        stack.switchtoQCMView();
    }

    /**
     * Exits the application.
     */
    @FXML
    public void exitApp(ActionEvent event) {
        /*
         * if (this.timerFlag == 1){ //if timer is running
         * this.timeline.stop(); //stop timer
         * this.timerFlag = 0; //reset timer flag
         * 
         * }
         */
        System.exit(0);
    }


    /**
     * Imports a stack of the user's choice by opening a FileChooser window.
     */
    @FXML
    public void importStack(ActionEvent event) {
        String homepath = System.getProperty("user.home");
        File d = new File(homepath + "/codingandchill/data/");
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(d);
        chooser.setTitle("Choisir un fichier");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichier JSON", "*.json"));
        File filechose = chooser.showOpenDialog(null);
        if (filechose != null) {
            String file = filechose.getAbsolutePath();
            String[] fileNameSplit = file.split("/");
            String fileName = fileNameSplit[fileNameSplit.length - 1];
            String[] fileNameSplit2 = fileName.split("\\.");

            if (fileNameSplit2.length > 2) {
                return;
            }
            String fileName2 = fileNameSplit2[0];
            if (fileName2.equals("stat")) {
                return;
            }
            fileName2 = fileName2.replaceAll("%20", " ");
            stack.importStack(fileName2);
        }

    }

    /**
     * Creates a new session : resets the number of successes and failures for each
     * card, and shuffles the indexes.
     */
    @FXML
    public void newSession() {
        /*
         * if (this.timerFlag == 1){ //if timer is running
         * this.timeline.stop(); //stop timer
         * this.timerFlag = 0; //reset timer flag
         * 
         * }
         */
        for (int i = 0; i < this.stack.getCards().size(); i++) {
            this.stack.getCards().get(i).setNbSuccess(0);
            this.stack.getCards().get(i).setNbFail(0);
            this.stack.getCards().get(i).setLearnt(false);
        }
        ArrayList<Integer> indexes = this.stack.getIndexes();
        Collections.shuffle(indexes);
        stack.setCurrentCard(this.stack.getCards().size() - 1);
        good.setDisable(false);
        bad.setDisable(false);
    }

}
