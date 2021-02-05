package com.work.lp3.service.jogo;

import com.work.lp3.entity.Jogo;
import com.work.lp3.repository.JogoRepository;
import org.jdatepicker.JDatePanel;
import org.jdatepicker.SqlDateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.work.lp3.Commons.getJanela;

@Service
public class JogoService {

    @Autowired
    private JogoRepository jogoRepository;

    public static JList<Jogo> jogosJList = new JList<>();

    //VARIAVEIS DE JOGO
    private JTextField textFieldTimeCasaJogo = new JTextField();
    private JTextField textFieldTimeForaJogo = new JTextField();
    private JTextField textFieldTotalDeGolsJogo = new JTextField();
    private JDatePanel dataDoJogoField = new JDatePanel(new SqlDateModel());

    //BOTOES RELACIONADO A AÇÕES DE JOGO
    private JButton btCadastrarJogo = new JButton("Cadastrar jogo");
    public static JButton btListarJogo = new JButton("Listar jogos");
    private JButton btDeletarJogo = new JButton("Deletar jogo");

    //BOTOES RELACIONADOS AO MENU DE JOGOS
    private JButton btCadastroJogo = new JButton("Cadastrar jogo");
    public static JButton btListarJogos = new JButton("Listar jogos");

    public void defineMenuJogo() {
        criarMenuJogo();

        //AÇÃO DO BOTAO DE CADASTRO
        btCadastroJogo.addActionListener(e -> criaMenuCadastroJogo());
        //AÇÃO DO BOTAO DE LISTAGEM DE APOSTADORES
        btListarJogos.addActionListener(e -> criaMenuListagemJogos());

        defineAcoesDosBotoesRelacionadoAJogo(jogoRepository);
    }

    public void defineAcoesDosBotoesRelacionadoAJogo(JogoRepository jogoRepository) {
        //CADASTRA JOGO
        btCadastrarJogo.addActionListener(a -> {

            Jogo jogo = new Jogo();

            String timeCasa = textFieldTimeCasaJogo.getText();
            String timeFora = textFieldTimeForaJogo.getText();
            Integer totalDeGols = Integer.valueOf(textFieldTotalDeGolsJogo.getText());
            var dataDoJogo = (Date) dataDoJogoField.getModel().getValue();

            jogo.setTimeCasa(timeCasa);
            jogo.setTimeFora(timeFora);
            jogo.setTotalDeGols(totalDeGols);
            jogo.setDataDoJogo(dataDoJogo);

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

        janela.getContentPane().setLayout(new BorderLayout());
        janela.getContentPane().add(painel, BorderLayout.CENTER);

        janela.revalidate();
        janela.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private void criaMenuCadastroJogo() {
        var janela = getJanela("Cadastro");

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 2));
        painel.add(new JLabel("Time em casa"));
        painel.add(textFieldTimeCasaJogo);

        painel.add(new JLabel("Time de fora"));
        painel.add(textFieldTimeForaJogo);

        painel.add(new JLabel("Total de gols"));
        painel.add(textFieldTotalDeGolsJogo);

        painel.add(new JLabel("Data do jogo"));
        painel.add(dataDoJogoField);

        painel.add(btCadastrarJogo, Component.CENTER_ALIGNMENT);

        janela.getContentPane().add(painel);

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

}
