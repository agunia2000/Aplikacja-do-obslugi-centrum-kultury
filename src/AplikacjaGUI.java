import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AplikacjaGUI {
    public JPanel mainPanel;
    public JPanel cardPanel;
    private  JPanel hallMainPanel;
    private JPanel buttonMainPanel;
    private JButton dalejHallButton;
    private JButton wsteczHallButton;
    private JPanel placesPanel;
    public  JButton wydarzeniaButton;
    private JPanel ticketsMainPanel;
    private JButton dodajWydarzenieButton;
    private JButton biletyButton;
    private JLabel titleEventLabel;
    private JPanel ticketsContentPanel;
    private JLabel dateEventLabel;
    private JPanel transactionMainPanel;
    private JLabel sumaLabel;
    private JRadioButton paragonRadioButton;
    private JRadioButton fakturaVATRadioButton;
    private JRadioButton kartaPlatniczaRadioButton;
    private JRadioButton gotowkaRadioButton;
    private JButton wsteczTicketsButton;
    private JButton dalejTicketsButton;
    private JButton zakończTransactionButton;
    private JButton wsteczTransactionButton;
    private JTextField nazwiskoInvoiceTextField;
    private JTextField nazwaFirmyInvoiceTextField;
    private JTextField NIPInvoiceTextField;
    private JTextField adresInvoiceTextField;
    private JTextField telefonInvoiceTextField;
    private JTextField emailInvoiceTextField;
    private JPanel invoicePanel;
    private JPanel eventsMainPanel;
    private JButton searchEventButton;
    private JComboBox sortComboBox;
    private JComboBox filterComboBox;
    private JPanel eventsContentPanel;
    private JPanel addEventMainPanel;
    private JButton anulujAddEventButton;
    private JButton potwierdzAddEventButton;
    private JFormattedTextField posterPathField;
    private JTextField imieInvoiceTextField;
    private JTextField adresCdInvoiceTextField;
    private JLabel imieInvoiceLabel;
    private JLabel nazwiskoInvoiceLabel;
    private JLabel nazwaFirmyInvoiceLabel;
    private JLabel NIPInvoiceLabel;
    private JLabel adresInvoiceLabel;
    private JLabel adresCdInvoiceLabel;
    private JLabel telefonInvoiceLabel;
    private JLabel emailInvoiceLabel;
    private JLabel daneInvoiceLabel;
    private JLabel formaPlatnosciLabel;
    private JLabel formaPotwierdzeniaTransakcjiLabel;
    private JTextField nameEventTextField;
    private JButton wybierzObrazEventButton;
    private JComboBox minAgeEventComboBox;
    private JTextField searchEventTextField;
    private JTextArea opisEventTextArea;
    private JComboBox hallEventComboBox;
    private JComboBox typeEventComboBox;
    private JComboBox occupancyEventComboBox;
    private JSpinner dayEventSpinner;
    private JSpinner monthEventSpinner;
    private JSpinner yearEventSpinner;
    private JSpinner hourEventSpinner;
    private JSpinner minutesEventSpinner;
    private JSpinner bobsEventSpinner;
    private JSpinner zlotyEventSpinner;


    public AplikacjaGUI() {
        AddEventPanelManager addEventPanelManager = new AddEventPanelManager(cardPanel, eventsMainPanel, nameEventTextField, dayEventSpinner, monthEventSpinner, yearEventSpinner, hourEventSpinner, minutesEventSpinner, zlotyEventSpinner, bobsEventSpinner, opisEventTextArea, minAgeEventComboBox, typeEventComboBox, hallEventComboBox, occupancyEventComboBox, posterPathField, wybierzObrazEventButton, anulujAddEventButton, potwierdzAddEventButton);
        EventsPanelManager eventsPanelManager = new EventsPanelManager(cardPanel, eventsContentPanel, addEventMainPanel, addEventPanelManager, hallMainPanel, filterComboBox, sortComboBox, searchEventTextField, searchEventButton);
        TransactionPanelManager transactionPanelManager = new TransactionPanelManager(cardPanel, eventsMainPanel, ticketsMainPanel, sumaLabel, invoicePanel, formaPotwierdzeniaTransakcjiLabel ,paragonRadioButton, fakturaVATRadioButton, formaPlatnosciLabel, gotowkaRadioButton, kartaPlatniczaRadioButton, daneInvoiceLabel, imieInvoiceTextField, nazwiskoInvoiceTextField, nazwaFirmyInvoiceTextField, NIPInvoiceTextField, adresInvoiceTextField, adresCdInvoiceTextField, telefonInvoiceTextField, emailInvoiceTextField, imieInvoiceLabel, nazwiskoInvoiceLabel, nazwaFirmyInvoiceLabel, NIPInvoiceLabel, adresInvoiceLabel, adresCdInvoiceLabel, telefonInvoiceLabel, emailInvoiceLabel ,wsteczTransactionButton, zakończTransactionButton);
        TicketPanelManager ticketPanelManager = new TicketPanelManager(cardPanel, hallMainPanel, ticketsContentPanel, transactionPanelManager, dalejTicketsButton, wsteczTicketsButton, eventsMainPanel, transactionMainPanel);
        HallPanelManager hallPanelManager = new HallPanelManager(placesPanel, titleEventLabel, dateEventLabel, dalejHallButton, wsteczHallButton, ticketPanelManager, ticketsMainPanel, eventsMainPanel, cardPanel);

        addEventPanelManager.setEventsPanelManager(eventsPanelManager);
        eventsPanelManager.setHallPanelManager(hallPanelManager);
        ticketPanelManager.setEventsPanelManager(eventsPanelManager);
        ticketPanelManager.setHallPanelManager(hallPanelManager);
        transactionPanelManager.setEventsPanelManager(eventsPanelManager);

        hallPanelManager.loadHalls();

        eventsPanelManager.selectEvents();

        cardPanel.removeAll();
        cardPanel.add(eventsMainPanel);
        cardPanel.repaint();
        cardPanel.revalidate();

        wydarzeniaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventsPanelManager.resetEventPanelComboBox();
                eventsPanelManager.selectEvents();
                cardPanel.removeAll();
                cardPanel.add(eventsMainPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });

        dodajWydarzenieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEventPanelManager.clearForm();
                addEventPanelManager.unlockForm();
                cardPanel.removeAll();
                cardPanel.add(addEventMainPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });

        biletyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticketPanelManager.showTickets();
                cardPanel.removeAll();
                cardPanel.add(ticketsMainPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("");
        frame.setContentPane(new AplikacjaGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1350,700));
        frame.pack();
        frame.setVisible(true);
    }
}