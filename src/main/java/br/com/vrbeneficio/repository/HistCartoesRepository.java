package br.com.vrbeneficio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.vrbeneficio.modelo.HistCartoes;

@Repository
public interface HistCartoesRepository extends CrudRepository<HistCartoes, Long> {
}
