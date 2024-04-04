package Midterm.DataObjectClasses;

public class Ticket {
    private int ticketID;
    private int price;
    private int scheduleID;

    public Ticket(int t, int p, int s) {
        this.ticketID = t;
        this.price = p;
        this.scheduleID = s;
    }

    public int getTicketID() {
        return ticketID;
    }

    public int getPrice() {
        return price;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public String toString() {
        String formattedString = String.format("%-13s %-9s %-20s", ticketID, price, scheduleID);
        return formattedString;
    }
}
