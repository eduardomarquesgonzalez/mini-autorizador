package br.com.vrbeneficio.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CartaoRequest {

	@NotNull(message = "Numero do cartaõ é um campo obrigatório")
	private Long numero;

	private String nome;

	private String validade;

	private String cvc;

	@NotBlank(message = "Senha é um campo obrigatório")
	@NotNull(message = "Senha é um campo obrigatório")
	private String senha;

	private Double valor;

	private Integer tipo;
}
