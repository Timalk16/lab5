package org.example.models;

public enum TicketType {
    VIP,
    USUAL,
    BUDGETARY,
    CHEAP;


    public static String names() {
        StringBuilder namesList = new StringBuilder();
        for (var ticketType: values()) {
            namesList.append(ticketType.name()).append(", ");
        }
        return namesList.substring(0, namesList.length()-2);
    }
}
