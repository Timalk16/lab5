package org.example.models;

import org.example.utility.Validatable;

import java.util.Objects;

public class Coordinates implements Validatable {
    private Long x; //Поле не может быть null
    private int y;

    public Coordinates(Long x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean validate() {
        if (x == null) return false;
        return true;
    }

    public Long getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
