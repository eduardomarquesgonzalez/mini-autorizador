package br.com.vrbeneficio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.validation.ReportAsSingleViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import br.com.vrbeneficio.modelo.Cartoes;
import br.com.vrbeneficio.repository.CartoesRepository;
import br.com.vrbeneficio.repository.HistCartoesRepository;
import br.com.vrbeneficio.request.CartaoRequest;
import br.com.vrbeneficio.response.CartaoResponse;
import br.com.vrbeneficio.response.Response;

class CartoesServiceTest {

	private static final double VALOR = 10.1;

	private static final Integer TIPO = 2;

	private static final String SENHA = "1234";

	private static final String CVC = "123";

	private static final String VALIDADE = "12/2022";

	private static final String NOME1 = "nome1";

	private static final long NUMERO_CARTAO = 12341L;

	@InjectMocks
	private CartoesService service;

	@Mock
	private CartoesRepository repository;

	@Mock
	private HistCartoesRepository histCartoesRepository;

	private Cartoes cartoes;
	private CartaoRequest request;
	private Optional<Cartoes> optCartoes;

	private CartaoResponse response;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		startCartoes();
	}

	@Test
	void whenfindByNumeroCartao() {

		when(repository.findById(request.getNumero())).thenReturn(optCartoes);

		ResponseEntity<Response> findByNumeroCartao = service.findByNumeroCartao(NUMERO_CARTAO);
		assertNotNull(findByNumeroCartao);
		assertEquals(ResponseEntity.class, findByNumeroCartao.getClass());

	}

	@Test
	void whenCompra() {

		when(repository.save(cartoes)).thenReturn(cartoes);

		ResponseEntity<Response> response = service.comprar(request);
		assertNotNull(response);

	}

	private void startCartoes() {
		cartoes = new Cartoes(NUMERO_CARTAO, NOME1, VALIDADE, CVC, SENHA, TIPO);
		request = new CartaoRequest(NUMERO_CARTAO, NOME1, VALIDADE, CVC, SENHA, VALOR, TIPO);
		response = new CartaoResponse(new Cartoes(NUMERO_CARTAO, NOME1, VALIDADE, CVC, SENHA, TIPO));
		optCartoes = Optional.of(new Cartoes(NUMERO_CARTAO, NOME1, VALIDADE, CVC, SENHA, TIPO));

	}

}
