import java.util.ArrayList;
import java.util.Comparator;

public class Event {

    private int eventID;
    private String name;
    private String date;
    private String time;
    private String priceOfTickets;
    private String description;
    private String type;
    private Hall hall;
    private String occupancyOfHall;
    private int minimalAge;
    private String pathToPoster;
    private ArrayList<String> takenPlaces;

    public int getEventID() {
        return eventID;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPriceOfTickets() {
        return priceOfTickets;
    }

    public double getDoubleOfPriceOfTickets() {
        return Double.valueOf(priceOfTickets);
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Hall getHall() {
        return hall;
    }

    public String getOccupancyOfHall() {
        return occupancyOfHall;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public String getStringOfMinimalAge(){
        String minAge;
        if(minimalAge==0) minAge = "-";
        else minAge = String.valueOf(minimalAge);
        return minAge;
    }

    public String getPathToPoster() {
        return pathToPoster;
    }

    public ArrayList<String> getTakenPlaces() {
        return takenPlaces;
    }

    public Event(int eventID, String name, String date, String time, String priceOfTickets, String description, String type, Hall hall, String occupancyOfHall, int minimalAge, String pathToPoster, ArrayList<String> takenPlaces) {
        this.eventID = eventID;
        this.name = name;
        this.date = date;
        this.time = time;
        this.priceOfTickets = priceOfTickets;
        this.description = description;
        this.type = type;
        this.hall = hall;
        this.occupancyOfHall = occupancyOfHall;
        this.minimalAge = minimalAge;
        this.takenPlaces = takenPlaces;
        this.pathToPoster = pathToPoster;
    }

    public int getFreePlaces() {
        int freePlaces;
        if (occupancyOfHall.equals("50%")) {
            freePlaces = hall.getNumberOfPlacesInRows() * getHall().getNumberOfRows() / 2 - getTakenPlaces().size();
        } else {
            freePlaces = hall.getNumberOfPlacesInRows() * getHall().getNumberOfRows() - getTakenPlaces().size();
        }
        return freePlaces;
    }
}
