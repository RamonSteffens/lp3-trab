package com.work.lp3.service;

import com.work.lp3.repository.ApostaRepository;
import com.work.lp3.repository.JogoRepository;
import com.work.lp3.service.aposta.ApostaService;
import com.work.lp3.service.apostador.ApostadorService;
import com.work.lp3.service.jogo.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

import static com.work.lp3.Commons.getJanela;

@Service
public class MenuGeralService {

    @Autowired
    private ApostadorService apostadorService;

    @Autowired
    private JogoService jogoService;

    @Autowired
    private ApostaService apostaService;

    //BOTOES RELACIONADOS AO MENU DE APOSTAS
    private JButton btApostador = new JButton("Menu apostador");
    private JButton btJogos = new JButton("Menu de jogos");
    private JButton btListarApostas = new JButton("Listar todas apostas");


    public void criarMenuAposta() {
        var janela = getJanela("Menu geral");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));

        painel.add(btApostador);
        painel.add(btJogos);
        painel.add(btListarApostas);

        btApostador.addActionListener(e -> apostadorService.defineMenuApostador());
        btJogos.addActionListener(e -> jogoService.defineMenuJogo());
        btListarApostas.addActionListener(e -> apostaService.listagemGeralDeApostas());


        janela.getContentPane().setLayout(new BorderLayout());
        janela.getContentPane().add(painel, BorderLayout.CENTER);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
