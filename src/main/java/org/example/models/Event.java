package org.example.models;

import org.example.managers.CollectionManager;
import org.example.utility.Element;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Event extends Element {
    private static Long nextId = 1L;
    private static transient Map<Long, Event> events = new HashMap<>();
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDate date; //Поле не может быть null
    private int minAge;
    private String description; //Поле не может быть null

    public Event(String name, LocalDate date, int minAge, String description) {
        this.id = nextId;
        nextId++;
        this.name = name;
        this.date = date;
        this.minAge = minAge;
        this.description = description;
        events.put(this.id, Event.byId(id));
    }



    public static void updateNextId(CollectionManager collectionManager) {
        collectionManager
                .getCollection()
                .stream()
                .map(Ticket::getEvent)
                .filter(Objects::nonNull);

    }

    public static Map<Long, Event> allEvents() {
        return events;
    }

    @Override
    public boolean validate() {
        if (id == null || id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (date == null) return false;
        return description != null;
    }

    @Override
    public long getId() {
        return id;
    }

    public static Event byId(Long id) {
        return events.get(id);
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMinAge() {
        return minAge;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return date == event.date && Objects.equals(id, event.id) && Objects.equals(name, event.name) && minAge == event.minAge && Objects.equals(description, event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, date, minAge);
    }

    @Override
    public String toString() {
        return "Событие \"" + name+ "\" №" + id +
                "; Дата: " + date +
                "; Описание: " + description +
                "; Мин возраст: " + minAge;
    }

    @Override
    public int compareTo(Element o) {
        return 0;
    }
}
