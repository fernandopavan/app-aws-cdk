package br.com.curso_aws_project01.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UrlResponse {

    private String url;
    private long expirationTime;

}