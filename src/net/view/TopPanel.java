/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.rmi.beans.Empresa;

/**
 * Classe
 * 
 * @author henrique
 */
public class TopPanel extends JPanel {

    private JLabel message;

    /**
     * Construtora da classe.
     */
    public TopPanel() {
        super(new FlowLayout(FlowLayout.CENTER, 10, 10));
        message = new JLabel("Teste");
        this.add(message);
    }
    
    /**
     * Método para apresentar a porcentagem que um valor oscilou.
     * 
     * @param emp empresa.
     * @param oldV valor antigo.
     */
    void update(Empresa emp, Integer oldV) {
        Integer modify = emp.getValue() - oldV;
        message.setIcon(getIcon(modify));
        
        Double porc = (100.0*modify)/oldV;
        message.setText(emp.getName() + " " + new DecimalFormat("##0,00 %").format(porc));
    }

    /**
     * Método para o uso de ícones que mostram quanto subiu, ou desceu, o valor de uma ação.
     * 
     * @param modify valor alterado.
     * @return imagem com seta para cima, ou para baixo.
     */
    private Icon getIcon(Integer modify) {
        BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 20, 20);
        
        int x[], y[];
        
        if (modify > 0) {
            g2d.setColor(Color.BLUE);
            
            x = new int[]{0, 19, 10};
            y = new int[]{19, 19, 0};
        } else {
            g2d.setColor(Color.RED);
            
            x = new int[]{0, 19, 10};
            y = new int[]{0, 0, 19};
        }
        
        g2d.fill(new Polygon(x, y, 3));
        
        return new ImageIcon(image);
    }
}
