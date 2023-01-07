package eu.telecomnancy.application;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.lang.Math;
import java.text.DecimalFormat;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;



/**
 * Controller for the "Statistics" part.
 */
public class StatController implements Observer{

    StackOfCards stack;

    @FXML
    private Menu importcool;

    @FXML
    private Label c00;

    @FXML
    private Label c01;

    @FXML
    private Label c10;

    @FXML
    private Label c11;

    @FXML
    private Label c02;

    @FXML
    private Label c12;


    int typeOfSort = 0;

    String statLoad = "";
    

    public StatController(StackOfCards pile){
        this.stack=pile;
        this.stack.addObserver(this);
    }

    /**
     * Go to the "Create" view.
     */
    @FXML
    public void toCreate(ActionEvent event) throws Exception{
        stack.switchtoCreationView();
    }

    /**
     * Go to the "Learning" view.
     */
    @FXML
    public void toLearn(ActionEvent event) throws Exception{
        stack.switchtoLearnView();
    }

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

    private String lisibleDate(LocalDateTime time) {
        String tmp = time.toString();
        String year = tmp.substring(0, 4);
        String month = tmp.substring(5, 7);
        String day = tmp.substring(8, 10);
        String hour = tmp.substring(11, 13);
        String minute = tmp.substring(14, 16);
        return day + "/" + month + "/" + year + " à " + hour + "h" + minute;
    }
    /**
     * Update the view.
     */
    public void update() {
        if (stack.getSceneFlag() == 3) {
            this.importcool.getItems().clear();
            ArrayList<String> list = stack.getAllStackNames();
            MenuItem base = new MenuItem("Toutes les statistiques");
            base.setOnAction(e -> {
                this.statLoad = "";
                this.stack.notifyObserver();
            });
            importcool.getItems().add(base);
            for (int i = 0; i < list.size(); i++) {
                MenuItem newitem = new MenuItem(list.get(i));
                newitem.setOnAction(e -> {
                    this.statLoad = newitem.getText();
                    this.stack.notifyObserver();
                });
                importcool.getItems().add(newitem);
            }
            ArrayList<ArrayList<String>> stats = stack.loadstat();
            ArrayList<ArrayList<String>> stats2 = new ArrayList<ArrayList<String>>();
            if (statLoad.equals("")) {
                for (int i = 1; i < stats.size(); i++) {
                    stats2.add(stats.get(i));
                }
            } else {
                for (int i = 1; i < stats.size(); i++) {
                    if (stats.get(i).get(2).equals(statLoad)) {
                        stats2.add(stats.get(i));
                    }
                }
            }


            if (typeOfSort == 0) {
                int NumberOfStat = stats2.size();
                if (NumberOfStat == 0) {
                    c00.setText("Aucune statistique disponible");
                    c01.setText("");
                    c10.setText("");
                    c11.setText("");
                    c02.setText("");
                    c12.setText("");
        
                } else if (NumberOfStat <= 6) {
                    int NumberMaxOfStat = NumberOfStat;
                    ArrayList<String> pri = new ArrayList<String>();
                    for (int i = NumberMaxOfStat-1; i>=0; i--) {
                        ArrayList<String> stat = stats2.get(i);
                        LocalDateTime time = LocalDateTime.parse(stat.get(0));
                        String nameOfStack = stat.get(2);
                        double ratio = Double.parseDouble(stat.get(1));
                        Stat seance = new Stat(time, ratio, nameOfStack);
                        String tmp = "Séance du : " + lisibleDate(seance.getTime()) + "\n";
                        tmp += "Pile : " + seance.getNameOfStack() + "\n";
                        tmp += "Rapport Appris/Total : " + new DecimalFormat("###.##").format(100*seance.getRatio()) + "%\n";
                        pri.add(tmp);
                    }

                    for (int i = 0; i<NumberMaxOfStat; i++) {
                        if (i == 0) {
                            c00.setText(pri.get(0));
                       } else if (i == 1) {
                            c10.setText(pri.get(1));
                       } else if (i == 2) {
                            c01.setText(pri.get(2));
                       } else if (i == 3) {
                            c11.setText(pri.get(3));
                       } else if (i == 4) {
                            c02.setText(pri.get(4));
                       } else if (i == 5) {
                            c12.setText(pri.get(5));
                       }
                    }

                    for (int i = NumberMaxOfStat; i<6; i++) {
                        if (i == 0) {
                            c00.setText("");
                       } else if (i == 1) {
                            c10.setText("");
                       } else if (i == 2) {
                            c01.setText("");
                       } else if (i == 3) {
                            c11.setText("");
                       } else if (i == 4) {
                            c02.setText("");
                       } else if (i == 5) {
                            c12.setText("");
                       }
                    }

                } else {
                    int NumberMaxOfStat = 6;
                    ArrayList<String> pri = new ArrayList<String>();
                    for (int i = 0; i < NumberMaxOfStat; i++) {
                        ArrayList<String> stat = stats2.get(stats2.size()-1-i);
                        LocalDateTime time = LocalDateTime.parse(stat.get(0));
                        String nameOfStack = stat.get(2);
                        double ratio = Double.parseDouble(stat.get(1));
                        Stat seance = new Stat(time, ratio, nameOfStack);
                        String tmp = "Séance du : " + lisibleDate(seance.getTime()) + "\n";
                        tmp += "Pile : " + seance.getNameOfStack() + "\n";
                        tmp += "Rapport Appris/Total : " + new DecimalFormat("###.##").format(100*seance.getRatio()) + "%\n";
                        pri.add(tmp);
                    }

                    for (int i = 0; i<NumberMaxOfStat; i++) {
                        if (i == 0) {
                            c00.setText(pri.get(0));
                       } else if (i == 1) {
                            c10.setText(pri.get(1));
                       } else if (i == 2) {
                            c01.setText(pri.get(2));
                       } else if (i == 3) {
                            c11.setText(pri.get(3));
                       } else if (i == 4) {
                            c02.setText(pri.get(4));
                       } else if (i == 5) {
                            c12.setText(pri.get(5));
                       }
                    }


                }
            }
        }

    }    
}
