package br.com.idtrust.cotacaoBMF;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClientException;

import br.com.idtrust.cotacaobmf.exception.CotacaoMoedaException;
import br.com.idtrust.cotacaobmf.service.RestObterCotacaoPorCulturaDataService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author José Humberto Fróes Júnior
 *
 */
@SpringBootTest
@Slf4j
class CotacaoBmfApplicationTests {

	private static final String CULTURA_TESTADA = "CALF_C";

	private static final String VALOR_COTACAO_CULTURA_CONVERTIDO_REAL_TESTE = "2.885,88";
	
	private static final String VALOR_COTACAO_MOEDA_TESTE = "5.5986";
	
	@Autowired RestObterCotacaoPorCulturaDataService service;
	
	@Test
	void testandoCotacaoMoedaNull() {
		
		try {
			
			LocalDate dataPesquisa = LocalDate.of(2021, 2, 23);
			
			assertEquals(service.obterCotacaoMoeda(dataPesquisa) == null , false);
			
		} catch (Exception e) {
		
			log.error(e.getMessage());
		}
	}

	@Test
	void testandoCotacaoMoedaDiferenteZero() {
		
		try {
			
			BigDecimal cotacaoMoeda = padraoTesteMoeda();
			
			assertEquals((cotacaoMoeda != null && cotacaoMoeda.compareTo(new BigDecimal(VALOR_COTACAO_MOEDA_TESTE)) == 0), true);
			
		} catch (Exception e) {

			log.error(e.getMessage());
		}
	}

	private BigDecimal padraoTesteMoeda() throws CotacaoMoedaException {
		
		LocalDate dataPesquisa = LocalDate.of(2021, 2, 23);
		
		BigDecimal cotacaoMoeda = service.obterCotacaoMoeda(dataPesquisa);
		
		return cotacaoMoeda;
	}
	
	@Test
	void testandoLinkCotacaoCulturas() {
		
		try {
			
			String culturaPesquisar = CULTURA_TESTADA;
			
			LocalDate dataPesquisa = LocalDate.of(2021, 2, 23);
			
			BigDecimal cotacaoDolar = service.cotacaoCultura(culturaPesquisar, dataPesquisa);
			
			BigDecimal cotacaoMoeda = padraoTesteMoeda();
			
			BigDecimal cotacaoCulturaReal = cotacaoDolar.multiply(cotacaoMoeda);

			cotacaoCulturaReal = cotacaoCulturaReal.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
			
			assertEquals((cotacaoCulturaReal != null && cotacaoCulturaReal.compareTo(new BigDecimal(VALOR_COTACAO_CULTURA_CONVERTIDO_REAL_TESTE)) == 0), true);
			
		} catch (RestClientException e) {
			
			log.error(e.getMessage());
			
		} catch (Exception e) {

			log.error(e.getMessage());
		}
	}
}
