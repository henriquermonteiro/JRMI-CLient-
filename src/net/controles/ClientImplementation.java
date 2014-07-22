/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.controles;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.rmi.beans.Empresa;
import net.rmi.beans.Operacao;
import net.rmi.interfaces.ClientInterface;
import net.rmi.interfaces.ServerInterface;
import net.view.MainFrame;

/**
 * Classe principal de um Cliente.
 *
 * @author henrique
 */
public class ClientImplementation extends UnicastRemoteObject implements ClientInterface {

    private Controller controller;
    private MainFrame window;

    /**
     * Construtora da classe.
     *
     * @throws RemoteException
     */
    public ClientImplementation() throws RemoteException {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1");

            ServerInterface server = (ServerInterface) reg.lookup(ServerInterface.SERVER_NAME);

            controller = new Controller(server, this);
            
            controller.startSimulation();
            
            window = new MainFrame(controller);
            
            controller.loadSimulation(window);

        } catch (NotBoundException ex) {
            Logger.getLogger(ClientImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex) {
            Logger.getLogger(ClientImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para notificar o cliente sobre uma operação realizada com as ações
     * de um empresa.
     *
     * @param operacao compra ou venda efetuada por este cliente.
     * @throws RemoteException
     */
    @Override
    public boolean notifyCompletedOperation(Operacao operacao) throws RemoteException {
        System.out.println("Operação recebida (concluída): " + operacao.getCompanyID() + ", "+operacao.getPreçoUnitarioDesejado() + ", " + operacao.getQuantidade());
        if (controller.receiveCompletedOperation(operacao)) {
            window.notifyOperation(operacao, controller.getCompanyFor(operacao.getCompanyID()));
            return true;
        }
        
        return false;
    }

    /**
     * Método que recebe notificação de atualização de valores.
     *
     * @param empresaAtualizado valor da ação de uma empresa que foi atualizada.
     * @throws RemoteException
     */
    @Override
    public void notifyUpdate(Empresa empresaAtualizado) throws RemoteException {
        int oldValue = controller.receiveUpdate(empresaAtualizado);

        if (oldValue != 0) {
            window.notifyUpdate(empresaAtualizado, oldValue);
        }
    }

    /**
     * Método Main da implementação Cliente.
     *
     * @param args
     */
    public static void main(String... args) {
        try {
            new ClientImplementation();

        } catch (RemoteException ex) {
            Logger.getLogger(ClientImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
