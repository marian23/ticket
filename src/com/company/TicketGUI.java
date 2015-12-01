package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by marian on 11/20/2015.
 */
public class TicketGUI extends JFrame{
    private JPanel rootPanel;
    private JTextField prioritytextField;
    private JTextField reporttextField2;
    private JTextField decriptiontextField3;
    private JButton deleteButton;
    private JButton addButton;
    private JList openticketlist1;
    private JButton quitButton;

    LinkedList<Ticket> openTicketList = new LinkedList<Ticket>();
    LinkedList<Ticket> resolvedTicketList = new LinkedList<Ticket>();


    DefaultListModel<Ticket> ticketListModel;

    public TicketGUI(){
        super("Ticket support");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        ticketListModel = new DefaultListModel<Ticket>();
        openticketlist1.setModel(ticketListModel);
        openticketlist1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String  priorityAsString = prioritytextField.getText();
                String report = reporttextField2.getText();
                String decription = decriptiontextField3.getText();
                Date date = new Date();
                int priority;
                try {
                    priority = Integer.parseInt(priorityAsString);
                }
                catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(TicketGUI.this, "Enter an integer number for priority.");
                    return;
                }
                Ticket newTicket = new Ticket(decription, priority, report,date);
                TicketGUI.this.ticketListModel.addElement(newTicket);
                openTicketList.add(newTicket);
            }
        });


        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MMM_dd_yyyy");
                String resolve = sdf.format(date);
                String file = "resolved date " + resolve + "txt";
                String open = "open_tickets.txt";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                ) {
                    BufferedWriter bwrite = new BufferedWriter(new FileWriter(open));

                    for (Ticket t : resolvedTicketList) {

                        writer.write(t.toString() + "\n");
                    }
                    for (Ticket B : openTicketList) {
                        bwrite.write(B.toString() + "\n");
                    }
                    writer.close();
                    bwrite.close();


                } catch (IOException ioe) {
                    //todo add some meaningful text for user instead of stacktrace
                    JOptionPane.showMessageDialog(TicketGUI.this, "Enter an integer number for priority.");
                    ioe.printStackTrace();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ticket ticket = (Ticket) TicketGUI.this.openticketlist1.getSelectedValue();
                TicketGUI.this.ticketListModel.removeElement(ticket);
            }
        });
    }
}
