package airportManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.sql.*;

public class ChangeDate extends JFrame implements ActionListener {
    JTextField tfPNR;
    JDateChooser newDateChooser;
    JButton updateButton, fetchButton;
    JLabel lblName, lblFlightName, lblFlightCode;

    public ChangeDate() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Change Date of Departure");
        heading.setBounds(120, 20, 300, 30);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        heading.setForeground(Color.BLUE);
        add(heading);
        
        JLabel lblPNR = new JLabel("PNR Number");
        lblPNR.setBounds(30, 80, 150, 25);
        lblPNR.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblPNR);
        
        tfPNR = new JTextField();
        tfPNR.setBounds(180, 80, 150, 25);
        add(tfPNR);

        fetchButton = new JButton("Fetch Details");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(340, 80, 130, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);
        
        JLabel lblNameLabel = new JLabel("Passenger Name");
        lblNameLabel.setBounds(30, 130, 150, 25);
        lblNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblNameLabel);

        lblName = new JLabel();
        lblName.setBounds(180, 130, 250, 25);
        add(lblName);

        JLabel lblFlightNameLabel = new JLabel("Flight Name");
        lblFlightNameLabel.setBounds(30, 180, 150, 25);
        lblFlightNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblFlightNameLabel);

        lblFlightName = new JLabel();
        lblFlightName.setBounds(180, 180, 250, 25);
        add(lblFlightName);

        JLabel lblFlightCodeLabel = new JLabel("Flight Code");
        lblFlightCodeLabel.setBounds(30, 230, 150, 25);
        lblFlightCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblFlightCodeLabel);

        lblFlightCode = new JLabel();
        lblFlightCode.setBounds(180, 230, 250, 25);
        add(lblFlightCode);

        JLabel lblNewDate = new JLabel("New Date");
        lblNewDate.setBounds(30, 280, 150, 25);
        lblNewDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblNewDate);
        
        newDateChooser = new JDateChooser();
        newDateChooser.setBounds(180, 280, 150, 25);
        add(newDateChooser);
        
        updateButton = new JButton("Update Date");
        updateButton.setBackground(Color.BLACK);
        updateButton.setForeground(Color.WHITE);
        updateButton.setBounds(100, 330, 150, 25);
        updateButton.addActionListener(this);
        updateButton.setEnabled(false);
        add(updateButton);
        
        setSize(500, 450);
        setLocation(500, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String pnr = tfPNR.getText();
            if (pnr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a PNR number.");
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "SELECT name, flightname, flightcode FROM reservation WHERE pnr = '" + pnr + "'";
                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    lblName.setText(rs.getString("name"));
                    lblFlightName.setText(rs.getString("flightname"));
                    lblFlightCode.setText(rs.getString("flightcode"));
                    updateButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "No details found for this PNR.");
                    updateButton.setEnabled(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == updateButton) {
            String pnr = tfPNR.getText();
            String newDate = ((JTextField) newDateChooser.getDateEditor().getUiComponent()).getText();

            if (newDate.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select a new date.");
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "UPDATE reservation SET Ddate = '" + newDate + "' WHERE pnr = '" + pnr + "'";
                int result = conn.s.executeUpdate(query);

                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Date updated successfully.");
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update date.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        new ChangeDate();
    }
}
