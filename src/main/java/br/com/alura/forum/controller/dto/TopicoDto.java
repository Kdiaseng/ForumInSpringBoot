package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.Topico;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TopicoDto {
    private Long id;
    private String titulo;
    private String mensagem;
    private int quantityResponse;
    private LocalDateTime dataCriacao;

    public TopicoDto(Topico topico){
        this.id = topico.getId();
        this.dataCriacao = topico.getDataCriacao();
        this.mensagem = topico.getMensagem();
        this.titulo = topico.getTitulo();
        this.quantityResponse = topico.getRespostas().size();
    }


    public static Page<TopicoDto> coverter(Page<Topico> topicos){
        return topicos.map(TopicoDto::new);
    }
}
