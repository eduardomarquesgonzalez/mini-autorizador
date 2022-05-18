package br.com.vrbeneficio.response;

import java.text.NumberFormat;
import java.util.Locale;

import br.com.vrbeneficio.enumerator.TiposBeneficioEnum;
import br.com.vrbeneficio.modelo.Cartoes;
import lombok.Data;

@Data
public class CartaoResponse implements Response {
	private String nomeCliente;
	private Long numCartao;
	private String valorFormatado;
	private TiposBeneficioEnum tipoCartao;

	public CartaoResponse() {
		super();
	}

	public CartaoResponse(Cartoes cartao) {
		this.nomeCliente = cartao.getNome();
		this.numCartao = cartao.getNumero();

		Locale localBrasil = new Locale("pt", "BR");
		valorFormatado = NumberFormat.getCurrencyInstance(localBrasil).format(cartao.getSaldo());

		this.tipoCartao = TiposBeneficioEnum.getEnum(cartao.getTipo());
	}

}
