package br.com.alura.forum.config.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice // indico que esse é controle advice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST) //altero o status da resposta
    @ExceptionHandler(MethodArgumentNotValidException.class) //caso ocorra qualquer excessao em qualquer controller é chamado esse método
    public List<ErrorDeFormularioDto> handle(MethodArgumentNotValidException exception){

        List<ErrorDeFormularioDto> errosDto = new ArrayList<>();
        List<FieldError> fieldErros =  exception.getBindingResult().getFieldErrors();

        fieldErros.forEach(e ->{
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErrorDeFormularioDto erro = new ErrorDeFormularioDto(e.getField(), mensagem);
            errosDto.add(erro);
        });

        return errosDto;
    }
}
