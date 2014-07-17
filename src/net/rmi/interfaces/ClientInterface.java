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
    //recebe notificação de operação concluída
    public void notifyCompletedOperation(Operacao operacao) throws RemoteException ;
    
    //recebe notificação de atualização de valores
    public void notifyUpdate(Empresa empresaAtualizado) throws RemoteException ;
}
