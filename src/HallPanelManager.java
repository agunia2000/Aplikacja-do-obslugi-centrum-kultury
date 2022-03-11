import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class HallPanelManager {

    private ArrayList<Hall> halls = new ArrayList();

    private ArrayList<JButton> chosenPlacesButtons = new ArrayList();
    private ArrayList<String> takenPlaces = new ArrayList();

    private JPanel placesPanel;

    private JLabel titleEventLabel;
    private JLabel dateEventLabel;
    private TicketPanelManager ticketPanelManager;


    public HallPanelManager(JPanel placesPanel, JLabel titleEventLabel, JLabel dateEventLabel, JButton dalejHallButton, JButton wsteczHallButton, TicketPanelManager ticketPanelManager, JPanel ticketsMainPanel, JPanel eventsMainPanel, JPanel cardPanel) {
        this.placesPanel = placesPanel;
        this.titleEventLabel = titleEventLabel;
        this.dateEventLabel = dateEventLabel;
        this.ticketPanelManager = ticketPanelManager;

        dalejHallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticketPanelManager.showTickets();
                cardPanel.removeAll();
                cardPanel.add(ticketsMainPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });

        wsteczHallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(eventsMainPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
    }

    public void showPlaces (Event event) {
        showTitleAndDate(event);
        chosenPlacesButtons.clear();
        ticketPanelManager.checkEventExist();

        placesPanel.removeAll();
        placesPanel.setLayout(new GridLayout(event.getHall().getNumberOfRows(), 1));

        for (int row = 1; row <= event.getHall().getNumberOfRows(); row++) {
                JPanel rowPanel = new JPanel();
                rowPanel.setBackground(new Color(230, 230, 230));
                rowPanel.setPreferredSize(new Dimension( 1200, 480/event.getHall().getNumberOfRows()));
                placesPanel.add(rowPanel);

           for (int place = 1; place <= event.getHall().getNumberOfPlacesInRows(); place++) {
               JButton placeButton = new JButton();
               placeButton.setBackground(new Color(0, 100, 150));
               placeButton.setForeground(new Color(255, 255, 255));
               placeButton.setText(String.valueOf(place));
               placeButton.setName((char) (64 + row) + String.valueOf(place));
               placeButton.setVerticalTextPosition(SwingConstants.CENTER);
               placeButton.setPreferredSize(new Dimension(950/event.getHall().getNumberOfPlacesInRows(), 400/event.getHall().getNumberOfRows()));
               rowPanel.add(placeButton);

               if(950/event.getHall().getNumberOfPlacesInRows() < 50){
                   placeButton.setFont(new Font("Arial",Font.BOLD, 9));
               }

               if (place == event.getHall().getNumberOfPlacesInRows() / 2) {
                   JLabel rowName = new JLabel();
                   rowName.setText("rząd " + (char) (64 + row));
                   rowName.setFont(new Font("Arial", Font.BOLD, 15));
                   rowName.setPreferredSize(new Dimension(65, 30));
                   rowName.setForeground(new Color(65, 75, 75));
                   rowName.setHorizontalAlignment(SwingConstants.CENTER);
                   rowPanel.add(rowName);
               }

               showExcludedPlaces(event, placeButton);
               showTakenPlaces(event, placeButton);

               showChosenPlaces(event, placeButton);


               placeButton.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       if (chosenPlacesButtons.contains(placeButton)) {
                           placeButton.setBackground(new Color(0, 100, 150));
                           chosenPlacesButtons.remove(placeButton);
                           if(!ticketPanelManager.getTickets().isEmpty()){
                               ticketPanelManager.getTickets().removeIf(ticket -> ticket.getEvent().getEventID()==event.getEventID() && ticket.getRow().equals(String.valueOf(placeButton.getName().charAt(0))) && ticket.getPlace() == Integer.valueOf(placeButton.getName().substring(1)));
                           }
                       } else if (!chosenPlacesButtons.contains(placeButton)) {
                           placeButton.setBackground(new Color(70, 160, 10));
                           chosenPlacesButtons.add(placeButton);
                           Ticket newTicket = new Ticket(event, event.getHall().getName(), String.valueOf(placeButton.getName().charAt(0)), Integer.valueOf(placeButton.getName().substring(1)), event.getDate(), event.getTime(), Double.valueOf(event.getPriceOfTickets()));
                           ticketPanelManager.getTickets().add(newTicket);

                       }
                   }
               });
           }
        }
    }

    private void showTakenPlaces(Event event, JButton placeButton){
        takenPlaces = event.getTakenPlaces();
        if(!takenPlaces.isEmpty()){
            for (String takenPlace : takenPlaces) {
                if ((int) placeButton.getName().charAt(0) == (int) takenPlace.charAt(0) &&  Integer.valueOf(placeButton.getName().substring(1)) == Integer.valueOf(takenPlace.substring(1))) {
                    placeButton.setBackground(new Color(250, 10, 20));
                    placeButton.setEnabled(false);
                }
            }
        }
    }

    private void showChosenPlaces(Event event, JButton placeButton){
        for (Ticket ticket : ticketPanelManager.getTickets()){
            if(ticket.getEvent().getEventID()==event.getEventID() && ticket.getRow().equals(String.valueOf(placeButton.getName().charAt(0))) && ticket.getPlace()==Integer.valueOf(placeButton.getName().substring(1))){
                placeButton.setBackground(new Color(70, 160, 10));
                chosenPlacesButtons.add(placeButton);
            }
        }
    }

    private void showTitleAndDate(Event event){
        titleEventLabel.setText(event.getName());
        dateEventLabel.setText(event.getDate() + " " + event.getTime());
    }

    private void showExcludedPlaces(Event event, JButton placeButton){
        if (event.getOccupancyOfHall().equals("50%")) {
            if (placeButton.getName().charAt(0) % 2 == 0 && Integer.valueOf(placeButton.getName().substring(1)) % 2 == 1) {
                placeButton.setBackground(new Color(250, 10, 20));
                placeButton.setEnabled(false);
            }
            if (placeButton.getName().charAt(0) % 2 == 1 && Integer.valueOf(placeButton.getName().substring(1)) % 2 == 0) {
                placeButton.setBackground(new Color(250, 10, 20));
                placeButton.setEnabled(false);
            }
        }
    }

    public void loadHalls(){
        try {
            Connection connection = ConnectionToDB.getConnection();

            String sql = "SELECT * FROM halls";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()){
                halls.add(new Hall(result.getString(1),result.getInt(2),result.getInt(3)));
            }
            statement.close();
            result.close();
        } catch (SQLException var) {
            var.printStackTrace();
        }
    }

    public ArrayList<Hall> getHalls() {
        return halls;
    }
}