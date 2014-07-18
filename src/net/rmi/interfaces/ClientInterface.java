/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import net.rmi.beans.Empresa;
import net.rmi.beans.Operacao;

/**
 *
 * @author henrique
 */
public interface ClientInterface extends Remote{

    //Recebe notificação de operação realizada.
    public void notifyCompletedOperation(Operacao operacao) throws RemoteException ;
    
    //Recebe notificação de atualização dos valores da ação de uma empresa.
    public void notifyUpdate(Empresa empresaAtualizado) throws RemoteException ;
}
