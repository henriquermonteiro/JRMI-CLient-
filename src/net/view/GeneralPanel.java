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
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.rmi.beans.Empresa;

/**
 * Classe da janela de apresentação.
 *
 * @author henrique
 */
public class GeneralPanel extends JPanel {

    private JButton update;
    private JButton listenTo;
    private JTable acoes;

    private MainFrame frame;

    private final String[] tableHeader = new String[]{"ID", "Nome empresa", "Valor Ações"};

    /**
     * Construtora da classe.
     *
     * @param mainF janela do cliente.
     */
    public GeneralPanel(MainFrame mainF) {
        super(new BorderLayout());
        this.frame = mainF;

        acoes = new JTable(new DefaultTableModel(new Object[0][0], tableHeader));

        update = new JButton("recarregar");
        update.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent me) {
                refresh();
            }
        });

        listenTo = new JButton("Monitorar");
        listenTo.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent me) {
                if (listenTo.isEnabled()) {
                    addMonitor();
                }
            }
        });

        this.add(new JScrollPane(acoes));

        JPanel axPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        axPanel.add(update);

        this.add(axPanel, BorderLayout.NORTH);

        axPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        axPanel.add(listenTo);

        this.add(axPanel, BorderLayout.SOUTH);

        refresh();
    }

    /**
     * Método para atualizar a tabela de empresas.
     */
    void refresh() {
        ArrayList<Empresa> empresas = frame.refresh();

        if (empresas.size() > 0) {
            Object[][] data = new Object[empresas.size()][3];
            int k = 0;

            for (Empresa emp : empresas) {
                data[k][0] = emp.getID();
                data[k][1] = emp.getName();
                data[k][2] = "R$ " + (emp.getValue() / 100.0);

                k++;
            }

            acoes.setModel(new DefaultTableModel(data, tableHeader));
        }

        listenTo.setEnabled(acoes.getRowCount() > 0);
    }

    /**
     * Método adiciona uma empresa para ser observada.
     */
    void addMonitor() {
        if (acoes.getSelectedRow() > -1) {
            Empresa empresa = new Empresa(acoes.getValueAt(acoes.getSelectedRow(), 0).toString()).setName(acoes.getValueAt(acoes.getSelectedRow(), 1).toString());

            frame.addMonitor(empresa);
        }
    }
}
