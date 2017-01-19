package org.yiwan.easy.bmproxy.subject;


import org.yiwan.easy.bmproxy.observer.Observer;

/**
 * Created by Kenny Wang on 3/14/2016.
 */
public interface Subject {

    public void attach(Observer observer);

    public void detach(Observer observer);

    public void nodifyObserversStart();

    public void nodifyObserversStop();

}
