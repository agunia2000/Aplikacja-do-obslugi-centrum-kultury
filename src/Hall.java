import javax.swing.*;

public class Hall {

    private String name;
    private int numberOfRows;
    private int numberOfPlacesInRows;

    public Hall(String name, int numberOfRows, int numberOfPlacesInRows) {
        this.name = name;
        this.numberOfRows = numberOfRows;
        this.numberOfPlacesInRows = numberOfPlacesInRows;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlacesInRows() {
        return numberOfPlacesInRows;
    }
}
