/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import net.rmi.beans.Empresa;
import net.rmi.beans.Operacao;

/**
 *
 * @author henrique
 */
public interface ServerInterface extends Remote{
    public final static String SERVER_NAME = "Bolsa de Valores";
    //Registrar interece
    public boolean listenToCompany(Empresa empresa, ClientInterface client) throws RemoteException ;
    
    //Registrar operação(ões)
    public boolean registerOperation(Operacao operacao) throws RemoteException ;
    
    //Informa Ações
    public ArrayList<Empresa> getAllCompaniesStatus() throws RemoteException ;
    
}
