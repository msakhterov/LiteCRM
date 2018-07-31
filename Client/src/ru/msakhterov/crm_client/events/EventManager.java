package ru.msakhterov.crm_client.events;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private List<EventListener> listeners;

    public EventManager(){
        this.listeners = new ArrayList<>();
    }

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public void removeListeber(EventListener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners(EventType type) {
        for (EventListener listener : listeners) {
            listener.execute(type);
        }
    }
}
