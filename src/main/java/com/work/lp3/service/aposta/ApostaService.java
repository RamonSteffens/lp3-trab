package com.work.lp3.service.aposta;

import com.work.lp3.entity.Aposta;
import com.work.lp3.entity.Apostador;
import com.work.lp3.repository.ApostaRepository;
import com.work.lp3.repository.JogoRepository;
import com.work.lp3.service.jogo.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.work.lp3.Commons.getJanela;
import static com.work.lp3.service.aposta.BotaoApostaService.*;
import static com.work.lp3.service.jogo.JogoService.btListarJogo;
import static com.work.lp3.service.jogo.JogoService.jogosJList;

@Service
public class ApostaService {

    @Autowired
    private ApostaRepository apostaRepository;

    @Autowired
    private BotaoApostaService botaoApostaService;

    @Autowired
    private JogoService jogoService;

    @Autowired
    private JogoRepository jogoRepository;

    //BOTOES RELACIONADOS AO MENU DE APOSTAS
    private JButton btCadastroAposta = new JButton("Cadastrar aposta");
    private JButton btListarApostas = new JButton("Listar apostas");

    //VARIAVEIS DE APOSTA
    public static JTextField textFieldValorAposta = new JTextField();

    public static JList<Aposta> apostasJListDeUmApostador = new JList<>();

    public static JList<Aposta> apostasJList = new JList<>();

    public void redirecionaParaTelaAposta(Apostador apostador) {
        estruturaMenuAposta(apostador);
    }

    private void estruturaMenuAposta(Apostador apostador) {
        criarMenuAposta();
        //AÇÃO DO BOTAO DE CADASTRO
        btCadastroAposta.addActionListener(e -> criaMenuCadastroAposta());
        //AÇÃO DO BOTAO DE LISTAGEM DE APOSTADORES
        btListarApostas.addActionListener(e -> criaMenuListagemApostas());

        botaoApostaService.defineAcoesDosBotoesRelacionadoAAposta(apostaRepository, apostador);
    }

    private void criarMenuAposta() {
        var janela = getJanela("Menu aposta");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));

        painel.add(btCadastroAposta);
        painel.add(btListarApostas);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.getContentPane().add(painel, BorderLayout.CENTER);

        janela.revalidate();
    }

    private void criaMenuCadastroAposta() {
        var janela = getJanela("Cadastro de Apostas");
        jogoService.defineAcoesDosBotoesRelacionadoAJogo(jogoRepository);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));

        painel.setOpaque(true);
        janela.getContentPane().setLayout(new GridBagLayout());
        janela.setContentPane(painel);

        painel.add(new JLabel("Valor de aposta"));
        painel.add(textFieldValorAposta);

        painel.add(new JLabel("Jogos disponíveis"));
        painel.add(jogosJList);
        painel.add(btListarJogo);


        //Cria scroll com base na lista
        painel.add(new JScrollPane(jogosJList));

        painel.add(btCadastrarAposta);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

    }

    private void criaMenuListagemApostas() {
        var janela = getJanela("Listagem");

        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout());
        painel.setOpaque(true);

        painel.add(new JLabel("Apostas"), BorderLayout.PAGE_START);

        painel.add(apostasJListDeUmApostador);

        //Cria scroll com base na lista
        JScrollPane listScrollPane = new JScrollPane(apostasJListDeUmApostador);

        painel.add(btListarApostaDeUmApostador, BorderLayout.BEFORE_LINE_BEGINS);
        painel.add(btDeletarAposta, BorderLayout.PAGE_END);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.setContentPane(painel);
        janela.add(listScrollPane);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }


    public void listagemGeralDeApostas() {
        var janela = getJanela("Listagem geral de apostas");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));
        painel.setOpaque(true);

        botaoApostaService.listarTodasApostas(apostaRepository);

        painel.add(new JLabel("Apostas"));
        painel.add(apostasJList);
        painel.add(btListarTodasApostas);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.setContentPane(painel);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

    }
}
