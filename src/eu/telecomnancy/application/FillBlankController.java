package eu.telecomnancy.application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;


public class FillBlankController implements Observer {

    private StackOfCards stack;

    @FXML
    private Label question;
    @FXML
    private Menu importcool;
    /**
     * Label to display if the user answered correctly or not
     */
    @FXML
    private Label answered;
    @FXML
    private TextField reponse;
    @FXML 
    private Label description;
    @FXML
    private Label title;
    @FXML
    /**
     * Displays the correct answer
     */
    private Label answer;
    @FXML
    private Button valider;
    @FXML 
    private Pane mediaContainer;

    public FillBlankController(StackOfCards stack) {
        this.stack = stack;
        stack.addObserver(this);
    }

    /**
     * Initializes the view
     */
    public void initialize() {
        this.update();
    }

    /**
     * Updates the view
     */
    public void update() {
        if(stack.getSceneFlag() == 7) {
            this.importcool.getItems().clear();
            ArrayList<String> list = stack.getAllStackNames();
            for (int i = 0; i < list.size(); i++) {
                MenuItem newitem = new MenuItem(list.get(i));
                newitem.setOnAction(e -> {
                    stack.importStack(newitem.getText());
                 });
                importcool.getItems().add(newitem);
            }


            if (mediaContainer.getChildren() instanceof MediaView){
                ((MediaView)mediaContainer.getChildren().get(0)).getMediaPlayer().stop();
            }
            mediaContainer.getChildren().clear();
            this.reponse.setText("");
            this.valider.setDisable(false);
            this.title.setText(stack.getStackName());
            this.description.setText(stack.getDesc());
            int bonIndice = stack.getIndexes().get(stack.getIndex());
            Card carteAModif = stack.getCards().get(bonIndice);
            this.question.setText(carteAModif.getQuestion());
            if (stack.getCards().get(bonIndice).getMedia()!=null && !stack.getCards().get(bonIndice).getMedia().equals("")){
                if (stack.getCards().get(bonIndice).getMedia().endsWith(".mp4")){
                    MediaView mediaView = new MediaView();
                    mediaView.setMediaPlayer(new MediaPlayer(new Media(new File(stack.getCards().get(bonIndice).getMedia()).toURI().toString())));
                    mediaView.setFitWidth(400);
                    mediaView.setFitHeight(300);
                    mediaView.setPreserveRatio(true);
                    mediaContainer.getChildren().add(mediaView);
                    //play the video 
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
                }
                else if(stack.getCards().get(bonIndice).getMedia().endsWith(".mp3")){
                    ImageView audio = new ImageView(new Image(getClass().getResource("/audio.png").toString()));
                    audio.setFitWidth(283);
                    audio.setFitHeight(200);
                    audio.setPreserveRatio(true);
                    mediaContainer.getChildren().add(audio);
                    MediaView mediaView = new MediaView();
                    mediaView.setMediaPlayer(new MediaPlayer(new Media(new File(stack.getCards().get(bonIndice).getMedia()).toURI().toString())));
                    mediaView.setFitWidth(283);
                    mediaView.setFitHeight(200);
                    mediaContainer.getChildren().add(mediaView);
                    //play the sound
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
                }
                else{
                    ImageView image = new ImageView(new Image(new File(stack.getCards().get(bonIndice).getMedia()).toURI().toString()));
                    image.setFitWidth(283);
                    image.setFitHeight(200);
                    mediaContainer.getChildren().add(image);
                }
            }
            if(carteAModif.getQCMAnswered()==1){
                this.answered.setText("Vous aviez répondu correctement à la question !");
                this.answer.setText("La bonne réponse était bien : " + carteAModif.getAnswer());
                this.answered.setStyle("-fx-text-fill: #03ab87;");
                this.answer.setStyle("-fx-text-fill: #03ab87;");
                this.valider.setDisable(true);
            } else if (carteAModif.getQCMAnswered()==-1){
                this.answered.setText("Vous aviez répondu incorrectement à la question !");
                this.answer.setText("La bonne réponse était en fait : " + carteAModif.getAnswer());
                this.answered.setStyle("-fx-text-fill: #f88787");
                this.answer.setStyle("-fx-text-fill: #f88787");
                this.valider.setDisable(true);
            } else {
                this.answered.setText("");
                this.answer.setText("");
            }
        }
        

    }

    /**
     * Goes to "Create" view
     */
    @FXML
    public void toCreate(ActionEvent event) throws Exception {
        /* if (this.timerFlag == 1){   //if timer is running
            this.timeline.stop();   //stop timer
            this.timerFlag = 0;     //reset timer flag
            
        } */
        stack.switchtoCreationView();
    }

    /**
     * Goes to the "Stat" view
     */
    @FXML
    public void toStat(ActionEvent event) throws Exception {
        /* if (this.timerFlag == 1){   //if timer is running
            this.timeline.stop();   //stop timer
            this.timerFlag = 0;     //reset timer flag
            
        } */
        stack.switchtoStatView();
    }

    /**
     * Goes to the "QCM" view
     */
    @FXML
    public void toQCM(ActionEvent event) throws Exception {
        /*if (this.timerFlag == 1){   //if timer is running
            this.timeline.stop();   //stop timer
            this.timerFlag = 0;     //reset timer flag
            
        }*/
        stack.switchtoQCMView();
    }

    /**
     * Goes to the "Learning" view.
     */
    @FXML
    public void toLearn(ActionEvent event) throws Exception {
        stack.switchtoLearnView();
    }

    /**
     * Exits the application.
     */
    @FXML
    public void exitApp(ActionEvent event) {
        /* if (this.timerFlag == 1){   //if timer is running
            this.timeline.stop();   //stop timer
            this.timerFlag = 0;     //reset timer flag
            
        } */
        System.exit(0);
    }


    /**
     * Check if the answer is correct or not. Displays the correct answer either way, changing the color of the text to green if the answer is correct, and red if it is not. Also disables the "Valider" button.
     */
    @FXML
    public void checkAnswer() {
        int bonIndice = stack.getIndexes().get(stack.getIndex());
        Card carteAModif = stack.getCards().get(bonIndice);
        String bonReponse = carteAModif.getAnswer().toLowerCase();
        String reponseUser = this.reponse.getText().toLowerCase();
        if (bonReponse.equals(reponseUser)) {
            carteAModif.addSuccess();
            carteAModif.setQCMAnswered(1);
            this.answered.setText("Bonne réponse !");
            this.answer.setText("La bonne réponse était bien : " + carteAModif.getAnswer());
            this.answered.setStyle("-fx-text-fill: #03ab87;");
            this.answer.setStyle("-fx-text-fill: #03ab87;");
            this.valider.setDisable(true);
        } else {
            carteAModif.addFail();
            carteAModif.setQCMAnswered(-1);
            this.answered.setText("Mauvaise réponse !");
            this.answer.setText("La bonne réponse était en fait : " + carteAModif.getAnswer());
            this.answered.setStyle("-fx-text-fill: #f88787");
            this.answer.setStyle("-fx-text-fill: #f88787");
            this.valider.setDisable(true);
        }
        
    }
    /**
     * Goes to the previous card in the QCM
     */
    @FXML
    public void QCMPrevious() {
        this.stack.previousCard();
    }

    /**
     * Goes to the next card in the QCM
     */
    @FXML
    public void QCMNext() {
        this.stack.nextCard();
    }

    /**
     * Resets the QCM
     */
    @FXML
    public void resetQCM() {
        this.stack.resetQCM();
    }

}