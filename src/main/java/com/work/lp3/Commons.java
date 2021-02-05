package com.work.lp3;

import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class Commons {


    public static JFrame getJanela(String s) {
        JFrame janela = new JFrame(s);
        janela.setSize(880, 1000);
        janela.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);

        return janela;
    }
}
