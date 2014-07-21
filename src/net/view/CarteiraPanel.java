/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import net.controles.EstoqueAcao;
import net.rmi.beans.Empresa;
import net.rmi.beans.Operacao;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilCalendarModel;

/**
 * Classe da janela de operações.
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
    private final String[] tableHeader = new String[]{"ID", "Nome", "Valor Unitário", "Quantidade em posse"};
    private final MainFrame frame;
    private JDatePickerImpl datePicker;

    /**
     * Construtora da classe.
     *
     * @param frame janela do cliente.
     */
    public CarteiraPanel(MainFrame frame) {
        super(new BorderLayout());
        this.frame = frame;

        monitored = new JTable(new DefaultTableModel(new Object[0][0], tableHeader));

        quantity = new JSpinner(new SpinnerNumberModel(0, 0, 1000000000, 1));
        price = new JSpinner(new SpinnerNumberModel(0, 0, 1000000, 0.1));
        operationID = new JComboBox<>();
        tipo = new JComboBox<String>(new String[]{"Comprar", "Vender"});
        addOperation = new JButton("registrar");
        datePicker = new JDatePickerImpl(new JDatePanelImpl(new UtilCalendarModel(Calendar.getInstance())));

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
        axPanel.add(datePicker);
        axPanel.add(addOperation);

        this.add(axPanel, BorderLayout.SOUTH);
    }

    /**
     * Método captura uma operação feita na janela.
     */
    void register() {
        Operacao operacao = new Operacao(getType(), operationID.getSelectedItem().toString(), (Calendar) datePicker.getModel().getValue(), frame.getClient()).setPreçoUnitarioDesejado((int) (((Double) price.getValue()) * 100)).setQuantidade(((Integer) quantity.getValue()));

        if (frame.validateOperation(operacao)) {
            frame.registerOperation(operacao);
        }else{
            frame.showMessage("Erro ao criar operação.", "<html>A operação não pode ser criada.</br>Possíveis causas incluem montante insuficiente de ações para venda.</html>");
        }
    }

    /**
     * Método que adiciona uma empresa na tabela de monitoramento.
     *
     * @param emp empresa desejada.
     */
    void addMonitoredCompany(EstoqueAcao estoque) {
        Empresa emp = estoque.getEmpresa();
        
        
        
        operationID.addItem(emp.getID());

        ((DefaultTableModel) monitored.getModel()).addRow(new Object[]{emp.getID(), emp.getName(), emp, estoque.getQuantidade_Mutable()});
    }

    /**
     * Método retorna o tipo de uma operação.
     *
     * @return true se for "Comprar", false caso contrário.
     */
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
