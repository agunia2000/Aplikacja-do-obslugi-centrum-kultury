import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EventsPanelManager {

    private DecimalFormat df2 = new DecimalFormat("#,##0.00");

    private JPanel cardPanel;
    private JPanel eventsPanel;
    private JPanel addEventMainPanel;
    private JPanel hallMainPanel;

    private AddEventPanelManager addEventPanelManage;
    private HallPanelManager hallPanelManage;
    private JComboBox filterComboBox;
    private JComboBox sortComboBox;
    private JTextField searchEventTextField;
    private JButton searchEventButton;

    public ArrayList<Event> getEvents() {
        return events;
    }

    private ArrayList<Event> events = new ArrayList();

    public EventsPanelManager(JPanel cardPanel, JPanel eventContentPanel, JPanel addEventMainPanel, AddEventPanelManager addEventPanelManage, JPanel hallMainPanel, JComboBox filterComboBox, JComboBox sortComboBox, JTextField searchEventTextField, JButton searchEventButton) {
        this.cardPanel = cardPanel;
        this.eventsPanel = eventContentPanel;
        this.addEventMainPanel = addEventMainPanel;
        this.addEventPanelManage = addEventPanelManage;
        this.hallMainPanel = hallMainPanel;
        this.filterComboBox = filterComboBox;
        this.sortComboBox = sortComboBox;
        this.searchEventTextField = searchEventTextField;
        this.searchEventButton = searchEventButton;

        ((JLabel)sortComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)filterComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        sortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sortComboBox.getSelectedItem().equals("nazwa")){
                    events.sort(Comparator.comparing(Event::getName));
                    showEvents();
                }
                if (sortComboBox.getSelectedItem().equals("min. wiek")) {
                    events.sort(Comparator.comparing(Event::getMinimalAge));
                    showEvents();
                }
                if (sortComboBox.getSelectedItem().equals("cena")) {
                    events.sort(Comparator.comparing(Event::getDoubleOfPriceOfTickets));
                    showEvents();
                }
				if (sortComboBox.getSelectedItem().equals("wolne miejsca")) {
                    events.sort(Comparator.comparing(Event::getFreePlaces));
                    showEvents();
                }
            }
        });

        filterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!filterComboBox.getSelectedItem().equals("filtruj")){
                    selectEventType(String.valueOf(filterComboBox.getSelectedItem()));
                }
                if(filterComboBox.getSelectedItem().equals("wszystko")){
                    selectEvents();
                }
            }
        });

        searchEventTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                searchEventTextField.setText("");
                searchEventTextField.setFont(new Font("Arial", Font.PLAIN,15));
                searchEventTextField.setForeground(new Color(20,20,20));
            }
        });

        searchEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectEventName(searchEventTextField.getText());
            }
        });
    }

    public void setHallPanelManager(HallPanelManager hallPanelManage) {
        this.hallPanelManage = hallPanelManage;
    }

    public void selectEvents() {
        try {
            Connection connection = ConnectionToDB.getConnection();

            String sql = "SELECT * FROM events";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            events.clear();
            Hall sala = new Hall("S1",8,12);
            while (result.next()){
                for (Hall hall : hallPanelManage.getHalls()){
                    if (result.getString(8).equals(hall.getName())){
                        sala = hall;
                    }
                }
                events.add(new Event(result.getInt(1),result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), sala, result.getString(9), result.getInt(10),result.getString(11), getStringToArrayList(result.getString(12)) ));
            }

            statement.close();
            result.close();
        } catch (SQLException var) {
            var.printStackTrace();
        }
        showEvents();
    }

    private void selectEventType(String type){
        try {
            Connection connection = ConnectionToDB.getConnection();

            String sql = "SELECT * FROM events WHERE type = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,type);
            ResultSet result = preparedStatement.executeQuery();

            events.clear();
            Hall sala = new Hall("S1",8,12);
            while (result.next()){
                for (Hall hall : hallPanelManage.getHalls()){
                    if (result.getString(8).equals(hall.getName())){
                        sala = hall;
                    }
                }
                events.add(new Event(result.getInt(1),result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), sala, result.getString(9), result.getInt(10),result.getString(11), getStringToArrayList(result.getString(12)) ));
            }

            preparedStatement.close();
            result.close();

        } catch (SQLException var) {
            var.printStackTrace();
        }
        showEvents();
    }

    private void selectEventName(String searchedPhrase) {
        try {
            Connection connection = ConnectionToDB.getConnection();

            String sql = "SELECT * FROM events WHERE name LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"%"+searchedPhrase+"%");
            ResultSet result = preparedStatement.executeQuery();

            events.clear();
            Hall sala = new Hall("S1",8,12);
            while (result.next()){
                for (Hall hall : hallPanelManage.getHalls()){
                    if (result.getString(8).equals(hall.getName())){
                        sala = hall;
                    }
                }
                events.add(new Event(result.getInt(1),result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), sala, result.getString(9), result.getInt(10),result.getString(11), getStringToArrayList(result.getString(12)) ));
            }

            preparedStatement.close();
            result.close();

        } catch (SQLException var) {
           var.printStackTrace();
        }
        showEvents();
    }


    private void deleteEvent(Event event) {
        try {
            Connection connection = ConnectionToDB.getConnection();

            String sql = "DELETE FROM events WHERE eventID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,event.getEventID());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException var) {
            var.printStackTrace();
        }
    }

    private ArrayList<String> getStringToArrayList(String takenPlacesString){
        ArrayList<String> takenPlaces = new ArrayList();
        String temp = "";
        for(int i = 0 ; i < takenPlacesString.length(); i++){
            if (takenPlacesString.charAt(i)==','){
                takenPlaces.add(temp);
                temp = "";
            } else {
                temp = temp + takenPlacesString.charAt(i);
            }
        }
        return takenPlaces;
    }

    public void showEvents() {
        eventsPanel.setVisible(false);
        eventsPanel.setVisible(true);
        resetSearchEventTextField();

        eventsPanel.removeAll();
        eventsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        eventsPanel.setPreferredSize(new Dimension(1250, events.size() * 120));

        for (Event event : events) {
            JPanel oneEventPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            oneEventPanel.setPreferredSize(new Dimension(1200, 115));
            oneEventPanel.setBackground(new Color(250, 180, 20));
            oneEventPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));

            JPanel eventHeadersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            eventHeadersPanel.setPreferredSize(new Dimension(1150, 50));
            eventHeadersPanel.setBackground(new Color(250, 180, 20));
            JLabel titleHeaderLabel = new JLabel();
            modifyLabel(titleHeaderLabel, "nazwa wydarzenia", 12, 150, 30, eventHeadersPanel);
            JLabel hallHeaderLabel = new JLabel();
            modifyLabel(hallHeaderLabel, "sala", 12, 50, 30, eventHeadersPanel);
            JLabel freePlacesHeaderLabel = new JLabel();
            modifyLabel(freePlacesHeaderLabel, "wolne miejsca", 12, 90, 30, eventHeadersPanel);
            JLabel dateHeaderLabel = new JLabel();
            modifyLabel(dateHeaderLabel, "data", 12, 100, 30, eventHeadersPanel);
            JLabel hourHeaderLabel = new JLabel();
            modifyLabel(hourHeaderLabel, "godzina", 12, 80, 30, eventHeadersPanel);
            JLabel priceHeaderLabel = new JLabel();
            modifyLabel(priceHeaderLabel, "cena biletu", 12, 80, 30, eventHeadersPanel);
            JLabel typeHeaderLabel = new JLabel();
            modifyLabel(typeHeaderLabel, "kategoria", 12, 80, 30, eventHeadersPanel);
            JLabel minAgeHeaderLabel = new JLabel();
            modifyLabel(minAgeHeaderLabel, "min. wiek", 12, 70, 30, eventHeadersPanel);
            JButton editEventButton = new JButton();
            modifyButton(editEventButton, "Edytuj", 120, 25, eventHeadersPanel);
            oneEventPanel.add(eventHeadersPanel);
            JButton showPosterButton = new JButton();
            modifyButton(showPosterButton, "Pokaż plakat", 120, 25, eventHeadersPanel);
            oneEventPanel.add(eventHeadersPanel);

            JPanel eventContentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            eventContentPanel.setPreferredSize(new Dimension(1150, 50));
            eventContentPanel.setBackground(new Color(250, 180, 20));
            JLabel titleContentLabel = new JLabel();
            modifyLabel(titleContentLabel, event.getName(), 17, 150, 30, eventContentPanel);
            JLabel hallContentLabel = new JLabel();
            modifyLabel(hallContentLabel, event.getHall().getName(), 16, 50, 30, eventContentPanel);
            JLabel freePlacesContentLabel = new JLabel();
            modifyLabel(freePlacesContentLabel, String.valueOf(event.getFreePlaces()), 15, 90, 30, eventContentPanel);
            JLabel dateContentLabel = new JLabel();
            modifyLabel(dateContentLabel, event.getDate(), 15, 100, 30, eventContentPanel);
            JLabel hourContentLabel = new JLabel();
            modifyLabel(hourContentLabel, event.getTime(), 15, 80, 30, eventContentPanel);
            JLabel priceContentLabel = new JLabel();
            modifyLabel(priceContentLabel, (df2.format(event.getDoubleOfPriceOfTickets())) + " PLN", 15, 80, 30, eventContentPanel);
            JLabel typeLabel = new JLabel();
            modifyLabel(typeLabel, event.getType(), 15, 80, 30, eventContentPanel);
            JLabel minAgeLabel = new JLabel();
            modifyLabel(minAgeLabel, event.getStringOfMinimalAge(), 15, 70, 30, eventContentPanel);
            JButton deleteEventButton = new JButton();
            modifyButton(deleteEventButton, "Usuń", 120, 25, eventContentPanel);
            oneEventPanel.add(eventContentPanel);
            JButton choosePlacesButton = new JButton();
            modifyButton(choosePlacesButton, "Wybierz bilety", 120, 25, eventContentPanel);
            oneEventPanel.add(eventContentPanel);

            eventsPanel.add(oneEventPanel);

            editEventButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addEventPanelManage.editEvent(event);
                    cardPanel.removeAll();
                    cardPanel.add(addEventMainPanel);
                    cardPanel.repaint();
                    cardPanel.revalidate();
                }
            });

            showPosterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new PreviewPosterFrame(event);
                }
            });

            deleteEventButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteEvent(event);
                    events.remove(event);
                    showEvents();
                    eventsPanel.setVisible(false);
                    eventsPanel.setVisible(true);
                }
            });

            choosePlacesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardPanel.removeAll();
                    cardPanel.add(hallMainPanel);
                    cardPanel.repaint();
                    cardPanel.revalidate();
                    hallPanelManage.showPlaces(event);
                }
            });
        }
    }

    public void resetEventPanelComboBox(){
        filterComboBox.setSelectedIndex(0);
        sortComboBox.setSelectedIndex(0);
    }

    private void resetSearchEventTextField(){
        searchEventTextField.setText("szukaj wydarzenia");
        searchEventTextField.setFont(new Font("Arial", Font.PLAIN,11));
        searchEventTextField.setForeground(new Color(100,100,100));
    }

    private void modifyLabel(JLabel label, String text, int fontSize, int width, int height, JPanel destinationPanel) {
        label.setText(text);
        label.setForeground(new Color(0, 0, 0));
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(width, height));
        destinationPanel.add(label);
    }

    private void modifyButton(JButton button, String text, int width, int height, JPanel destinationPanel) {
        button.setText(text);
        button.setForeground(new Color(0, 0, 0));
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(new Color(240, 240, 240));
        destinationPanel.add(button);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
    }


}



