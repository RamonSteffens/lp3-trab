package com.work.lp3;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.SqlDateModel;
import org.jdatepicker.UtilDateModel;
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

    private JTextField textFieldNome = new JTextField();
    private JTextField textFieldValor = new JTextField();
    private JDatePanel dataCriacaoAposta = new JDatePanel(new SqlDateModel());

    //TIPO PARA TRABALHAR COM DATAS, LIB EXTERNA
    private JDatePanel dataAniversarioField = new JDatePanel(new SqlDateModel());

    //BOTOES RELACIONADO A AÇÕES DE APOSTADOR
    private JButton btCadastrar = new JButton("Cadastrar apostador");
    private JButton btListar = new JButton("Listar apostadores");
    private JButton btDeletar = new JButton("Deletar apostador");

    //BOTOES RELACIONADO A AÇÕES DE APOSTA
    private JButton btCadastrarAposta = new JButton("Cadastrar aposta");
    private JButton btListarAposta = new JButton("Listar apostas");
    private JButton btDeletarAposta = new JButton("Deletar aposta");

    //INJETANDO REPOSITORIO DE APOSTADOR
    @Autowired
    private ApostadorRepository apostadorRepository;

    //LISTA EXIBIDA NA LISTAGEM DE APOSTADORES
    private JList<Apostador> apostadoresJList = new JList<>();

    private JList<Aposta> apostasJList = new JList<>();

    //BOTOES RELACIONADO A MENU DE APOSTADOR
    private JButton btCadastroApostador = new JButton("Cadastro");
    private JButton btListarApostadores = new JButton("Listar apostadores");

    //INJETANDO REPOSITORIO DE APOSTA
    @Autowired
    private ApostaRepository apostaRepository;

    //BOTOEs RELACIONADOS A MENU DE APOSTAS
    private JButton btCadastroAposta = new JButton("Cadastrar aposta");
    private JButton btListarApostas = new JButton("Listar apostas");
    private JButton btEncontrarApostaPorId = new JButton ("Encontrar aposta");


    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Lp3Application.class);
        builder.headless(false);
        builder.run(args);
    }

    @Bean
    public void run() {

        defineMenuApostador();

        defineMenuAposta();
    }

    private void defineMenuApostador() {
        criarMenuApostador();

        //AÇÃO DO BOTAO DE CADASTRO
        btCadastroApostador.addActionListener(e -> criaMenuCadastro());
        //AÇÃO DO BOTAO DE LISTAGEM DE APOSTADORES
        btListarApostadores.addActionListener(e -> criaMenuListagem());

        defineAcoesDosBotoesRelacionadoAApostador(apostadorRepository);
    }

    private void defineMenuAposta() {
        criarMenuAposta();

        //AÇÃO DO BOTAO DE CADASTRO
        btCadastroAposta.addActionListener(e -> criaMenuCadastroAposta());
        //AÇÃO DO BOTAO DE LISTAGEM DE APOSTADORES
        btListarApostas.addActionListener(e -> criaMenuListagemApostas());

        defineAcoesDosBotoesRelacionadoAAposta(apostadorRepository, apostaRepository);
    }

    private void defineAcoesDosBotoesRelacionadoAApostador(ApostadorRepository apostadorRepository) {
        //CADASTRA APOSTADOR
        btCadastrar.addActionListener(a -> {

            Apostador apostador = new Apostador();

            String nome = textFieldNome.getText();

            Date selectedDate = (Date) dataAniversarioField.getModel().getValue();

            apostador.setNome(nome);
            apostador.setDataAniversario(selectedDate);

            apostadorRepository.save(apostador);

            JOptionPane.showMessageDialog(btCadastrar, "Apostador cadastrado");
        });
        //LISTA DE APOSTADOR
        btListar.addActionListener(a -> {

            List<Apostador> apostadores = apostadorRepository.findAll();

            List<Apostador> apostadoresReal = new ArrayList<>(apostadores);

            Apostador[] apostadoresArray = apostadoresReal.toArray(new Apostador[0]);

            apostadoresJList.setListData(apostadoresArray);

            if (apostadores.size() > 0) { //Habilita botão de deleção
                btDeletar.setEnabled(true);
            }
        });
        //DELETE APOSTADOR SELECIONADO
        btDeletar.addActionListener(a -> {

            var apostador = apostadoresJList.getSelectedValue();
            //DELETE APOSTADOR
            if (Objects.nonNull(apostador)) {
                apostadorRepository.deleteById(apostador.getId());

                //MONTA NOVA LISTA
                var listaBanco = apostadorRepository.findAll();

                apostadoresJList.setListData(listaBanco.toArray(Apostador[]::new));

                if (listaBanco.size() == 0) { //Desabilita botão de deleção
                    btDeletar.setEnabled(false);
                }
            }
        });
    }

    private void defineAcoesDosBotoesRelacionadoAAposta(ApostadorRepository apostadorRepository1, ApostaRepository apostaRepository) {
        //CADASTRA APOSTADOR
        btCadastrarAposta.addActionListener(a -> {
            Apostador apostador = apostadoresJList.getSelectedValue();

            Aposta aposta = new Aposta();

            String valor  = textFieldValor.getText();

            Date selectedDate = (Date) dataCriacaoAposta.getModel().getValue();

            aposta.setValorAposta(Double.valueOf(valor));
            aposta.setDataCriacao(selectedDate);
            aposta.setApostadorId(apostador);

            apostaRepository.save(aposta);

            JOptionPane.showMessageDialog(btCadastrarAposta, "Aposta cadastrada");
        });
        //LISTA DE APOSTADOR
        btListarAposta.addActionListener(a -> {

            List<Aposta> apostas = apostaRepository.findAll();

            List<Aposta> apostasReal = new ArrayList<>(apostas);

            Aposta[] apostasArray = apostasReal.toArray(new Aposta[0]);

            apostasJList.setListData(apostasArray);

            if (apostas.size() > 0) { //Habilita botão de deleção
                btDeletarAposta.setEnabled(true);
            }
        });
        //DELETE APOSTADOR SELECIONADO
        btDeletarAposta.addActionListener(a -> {

            var aposta = apostasJList.getSelectedValue();
            //DELETE APOSTADOR
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

    private void criarMenuApostador() {
        var janela = getJanela("Menu");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));

        painel.add(btCadastroApostador);
        painel.add(new JLabel());
        painel.add(btListarApostadores);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.getContentPane().add(painel, BorderLayout.CENTER);

        janela.revalidate();
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

    private void criaMenuCadastro() {
        var janela = getJanela("Cadastro");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1));

        painel.add(new JLabel("Nome"));
        painel.add(textFieldNome);

        painel.add(new JLabel("Data de Aniversario"));
        painel.add(dataAniversarioField);

        painel.add(btCadastrar);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.getContentPane().add(painel, BorderLayout.CENTER);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private void criaMenuListagem() {
        var janela = getJanela("Listagem");

        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout());
        painel.setOpaque(true);

        painel.add(new JLabel("Apostadores"), BorderLayout.PAGE_START);

        painel.add(apostadoresJList, BorderLayout.CENTER);

        //Cria scroll com base na lista
        JScrollPane listScrollPane = new JScrollPane(apostadoresJList);

        painel.add(btListar, BorderLayout.BEFORE_LINE_BEGINS);
        painel.add(btDeletar, BorderLayout.PAGE_END);

        janela.getContentPane().setLayout(new BorderLayout());
        janela.setContentPane(painel);
        janela.add(listScrollPane);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
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

        painel.add(btListar, BorderLayout.BEFORE_LINE_BEGINS);
        painel.add(new JLabel("Valor a Apostar"));
        painel.add(textFieldValor);

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

    private JFrame getJanela(String s) {
        JFrame janela = new JFrame(s);
        janela.setSize(720, 680);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);

        return janela;
    }

}
