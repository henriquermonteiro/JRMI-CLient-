/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import net.controles.Controller;
import net.rmi.beans.Empresa;
import net.rmi.beans.Operacao;
import net.rmi.interfaces.ClientInterface;

/**
 * Classe da janela de um cliente.
 * 
 * @author henrique
 */
public class MainFrame extends JFrame {
    
    private TopPanel topPanel;
    private CarteiraPanel carteira;
    private GeneralPanel general;
    private Controller controller; 
            
    /**
     * Construtora da classe.
     * 
     * @param cont controle de um cliente.
     */
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
        this.setSize(800, 600);
        this.setVisible(true);
    }
    
    /**
     * Mensagem de operação realizada.
     * 
     * @param operacao compra ou venda.
     * @param emp empresa em que foram realizadas transações com as suas ações.
     */    
    public void notifyOperation(Operacao operacao, Empresa emp) {
        
        String info, title, auxOpType;
        
        if (operacao.isCompra()) {
            auxOpType = "compra";
        } else {
            auxOpType = "venda";
        }
        
        title = emp.getName();
        
        info = "<html>Operação de " + auxOpType + " realizada com sucesso. </br>"
                + operacao.getQuantidade() + " ações negociadas a um preço unitário de R$ "
                + (operacao.getPreçoUnitarioDesejado() /100.0) + ", totalizando R$ "
                + ((operacao.getQuantidade() * operacao.getPreçoUnitarioDesejado()) / 100.0) + ".</html>";
        
        showMessage(title, info);
        
        carteira.repaint();
    }
    
    /**
     * Retorna a interface de um cliente.
     * 
     * @return interface.
     */
    ClientInterface getClient(){
        return controller.getClient();
    }
    
    /**
     * Método que cria o popup.
     * 
     * @param title título da mensagem.
     * @param info informação da mensagem.
     */
    void showMessage(String title, String info){
        JFrame f = new JFrame(title);
        
        JLabel label = new JLabel(info);
        
        f.setLayout(new BorderLayout());
        
        f.add(label);
        
        f.setSize(300, 200);
        
        f.setVisible(true);
    }
    
    /**
     * Método que apresenta a mudança do preço de uma ação.
     * 
     * @param empresaAtual empresa atualizada.
     * @param oldValue valor antigo.
     */
    public void notifyUpdate(Empresa empresaAtual, Integer oldValue) {
        
        topPanel.update(empresaAtual, oldValue);
        carteira.repaint();
    }
    
    /**
     * Método atualiza a lista de empresas.
     * 
     * @return lista de empresas.
     */
    ArrayList<Empresa> refresh(){
        return controller.getListaEmpresas();
    }
    
    /**
     * Método para monitorar as ações de uma empresa.
     * 
     * @param emp empresa de interesse do cliente.
     */
    public void addMonitor(Empresa emp){
        carteira.addMonitoredCompany(controller.addCompanyListener(emp));
    }
    
    /**
     * Método de registro de uma operação.
     * 
     * @param ope operação de compra ou venda.
     */
    void registerOperation(Operacao ope){
        controller.registerOperation(ope);
    }
    
    boolean validateOperation(Operacao ope){
        return controller.validateOperation(ope);
    }
}
