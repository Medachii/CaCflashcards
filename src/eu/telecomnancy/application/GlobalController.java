package eu.telecomnancy.application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;


/**
 * Controller for the "Creating and modifying" part.
 */
public class GlobalController implements Observer {

    private StackOfCards stack;

    @FXML
    private Pane mediaContainer;

    public GlobalController(StackOfCards pile){
        this.stack=pile;
        this.stack.addObserver(this);
    }

     /**
     * Initialize the view.
     */
    public void initialize(){
        String pathvideo = "/Intro.mp4";
        MediaView introvideo = new MediaView();
        introvideo.setMediaPlayer(new MediaPlayer(new Media(getClass().getResource(pathvideo).toExternalForm())));
        mediaContainer.getChildren().add(introvideo);
        introvideo.getMediaPlayer().play();
        this.update();
    }
    
    /**
     * Update the view.
     */
    public void update(){
    }
    /**
     * Go to the "Learning" view.
     */
    @FXML
    public void toLearn(ActionEvent event) throws Exception{
        stack.switchtoLearnView();
    }

    /**
     * Go to the "Create" view.
     */
    @FXML
    public void toCreate(ActionEvent event) throws Exception{
        stack.switchtoCreationView();
    }

    /**
     * Go to the "Statistics" view.
     */
    @FXML
    public void toStat(ActionEvent event) throws Exception{
        stack.switchtoStatView();
    }



    /**
     * Go to the "FillBlank" view.
     */
    @FXML 
    public void toFillBlank(ActionEvent event) throws Exception {
        stack.switchtoFillBlankView();
    }

    @FXML
    public void toQCM(ActionEvent event) throws Exception {
        stack.switchtoQCMView();
    }

    /**
     * Exit the application.
     */
    @FXML 
    public void exitApp(ActionEvent event) {
        System.exit(0);
    }
}
