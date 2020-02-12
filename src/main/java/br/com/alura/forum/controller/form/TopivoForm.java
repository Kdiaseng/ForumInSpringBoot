package br.com.alura.forum.controller.form;

import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class TopivoForm {

    @NotNull @NotEmpty
    private String titulo;

    @NotNull @NotEmpty
    private String mensagem;

    @NotNull @NotEmpty
    private String nomeCurso;

    public  TopivoForm(){

    }

    public Topico converter(CursoRepository cursoRepositoy) {
        Curso curso = cursoRepositoy.findByNome(this.nomeCurso);
        return new Topico(this.titulo, this.mensagem, curso);
    }
}
