package br.com.vrbeneficio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.vrbeneficio.modelo.Cartoes;

@Repository
public interface CartoesRepository extends CrudRepository<Cartoes, Long> {
}
