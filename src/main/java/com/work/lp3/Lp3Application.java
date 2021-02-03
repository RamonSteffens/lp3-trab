package com.work.lp3;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.SqlDateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class Lp3Application {



    //VARIAVEIS DE APOSTADOR
    private JTextField textFieldNomeApostador = new JTextField();
    //TIPO PARA TRABALHAR COM DATAS, LIB EXTERNA
    private JDatePanel dataAniversarioField = new JDatePanel(new SqlDateModel());

    //VARIAVEIS DE APOSTA
    private JTextField textFieldValorAposta = new JTextField();
    private JDatePanel dataCriacaoAposta = new JDatePanel(new SqlDateModel());

    //VARIAVEIS DE JOGO
    private JTextField textFieldTimeCasaJogo = new JTextField();
    private JTextField textFieldTimeForaJogo = new JTextField();
    private JTextField textFieldTotalDeGolsJogo = new JTextField();

    //BOTOES RELACIONADO A AÇÕES DE APOSTADOR
    private JButton btCadastrarApostador = new JButton("Cadastrar apostador");
    private JButton btListarApostador = new JButton("Listar apostadores");
    private JButton btDeletarApostador = new JButton("Deletar apostador");

    //BOTOES RELACIONADO A AÇÕES DE APOSTA
    private JButton btCadastrarAposta = new JButton("Cadastrar aposta");
    private JButton btListarAposta = new JButton("Listar apostas");
    private JButton btDeletarAposta = new JButton("Deletar aposta");

    //BOTOES RELACIONADO A AÇÕES DE JOGO
    private JButton btCadastrarJogo = new JButton("Cadastrar jogo");
    private JButton btListarJogo = new JButton("Listar jogos");
    private JButton btDeletarJogo = new JButton("Deletar jogo");

    //INJETANDO REPOSITORIO DE APOSTADOR, APOSTA E JOGO
    @Autowired
    private ApostadorRepository apostadorRepository;

    @Autowired
    private ApostaRepository apostaRepository;

    @Autowired
    private JogoRepository jogoRepository;

    //LISTAS UTILIZADAS NA LISTAGEM DE APOSTADORES, APOSTAS E JOGOS
    private JList<Apostador> apostadoresJList = new JList<>();

    private JList<Aposta> apostasJList = new JList<>();

    private JList<Jogo> jogosJList = new JList<>();

    //BOTOES RELACIONADOS AO MENU DE APOSTADOR
    private JButton btCadastroApostador = new JButton("Cadastro");
    private JButton btListarApostadores = new JButton("Listar apostadores");

    //BOTOES RELACIONADOS AO MENU DE APOSTAS
    private JButton btCadastroAposta = new JButton("Cadastrar aposta");
    private JButton btListarApostas = new JButton("Listar apostas");
    private JButton btEncontrarApostaPorId = new JButton ("Encontrar aposta");

    //BOTOES RELACIONADOS AO MENU DE JOGOS
    private JButton btCadastroJogo = new JButton("Cadastrar jogo");
    private JButton btListarJogos = new JButton("Listar jogos");
    private JButton btEncontrarJogoPorId = new JButton ("Encontrar jogo");

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Lp3Application.class);
        builder.headless(false);
        builder.run(args);
    }

    @Bean
    public void run() {

        defineMenuApostador();

        defineMenuAposta();

        defineMenuJogo();
    }

    private void defineMenuApostador() {
        criarMenuApostador();

        //AÇÃO DO BOTAO DE CADASTRO
        btCadastroApostador.addActionListener(e -> criaMenuCadastroApostador());
        //AÇÃO DO BOTAO DE LISTAGEM DE APOSTADORES
        btListarApostadores.addActionListener(e -> criaMenuListagemApostador());

        defineAcoesDosBotoesRelacionadoAApostador(apostadorRepository);
    }

    private void defineAcoesDosBotoesRelacionadoAApostador(ApostadorRepository apostadorRepository) {
        //CADASTRA APOSTADOR
        btCadastrarApostador.addActionListener(a -> {

            Apostador apostador = new Apostador();

            String nome = textFieldNomeApostador.getText();

            Date selectedDate = (Date) dataAniversarioField.getModel().getValue();

            apostador.setNome(nome);
            apostador.setDataAniversario(selectedDate);

            apostadorRepository.save(apostador);

            JOptionPane.showMessageDialog(btCadastrarApostador, "Apostador cadastrado");
        });
        //LISTA DE APOSTADOR
        btListarApostador.addActionListener(a -> {

            List<Apostador> apostadores = apostadorRepository.findAll();

            List<Apostador> apostadoresReal = new ArrayList<>(apostadores);

            Apostador[] apostadoresArray = apostadoresReal.toArray(new Apostador[0]);

            apostadoresJList.setListData(apostadoresArray);

            if (apostadores.size() > 0) { //Habilita botão de deleção
                btDeletarApostador.setEnabled(true);
            }
        });
        //DELETE APOSTADOR SELECIONADO
        btDeletarApostador.addActionListener(a -> {

            var apostador = apostadoresJList.getSelectedValue();
            //DELETE APOSTADOR
            if (Objects.nonNull(apostador)) {
                apostadorRepository.deleteById(apostador.getId());

                //MONTA NOVA LISTA
                var listaBanco = apostadorRepository.findAll();

                apostadoresJList.setListData(listaBanco.toArray(Apostador[]::new));

                if (listaBanco.size() == 0) { //Desabilita botão de deleção
                    btDeletarApostador.setEnabled(false);
                }
            }
        });
    }

    private void criarMenuApostador() {
        var janela = getJanela("Menu apostador");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));

        painel.add(btCadastroApostador);
        painel.add(new JLabel());
        painel.add(btListarApostadores);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.getContentPane().add(painel, BorderLayout.CENTER);

        janela.revalidate();
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

        janela.getContentPane().setLayout(new BorderLayout());
        janela.setContentPane(painel);
        janela.add(listScrollPane);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private void defineMenuAposta() {
        criarMenuAposta();

        //AÇÃO DO BOTAO DE CADASTRO
        btCadastroAposta.addActionListener(e -> criaMenuCadastroAposta());
        //AÇÃO DO BOTAO DE LISTAGEM DE APOSTADORES
        btListarApostas.addActionListener(e -> criaMenuListagemApostas());

        defineAcoesDosBotoesRelacionadoAAposta(apostaRepository);
    }

    private void defineAcoesDosBotoesRelacionadoAAposta(ApostaRepository apostaRepository) {
        //CADASTRA APOSTA
        btCadastrarAposta.addActionListener(a -> {
            Apostador apostador = apostadoresJList.getSelectedValue();

            Aposta aposta = new Aposta();

            String valor  = textFieldValorAposta.getText();

            Date selectedDate = (Date) dataCriacaoAposta.getModel().getValue();

            aposta.setValorAposta(Double.valueOf(valor));
            aposta.setDataCriacao(selectedDate);
            aposta.setApostador(apostador);

            apostaRepository.save(aposta);

            JOptionPane.showMessageDialog(btCadastrarAposta, "Aposta cadastrada");
        });
        //LISTA DE APOSTAS
        btListarAposta.addActionListener(a -> {

            List<Aposta> apostas = apostaRepository.findAll();

            List<Aposta> apostasReal = new ArrayList<>(apostas);

            Aposta[] apostasArray = apostasReal.toArray(new Aposta[0]);

            apostasJList.setListData(apostasArray);

            if (apostas.size() > 0) { //Habilita botão de deleção
                btDeletarAposta.setEnabled(true);
            }
        });
        //DELETE APOSTA SELECIONADA
        btDeletarAposta.addActionListener(a -> {

            var aposta = apostasJList.getSelectedValue();
            //DELETE APOSTA
            if (Objects.nonNull(aposta)) {
                apostaRepository.deleteById(aposta.getId());

                //MONTA NOVA LISTA
                var listaBanco = apostaRepository.findAll();

                apostasJList.setListData(listaBanco.toArray(Aposta[]::new));

                if (listaBanco.size() == 0) { //Desabilita botão de deleção
                    btDeletarAposta.setEnabled(false);
                }
            }
        });
    }

    private void criarMenuAposta() {
        var janela = getJanela("Menu aposta");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));

        painel.add(btCadastroAposta);
        painel.add(btListarApostas);
        painel.add(btEncontrarApostaPorId);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.getContentPane().add(painel, BorderLayout.CENTER);

        janela.revalidate();
    }

    private void criaMenuCadastroAposta() {
        var janela = getJanela("Cadastro de Apostas");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));

        painel.setOpaque(true);

        painel.add(new JLabel("Apostadores"), BorderLayout.PAGE_START);

        painel.add(apostadoresJList, BorderLayout.AFTER_LAST_LINE);

        //Cria scroll com base na lista
        JScrollPane listScrollPane = new JScrollPane(apostadoresJList);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.setContentPane(painel);
        janela.add(listScrollPane);

        painel.add(btListarApostador, BorderLayout.BEFORE_LINE_BEGINS);
        painel.add(new JLabel("Valor a Apostar"));
        painel.add(textFieldValorAposta);

        painel.add(new JLabel("Data da Aposta"));
        painel.add(dataCriacaoAposta);

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

        painel.add(apostasJList, BorderLayout.CENTER);

        //Cria scroll com base na lista
        JScrollPane listScrollPane = new JScrollPane(apostasJList);

        painel.add(btListarAposta, BorderLayout.BEFORE_LINE_BEGINS);
        painel.add(btDeletarAposta, BorderLayout.PAGE_END);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.setContentPane(painel);
        janela.add(listScrollPane);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private void defineMenuJogo() {
        criarMenuJogo();

        //AÇÃO DO BOTAO DE CADASTRO
        btCadastroJogo.addActionListener(e -> criaMenuCadastroJogo());
        //AÇÃO DO BOTAO DE LISTAGEM DE APOSTADORES
        btListarJogos.addActionListener(e -> criaMenuListagemJogos());

        defineAcoesDosBotoesRelacionadoAJogo(jogoRepository);
    }

    private void defineAcoesDosBotoesRelacionadoAJogo(JogoRepository jogoRepository) {
        //CADASTRA JOGO
        btCadastrarJogo.addActionListener(a -> {

            Jogo jogo = new Jogo();

            String timeCasa = textFieldTimeCasaJogo.getText();
            String timeFora = textFieldTimeForaJogo.getText();
            Integer totalDeGols = Integer.valueOf(textFieldTotalDeGolsJogo.getText());

            jogo.setTimeCasa(timeCasa);
            jogo.setTimeFora(timeFora);
            jogo.setTotalDeGols(totalDeGols);

            jogoRepository.save(jogo);

            JOptionPane.showMessageDialog(btCadastrarJogo, "Jogo cadastrado");
        });
        //LISTA DE JOGO
        btListarJogo.addActionListener(a -> {

            List<Jogo> jogos = jogoRepository.findAll();

            List<Jogo> jogosReal = new ArrayList<>(jogos);

            Jogo[] jogosArray = jogosReal.toArray(new Jogo[0]);

            jogosJList.setListData(jogosArray);

            if (jogos.size() > 0) { //Habilita botão de deleção
                btDeletarJogo.setEnabled(true);
            }
        });
        //DELETE JOGO SELECIONADO
        btDeletarJogo.addActionListener(a -> {

            var jogo = jogosJList.getSelectedValue();
            //DELETE APOSTADOR
            if (Objects.nonNull(jogo)) {
                jogoRepository.deleteById(jogo.getId());

                //MONTA NOVA LISTA
                var listaBanco = jogoRepository.findAll();

                jogosJList.setListData(listaBanco.toArray(Jogo[]::new));

                if (listaBanco.size() == 0) { //Desabilita botão de deleção
                    btDeletarJogo.setEnabled(false);
                }
            }
        });
    }

    private void criarMenuJogo() {
        var janela = getJanela("Menu jogo");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));

        painel.add(btCadastroJogo);
        painel.add(btListarJogos);
        painel.add(btEncontrarJogoPorId);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.getContentPane().add(painel, BorderLayout.CENTER);

        janela.revalidate();
    }

    private void criaMenuCadastroJogo() {
        var janela = getJanela("Cadastro");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));

        painel.add(new JLabel("Time em casa"));
        painel.add(textFieldTimeCasaJogo);

        painel.add(new JLabel("Time de fora"));
        painel.add(textFieldTimeForaJogo);

        painel.add(new JLabel("Total de gols"));
        painel.add(textFieldTotalDeGolsJogo);

        painel.add(btCadastrarJogo);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.getContentPane().add(painel, BorderLayout.CENTER);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private void criaMenuListagemJogos() {
        var janela = getJanela("Listagem");

        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout());
        painel.setOpaque(true);

        painel.add(new JLabel("Jogos"), BorderLayout.PAGE_START);

        painel.add(jogosJList, BorderLayout.CENTER);

        //Cria scroll com base na lista
        JScrollPane listScrollPane = new JScrollPane(jogosJList);

        painel.add(btListarJogo, BorderLayout.BEFORE_LINE_BEGINS);
        painel.add(btDeletarJogo, BorderLayout.PAGE_END);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.setContentPane(painel);
        janela.add(listScrollPane);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private JFrame getJanela(String s) {
        JFrame janela = new JFrame(s);
        janela.setSize(720, 680);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);

        return janela;
    }

}
