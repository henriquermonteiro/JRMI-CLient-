/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.controles;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.rmi.beans.Empresa;
import net.rmi.beans.Operacao;
import net.rmi.interfaces.ClientInterface;
import net.rmi.interfaces.ServerInterface;
import net.view.MainFrame;

/**
 * Classe de controle do Cliente.
 *
 * @author henrique
 */
public class Controller {

    private HashMap<String, EstoqueAcao> empresas;
    private ServerInterface server;
    private ClientInterface clientThis;

    /**
     * Construtora da classe.
     *
     * @param server interface do servidor.
     * @param client interface do cliente.
     */
    public Controller(ServerInterface server, ClientInterface client) {
        empresas = new HashMap<>();
        this.server = server;
        this.clientThis = client;
    }

    /**
     * Método para ouvir atualizações de uma determinada empresa.
     *
     * @param empresa empresa que tenha interesse, por parte do cliente.
     */
    public EstoqueAcao addCompanyListener(Empresa empresa) {
        if (!empresas.containsKey(empresa.getID())) {
            try {
                server.listenToCompany(empresa, clientThis);

                empresas.put(empresa.getID(), new EstoqueAcao(empresa));

            } catch (RemoteException ex) {
                System.err.println(this.getClass() + ": Erro remoto #addCompanyListener");
            }
        }
        
        return empresas.get(empresa.getID());
    }

    /**
     * Método para avisar ao servidor sobre uma operação desejada.
     *
     * @param operacao compra ou venda de ações de uma empresa.
     * @return
     */
    public boolean registerOperation(Operacao operacao) {
        if (!operacao.isCompra()) {
            EstoqueAcao estoque = empresas.get(operacao.getCompanyID());

            if (estoque.getQuantidade() < operacao.getQuantidade()) {
                return false;
            }
        }
        try {
            server.registerOperation(operacao);
            return true;

        } catch (RemoteException ex) {
            System.err.println(this.getClass() + ": Erro remoto #registerOperation");
            return false;
        }
    }

    /**
     * Método que retorna uma empresa, baseado em sua identificação.
     *
     * @param empresaID identificação de uma empresa.
     * @return
     */
    public Empresa getCompanyFor(String empresaID) {
        return empresas.get(empresaID).getEmpresa();
    }

    /**
     * Método para retornar uma lista das empresas cadastradas no servidor.
     *
     * @return uma lista de empresas.
     */
    public ArrayList<Empresa> getListaEmpresas() {
        try {
            return server.getAllCompaniesStatus();

        } catch (RemoteException ex) {
            System.err.println(this.getClass() + ": Erro remoto #getListaEmpresas");
        }

        return new ArrayList<>();
    }

    /**
     * Método que retorna a interface do cliente.
     *
     * @return interface de um cliente.
     */
    public ClientInterface getClient() {
        return clientThis;
    }

    /**
     * Método que faz a atualização do valor de uma ação.
     *
     * @param atual empresa com valores atualizados.
     * @return
     */
    public Integer receiveUpdate(Empresa atual) {

        Empresa cachedData = empresas.get(atual.getID()).getEmpresa();

        if (cachedData != null) {
            Integer oldValue = cachedData.getValue();

            cachedData.setValue(atual.getValue());
            return (oldValue != null ? oldValue : 0);
        }

        return 0;
    }

    public boolean receiveCompletedOperation(Operacao operacao) {
        EstoqueAcao estoque = empresas.get(operacao.getCompanyID());

        if (operacao.isCompra()) {
            if (estoque == null) {
                try {
                    estoque = new EstoqueAcao(server.getCompanyForID(operacao.getCompanyID()));

                } catch (RemoteException ex) {
                    System.out.println(this.getClass() + " !| #ReceiveCompletedOperation: Erro ao tentar pegar os dados de uma empresa.");
                    return false;
                }
            }

            estoque.incrementQuantidade(operacao.getQuantidade());

        } else {
            if (estoque == null) {
                return false;
            }

            estoque.incrementQuantidade(operacao.getQuantidade() * (-1));
        }

        return true;
    }

    public boolean validateOperation(Operacao operacao) {
        EstoqueAcao estoque = empresas.get(operacao.getCompanyID());

        if (!operacao.isCompra()) {
            if (estoque == null) {
                return false;
            }

            if (estoque.getQuantidade() < operacao.getQuantidade()) {
                return false;
            }
        }

        return true;
    }
    
    void startSimulation(){
        try {
            ArrayList<Empresa> empresas = server.getAllCompaniesStatus();
            
            int quantidade = Math.max(1, (int)(empresas.size() * 0.1));
            
            for(int k = 0; k < quantidade; k++){
                int random = (int) (Math.random() * empresas.size());
                
                EstoqueAcao estoque = new EstoqueAcao(empresas.get(random));
                estoque.setQuantidade((int)(Math.random() * 9) + 1);
                
                this.empresas.put(estoque.getEmpresa().getID(), estoque);
            }
        } catch (RemoteException ex) {
        }
    }

    void loadSimulation(MainFrame window) {
        for(EstoqueAcao est : empresas.values()){
            window.addMonitor(est.getEmpresa());
        }
    }
}
