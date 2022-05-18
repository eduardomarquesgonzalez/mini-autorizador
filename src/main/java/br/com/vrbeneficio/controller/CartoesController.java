package br.com.vrbeneficio.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.vrbeneficio.modelo.Cartoes;
import br.com.vrbeneficio.request.CartaoRequest;
import br.com.vrbeneficio.response.CartaoResponse;
import br.com.vrbeneficio.response.ExceptionResponse;
import br.com.vrbeneficio.response.Response;
import br.com.vrbeneficio.service.CartoesService;

@RestController
@RequestMapping("/cartoes")
public class CartoesController {

	@Autowired
	private CartoesService cartoesService;

	/**
	 * Criar um cartao com saldo inicial de R$ 500.00
	 * 
	 * Method: POST 
	 * URL: http://localhost:8080/cartoes
	 * 
	 * sucesso: 201
	 * erro: 422
	 * 
	 * @param cartao
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Response> gerarCartao(@Valid @RequestBody CartaoRequest cartao) {

		try {
			return cartoesService.gerarCartao(cartao);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new ExceptionResponse(Calendar.getInstance().getTime(),
					"Cartão inexistente.", HttpStatus.UNPROCESSABLE_ENTITY), HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	/**
	 * 
	 * Devolve os dados do cartao com o saldo
	 * 
	 * Obter saldo do Cartão 
	 * Method: GET 
	 * URL: http://localhost:8080/cartoes/{numeroCartao} , onde {numeroCartao} é o número do cartão que se deseja consultar
	 * 
	 * sucesso: 200
	 * erro: 404
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{numCartao}", method = RequestMethod.GET)
	public ResponseEntity<Response> obterSaldo(@PathVariable Long numCartao) {
		return cartoesService.findByNumeroCartao(numCartao);
	}

	/**
	 * 
	 * Realizar uma Transação
	 * Method: POST
	 * URL: http://localhost:8080/transacoes
	 * 
	 * sucesso: 201
	 * erro: 422
	 * 
	 * @param cartao
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/transacoes", method = RequestMethod.POST)
	public ResponseEntity<Response> comprar(@Valid @RequestBody CartaoRequest cartao) {
		return cartoesService.comprar(cartao);
	}

	/**
	 * 
	 * Listar todos os cartoes cadastrados.
	 * 
	 * @return
	 */
	@ResponseBody
	@GetMapping(value = "/listar")
	public ResponseEntity<List<CartaoResponse>> listarCartoes() {
		Iterable<Cartoes> iterable = cartoesService.listarCartoes();

		List<CartaoResponse> result = new ArrayList<CartaoResponse>();

		iterable.forEach(item -> result.add(new CartaoResponse(item)));

		return ResponseEntity.ok(result);
	}
	
	/**
	 * 
	 * Excluir um cartao
	 * 
	 * @return
	 */
	@ResponseBody
	@DeleteMapping(value = "/{numCartao}")
	public ResponseEntity<Response> excluir(@PathVariable Long numCartao) {
		return cartoesService.excluir(numCartao);
	}

}