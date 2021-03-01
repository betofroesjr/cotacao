package br.com.idtrust.cotacaobmf.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.idtrust.cotacaobmf.model.CotacaoDia;

@Repository
public interface CotacaoDiaRepository extends CrudRepository<CotacaoDia, Long> {

	public CotacaoDia findByDataCotacaoAndCulturaCotacao(LocalDate dataCotacao, String culturaCotacao);
}
