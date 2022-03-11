import java.util.Comparator;

public class Ticket {

    private Event event;
    private String hall;
    private String row;
    private int place;
    private String date;
    private String hour;
    private double price;
    private double value;
    private String type;
    private int discount;

    public Ticket(Event event, String hall, String row, int place, String date, String hour, double price) {
        this.event = event;
        this.hall = hall;
        this.row = row;
        this.place = place;
        this.date = date;
        this.hour = hour;
        this.price = price;
        this.value = price;
        this.type = "Normalny";
        this.discount = 0;
    }

    public Event getEvent() {
        return event;
    }

    public String getEventName() {
        return event.getName();
    }

    public String getRow() {
        return row;
    }

    public int getPlace() {
        return place;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return hour;
    }

    public double getPrice() {
        return price;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getType() {return type;}

    public void setType(String type) {
        this.type = type;
    }

    public int getDiscount() {return discount;}

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
