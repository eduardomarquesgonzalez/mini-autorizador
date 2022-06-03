package br.com.vrbeneficio.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.vrbeneficio.modelo.Cartoes;
import br.com.vrbeneficio.request.CartaoRequest;
import br.com.vrbeneficio.response.CartaoResponse;
import br.com.vrbeneficio.response.Response;
import br.com.vrbeneficio.service.CartoesService;

@SpringBootTest
class CartoesControllerTest {

	private static final int INDEX = 0;

	private static final double VALOR = 10.1;

	private static final Integer TIPO = 2;

	private static final String SENHA = "1234";

	private static final String CVC = "123";

	private static final String VALIDADE = "12/2022";

	private static final String NOME = "nome-test";

	private static final long NUMERO_CARTAO = 12341L;

	private static final Object CARTAO_ALIMENTACAO = "Cartão Alimentacao";

	private static final String VALOR_FORMATADO = "R$ 500,00";

	@InjectMocks
	private CartoesController controller;

	@Mock
	private CartoesService service;

	@Mock
	private Cartoes cartoes;

	@Mock
	private CartaoRequest request;

	@Mock
	private ResponseEntity<Response> optCartoes;

	@Mock
	private CartaoResponse cartaoResponse;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startCartoes();
	}

	@Test
	void buscarNumeroCartao() {

		when(service.findByNumeroCartao(NUMERO_CARTAO)).thenReturn(optCartoes);

		ResponseEntity<Response> response = controller.obterSaldo(NUMERO_CARTAO);
		assertNotNull(response);

	}

	@Test
	void whenFindAllCartoes() {
		when(service.listarCartoes()).thenReturn(List.of(cartoes));

		ResponseEntity<List<CartaoResponse>> response = controller.listarCartoes();

		assertNotNull(response);
		assertEquals(ArrayList.class, response.getBody().getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());

		assertEquals(NUMERO_CARTAO, response.getBody().get(INDEX).getNumCartao());
		assertEquals(NOME, response.getBody().get(0).getNomeCliente());
		assertEquals(CARTAO_ALIMENTACAO, response.getBody().get(INDEX).getTipoCartao().getDescricao());
		assertEquals(VALOR_FORMATADO, response.getBody().get(INDEX).getValorFormatado());

	}

	@Test
	void comprarTest() {

		when(service.comprar(request)).thenReturn(optCartoes);
		ResponseEntity<Response> response = controller.comprar(request);
		assertNotNull(response);
		response.getStatusCode();
		assertEquals(HttpStatus.CREATED, response.getStatusCode().CREATED);

	}

	@Test
	void gerarCartaoTest() {
		when(service.gerarCartao(request)).thenReturn(optCartoes);
		ResponseEntity<Response> response = controller.gerarCartao(request);
		assertNotNull(response);
	}

	private void startCartoes() {
		cartoes = new Cartoes(NUMERO_CARTAO, NOME, VALIDADE, CVC, SENHA, TIPO);
		request = new CartaoRequest(NUMERO_CARTAO, NOME, VALIDADE, CVC, SENHA, VALOR, TIPO);
	}

}
