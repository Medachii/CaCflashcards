package eu.telecomnancy.application;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.File;




public class Main extends Application{
    
    public static void main(String[] args) {

        Application.launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        String homepath = System.getProperty("user.home");
        File d = new File(homepath + "/codingandchill");
        if (!d.exists()) {
            d.mkdir();
        }
        File d_data = new File(homepath + "/codingandchill/data");
        if (!d_data.exists()) {
            d_data.mkdir();
        }

        primaryStage.setTitle("Coding & Chill");
        String title = "Stacktest";
        StackOfCards stack = new StackOfCards(title);
    
        SceneController sceneController = new SceneController(primaryStage,stack);
        stack.setSceneController(sceneController);
        stack.notifyObserver();


    }
}

