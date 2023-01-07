package eu.telecomnancy.application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

/**
 * Class that manages the different scenes of the application
 */
public class SceneController {
    private Stage stage;
    private StackOfCards stackOfCards;
    private Scene learnscene;
    private Scene creationscene;
    private Scene statscene;
    private Scene globalscene;
    private Scene qcmscene;
    private Scene fillblankscene;


    public SceneController(Stage stage, StackOfCards stackOfCards) throws Exception{
        this.stage = stage;
        this.stackOfCards = stackOfCards;

        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(getClass().getResource("/LearnView.fxml"));
        loader1.setControllerFactory(iC-> new LearnController(stackOfCards));
        Parent root1 = loader1.load();
        Scene scene1 = new Scene(root1);
        this.learnscene = scene1;   //for LearnView.fxml

        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("/CreateView.fxml"));
        loader2.setControllerFactory(iC-> new CreateController(stackOfCards));
        Parent root2 = loader2.load();
        Scene scene2 = new Scene(root2);
        this.creationscene = scene2;   //for CreationView.fxml

        FXMLLoader loader3 = new FXMLLoader();
        loader3.setLocation(getClass().getResource("/StatView.fxml"));
        loader3.setControllerFactory(iC-> new StatController(stackOfCards));
        Parent root3 = loader3.load();
        Scene scene3 = new Scene(root3);
        this.statscene = scene3;   //for StatView.fxml

        FXMLLoader loader4 = new FXMLLoader();
        loader4.setLocation(getClass().getResource("/GlobalView.fxml"));
        loader4.setControllerFactory(iC-> new GlobalController(stackOfCards));
        Parent root4 = loader4.load();
        Scene scene4 = new Scene(root4);
        this.globalscene = scene4;   //for GlobalView.fxml

        FXMLLoader loader5 = new FXMLLoader();
        loader5.setLocation(getClass().getResource("/QCMView.fxml"));
        loader5.setControllerFactory(iC-> new QCMController(stackOfCards));
        Parent root5 = loader5.load();
        Scene scene5 = new Scene(root5);
        this.qcmscene = scene5;   //for QCMView.fxml


        
        FXMLLoader loader7 = new FXMLLoader();
        loader7.setLocation(getClass().getResource("/FillBlankView.fxml"));
        loader7.setControllerFactory(iC-> new FillBlankController(stackOfCards));
        Parent root7 = loader7.load();
        Scene scene7 = new Scene(root7);
        this.fillblankscene = scene7;   //for FillBlankView.fxml 
       
        stage.setScene(globalscene);


        stage.show();


    }

    /**
     * Method that allows to switch to the "Learn" view, sets the sceneFlag to 1 and notifies the observers.
     */
    public void switchtoLearnView() throws Exception{
        stage.setScene(learnscene);
        this.stackOfCards.setSceneFlag(1);
        this.stackOfCards.setCurrentCard(this.stackOfCards.getCards().size()-1);
        
        //this.stackOfCards.notifyObserver();
    }

    /**
     * Method that allows to switch to the "Creation" view, sets the sceneFlag to 2 and notifies the observers.
     */
    public void switchtoCreationView() throws Exception{
        stage.setScene(creationscene);
        this.stackOfCards.setSceneFlag(2);
        this.stackOfCards.setCurrentCard(0);
        
        //this.stackOfCards.notifyObserver();
    }

    /**
     * Method that allows to switch to the "Stat" view, sets the sceneFlag to 3 and notifies the observers.
     */
    public void switchtoStatView() throws Exception{
        stage.setScene(statscene);
        this.stackOfCards.setSceneFlag(3);
        //this.stackOfCards.notifyObserver();
    }

    /**
     * Method that allows to switch to the "Global" view, sets the sceneFlag to 4 and notifies the observers.
     */
    public void switchtoGlobalView() throws Exception{
        stage.setScene(globalscene);
        this.stackOfCards.setSceneFlag(4);
        //this.stackOfCards.notifyObserver();
    }

    /**
     * Method that allows to switch to the "QCM" view, sets the sceneFlag to 5 and notifies the observers.
     */
    public void switchtoQCMView() throws Exception{
        stage.setScene(qcmscene);
        this.stackOfCards.setSceneFlag(5);
        //this.stackOfCards.notifyObserver();
    }

    /**
     * Method that allows to switch to the "FillBlank" view, sets the sceneFlag to 7 and notifies the observers.
     */
    public void switchtoFillBlankView() throws Exception {
        stage.setScene(fillblankscene);
        this.stackOfCards.setSceneFlag(7);
        //this.stackOfCards.notifyObserver();
    }

    /**
     * Getter for the stage.
     */
    public Stage getStage() {
        return this.stage;
    }

}
