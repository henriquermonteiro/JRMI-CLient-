/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.controles;

import net.rmi.beans.Empresa;
import net.util.MutableValue;

/**
 *
 * @author henrique
 */
public class EstoqueAcao {
    private Empresa empresa;
    private MutableValue<Integer> quantidade;

    public EstoqueAcao(Empresa empresa) {
        this.empresa = empresa;
        quantidade = new MutableValue<Integer>((Integer)0);
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Integer getQuantidade() {
        return quantidade.getValue();
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade.setValue(Math.abs(quantidade));
    }
    
    public void incrementQuantidade(int incremento){
        setQuantidade(quantidade.getValue() + incremento);
    }

    public MutableValue<Integer> getQuantidade_Mutable() {
        return quantidade;
    }
}
