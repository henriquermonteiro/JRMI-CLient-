/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.controles;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import net.rmi.beans.Empresa;
import net.rmi.beans.Operacao;
import net.rmi.interfaces.ClientInterface;
import net.rmi.interfaces.ServerInterface;

/**
 *
 * @author henrique
 */
public class Controller {

    private HashMap<String, Empresa> empresas;
    private ServerInterface server;
    private ClientInterface clientThis;

    public Controller(ServerInterface server, ClientInterface client) {
        empresas = new HashMap<>();
        this.server = server;
        this.clientThis = client;
    }

    public void addCompanyListener(Empresa empresa) {
        if (!empresas.containsKey(empresa.getID())) {
            try {
                server.listenToCompany(empresa, clientThis);

                empresas.put(empresa.getID(), empresa);

            } catch (RemoteException ex) {
                System.err.println(this.getClass() + ": Erro remoto #addCompanyListener");
            }
        }
    }

    public boolean registerOperation(Operacao operacao) {
        try {
            server.registerOperation(operacao);
            return true;
        } catch (RemoteException ex) {
            System.err.println(this.getClass() + ": Erro remoto #registerOperation");
            return false;
        }
    }

    //Redireciona para empresa;
    public Empresa getCompanyFor(String empresaID) {
        return empresas.get(empresaID);
    }

    //Retorna lista das empresas;
    public ArrayList<Empresa> getListaEmpresas() {
        try {
            return server.getAllCompaniesStatus();
        } catch (RemoteException ex) {
            System.err.println(this.getClass() + ": Erro remoto #getListaEmpresas");
        }

        return new ArrayList<>();
    }
    
    public ClientInterface getClient(){
        return clientThis;
    }
    
    public Integer receiveUpdate(Empresa atual){
        Empresa cachedData = empresas.get(atual.getID());
        
        if(cachedData != null){
            Integer oldValue = cachedData.getValue();
            
            cachedData.setValue(atual.getValue());
            return (oldValue != null? oldValue: 0);
        }
        
        return 0;
    }
}
