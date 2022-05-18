package br.com.vrbeneficio.modelo;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
public class HistCartoes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long numeroCartao;

	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal valor = new BigDecimal(0);

	private LocalDateTime dataTransacao;

	public HistCartoes() {
	}

	public HistCartoes(Long numeroCartao, BigDecimal valor) {
		this.numeroCartao = numeroCartao;
		this.valor = valor;
		this.dataTransacao = LocalDateTime.now();
	}

}
