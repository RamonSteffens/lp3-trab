package com.work.lp3.service.apostador;

import com.work.lp3.entity.Apostador;
import com.work.lp3.repository.ApostadorRepository;
import com.work.lp3.service.aposta.ApostaService;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.SqlDateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static com.work.lp3.Commons.getJanela;
import static com.work.lp3.service.apostador.BotaoApostadorService.*;


@Service
public class ApostadorService {

    @Autowired
    private ApostadorRepository apostadorRepository;

    @Autowired
    private BotaoApostadorService botaoApostadorService;

    //VARIAVEIS DE APOSTADOR
    public static JTextField textFieldNomeApostador = new JTextField();
    public static JTextField textFieldNomeApostadorProcurado = new JTextField();
    public static JDatePanel dataAniversarioField = new JDatePanel(new SqlDateModel());

    //LISTAS UTILIZADAS NA LISTAGEM DE APOSTADORES
    public static JList<Apostador> apostadoresJList = new JList<>();

    //BOTOES RELACIONADOS AO MENU DE APOSTADOR
    private JButton btCadastroApostador = new JButton("Cadastro");
    private JButton btListarApostadores = new JButton("Listar apostadores");
    private JButton btLoginApostador = new JButton("Login");

    public void defineMenuApostador() {
        criarMenuApostador();
        //AÇÃO DO BOTAO DE LOGIN
        btLoginApostador.addActionListener(e -> criaMenuLogin());


        botaoApostadorService.definirAcoes(apostadorRepository);
    }

    private void criaMenuLogin() {
        var janela = getJanela("Login");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));

        painel.add(new JLabel("Digite seu login"));
        painel.add(textFieldNomeApostadorProcurado);

        painel.add(btEntrar);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.getContentPane().add(painel, BorderLayout.CENTER);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

    }


    private void criarMenuApostador() {
        var janela = getJanela("Menu apostador");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));

        painel.add(btCadastroApostador);
        painel.add(new JLabel());
        painel.add(btLoginApostador);
        painel.add(new JLabel());
        painel.add(btListarApostadores);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.getContentPane().add(painel, BorderLayout.CENTER);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        //AÇÃO DO BOTAO DE CADASTRO
        btCadastroApostador.addActionListener(e -> criaMenuCadastroApostador());
        //AÇÃO DO BOTAO DE LISTAGEM DE APOSTADORES
        btListarApostadores.addActionListener(e -> criaMenuListagemApostador());
    }

    private void criaMenuCadastroApostador() {
        var janela = getJanela("Cadastro");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));

        painel.add(new JLabel("Nome"));
        painel.add(textFieldNomeApostador);

        painel.add(new JLabel("Data de Aniversario"));
        painel.add(dataAniversarioField);

        painel.add(btCadastrarApostador);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.getContentPane().add(painel, BorderLayout.CENTER);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private void criaMenuListagemApostador() {
        var janela = getJanela("Listagem");

        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout());
        painel.setOpaque(true);

        painel.add(new JLabel("Apostadores"), BorderLayout.PAGE_START);

        painel.add(apostadoresJList, BorderLayout.CENTER);

        //Cria scroll com base na lista
        JScrollPane listScrollPane = new JScrollPane(apostadoresJList);

        painel.add(btListarApostador, BorderLayout.BEFORE_LINE_BEGINS);
        painel.add(btDeletarApostador, BorderLayout.PAGE_END);
        painel.add(btLoginApostador, BorderLayout.BEFORE_FIRST_LINE);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.setContentPane(painel);
        janela.add(listScrollPane);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }
}
