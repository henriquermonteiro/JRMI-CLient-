/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import net.rmi.beans.Empresa;
import net.rmi.beans.Operacao;

/**
 *
 * @author henrique
 */
public class CarteiraPanel extends JPanel {

    private JButton addOperation;
    private JSpinner quantity;
    private JSpinner price;
    private JComboBox<String> operationID;
    private JComboBox<String> tipo;
    private JTable monitored;
    private final String[] tableHeader = new String[]{"ID", "Nome", "Valor Unitário"};
    private final MainFrame frame;

    public CarteiraPanel(MainFrame frame) {
        super(new BorderLayout());
        this.frame = frame;

        monitored = new JTable(new DefaultTableModel(new Object[0][0], tableHeader));

        quantity = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        quantity.setSize(70, (int) quantity.getSize().getHeight());
        price = new JSpinner(new SpinnerNumberModel(0, 0, Float.MAX_VALUE, 0.1));
        price.setSize(70, (int) price.getSize().getHeight());
        price.setMaximumSize(price.getSize());
        operationID = new JComboBox<>();
        tipo = new JComboBox<String>(new String[]{"Comprar", "Vender"});
        addOperation = new JButton("registrar");

        addOperation.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                register();
            }
        });

        this.add(new JScrollPane(monitored));

        JPanel axPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        axPanel.add(operationID);
        axPanel.add(tipo);
        axPanel.add(price);
        axPanel.add(quantity);
        axPanel.add(addOperation);

        this.add(axPanel, BorderLayout.SOUTH);
    }

    void register() {
        Operacao operacao = new Operacao(getType(), operationID.getSelectedItem().toString(), frame.getClient()).setPreçoUnitarioDesejado((int) (((Double) price.getValue()) * 100)).setQuantidade(((Integer) quantity.getValue()));

        frame.registerOperation(operacao);
    }

    void addMonitoredCompany(Empresa emp) {
        operationID.addItem(emp.getID());

        ((DefaultTableModel) monitored.getModel()).addRow(new Object[]{emp.getID(), emp.getName(), emp});
    }

    private boolean getType() {
        switch (tipo.getSelectedItem().toString()) {
            case "Comprar":
                return true;
            case "Vender":
                return false;
        }

        throw new RuntimeException("OMG Wrong option");
    }
}
