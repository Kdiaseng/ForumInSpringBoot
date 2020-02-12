package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.Resposta;
import br.com.alura.forum.modelo.StatusTopico;
import br.com.alura.forum.modelo.Topico;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DetalhesTopicoDto {
    private Long id;
    private String titulo;
    private  String nomeAutor;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private StatusTopico status;
    List<RespostaDto> respostaDtoList;

    public DetalhesTopicoDto(Topico topico){
        this.id = topico.getId();
        this.dataCriacao = topico.getDataCriacao();
        this.mensagem = topico.getMensagem();
        this.titulo = topico.getTitulo();
        this.nomeAutor = topico.getAutor().getNome();
        this.status = topico.getStatus();

        this.respostaDtoList = new ArrayList<>();
        this.respostaDtoList.addAll(topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));

    }

}
