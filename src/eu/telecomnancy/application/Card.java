package eu.telecomnancy.application;

public class Card {
    
    /**
     * The question that's going to be displayed
     */
    private String question;

    /**
     * The answer to the question
     */
    private String answer;

    /**
     * The number of success during the session. Reset at the end of the learning session
     */
    private int nbSuccess;

    /**
     * The number of fails during the session. Reset at the end of the learning session
     */
    private int nbFail;

    /**
     * Stores if the card has been learnt or not, updates when nbSuccess >= 2.
     */
    private boolean learnt;

    /**
     * Stores the path to the media file. If there is no media, it's an empty string.
     */
    private String media;

    /**
     * Stores the fact that the user has seen the card or not. 0 means not seen, 1 means correct, -1 means incorrect. Used by both QCM and FillBlank modes.
     */
    private int QCMAnswered = 0;


    /**
     * Constructor for the Card class, with the attributes
     * @param question the question
     * @param reponse the answer
     * @param succes the nbSuccess
     * @param rate the nbFail
     */
    public Card(String question, String reponse, int succes, int rate) {
        this.question = question;
        this.answer = reponse;
        this.nbSuccess = succes;
        this.nbFail = rate;
        this.media = "";
        this.learnt = false;
    }

    /**
     * Constructor for the Card class, with no attributes
     */
    public Card() {
        this.question = "La question sera ici !";
        this.answer = "La réponse sera là !";
        this.nbSuccess = 0;
        this.nbFail = 0;
        this.media = "";   
        this.learnt = false; 
    }

    /**
     * Getter for the question attribute
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Setter for the question attribute
     * @param question the question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Getter for the answer attribute
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Setter for the answer attribute
     * @param answer the answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Getter for the nbSuccess attribute
     * @return the nbSuccess
     */
    public int getNbSuccess() {
        return nbSuccess;
    }

    /**
     * Setter for the nbSuccess attribute
     * @param nbSuccess the nbSuccess
     */
    public void setNbSuccess(int nbSuccess) {
        this.nbSuccess = nbSuccess;
    }

    /**
     * Getter for the nbFail attribute
     * @return the nbFail
     */
    public int getNbFail() {
        return nbFail;
    }

    /**
     * Setter for the nbFail attribute
     * @param nbFail the nbFail
     */
    public void setNbFail(int nbFail) {
        this.nbFail = nbFail;
    }

    /**
     * Setter for the learnt attribute
     * @param learnt the learnt
     */
    public void setLearnt(Boolean learnt) {
        this.learnt = learnt;
    }

    /**
     * Getter for the learnt attribute
     * @return the learnt
     */
    public boolean getLearnt() {
        return learnt;
    }

    /**
     * Method to add a success to the card, update the learnt attribute if needed
     */
    public void addSuccess() {
        this.nbSuccess++;
        if(nbSuccess >= 2) {
            this.learnt = true;
        }
    }

    /**
     * Method to add a fail to the card
     */
    public void addFail() {
        this.nbFail++;
    }

    /**
     * Method to check if another card is equal to this one. Equal means same question and same answer.
     * @param carte the card the check
     * @return true if the cards are equal, false otherwise
     */
    public Boolean equals(Card carte) {
        return this.question.equals(carte.getQuestion()) && this.answer.equals(carte.getAnswer());
    }

    /**
     * Getter for the media attribute
     * @return the media
     */
    public String getMedia() {
        return this.media;
    }

    /**
     * Setter for the media attribute
     * @param media the media
     */
    public void setMedia(String media) {
        this.media = media;
    }

    /**
     * Getter for QCMAnswered
     * @return the QCMAnswered
     */
    public int getQCMAnswered() {
        return QCMAnswered;
    }

    /**
     * Setter for QCMAnswered. Sets only if i is -1, 0 or 1.
     * @param i the value we want to set. 
     */
    public void setQCMAnswered(int i) {
        if(i == 0 || i == 1 || i == -1) {
            this.QCMAnswered = i;
        }
    }
}
