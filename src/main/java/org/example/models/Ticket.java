package org.example.models;

import org.example.managers.CollectionManager;
import org.example.utility.Element;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket extends Element {
    private static long nextId = 1;
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double price; //Значение поля должно быть больше 0
    private float discount; //Значение поля должно быть больше 0, Максимальное значение поля: 100
    private TicketType type; //Поле не может быть null
    private org.example.models.Event event; //Поле не может быть null

    public Ticket(String name, Coordinates coordinates, LocalDateTime creationDate, double price, float discount, TicketType type, org.example.models.Event event) {
        this.id = nextId;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.discount = discount;
        this.type = type;
        this.event = event;
    }


    /**
     * валидация правильности полей
     *
     * @return true, если все верно, иначе false
     */
    @Override
    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (price <= 0) return false;
        if (discount <= 0 || discount > 100) return false;
        return true;
    }

    public void update(Ticket ticket) {
        this.name = ticket.name;
        this.coordinates = ticket.coordinates;
        this.creationDate = ticket.creationDate;
        this.price = ticket.price;
        this.discount = ticket.discount;
        this.type = ticket.type;
        this.event = ticket.event;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getPrice() {
        return price;
    }

    public Float getDiscount() {
        return discount;
    }

    public TicketType getType() {
        return type;
    }

    public Event getEvent() {
        return event;
    }

    public static void touchNextId() {
        nextId++;
    }

    public static void updateNextId(CollectionManager collectionManager) {
        var maxId = collectionManager
                .getCollection()
                .stream().filter(Objects::nonNull)
                .map(Ticket::getId)
                .mapToInt(Long::intValue).max().orElse(0);
        nextId = maxId + 1;
    }

    @Override
    public int compareTo(Element element) {
        return (int) (this.id - element.getId());
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && Objects.equals(name, ticket.name) && Objects.equals(coordinates, ticket.coordinates)
                && Objects.equals(creationDate, ticket.creationDate) && Objects.equals(price, ticket.price)
                && Objects.equals(discount, ticket.discount) && type == ticket.type
                && Objects.equals(event, ticket.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, price, discount, type, event);
    }


    // поправить
    @Override
    public String toString() {
        String info = "";
        info += "Билет №" + id;
        info += " (добавлен " + creationDate.toString() + ")";
        info += "\n Название: " + name;
        info += "\n Местоположение: " + coordinates;
        info += "\n Цена: " + price;
        return info;
    }
}