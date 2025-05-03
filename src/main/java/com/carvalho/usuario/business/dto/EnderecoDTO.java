package com.carvalho.usuario.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {

    private Long id;
    private String rua;
    private String bairro;
    private Long numero;
    private String cidade;
    private String estado;
    private String complemento;
    private String cep;
}
