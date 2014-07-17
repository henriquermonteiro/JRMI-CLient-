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
import javax.swing.JOptionPane;
import net.rmi.beans.Empresa;
import net.rmi.beans.Operacao;
import net.rmi.interfaces.ClientInterface;
import net.rmi.interfaces.ServerInterface;
import net.view.MainFrame;

/**
 *
 * @author henrique
 */
public class ClientImplementation extends UnicastRemoteObject implements ClientInterface{
    private Controller controller;
    private MainFrame window;
    
    public ClientImplementation() throws RemoteException {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1");
            
            ServerInterface server = (ServerInterface) reg.lookup(ServerInterface.SERVER_NAME);
            
            controller = new Controller(server, this);
            window = new MainFrame(controller);
        } catch (NotBoundException ex) {
            Logger.getLogger(ClientImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessException ex) {
            Logger.getLogger(ClientImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notifyCompletedOperation(Operacao operacao) throws RemoteException {
        window.notifyOperation(operacao, controller.getCompanyFor(operacao.getCompanyID()));
    }

    @Override
    public void notifyUpdate(Empresa empresaAtualizado) throws RemoteException {
        int oldValue = controller.receiveUpdate(empresaAtualizado);
        
        if(oldValue != 0){
            window.notifyUpdate(empresaAtualizado, oldValue);
        }
    }
    
    public static void main(String ... args){
        
//        JOptionPane.showMessageDialog(null, "Bla", "Titulo", JOptionPane.PLAIN_MESSAGE);
        
        try {
            new ClientImplementation();
        } catch (RemoteException ex) {
            Logger.getLogger(ClientImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
