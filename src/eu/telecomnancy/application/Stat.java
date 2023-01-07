package eu.telecomnancy.application;
import java.time.LocalDateTime;


public class Stat {

    /**
     * Saves the time of the learning session
     */
    private LocalDateTime time;

    /**
     * Saves the ratio of learning success
     */
    private double ratio;

    private String nameOfStack;

    /**
     * Constructor for the Stat class, with the attributes
     * @param time the time of the learning session
     * @param ratio the ratio of success for the session
     * @return a Stat object
     */

    public Stat(LocalDateTime time, double ratio, String nameOfStack) {
        this.time = time;
        this.ratio = ratio;
        this.nameOfStack = nameOfStack;
    }


    public String getNameOfStack() {
        return nameOfStack;
    }

    public void setNameOfStack(String nameOfStack) {
        this.nameOfStack = nameOfStack;
    }

    /**
     * Constructor for the Stat class, with no attributes
     * @return a Stat object
     */
    public Stat() {
        this.time = LocalDateTime.now();
        this.ratio = 0;
        this.nameOfStack = "";
    }

    /**
     * Getter for the time attribute
     * @return the time of the learning session
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Setter for the time attribute
     * @param time the time of the learning session
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Getter for the ratio attribute
     * @return the ratio of success for the session
     */
    public double getRatio() {
        return ratio;
    }

    /**
     * Setter for the ratio attribute
     * @param ratio the ratio of success for the session
     */
    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}