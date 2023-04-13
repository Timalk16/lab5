package org.example.managers;

import org.example.models.Event;
import org.example.models.Ticket;
import org.example.utility.Shell;


import java.time.LocalDateTime;
import java.util.*;

public class CollectionManager {
    private Set<Ticket> collection = new LinkedHashSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private FileManager fileManager;

    public CollectionManager(FileManager fileManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.fileManager = fileManager;

        loadCollection();
    }

    public void validateAll(Shell shell) {
        Event.allEvents().values().forEach(event -> {
            if (!event.validate()) {
                shell.printErr("Событие с id=" + event.getId() + " имеет невалидные поля.");
            }
        });

        (new ArrayList<>(this.getCollection())).forEach(ticket -> {
            if (!ticket.validate()) {
                shell.printErr("Билет с id=" + ticket.getId() + " имеет невалидные поля.");
            }
        });
        shell.println("поля валидны!");
    }

    public Set<Ticket> getCollection() {
        return collection;
    }

    /**
     * @return Последнее время инициализации.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Последнее время сохранения.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Имя типа коллекции.
     */
    public String collectionType() {
        return collection.getClass().getName();
    }

    /**
     * @return Размер коллекции.
     */
    public int collectionSize() {
        return collection.size();
    }

    //!!!!!
    public void addElement(Ticket elem) {
        collection.add(elem);
        Ticket.touchNextId();
    }

    public void saveCollection(String fileName) {
        fileManager.writeCollection(collection, fileName);
        lastSaveTime = LocalDateTime.now();
    }

    public void clearCollection() {
        collection.clear();
    }

    public Ticket getById(int id) {
        for (Ticket ticket: collection) {
            if (ticket.getId() == id) return ticket;
        }
        return null;
    }

    public void removeFromCollection(Ticket ticket) {
      collection.remove(ticket);
    }


    /**
     * Загружает коллекцию
     */
    private void loadCollection() {
        collection = (LinkedHashSet<Ticket>) fileManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    public void shuffleCollection() {
        ArrayList<Ticket> list = new ArrayList<>(collection);
        Collections.shuffle(list);
        collection = new LinkedHashSet<>(list);
    }

    public void addElementAtIndex(int index, Ticket elem) {
        List<Ticket> list = new ArrayList<>(collection);
        list.add(index, elem);
        collection.clear();
        collection.addAll(list);
    }

}
