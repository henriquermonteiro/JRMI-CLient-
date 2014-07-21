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
 * Interface para o servidor JRMI.
 * @author Henriques
 */
public interface ServerInterface extends Remote{
    public final static String SERVER_NAME = "Bolsa de Valores";

    //Metodo que registra um novo cliente que tem interesse em ações de uma determinada empresa.
    public boolean listenToCompany(Empresa empresa, ClientInterface client) throws RemoteException;

    //Registra uma nova operação de compra ou venda.
    public boolean registerOperation(Operacao operacao) throws RemoteException;

    //Retorna uma lista de todas as empresas cadastradas no servidor.
    public ArrayList<Empresa> getAllCompaniesStatus() throws RemoteException;
    
    //Retorna os dados de uma empresa a partir de seu ID.
    public Empresa getCompanyForID(String ID) throws RemoteException;
    
}
