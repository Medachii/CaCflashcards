package eu.telecomnancy.application;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;


public class StackOfCards {
    
    private String stackName;
    private Stack<Card> cards;
    private int currentCard = 0;
    private SceneController sceneController;
    private String desc;
    private ArrayList<Observer> observers = new ArrayList<Observer>();

    /**
     * Integer that stores which scene is active to only update the current scene and dodge any issues.
     */
    private int sceneFlag = 2;
    private JSONEncode jsonEncode;
    private ArrayList<ArrayList<String>> stackOfCardsDictionnary = new ArrayList<ArrayList<String>>();
    /**
     * List of randomized indexes of the cards in the stack, used for randomization
     */
    private ArrayList<Integer> indexes = new ArrayList<Integer>();
    private int timeOfReflexion = 5;
    private Stat stat = new Stat();

    public StackOfCards(String stackName){
        this.stackName = stackName;
        JSONEncode jsonEncode = new JSONEncode(this);
        this.jsonEncode=jsonEncode;
        this.desc = "Une description";
        this.cards = new Stack<Card>();
        this.currentCard = 0;   
        this.addCard();   
    }

    /**
     * Sets the time of reflexion
     */
    public void setTimeOfReflexion(int time){
        this.timeOfReflexion = time;
        this.notifyObserver();
    }

    /**
     * Gets the time of reflexion
     *@return the time of reflexion
     */
    public int getTimeOfReflexion(){
        return this.timeOfReflexion;
    }

    /**
     * Sets the stats
     */
    public void setStat(Stat stat){
        this.stat = stat;
    }
    
    /**
     * Gets the stats
     *@return the stats
     */
    public Stat getStat(){
        return this.stat;
    }

    /**
     * Set the scene controller
     * @param sceneController the scene controller
     */
    public void setSceneController(SceneController sceneController){
        this.sceneController = sceneController;
    }

    /**
     * Setter for the scene controller index : 1 = learn, 2 = creation, 3 = stats, 4 = global, 5 = QCM, 7 = fillblank
     * @param i the index we want to set it to
     */
    public void setSceneFlag(int i){
        this.sceneFlag = i;
        this.notifyObserver();
    }

    /**
     * Getter for the scene controller index : 1 = learn, 2 = creation, 3 = stats
     * @return the scene controller index
     */
    public int getSceneFlag(){
        return sceneFlag;
    }
    /**
     * Getter for the stack name
     * @return the name of the stack
     */
    public String getStackName(){
        return stackName;
    }

    /**
     * Setter for the stack name
     * @param stackName the name of the stack
     */
    public void setStackName(String stackName){
        this.stackName = stackName;
        this.notifyObserver();
    }

    /**
     * Getter for the cards
     * @return the cards
     */
    public Stack<Card> getCards(){
        return cards;
    }

    /**
     * Getter for the description
     * @return the description
     */
    public String getDesc(){
        return desc;
    }

    /**
     * Setter for the description
     * @param desc the description
     */
    public void setDesc(String desc){
        this.desc = desc;
        this.notifyObserver();
    }

    /**
     * Setter for the cards
     * @param cards the cards
     */
    public void setCards(Stack<Card> cards){
        this.cards = cards;
        this.notifyObserver();
    }

    /**
     * Getter for the current card
     * @return the current card
     */
    public Card getCurrentCard(){
        if(currentCard < 0) {
            this.stat.setTime(LocalDateTime.now());
            this.stat.setNameOfStack(this.stackName);
            double r = 0;
            double t = 0;
            for (int i = 0; i<cards.size(); i++) {
                Card c = cards.get(i);
                r += c.getNbSuccess();
                t += c.getNbSuccess() + c.getNbFail();
            }
            this.stat.setRatio(r/t);
            return new Card("Vous venez de finir la session !", "En fait NON", 0, 0);
        } else {
           return cards.get(currentCard);
        }
    }

    /**
     * Setter for the current card
     * @param i indice of the current card in the list of cards
     */
    public void setCurrentCard(int i){
        this.currentCard = i;
        this.notifyObserver();
    }

    /**
     * Setter for the current card without notifying the observers (issues with update)
     */
    public void setCurrentCardSansNotifier(int i){
        this.currentCard = i;
    }

    /**
     * Getter for the index of the current card
     * @return currentCard
     */
    public int getIndex() {
        return this.currentCard;
    }

    /**
     * Setter for the randomized list of indexes.
     * @param indexes the new list of indexes 
     */
    public void setIndexes(ArrayList<Integer> indexes) {
        this.indexes = indexes;
        this.notifyObserver();
    }

    /**
     * Add a card to the stack
     * @param card the card to add
     */
    public void addCard(Card card){
        cards.push(card);
        //indexes.add(cards.size()-1);
        //Collections.shuffle(indexes);
        this.notifyObserver();
    }

    /**
     * Adds an empty card to the stack
     */
    public void addCard() {
        cards.push(new Card());
        this.currentCard = this.cards.size() - 1;
        //indexes.add(this.cards.size()-1);
        //Collections.shuffle(indexes);
        this.notifyObserver();
    }

    /**
     * Remove a card from the stack
     * @return the removed card
     */
    public Card popCard(){
        Card newCard=cards.pop();
        return newCard;
    }

    /**
     * Adds an observer to the stack
     * @param o the observer to add
     */
    public void addObserver(Observer o){
        this.observers.add(o);
    }

    /**
     * Triggers the update method of all the observers
     */
    public void notifyObserver(){
        for(Observer o: this.observers){
            o.update();
        }
    }

    /**
     * Goes to the next card
     */

    public void nextCard(){
        this.currentCard++;
        if(this.currentCard == this.cards.size()){
            this.currentCard = 0;
        }
        this.notifyObserver();
    }


    /**
     * Goes to the previous card
     */
    public void previousCard(){
        this.currentCard--;
        if(this.currentCard == -1){
            this.currentCard = this.cards.size()-1;
        }
        this.notifyObserver();
    }

    /**
     * Get at most 4 different answers from the stack of cards. Shuffles the created list before returning it.
     * @return an arraylist of at most 4 answers, shuffled
     */
    public ArrayList<String> getAnswers(){
        ArrayList<String> answers = new ArrayList<String>();
        int indice = this.indexes.get(this.currentCard);
        Card carteAModif = this.cards.get(indice);
        answers.add(carteAModif.getAnswer());
        this.cards.push(carteAModif);
        int i = 0;
        boolean pushed = false;
        while(i < 3 && i < this.cards.size() - 1){
            pushed = false;
            while(!pushed) {
                int random = (int)(Math.random() * this.cards.size());
                if(!answers.contains(this.cards.get(random).getAnswer())){
                    answers.add(this.cards.get(random).getAnswer());
                    pushed = true;
                }
            }
            i++;
        }
        Collections.shuffle(answers);
        return answers;
    }

    /**
     * Deletes the ith card in the stack
     * @param i the index of the card to delete
     */
    public void deleteCard(int i) {
        this.cards.remove(i);
        this.currentCard = i - 1 < 0 ? 0 : i - 1;
        if (this.cards.size() == 0) {
            this.addCard();
        }
        this.notifyObserver();
    }

    /**
     * Activated when the user gives the good answer. Adds a success to the current card (pulled from a shuffled version of the stack).
     * If the card is not learnt, it is moved to the end of the stack to view it again. Else, the currentCard index is decremented to view the next card.
     */
    public void goodAnswer() {
        int indice = this.indexes.get(this.currentCard);
        Card carteAModif = this.cards.get(indice);
        carteAModif.addSuccess();
        if(carteAModif.getLearnt()) {
            this.currentCard--;
        } else {
            int move = this.indexes.remove(this.currentCard);
            this.indexes.add(0, move);
        }
        this.notifyObserver();
    }

    /**
     * Activated when the user gives the wrong answer. Adds a fail to the current card (pulled from a shuffled version of the stack).
     * The index is moved to the end of the indexes array to view it again.
     */
    public void badAnswer() {
        int indice = this.indexes.get(this.currentCard);
        Card carteAModif = this.cards.get(indice);
        carteAModif.addFail();
        this.getCurrentCard().addFail();
        int move = this.indexes.remove(this.currentCard);
        this.indexes.add(0, move);
        this.notifyObserver();
    }

    /**
     * Go to the "Learning" View
     * @throws Exception
     */
    public void switchtoLearnView() throws Exception{
        this.indexes = new ArrayList<Integer>();
        for (int i = 0; i < this.cards.size(); i++) {
            this.indexes.add(i);
        }
        Collections.shuffle(this.indexes);
        this.currentCard=this.cards.size()-1;
        this.sceneController.switchtoLearnView();
    }

    /**
     * Go to the "Creation" View
     * @throws Exception
     */
    public void switchtoCreationView() throws Exception{
        this.sceneController.switchtoCreationView();
    }

    /**
     * Go to the "Stats" View
     * @throws Exception
     */
    public void switchtoStatView() throws Exception{
        this.sceneController.switchtoStatView();
    }

    /**
     * Go to the "QCM" View
     * @throws Exception
     */
    public void switchtoQCMView() throws Exception{
        this.indexes = new ArrayList<Integer>();
        for (int i = 0; i < this.cards.size(); i++) {
            this.indexes.add(i);
        }
        resetQCM();
        this.currentCard=this.cards.size()-1;
        Collections.shuffle(this.indexes);
        this.sceneController.switchtoQCMView();
    }

    /**
     * Go to the "Global" View
     * @throws Exception
     */
    public void switchtoGlobalView() throws Exception{
        this.sceneController.switchtoGlobalView();
    }

    public void switchtoFillBlankView() throws Exception {
        this.indexes = new ArrayList<Integer>();
        for (int i = 0; i < this.cards.size(); i++) {
            this.indexes.add(i);
        }
        resetQCM();
        this.currentCard=this.cards.size()-1;
        Collections.shuffle(this.indexes);
        this.sceneController.switchtoFillBlankView();
    }
    


    /**
     * Create a dictionnary of the stack of cards
     */
    public void createCardsDictionnary(){
        this.stackOfCardsDictionnary.clear();
        for(int i = 0; i < this.cards.size(); i++){
            ArrayList<String> cardDictionnary = new ArrayList<String>();
            Card c = this.cards.get(i);
            cardDictionnary.add(c.getQuestion());
            cardDictionnary.add(c.getAnswer());
            cardDictionnary.add(c.getNbSuccess()+"");
            cardDictionnary.add(c.getNbFail()+"");
            cardDictionnary.add(c.getLearnt()+"");
            cardDictionnary.add(c.getMedia());
            stackOfCardsDictionnary.add(cardDictionnary);
        }
    }

    /**
     * Getter for the stack of cards dictionnary
     *@return the stack of cards dictionnary
     */
    public ArrayList<ArrayList<String>> getStackOfCardsDictionnary(){
        return this.stackOfCardsDictionnary;
    }
    /**
     * Saves the stack of cards as a json file
     */
    public void save(){
        createCardsDictionnary();
        this.jsonEncode.save();
        this.notifyObserver();
    }

    /**
     * Saves the stats of this stack of cards as a json file
     */
    public void savestat(){
        this.jsonEncode.savestat();
    }
    
    
    /**
     * Loads a stack of cards from a json file
     * @param fileName the name of the file to load
     */
    public void load(String fileName){
        ArrayList<ArrayList<String>> stack = this.jsonEncode.load(fileName);
        this.stackName = stack.get(0).get(0);
        this.desc = stack.get(0).get(1);
        for (int i=1;  i<stack.size(); i++){
            this.cards.get(i-1).setQuestion(stack.get(i).get(0));
            this.cards.get(i-1).setAnswer(stack.get(i).get(1));
            this.cards.get(i-1).setNbSuccess(Integer.parseInt(stack.get(i).get(2)));
            this.cards.get(i-1).setNbFail(Integer.parseInt(stack.get(i).get(3)));
            this.cards.get(i-1).setLearnt(Boolean.parseBoolean(stack.get(i).get(4)));
            if (stack.get(i-1).size() == 0) {
                this.cards.get(i-1).setMedia("");
            } else {
                this.cards.get(i-1).setMedia(stack.get(i).get(5));

            }
        }
        if(this.sceneFlag==1|this.sceneFlag==5){
            this.currentCard=this.cards.size()-1;
        }else{
            this.currentCard = 0;
        }
        
        this.indexes = new ArrayList<Integer>();
        for (int i = 0; i < this.cards.size(); i++) {
            this.indexes.add(i);
        }
        for(int i=0; i<this.cards.size(); i++){
            this.cards.get(i).setNbSuccess(0);
            this.cards.get(i).setNbFail(0);
            this.cards.get(i).setLearnt(false);
        }
    }

    /**
     * Loads the stats of a stack of cards from a json file
     * @param fileName the name of the file to load
     */
    public ArrayList<ArrayList<String>> loadstat() {
        ArrayList<ArrayList<String>> stack = this.jsonEncode.loadstat();
        return stack;
    }

    /**
     * Resets the stack
     */
    public void reset(){
        this.cards.clear();
        this.setCurrentCardSansNotifier(0);
        this.addCard();
        this.indexes = new ArrayList<Integer>();
        this.indexes.add(0);
        this.notifyObserver();
    }

    /**
     * Imports a stack from a json file
     */
    public void importStack(String fileName){
        String homepath = System.getProperty("user.home");
        this.cards.clear();
        this.currentCard = 0;        
        this.stackName=fileName;
        File file = new File(homepath + "/codingandchill/data/" +stackName+".json");
        if (file.exists() && !file.isDirectory()) {
            for (int i=0;i<jsonEncode.getNbCards(this.stackName);i++){
                this.cards.push(new Card());
            }
        }
        if (this.getCards().isEmpty()) {
            this.addCard();
        }
        load(fileName);
        this.notifyObserver();
    }

    public ArrayList<Integer> getIndexes() {
        return indexes;
    }

    /**
     * Resets the learning state of the stack and shuffles the indexes.
     */
    public void resetLearning() {
        for (int i = 0; i < this.cards.size(); i++) {
            this.cards.get(i).setLearnt(false);
        }
        Collections.shuffle(this.indexes);
        this.notifyObserver();
    }

    public void resetQCM() {
        for (int i = 0; i < this.cards.size(); i++) {
            this.cards.get(i).setQCMAnswered(0);
        }
        Collections.shuffle(this.indexes);
        this.notifyObserver();
    }

    public void resetQCMwithoutNotify(){
        for (int i = 0; i < this.cards.size(); i++) {
            this.cards.get(i).setQCMAnswered(0);
        }
        Collections.shuffle(this.indexes);
    }

    public ArrayList<String> getAllStackNames(){
        return this.jsonEncode.getStackNames();
    }
}