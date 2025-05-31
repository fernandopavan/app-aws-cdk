package br.com.curso_aws_project01.model;


import br.com.curso_aws_project01.enums.EventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Envelope {

    private EventType eventType;
    private String data;

}
