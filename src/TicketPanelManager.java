import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class TicketPanelManager {

    private DecimalFormat df2 = new DecimalFormat("#,##0.00");
    private JPanel cardPanel;
    private JPanel hallMainPanel;
    private JPanel ticketsContentPanel;
    private HallPanelManager hallPanelManage;
    private EventsPanelManager eventsPanelManager;


    private ArrayList<Ticket> tickets = new ArrayList();

    public TicketPanelManager(JPanel cardPanel, JPanel hallMainPanel, JPanel ticketsContentPanel, TransactionPanelManager transactionPanelManager, JButton dalejTicketsButton, JButton wsteczTicketsButton, JPanel eventsMainPanel, JPanel transactionMainPanel) {
        this.cardPanel= cardPanel;
        this.hallMainPanel = hallMainPanel;
        this.ticketsContentPanel = ticketsContentPanel;

        wsteczTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(eventsMainPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });
        dalejTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transactionPanelManager.setSoldTickets(tickets);
                transactionPanelManager.countTotalValueOfTickets();
                cardPanel.removeAll();
                cardPanel.add(transactionMainPanel);
                cardPanel.repaint();
                cardPanel.revalidate();

            }
        });
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setHallPanelManager(HallPanelManager hallPanelManage) {
        this.hallPanelManage = hallPanelManage;
    }

    public void setEventsPanelManager(EventsPanelManager eventsPanelManager) {
        this.eventsPanelManager = eventsPanelManager;
    }

    public void showTickets() {
        checkEventExist();
        sortTickets();

        ticketsContentPanel.removeAll();
        ticketsContentPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        ticketsContentPanel.setPreferredSize(new Dimension(1200, tickets.size()*120));

        for(Ticket ticket: tickets) {
            JPanel oneTicketPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            oneTicketPanel.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 1));
            oneTicketPanel.setBackground(new Color(120, 180, 220));
            oneTicketPanel.setPreferredSize(new Dimension(1150, 115));

            JPanel ticketHeadersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            ticketHeadersPanel.setPreferredSize(new Dimension(1100, 50));
            ticketHeadersPanel.setBackground(new Color(120, 180, 220));

            JLabel titleHeaderLabel = new JLabel();
            modifyLabel(titleHeaderLabel,"nazwa wydarzenia",12,230, 30,ticketHeadersPanel);
            JLabel hallHeaderLabel = new JLabel();
            modifyLabel(hallHeaderLabel,"sala",12,50, 30,ticketHeadersPanel);
            JLabel rowHeaderLabel = new JLabel();
            modifyLabel(rowHeaderLabel,"rząd",12,30, 30,ticketHeadersPanel);
            JLabel placeHeaderLabel = new JLabel();
            modifyLabel(placeHeaderLabel,"miejsce",12,50, 30,ticketHeadersPanel);
            JLabel dateHeaderLabel = new JLabel();
            modifyLabel(dateHeaderLabel,"data",12,100, 30,ticketHeadersPanel);
            JLabel hourHeaderLabel = new JLabel();
            modifyLabel(hourHeaderLabel,"godzina", 12,80, 30, ticketHeadersPanel);
            JLabel priceHeaderLabel = new JLabel();
            modifyLabel(priceHeaderLabel,"cena",12,100, 30,ticketHeadersPanel);
            JLabel typeHeaderLabel = new JLabel();
            modifyLabel(typeHeaderLabel,"typ biletu",12,90, 30,ticketHeadersPanel);
            JLabel discountHeaderLabel = new JLabel();
            modifyLabel(discountHeaderLabel,"zniżka [%]",12,75, 30,ticketHeadersPanel);
            JLabel valueHeaderLabel = new JLabel();
            modifyLabel(valueHeaderLabel,"wartość",12,95, 30,ticketHeadersPanel);
            JButton editTicketButton = new JButton();
            modifyButton(editTicketButton,"Zmień miejsce",120, 25, ticketHeadersPanel);

            oneTicketPanel.add(ticketHeadersPanel);

            JPanel ticketContentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            ticketContentPanel.setPreferredSize(new Dimension(1100, 50));
            ticketContentPanel.setBackground(new Color(120, 180, 220));

            JLabel titleContentLabel = new JLabel();
            modifyLabel(titleContentLabel, ticket.getEventName(),16,230,30, ticketContentPanel);
            JLabel hallContentLabel = new JLabel();
            modifyLabel(hallContentLabel,ticket.getEvent().getHall().getName(),16,50, 25,ticketContentPanel);
            JLabel rowContentLabel = new JLabel();
            modifyLabel(rowContentLabel, ticket.getRow() ,16,30, 25, ticketContentPanel);
            JLabel placeContentLabel = new JLabel();
            modifyLabel(placeContentLabel, String.valueOf(ticket.getPlace()) ,16,50, 25, ticketContentPanel);
            JLabel dateContentLabel = new JLabel();
            modifyLabel(dateContentLabel,ticket.getDate(), 15,100, 25, ticketContentPanel);
            JLabel timeContentLabel = new JLabel();
            modifyLabel(timeContentLabel,ticket.getTime(), 15,80, 25, ticketContentPanel);
            JLabel priceContentLabel = new JLabel();
            modifyLabel(priceContentLabel,df2.format(ticket.getPrice())+ " PLN", 15,110, 25, ticketContentPanel);
            JComboBox typeComboBox = new JComboBox();
            modifyComboBox(typeComboBox, new String[]{"Normalny","Ulgowy"},85, 25, ticketContentPanel);
            typeComboBox.setSelectedItem(ticket.getType());
            JSpinner discountSpinner = new JSpinner();
            modifySpinner(discountSpinner,65, 25, ticketContentPanel);
            discountSpinner.setValue(ticket.getDiscount());
            JLabel valueContentLabel = new JLabel();
            modifyLabel(valueContentLabel, df2.format(ticket.getValue()) + " PLN",15,100, 25,ticketContentPanel);
            JButton deleteTicketButton = new JButton();
            modifyButton(deleteTicketButton,"Usuń bilet",120, 25, ticketContentPanel);

            oneTicketPanel.add(ticketContentPanel);

            ticketsContentPanel.add(oneTicketPanel);

            typeComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (typeComboBox.getSelectedItem().equals("Normalny")){
                        discountSpinner.setValue(0);
                        ticket.setDiscount((Integer)(discountSpinner.getValue()));
                        ticket.setType("Normalny");
                    }
                    if (typeComboBox.getSelectedItem().equals("Ulgowy")) {
                        discountSpinner.setValue(30);
                        ticket.setDiscount((Integer)(discountSpinner.getValue()));
                        ticket.setType("Ulgowy");
                    }
                }
            });

            discountSpinner.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    ticket.setDiscount((Integer)(discountSpinner.getValue()));
                    ticket.setValue(ticket.getPrice()*(100-(Integer)discountSpinner.getValue())/100.00);
                    valueContentLabel.setText(df2.format(ticket.getPrice()*(100-(Integer)discountSpinner.getValue())/100.00) + " PLN");
                }
            });

            editTicketButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardPanel.removeAll();
                    cardPanel.add(hallMainPanel);
                    cardPanel.repaint();
                    cardPanel.revalidate();
                    hallPanelManage.showPlaces(ticket.getEvent());
                }
            });

            deleteTicketButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tickets.remove(ticket);
                    showTickets();
                    ticketsContentPanel.setVisible(false);
                    ticketsContentPanel.setVisible(true);
                }
            });
        }
    }

    private void sortTickets(){
        tickets.sort(Comparator.comparing(Ticket::getPlace));
        tickets.sort(Comparator.comparing(Ticket::getRow));
        tickets.sort(Comparator.comparing(Ticket::getEventName));
    }

    public void checkEventExist(){
        ArrayList<Ticket> checkedTickets = new ArrayList(tickets);
        if(!eventsPanelManager.getEvents().isEmpty()){
            if(!tickets.isEmpty()){
                for (Ticket ticket : checkedTickets) {
                    boolean delete = true;
                    for (Event event : eventsPanelManager.getEvents()) {
                        if (ticket.getEvent().getEventID() == event.getEventID() && ticket.getDate().equals(event.getDate()) &&  ticket.getPrice()==Double.valueOf(event.getPriceOfTickets()) && ticket.getTime().equals(event.getTime())) {
                            delete = false;
                        }
                    }
                    if(delete) tickets.remove(ticket);
                }
            }
        }
        else tickets.clear();
        ticketsContentPanel.setVisible(false);
        ticketsContentPanel.setVisible(true);
    }

    private void modifyLabel(JLabel label, String text, int fontSize, int width, int height, JPanel destinationPanel ){
        label.setText(text);
        label.setForeground(new Color(0,0,0));
        label.setFont(new Font("Arial",Font.BOLD, fontSize));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(width, height));
        destinationPanel.add(label);
    }

    private void modifyButton(JButton button, String text, int width, int height, JPanel destinationPanel){
        button.setText(text);
        button.setForeground(new Color(0,0,0));
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(new Color(240,240,240));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        destinationPanel.add(button);
    }

    private void modifyComboBox(JComboBox comboBox, String[] content, int width, int height, JPanel destinationPanel){
        for (String item : content){
            comboBox.addItem(item);
        }
        comboBox.setForeground(new Color(0,0,0));
        comboBox.setPreferredSize(new Dimension(width, height));
        comboBox.setBackground(new Color(255,255,255));
        destinationPanel.add(comboBox);
    }

    private void modifySpinner(JSpinner spinner, int width, int height, JPanel destinationPanel){
        spinner.setModel(new SpinnerNumberModel(0,0,100,1));
        spinner.setForeground(new Color(255,255,255));
        spinner.setPreferredSize(new Dimension(width, height));
        spinner.setBackground(new Color(65,75,75));
        destinationPanel.add(spinner);
    }
}