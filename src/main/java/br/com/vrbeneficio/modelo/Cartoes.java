package br.com.vrbeneficio.modelo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.vrbeneficio.exception.NegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity
public class Cartoes {

	@Id
	private Long numero;

	@Column(length = 255)
	private String nome;

	@Size(max = 30)
	@Column(length = 30)
	private String validade;

	@Size(max = 3)
	@Column(length = 3)
	private String cvc;

	@Size(max = 60)
	@Column(length = 60)
	@NotEmpty(message = "A senha é obrigatório")
	@NotNull(message = "A senha é obrigatório")
	private String senha;

	@DecimalMin(value = "0.0", inclusive = false)
	private Double saldo;

	@NotNull(message = "O tipo de cartão é obrigatório")
	private Integer tipo;

	private LocalDateTime dataCriacao;

	// Saldo inicial do cartao
	public Cartoes() {
		this.saldo = 500.00;
	}

	public Cartoes(Long numero, String nome, String validade, String cvc, String senha, Integer tipo) {
		super();
		this.numero = numero;
		this.nome = nome;
		this.validade = validade;
		this.cvc = cvc;
		this.senha = senha;
		this.saldo = 500.00;
		this.tipo = tipo;
		this.dataCriacao = LocalDateTime.now();
	}

	public Cartoes validarSeCartaoExiste(Boolean isExiste) throws NegocioException {
		if (!isExiste) {
			throw new NegocioException("Cartão inexistente!");
		}

		return this;
	}

	public Cartoes validarSenha(String senha) throws NegocioException {
		if (!senha.equals(this.getSenha())) {
			throw new NegocioException("Senha incorreta!");
		}

		return this;
	}

	public Cartoes validarSaldo(Double valor) throws NegocioException {
		Double saldo = this.getSaldo() - valor;

		if (saldo < 0) {
			throw new NegocioException("Cartão com saldo insuficiente!");
		}

		return this;
	}

}
