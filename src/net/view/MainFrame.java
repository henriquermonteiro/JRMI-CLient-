/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import net.controles.Controller;
import net.rmi.beans.Empresa;
import net.rmi.beans.Operacao;
import net.rmi.interfaces.ClientInterface;

/**
 *
 * @author henrique
 */
public class MainFrame extends JFrame{
    private TopPanel topPanel;
    private CarteiraPanel carteira;
    private GeneralPanel general;
    private Controller controller; 
            
    public MainFrame(Controller cont) {
        super("Aplicações da Bolsa");
        controller = cont;
        
        general = new GeneralPanel(this);
        topPanel = new TopPanel();
        carteira = new CarteiraPanel(this);
        
        this.setLayout(new BorderLayout());
        
        JTabbedPane tabed = new JTabbedPane();
        tabed.addTab("Geral", general);
        tabed.addTab("Carteira", carteira);
        
        this.add(tabed);
        this.add(topPanel, BorderLayout.NORTH);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setVisible(true);
    }
    
    public void notifyOperation(Operacao operacao, Empresa emp){
        String info, title, auxOpType;
        
        if(operacao.isCompra()){
            auxOpType = "compra";
        }else{
            auxOpType = "venda";
        }
        
        title = emp.getName();
        
        info = "<html>Operação de " + auxOpType + " realizada com sucesso. </br>"
                + operacao.getQuantidade() + " ações negociadas a um preço unitário de R$ "
                + (operacao.getPreçoUnitarioDesejado() /100.0) + ", totalizando R$ "
                + ((operacao.getQuantidade() * operacao.getPreçoUnitarioDesejado()) / 100.0) + ".</html>";
        
//        System.out.println(info);
        
//        JOptionPane.showMessageDialog(null, info);
        showMessage(title, info);
    }
    
    ClientInterface getClient(){
        return controller.getClient();
    }
    
    void showMessage(String title, String info){
        JFrame f = new JFrame(title);
        
        JLabel label = new JLabel(info);
        
        f.setLayout(new BorderLayout());
        
        f.add(label);
        
        f.setSize(300, 200);
        
        f.setVisible(true);
    }
    
    public void notifyUpdate(Empresa empresaAtual, Integer oldValue){
        topPanel.update(empresaAtual, oldValue);
        carteira.repaint();
    }
    
    ArrayList<Empresa> refresh(){
        return controller.getListaEmpresas();
    }
    
    void addMonitor(Empresa emp){
        controller.addCompanyListener(emp);
        carteira.addMonitoredCompany(emp);
    }
    
    void registerOperation(Operacao ope){
        controller.registerOperation(ope);
    }
}
