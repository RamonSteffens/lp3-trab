package com.work.lp3.service.apostador;

import com.work.lp3.entity.Apostador;
import com.work.lp3.repository.ApostadorRepository;
import com.work.lp3.service.aposta.ApostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.*;

import static com.work.lp3.service.apostador.ApostadorService.*;

@Service
public class BotaoApostadorService {


    @Autowired
    private ApostaService apostaService;

    //BOTOES RELACIONADO A AÇÕES DE APOSTADOR
    public static JButton btListarApostador = new JButton("Listar apostadores");

    public static JButton btCadastrarApostador = new JButton("Cadastrar apostador");

    public static JButton btDeletarApostador = new JButton("Deletar apostador");

    public static JButton btEntrar = new JButton("Entrar");

    public void definirAcoes(ApostadorRepository apostadorRepository) {
        //CADASTRA APOSTADOR
        btCadastrarApostador.addActionListener(a -> {

            Apostador apostador = new Apostador();

            String apelido = textFieldApelidoApostador.getText();
            String email = textFieldEmailApostador.getText();
            String nome = textFieldNomeApostador.getText();

            Date selectedDate = (Date) dataAniversarioField.getModel().getValue();

            apostador.setApelido(apelido);
            apostador.setDataAniversario(selectedDate);
            apostador.setEmail(email);
            apostador.setNome(nome);

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

        btEntrar.addActionListener(e -> {

            var nomeApostador = textFieldNomeApostadorProcurado.getText();
            Optional<Apostador> apostador = apostadorRepository.findByNome(nomeApostador);

            apostador.ifPresent(apostador1 -> apostaService.redirecionaParaTelaAposta(apostador1));
            if (apostador.isEmpty()) {
                JOptionPane.showMessageDialog(btEntrar, "Nome invalido");
            }
        });

    }
}
