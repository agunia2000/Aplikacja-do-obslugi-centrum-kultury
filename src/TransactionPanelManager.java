import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TransactionPanelManager {

    private DecimalFormat df2 = new DecimalFormat("#,##0.00");
    private double totalValueOfTickets;

    public void setSoldTickets(ArrayList<Ticket> soldTickets) {
        this.soldTickets = soldTickets;
    }

    private ArrayList<Ticket> soldTickets = new ArrayList();

    public void setEventsPanelManager(EventsPanelManager eventsPanelManager) {
        this.eventsPanelManager = eventsPanelManager;
    }

    private EventsPanelManager eventsPanelManager;

    private JLabel sumaLabel;
    private JPanel invoicePanel;
    private JLabel daneInvoiceLabel;
    private JTextField imieInvoiceTextField;
    private JTextField nazwiskoInvoiceTextField;
    private JTextField nazwaFirmyInvoiceTextField;
    private JTextField NIPInvoiceTextField;
    private JTextField adresInvoiceTextField;
    private JTextField adresCdInvoiceTextField;
    private JTextField telefonInvoiceTextField;
    private JTextField emailInvoiceTextField;
    private JLabel imieInvoiceLabel;
    private JLabel nazwiskoInvoiceLabel;
    private JLabel nazwaFirmyInvoiceLabel;
    private JLabel NIPInvoiceLabel;
    private JLabel adresInvoiceLabel;
    private JLabel adresCdInvoiceLabel;
    private JLabel telefonInvoiceLabel;
    private JLabel emailInvoiceLabel;

    public TransactionPanelManager(JPanel cardPanel, JPanel eventsMainPanel, JPanel ticketsMainPanel, JLabel sumaLabel, JPanel invoicePanel, JLabel formaPotwierdzeniaTransakcjiLabel, JRadioButton paragonRadioButton, JRadioButton fakturaVATRadioButton, JLabel formaPlatnosciLabel, JRadioButton gotowkaRadioButton, JRadioButton kartaPlatniczaRadioButton, JLabel daneInvoiceLabel, JTextField imieInvoiceTextField, JTextField nazwiskoInvoiceTextField, JTextField nazwaFirmyInvoiceTextField, JTextField NIPInvoiceTextField, JTextField adresInvoiceTextField, JTextField adresCdInvoiceTextField, JTextField telefonInvoiceTextField, JTextField emailInvoiceTextField, JLabel imieInvoiceLabel, JLabel nazwiskoInvoiceLabel, JLabel nazwaFirmyInvoiceLabel, JLabel NIPInvoiceLabel, JLabel adresInvoiceLabel, JLabel adresCdInvoiceLabel, JLabel telefonInvoiceLabel, JLabel emailInvoiceLabel, JButton wsteczTransactionButton, JButton zakończTransactionButton) {
        this.sumaLabel = sumaLabel;
        this.invoicePanel = invoicePanel;
        this.daneInvoiceLabel = daneInvoiceLabel;
        this.imieInvoiceTextField = imieInvoiceTextField;
        this.nazwiskoInvoiceTextField = nazwiskoInvoiceTextField;
        this.nazwaFirmyInvoiceTextField = nazwaFirmyInvoiceTextField;
        this.NIPInvoiceTextField = NIPInvoiceTextField;
        this.adresInvoiceTextField = adresInvoiceTextField;
        this.adresCdInvoiceTextField = adresCdInvoiceTextField;
        this.telefonInvoiceTextField = telefonInvoiceTextField;
        this.emailInvoiceTextField = emailInvoiceTextField;
        this.imieInvoiceLabel = imieInvoiceLabel;
        this.nazwiskoInvoiceLabel = nazwiskoInvoiceLabel;
        this.nazwaFirmyInvoiceLabel = nazwaFirmyInvoiceLabel;
        this.NIPInvoiceLabel = NIPInvoiceLabel;
        this.adresInvoiceLabel = adresInvoiceLabel;
        this.adresCdInvoiceLabel = adresCdInvoiceLabel;
        this.telefonInvoiceLabel = telefonInvoiceLabel;
        this.emailInvoiceLabel = emailInvoiceLabel;

        lockInvoicePanel();

        paragonRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lockInvoicePanel();
                clearInvoicePanel();
                colorRadioButtons(formaPotwierdzeniaTransakcjiLabel, paragonRadioButton, fakturaVATRadioButton, new Color(20, 20, 20));
            }
        });

        fakturaVATRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unlockInvoicePanel();
                colorRadioButtons(formaPotwierdzeniaTransakcjiLabel, paragonRadioButton, fakturaVATRadioButton, new Color(20, 20, 20));
            }
        });

        gotowkaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorRadioButtons(formaPlatnosciLabel, gotowkaRadioButton, kartaPlatniczaRadioButton, new Color(20, 20, 20));
            }
        });

        kartaPlatniczaRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorRadioButtons(formaPlatnosciLabel, gotowkaRadioButton, kartaPlatniczaRadioButton, new Color(20, 20, 20));
            }
        });

        wsteczTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(ticketsMainPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });

        zakończTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean transactionFinish = true;
                if (!fakturaVATRadioButton.isSelected() && !paragonRadioButton.isSelected()) {
                    transactionFinish = false;
                    colorRadioButtons(formaPotwierdzeniaTransakcjiLabel, paragonRadioButton, fakturaVATRadioButton, new Color(255, 0, 0));
                }
                if (!gotowkaRadioButton.isSelected() && !kartaPlatniczaRadioButton.isSelected()) {
                    transactionFinish = false;
                    colorRadioButtons(formaPlatnosciLabel, gotowkaRadioButton, kartaPlatniczaRadioButton, new Color(255, 0, 0));
                }
                if (transactionFinish) {
                    if(fakturaVATRadioButton.isSelected()){
                        addInvoiceToDB();
                        clearInvoicePanel();
                    }
                    updateTakenPlaces();
                    eventsPanelManager.selectEvents();
                    cardPanel.removeAll();
                    cardPanel.add(eventsMainPanel);
                    cardPanel.repaint();
                    cardPanel.revalidate();
                }
            }
        });
    }

    public void countTotalValueOfTickets() {
        totalValueOfTickets = 0;
        for (Ticket ticket : soldTickets) {
            totalValueOfTickets = totalValueOfTickets + ticket.getValue();
        }
        sumaLabel.setText(df2.format(totalValueOfTickets) + " PLN");
    }

    private void colorRadioButtons(JLabel label, JRadioButton radioButton1, JRadioButton radioButton2, Color color) {
        label.setForeground(color);
        radioButton1.setForeground(color);
        radioButton2.setForeground(color);
    }

    private void updateTakenPlaces() {
        ArrayList<Event> updatedEvents = new ArrayList();
        for (Ticket ticket : soldTickets) {
            ticket.getEvent().getTakenPlaces().add(ticket.getRow() + ticket.getPlace());
            if (updatedEvents.contains(ticket.getEvent())) updatedEvents.remove(ticket.getEvent());
            updatedEvents.add(ticket.getEvent());
        }
        for (Event event : updatedEvents) {
            ArrayList<String> takenPlaces = event.getTakenPlaces();
            StringBuilder takenPlacesSB = new StringBuilder();
            for (String place : takenPlaces) {
                takenPlacesSB.append(place);
                takenPlacesSB.append(",");
            }
            try {
                Connection conn = ConnectionToDB.getConnection();

                String sql = "UPDATE events SET takenPlaces = ? WHERE eventID = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, takenPlacesSB.toString());
                statement.setInt(2, event.getEventID());

                statement.executeUpdate();
                statement.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        soldTickets.clear();
    }

    private String SoldTicketsToString (){
        StringBuilder soldTicketsSB = new StringBuilder();
        for (Ticket ticket : soldTickets) {
            String ticketID = ticket.getEvent().getEventID() + "_" + ticket.getRow() + ticket.getPlace();
            soldTicketsSB.append(ticketID);
            soldTicketsSB.append(",");
        }
        String soldTicketsString = soldTicketsSB.toString();
        return soldTicketsString;
    }

    private void addInvoiceToDB(){
        try {
            String sql = "INSERT INTO invoices VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = ConnectionToDB.getConnection().prepareStatement(sql);
            statement.setString(1, imieInvoiceTextField.getText());
            statement.setString(2, nazwiskoInvoiceTextField.getText());
            statement.setString(3, nazwaFirmyInvoiceTextField.getText());
            statement.setString(4, NIPInvoiceTextField.getText());
            statement.setString(5, adresInvoiceTextField.getText());
            statement.setString(6, adresCdInvoiceTextField.getText());
            statement.setString(7, telefonInvoiceTextField.getText());
            statement.setString(8, emailInvoiceTextField.getText());
            statement.setString(9, df2.format(totalValueOfTickets));
            statement.setString(10, SoldTicketsToString());

            statement.executeUpdate();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void lockInvoicePanel() {
        invoicePanel.setBackground(new Color(220, 220, 220));
        daneInvoiceLabel.setForeground(new Color(110, 110, 110));
        imieInvoiceLabel.setForeground(new Color(110, 110, 110));
        imieInvoiceTextField.setBackground(new Color(240, 240, 240));
        imieInvoiceTextField.setEnabled(false);
        nazwiskoInvoiceLabel.setForeground(new Color(110, 110, 110));
        nazwiskoInvoiceTextField.setBackground(new Color(240, 240, 240));
        nazwiskoInvoiceTextField.setEnabled(false);
        nazwaFirmyInvoiceLabel.setForeground(new Color(110, 110, 110));
        nazwaFirmyInvoiceTextField.setBackground(new Color(240, 240, 240));
        nazwaFirmyInvoiceTextField.setEnabled(false);
        NIPInvoiceLabel.setForeground(new Color(110, 110, 110));
        NIPInvoiceTextField.setBackground(new Color(240, 240, 240));
        NIPInvoiceTextField.setEnabled(false);
        adresInvoiceLabel.setForeground(new Color(110, 110, 110));
        adresInvoiceTextField.setBackground(new Color(240, 240, 240));
        adresInvoiceTextField.setEnabled(false);
        adresCdInvoiceLabel.setForeground(new Color(110, 110, 110));
        adresCdInvoiceTextField.setBackground(new Color(240, 240, 240));
        adresCdInvoiceTextField.setEnabled(false);
        telefonInvoiceLabel.setForeground(new Color(110, 110, 110));
        telefonInvoiceTextField.setBackground(new Color(240, 240, 240));
        telefonInvoiceTextField.setEnabled(false);
        emailInvoiceLabel.setForeground(new Color(110, 110, 110));
        emailInvoiceTextField.setBackground(new Color(240, 240, 240));
        emailInvoiceTextField.setEnabled(false);
    }

    private void unlockInvoicePanel() {
        invoicePanel.setBackground(new Color(238, 238, 238));
        daneInvoiceLabel.setForeground(new Color(20, 20, 20));
        imieInvoiceLabel.setForeground(new Color(20, 20, 20));
        imieInvoiceTextField.setBackground(new Color(255, 255, 255));
        imieInvoiceTextField.setEnabled(true);
        nazwiskoInvoiceLabel.setForeground(new Color(20, 20, 20));
        nazwiskoInvoiceTextField.setBackground(new Color(255, 255, 255));
        nazwiskoInvoiceTextField.setEnabled(true);
        nazwaFirmyInvoiceLabel.setForeground(new Color(20, 20, 20));
        nazwaFirmyInvoiceTextField.setBackground(new Color(255, 255, 255));
        nazwaFirmyInvoiceTextField.setEnabled(true);
        NIPInvoiceLabel.setForeground(new Color(20, 20, 20));
        NIPInvoiceTextField.setBackground(new Color(255, 255, 255));
        NIPInvoiceTextField.setEnabled(true);
        adresInvoiceLabel.setForeground(new Color(20, 20, 20));
        adresInvoiceTextField.setBackground(new Color(255, 255, 255));
        adresInvoiceTextField.setEnabled(true);
        adresCdInvoiceLabel.setForeground(new Color(20, 20, 20));
        adresCdInvoiceTextField.setBackground(new Color(255, 255, 255));
        adresCdInvoiceTextField.setEnabled(true);
        telefonInvoiceLabel.setForeground(new Color(20, 20, 20));
        telefonInvoiceTextField.setBackground(new Color(255, 255, 255));
        telefonInvoiceTextField.setEnabled(true);
        emailInvoiceLabel.setForeground(new Color(20, 20, 20));
        emailInvoiceTextField.setBackground(new Color(255, 255, 255));
        emailInvoiceTextField.setEnabled(true);
    }

    private void clearInvoicePanel() {
        imieInvoiceTextField.setText("");
        nazwiskoInvoiceTextField.setText("");
        nazwaFirmyInvoiceTextField.setText("");
        NIPInvoiceTextField.setText("");
        adresInvoiceTextField.setText("");
        adresCdInvoiceTextField.setText("");
        telefonInvoiceTextField.setText("");
        emailInvoiceTextField.setText("");
    }

}
