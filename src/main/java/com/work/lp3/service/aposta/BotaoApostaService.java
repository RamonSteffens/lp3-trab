package com.work.lp3.service.aposta;

import com.work.lp3.entity.Aposta;
import com.work.lp3.entity.Apostador;
import com.work.lp3.repository.ApostaRepository;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.work.lp3.service.aposta.ApostaService.*;
import static com.work.lp3.service.jogo.JogoService.jogosJList;

@Service
public class BotaoApostaService {

    //BOTOES RELACIONADO A AÇÕES DE APOSTA
    public static JButton btCadastrarAposta = new JButton("Cadastrar aposta");

    public static JButton btListarApostaDeUmApostador = new JButton("Listar");

    public static JButton btListarTodasApostas = new JButton("Listar");

    public static JButton btDeletarAposta = new JButton("Deletar aposta");


    public void defineAcoesDosBotoesRelacionadoAAposta(ApostaRepository apostaRepository, Apostador apostador) {
        //CADASTRA APOSTA
        btCadastrarAposta.addActionListener(a -> {

            String valor = textFieldValorAposta.getText();

            Aposta aposta = new Aposta();
            aposta.setApostador(apostador);
            aposta.setValorAposta(Double.valueOf(valor));
            aposta.setJogos(jogosJList.getSelectedValuesList());

            apostaRepository.save(aposta);

            JOptionPane.showMessageDialog(btCadastrarAposta, "Aposta cadastrada");
        });
        //LISTA DE APOSTAS
        btListarApostaDeUmApostador.addActionListener(a -> {

            var apostas = apostaRepository.findAllByApostadorId(apostador.getId());

            apostas.ifPresent(apostas1 -> {
                List<Aposta> apostasReal = new ArrayList<>(apostas1);

                Aposta[] apostasArray = apostasReal.toArray(new Aposta[0]);

                apostasJListDeUmApostador.setListData(apostasArray);

                if (apostas1.size() > 0) { //Habilita botão de deleção
                    btDeletarAposta.setEnabled(true);
                }
            });
            if (apostas.isEmpty()) {
                JOptionPane.showMessageDialog(btListarApostaDeUmApostador, "Apostador sem apostas");
            }

        });

        btListarTodasApostas.addActionListener(a -> {

            List<Aposta> apostas = apostaRepository.findAll();

            List<Aposta> apostasReal = new ArrayList<>(apostas);

            Aposta[] apostasArray = apostasReal.toArray(new Aposta[0]);

            apostasJList.setListData(apostasArray);
        });

        //DELETE APOSTA SELECIONADA
        btDeletarAposta.addActionListener(a -> {

            var aposta = apostasJListDeUmApostador.getSelectedValue();
            //DELETE APOSTA
            if (Objects.nonNull(aposta)) {
                apostaRepository.deleteById(aposta.getId());

                //MONTA NOVA LISTA
                var listaBanco = apostaRepository.findAll();

                apostasJListDeUmApostador.setListData(listaBanco.toArray(Aposta[]::new));

                if (listaBanco.size() == 0) { //Desabilita botão de deleção
                    btDeletarAposta.setEnabled(false);
                }
            }
        });
    }
}
