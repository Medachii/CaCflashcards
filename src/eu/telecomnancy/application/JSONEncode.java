package eu.telecomnancy.application;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;

public class JSONEncode{

    private StackOfCards stackOfCards;
    private Gson gson;

    public JSONEncode(StackOfCards stackOfCards){
        this.stackOfCards = stackOfCards;
        this.gson = new Gson();
    }


    public void save(){
        String homepath = System.getProperty("user.home");
        try {
            FileWriter writer = new FileWriter(homepath + "/codingandchill/data/" + this.stackOfCards.getStackName()+".json");
        

            ArrayList<String> stackName = new ArrayList<String>();
            String name = stackOfCards.getStackName();
            String desc = stackOfCards.getDesc();
            stackName.add(name);
            stackName.add(desc);
            
            ArrayList<ArrayList<String>> stackDictionnary = this.stackOfCards.getStackOfCardsDictionnary();
            
            stackDictionnary.add(0, stackName);

            String jsoncarnet = gson.toJson(stackDictionnary);
        
            writer.write(jsoncarnet);

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savestat() {
        try {
            ArrayList<ArrayList<String>> statDictionnary = loadstat();
            String tmp = statDictionnary.get(0).get(0);
            int id = Integer.parseInt(tmp);
            Stat stat = this.stackOfCards.getStat();
            ArrayList<String> statList = new ArrayList<String>();
            statList.add(stat.getTime().toString());
            statList.add(Double.toString(stat.getRatio()));
            statList.add(stat.getNameOfStack());
            statList.add(Integer.toString(id));
            statDictionnary.add(statList);
            String jsoncarnet = gson.toJson(statDictionnary);
            id++;
            statDictionnary.get(0).set(0, Integer.toString(id));
            String homepath = System.getProperty("user.home");
            FileWriter writer = new FileWriter(homepath + "/codingandchill/stat.json");
            writer.write(jsoncarnet);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public ArrayList<ArrayList<String>> load(String fileName) {
        String homepath = System.getProperty("user.home");
        try {
            JsonReader reader = new JsonReader(new FileReader(homepath + "/codingandchill/data/" +fileName+".json"));
            ArrayList<ArrayList<String>> stackDictionnary = gson.fromJson(reader, ArrayList.class);
            return stackDictionnary;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> loadstat() {
        String homepath = System.getProperty("user.home");
        try {
            JsonReader reader = new JsonReader(new FileReader(homepath + "/codingandchill/stat.json"));
            ArrayList<ArrayList<String>> statDictionnary = gson.fromJson(reader, ArrayList.class);
            return statDictionnary;
        } catch (IOException e) {
            File f = new File(homepath + "/codingandchill/stat.json");
            try {
                f.createNewFile();
                FileWriter writer = new FileWriter(homepath + "/codingandchill/stat.json");
                writer.append("[[\"0\"]]");
                writer.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return new ArrayList<ArrayList<String>>();
    }

    

    public int getNbCards(String fileName){
        String homepath = System.getProperty("user.home");
        try {
            JsonReader reader = new JsonReader(new FileReader(homepath + "/codingandchill/data/" + fileName +".json"));
            ArrayList<ArrayList<String>> stackDictionnary = gson.fromJson(reader, ArrayList.class);
            return stackDictionnary.size()-1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<String> getStackNames(){
        String homepath = System.getProperty("user.home");
        File d = new File(homepath + "/codingandchill/data/");
        ArrayList<String> stackNames = new ArrayList<String>();
        for (File f : d.listFiles()) {
            if (f.isFile()) {
                String name = f.getName().substring(0, f.getName().length()-5);
                stackNames.add(name);
            }
        }
        return stackNames;
    }
}