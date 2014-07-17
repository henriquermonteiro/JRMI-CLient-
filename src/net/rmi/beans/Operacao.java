/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.rmi.beans;

import java.io.Serializable;
import net.rmi.interfaces.ClientInterface;

/**
 *
 * @author henrique
 */
public class Operacao implements Serializable{
    private final boolean isCompra;
    private String companyID;
    private Integer quantidade;
    private Integer preçoUnitarioDesejado;
    
    private final ClientInterface clientSign;

    public Operacao(boolean isCompra, String company, ClientInterface client) {
        this.isCompra = isCompra;
        this.clientSign = client;
        this.companyID = company;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Operacao setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        
        return this;
    }

    public Integer getPreçoUnitarioDesejado() {
        return preçoUnitarioDesejado;
    }

    public Operacao setPreçoUnitarioDesejado(Integer preçoUnitárioDesejado) {
        this.preçoUnitarioDesejado = preçoUnitárioDesejado;
        
        return this;
    }

    public boolean isCompra() {
        return isCompra;
    }

    public ClientInterface getClientSign() {
        return clientSign;
    }

    public String getCompanyID() {
        return companyID;
    }
    
    
}
