package br.com.vrbeneficio.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vrbeneficio.modelo.Cartoes;
import br.com.vrbeneficio.modelo.HistCartoes;
import br.com.vrbeneficio.repository.CartoesRepository;
import br.com.vrbeneficio.repository.HistCartoesRepository;
import br.com.vrbeneficio.request.CartaoRequest;
import br.com.vrbeneficio.response.CartaoResponse;
import br.com.vrbeneficio.response.ExceptionResponse;
import br.com.vrbeneficio.response.Response;

@Service
public class CartoesService {

	@Autowired
	private CartoesRepository cartoesRepository;

	@Autowired
	private HistCartoesRepository histCartoesRepository;

	@Transactional
	public ResponseEntity<Response> comprar(CartaoRequest cartao) {
		Response cartaoResponse = null;
		Optional<Cartoes> entidade = null;

		try {
			entidade = cartoesRepository.findById(cartao.getNumero());

			if (entidade.isPresent()) {
				/**
				 * 
				 * Uma transação pode ser autorizada se: o cartão existir a senha do cartão for
				 * a correta o cartão possuir saldo disponível
				 * 
				 */
				Cartoes item = entidade.get();
				item.validarSenha(cartao.getSenha()).validarSaldo(cartao.getValor());

				Double saldo = item.getSaldo() - cartao.getValor();

				// Atualizo o saldo.
				item.setSaldo(saldo);

				cartoesRepository.save(item);

				histCartoesRepository.save(new HistCartoes(item.getNumero(), new BigDecimal(cartao.getValor())));

				cartaoResponse = new CartaoResponse(item);

				return new ResponseEntity<Response>(cartaoResponse, HttpStatus.CREATED);
			} else {
				cartaoResponse = new ExceptionResponse(Calendar.getInstance().getTime(), "Cartão inexistente.",
						HttpStatus.NOT_FOUND);
				return new ResponseEntity<Response>(cartaoResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} catch (Exception e) {
			cartaoResponse = new ExceptionResponse(Calendar.getInstance().getTime(), e.getMessage(),
					HttpStatus.UNPROCESSABLE_ENTITY);
			return new ResponseEntity<Response>(cartaoResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public ResponseEntity<Response> findByNumeroCartao(Long numCartao) {
		Optional<Cartoes> entidade = cartoesRepository.findById(numCartao);

		if (entidade.isPresent()) {
			return ResponseEntity.ok(new CartaoResponse(entidade.get()));
		}

		return new ResponseEntity<Response>(
				new ExceptionResponse(Calendar.getInstance().getTime(), "Cartão inexistente.", HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}

	@Transactional
	public ResponseEntity<Response> gerarCartao(CartaoRequest cartao) {

		Optional<Cartoes> entidade = cartoesRepository.findById(cartao.getNumero());

		if (entidade.isPresent()) {
			return new ResponseEntity<Response>(new ExceptionResponse(Calendar.getInstance().getTime(),
					"Cartão já existe.", HttpStatus.UNPROCESSABLE_ENTITY), HttpStatus.UNPROCESSABLE_ENTITY);
		}

		CartaoResponse cartaoResponse = new CartaoResponse(cartoesRepository.save(new Cartoes(cartao.getNumero(),
				cartao.getNome(), cartao.getValidade(), cartao.getCvc(), cartao.getSenha(), cartao.getTipo())));

		return new ResponseEntity<Response>(cartaoResponse, HttpStatus.CREATED);
	}

	public Iterable<Cartoes> listarCartoes() {
		return cartoesRepository.findAll();
	}

	@Transactional
	public ResponseEntity<Response> excluir(Long numCartao) {

		Optional<Cartoes> entidade = cartoesRepository.findById(numCartao);

		if (entidade.isPresent()) {

			cartoesRepository.delete(entidade.get());

			CartaoResponse cartaoResponse = new CartaoResponse(entidade.get());

			return new ResponseEntity<Response>(cartaoResponse, HttpStatus.OK);
		}

		return new ResponseEntity<Response>(
				new ExceptionResponse(Calendar.getInstance().getTime(), "Cartão inexistente.", HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}
}
