/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.rmi.beans;

import java.io.Serializable;
import java.util.Calendar;
import net.rmi.interfaces.ClientInterface;

/**
 * Bean para uma Operação.
 * Beans são classes que apenas possuem variáveis, construtora e metodos get/set;
 * @author henrique
 */
public class Operacao implements Serializable {
    
    private final boolean isCompra;
    private String companyID;
    private Integer quantidade;
    private Integer preçoUnitarioDesejado;
    private Calendar expireDate;
    
    private final ClientInterface clientSign;

    public Operacao(Operacao operacaoToClone){
        this.isCompra = operacaoToClone.isCompra;
        this.clientSign = operacaoToClone.clientSign;
        this.companyID = operacaoToClone.companyID;
        this.expireDate = operacaoToClone.expireDate;
    }
    
    public Operacao(boolean isCompra, String company, Calendar expireDate, ClientInterface client) {
        this.isCompra = isCompra;
        this.clientSign = client;
        this.companyID = company;
        this.expireDate = expireDate;
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

    public Calendar getExpireDate() {
        return expireDate;
    }
    
    
}
