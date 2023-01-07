package eu.telecomnancy.application;
public interface Observable {

    public void notifyObserver();

    public void addObserver(Observer o);
    
}
