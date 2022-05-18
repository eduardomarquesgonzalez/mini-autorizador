package br.com.vrbeneficio.enumerator;

public enum TiposBeneficioEnum {

	CARTAO_REFEICAO(1, "Cartão Refeição"), CARTAO_ALIMENTACAO(2, "Cartão Alimentacao"),
	CARTAO_COMBUTIVEL(3, "Cartão Combustível"), CARTAO_FARMACIA(4, "Cartão Farmácia");

	private final Integer codigo;
	private final String descricao;

	TiposBeneficioEnum(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static TiposBeneficioEnum getEnum(int codigo) {
		for (TiposBeneficioEnum e : TiposBeneficioEnum.values()) {
			if (e.getCodigo() == codigo) {
				return e;
			}
		}
		return null;
	}
}