import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class AddEventPanelManager {

    private DecimalFormat df2 = new DecimalFormat("#,##0.00");
    private Event editedEvent;

    private JTextField nameEventTextField;
    private JSpinner dayEventSpinner;
    private JSpinner monthEventSpinner;
    private JSpinner yearEventSpinner;
    private JSpinner hourEventSpinner;
    private JSpinner minutesEventSpinner;
    private JSpinner zlotyEventSpinner;
    private JSpinner bobsEventSpinner;
    private JTextArea descriptionEventTextArea;
    private JComboBox minAgeEventComboBox;
    private JComboBox typeEventComboBox;
    private JComboBox hallEventComboBox;
    private JComboBox occupancyEventComboBox;
    private JFormattedTextField posterPathField;
    private JButton wybierzObrazEventButton;

    public void setEventsPanelManager(EventsPanelManager eventsPanelManager) {
        this.eventsPanelManager = eventsPanelManager;
    }

    private EventsPanelManager eventsPanelManager;

    public AddEventPanelManager(JPanel cardPanel, JPanel eventsMainPanel, JTextField nameEventTextField, JSpinner dayEventSpinner, JSpinner monthEventSpinner, JSpinner yearEventSpinner, JSpinner hourEventSpinner, JSpinner minutesEventSpinner, JSpinner zlotyEventSpinner, JSpinner bobsEventSpinner, JTextArea descriptionEventTextArea, JComboBox minAgeEventComboBox, JComboBox typeEventComboBox, JComboBox hallEventComboBox, JComboBox occupancyEventComboBox, JFormattedTextField posterPathField, JButton wybierzObrazEventButton, JButton anulujAddEventButton, JButton potwierdzAddEventButton) {
        this.nameEventTextField = nameEventTextField;
        this.dayEventSpinner = dayEventSpinner;
        this.monthEventSpinner = monthEventSpinner;
        this.yearEventSpinner = yearEventSpinner;
        this.hourEventSpinner = hourEventSpinner;
        this.minutesEventSpinner = minutesEventSpinner;
        this.zlotyEventSpinner = zlotyEventSpinner;
        this.bobsEventSpinner = bobsEventSpinner;
        this.descriptionEventTextArea = descriptionEventTextArea;
        this.minAgeEventComboBox = minAgeEventComboBox;
        this.typeEventComboBox = typeEventComboBox;
        this.hallEventComboBox = hallEventComboBox;
        this.occupancyEventComboBox = occupancyEventComboBox;
        this.posterPathField = posterPathField;
        this.wybierzObrazEventButton = wybierzObrazEventButton;

        ((JLabel)typeEventComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)hallEventComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)occupancyEventComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel)minAgeEventComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        dayEventSpinner.setModel(new SpinnerNumberModel(1,1,31,1));
        monthEventSpinner.setModel(new SpinnerNumberModel(1,1,12,1));
        yearEventSpinner.setModel(new SpinnerNumberModel(2021,2021,2050,1));
        hourEventSpinner.setModel(new SpinnerNumberModel(0,0,24,1));
        minutesEventSpinner.setModel(new SpinnerNumberModel(0,0,60,1));
        zlotyEventSpinner.setModel(new SpinnerNumberModel(0,0,1000000,1));
        bobsEventSpinner.setModel(new SpinnerNumberModel(0,0,99,1));

        potwierdzAddEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkAllField()){
                    if(nameEventTextField.isEditable()) addEvent();
                    if(!nameEventTextField.isEditable()) updateEvent(editedEvent);
                    cardPanel.removeAll();
                    cardPanel.add(eventsMainPanel);
                    cardPanel.repaint();
                    cardPanel.revalidate();
                    eventsPanelManager.resetEventPanelComboBox();
                    eventsPanelManager.selectEvents();
                }
            }
        });

        anulujAddEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(eventsMainPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });

        wybierzObrazEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FileChooserFrame(posterPathField);
            }
        });

        nameEventTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                nameEventTextField.setBackground(new Color(255,255,255));
            }
        });

        typeEventComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeEventComboBox.setBackground(new Color(255,255,255));
            }
        });

        hallEventComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hallEventComboBox.setBackground(new Color(255,255,255));
            }
        });

        occupancyEventComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                occupancyEventComboBox.setBackground(new Color(255,255,255));
            }
        });

        minAgeEventComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minAgeEventComboBox.setBackground(new Color(255,255,255));
            }
        });
    }

    private void addEvent() {
        try {
            String sql = "INSERT INTO events VALUES (null,?,?,?,?,?,?,?,?,?,?,'')";
            PreparedStatement statement = ConnectionToDB.getConnection().prepareStatement(sql);
            statement.setString(1, nameEventTextField.getText());
            statement.setString(2, dateToString());
            statement.setString(3, timeToString());
            statement.setString(4, priceToString());
            statement.setString(5, descriptionEventTextArea.getText());
            statement.setString(6, typeEventComboBox.getSelectedItem().toString());
            statement.setString(7, hallEventComboBox.getSelectedItem().toString());
            statement.setString(8, occupancyEventComboBox.getSelectedItem().toString());
            int minAge;
            if(minAgeEventComboBox.getSelectedItem().toString().equals("-")) minAge = 0;
            else minAge = Integer.valueOf(minAgeEventComboBox.getSelectedItem().toString());
            statement.setInt(9, minAge);
            statement.setString(10, posterPathField.getText());

            statement.executeUpdate();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateEvent(Event editedEvent) {
        try {
            Connection conn = ConnectionToDB.getConnection();

            String sql = "UPDATE events SET date = ?, time = ?, priceOfTicket = ?, description = ? WHERE eventID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, dateToString());
            statement.setString(2, timeToString());
            statement.setString(3, priceToString());
            statement.setString(4, descriptionEventTextArea.getText());
            statement.setInt(5, editedEvent.getEventID());

            statement.executeUpdate();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editEvent(Event event) {
        clearForm();
        unlockForm();
        nameEventTextField.setText(event.getName());
        nameEventTextField.setEditable(false);
        dayEventSpinner.setValue(Integer.valueOf(event.getDate().substring(0,2)));
        monthEventSpinner.setValue(Integer.valueOf(event.getDate().substring(3,5)));
        yearEventSpinner.setValue(Integer.valueOf(event.getDate().substring(6)));
        hourEventSpinner.setValue(Integer.valueOf(event.getTime().substring(0,2)));
        minutesEventSpinner.setValue(Integer.valueOf(event.getTime().substring(3)));
        zlotyEventSpinner.setValue(Integer.valueOf((event.getPriceOfTickets()).substring(0,String.valueOf(event.getPriceOfTickets()).length()-3)));
        bobsEventSpinner.setValue(Integer.valueOf((event.getPriceOfTickets()).substring(String.valueOf(event.getPriceOfTickets()).length()-2)));
        descriptionEventTextArea.setText(event.getDescription());
        descriptionEventTextArea.setEditable(false);
        typeEventComboBox.setSelectedItem(event.getType());
        typeEventComboBox.setEnabled(false);
        hallEventComboBox.setSelectedItem(event.getHall().getName());
        hallEventComboBox.setEnabled(false);
        occupancyEventComboBox.setSelectedItem(event.getOccupancyOfHall());
        occupancyEventComboBox.setEnabled(false);
        minAgeEventComboBox.setSelectedItem(event.getStringOfMinimalAge());
        minAgeEventComboBox.setEnabled(false);
        posterPathField.setText(event.getPathToPoster());
        wybierzObrazEventButton.setEnabled(false);
        editedEvent = event;
    }

    private String dateToString(){
        String day = new String();
        String month = new String();
        String date;
        if(String.valueOf(dayEventSpinner.getValue()).length()==1) day ="0" + dayEventSpinner.getValue();
        if(String.valueOf(dayEventSpinner.getValue()).length()==2) day = dayEventSpinner.getValue().toString();
        if(String.valueOf(monthEventSpinner.getValue()).length()==1) month ="0" + monthEventSpinner.getValue();
        if(String.valueOf(monthEventSpinner.getValue()).length()==2) month = monthEventSpinner.getValue().toString();
        date = day + "." + month + "." + yearEventSpinner.getValue().toString();
        return date;
    }

    private String timeToString(){
        String hour = new String();
        String minutes = new String();
        String time;
        if(String.valueOf(hourEventSpinner.getValue()).length()==1) hour ="0" + hourEventSpinner.getValue();
        if(String.valueOf(hourEventSpinner.getValue()).length()==2) hour = hourEventSpinner.getValue().toString();
        if(String.valueOf(minutesEventSpinner.getValue()).length()==1) minutes ="0" + minutesEventSpinner.getValue();
        if(String.valueOf(minutesEventSpinner.getValue()).length()==2) minutes  = minutesEventSpinner.getValue().toString();
        time = hour + ":" + minutes;
        return time;
    }

    private String priceToString(){
        String bobs = new String();
        String price;
        if(String.valueOf(bobsEventSpinner.getValue()).length()==1) bobs ="0" + bobsEventSpinner.getValue();
        if(String.valueOf(bobsEventSpinner.getValue()).length()==2) bobs = bobsEventSpinner.getValue().toString();
        price = zlotyEventSpinner.getValue().toString() + "." + bobs;
        return price;
    }

    private boolean checkAllField(){
        boolean checked = true;
        if(nameEventTextField.getText().isEmpty()){
            nameEventTextField.setBackground(new Color(250, 10, 20));
            checked = false;
        }
        if(typeEventComboBox.getSelectedItem().equals("")){
            typeEventComboBox.setBackground(new Color(250, 10, 20));
            checked = false;
        }
        if(hallEventComboBox.getSelectedItem().equals("")){
            hallEventComboBox.setBackground(new Color(250, 10, 20));
            checked = false;
        }
        if(occupancyEventComboBox.getSelectedItem().equals("")){
            occupancyEventComboBox.setBackground(new Color(250, 10, 20));
            checked = false;
        }
        if(minAgeEventComboBox.getSelectedItem().equals("")){
            minAgeEventComboBox.setBackground(new Color(250, 10, 20));
            checked = false;
        }
        return checked;
    }

    public void unlockForm(){
        nameEventTextField.setEditable(true);
        descriptionEventTextArea.setEditable(true);
        typeEventComboBox.setEnabled(true);
        hallEventComboBox.setEnabled(true);
        occupancyEventComboBox.setEnabled(true);
        minAgeEventComboBox.setEnabled(true);
        wybierzObrazEventButton.setEnabled(true);
    }

    public void clearForm() {
        nameEventTextField.setText("");
        nameEventTextField.setBackground(new Color(255,255,255));
        dayEventSpinner.setValue(1);
        monthEventSpinner.setValue(1);
        yearEventSpinner.setValue(2021);
        hourEventSpinner.setValue(0);
        minutesEventSpinner.setValue(0);
        zlotyEventSpinner.setValue(0);
        bobsEventSpinner.setValue(0);
        descriptionEventTextArea.setText("");
        minAgeEventComboBox.setSelectedIndex(0);
        typeEventComboBox.setSelectedIndex(0);
        hallEventComboBox.setSelectedIndex(0);
        occupancyEventComboBox.setSelectedIndex(0);
        posterPathField.setText("");
    }
}
