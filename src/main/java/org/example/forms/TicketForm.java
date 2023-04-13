package org.example.forms;

import org.example.exceptions.FormException;
import org.example.exceptions.LimitException;
import org.example.exceptions.MustBeNotEmpyException;
import org.example.exceptions.ScriptInputException;
import org.example.models.Coordinates;
import org.example.models.Event;
import org.example.models.Ticket;
import org.example.models.TicketType;
import org.example.managers.CollectionManager;
import org.example.utility.Shell;
import org.example.utility.UserInput;


import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class TicketForm extends Form<Ticket> {
    private Shell shell;
    private CollectionManager collectionManager;

    private final double MIN_PRICE = 0;
    private final double MIN_DISCOUNT = 0;

    public TicketForm(Shell shell, CollectionManager collectionManager) {
        this.shell = shell;
        this.collectionManager = collectionManager;
    }

    @Override
    public Ticket build() throws FormException, ScriptInputException {
        Ticket ticket = new Ticket(
                askName(),
                askCoordinates(),
                LocalDateTime.now(),
                askPrice(),
                askDiscount(),
                askType(),
                askEvent()
        );
        if (!ticket.validate()) throw new ScriptInputException();
        return ticket;
    }

    private String askName() throws ScriptInputException {
        String name;
        var fileMode = UserInput.fileMode();
        while (true) {
            try {
                shell.println("Введите название билета:");
                shell.ps2();

                name = UserInput.getUserScanner().nextLine().trim();
                if (fileMode) shell.println(name);
                if (name.equals("")) throw new MustBeNotEmpyException();
                break;
            } catch (NoSuchElementException e) {
                shell.println("название не распознано");
            } catch (MustBeNotEmpyException e) {
                shell.println("название не можеть быть пустым");
                if (fileMode) throw new ScriptInputException();
            } catch (IllegalStateException e) {
                shell.println("непредвиденная ошибка");
            }
        }
        return name;
    }

    private Coordinates askCoordinates() throws ScriptInputException, FormException {
        return new CoordinatesForm(shell).build();
    }

    private Double askPrice() throws ScriptInputException {
        var fileMode = UserInput.fileMode();
        Double price;
        while (true) {
            try {
                shell.println("Введите цену продукта:");
                shell.ps2();

                var strPrice = UserInput.getUserScanner().nextLine().trim();
                if (fileMode) shell.println(strPrice);

                price = Double.parseDouble(strPrice);
                if (price < MIN_PRICE) throw new LimitException();
                break;
            } catch (NoSuchElementException e) {
                shell.printErr("Цена продукта не распознана!");
                if (fileMode) throw new ScriptInputException();
            } catch (LimitException e) {
                shell.printErr("Цена продукта должна быть больше нуля!");
                if (fileMode) throw new ScriptInputException();
            } catch (NumberFormatException e) {
                shell.printErr("Цена продукта должна быть представлена числом!");
                if (fileMode) throw new ScriptInputException();
            } catch (NullPointerException | IllegalStateException e) {
                shell.printErr("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return price;
    }

    private float askDiscount() throws ScriptInputException {
        float discount;
        var fileMode = UserInput.fileMode();
        while (true) {
            try {
                shell.println("Введите скидку:");
                shell.ps2();

                String strDiscount = UserInput.getUserScanner().nextLine().trim();
                if (fileMode) shell.println(strDiscount);

                discount = Float.parseFloat(strDiscount);
                if (discount < MIN_DISCOUNT) throw new LimitException();
                break;
            } catch (NoSuchElementException e) {
                shell.printErr("Скидка на продукт не распознана!");
                if (fileMode) throw new ScriptInputException();
            } catch (LimitException e) {
                shell.printErr("Скидка на продукт должна быть больше нуля!");
                if (fileMode) throw new ScriptInputException();
            } catch (NumberFormatException e) {
                shell.printErr("Скидка на продукт должна быть представлена числом!");
                if (fileMode) throw new ScriptInputException();
            } catch (NullPointerException | IllegalStateException e) {
                shell.printErr("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return discount;
    }

    private TicketType askType() throws FormException, ScriptInputException {
        return new TicketTypeForm(shell).build();
    }

    private Event askEvent() throws ScriptInputException, FormException {
        return new EventForm(shell, collectionManager).build();
    }
}
