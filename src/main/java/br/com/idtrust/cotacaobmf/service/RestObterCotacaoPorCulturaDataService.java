package br.com.idtrust.cotacaobmf.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.idtrust.cotacaobmf.exception.CotacaoCulturaException;
import br.com.idtrust.cotacaobmf.exception.CotacaoMoedaException;
import br.com.idtrust.cotacaobmf.model.CotacaoDia;
import br.com.idtrust.cotacaobmf.repository.CotacaoDiaRepository;
import br.com.idtrust.cotacaobmf.vo.CotacaoVo;
import br.com.idtrust.cotacaobmf.vo.RootCotacao;
import br.com.idtrust.cotacaobmf.vo.RootUSD;

@Service
public class RestObterCotacaoPorCulturaDataService {
	
private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private @Value("${cotacaoBMF.api_key}") String API_KEY;

	private @Value("${cotacaoBMF.tipo.moeda}") String TIPO_MOEDA;

	private @Value("${cotacaoBMF.uri.quandl}") String URI_COTACAO;

	private @Value("${cotacaoBMF.uri.moeda}") String URI_COTACAO_MOEDA;

	@Autowired private RestTemplate restTemplate;
	
	@Autowired private CotacaoDiaRepository repository;
	
	public BigDecimal cotacaoCultura(String cultura, LocalDate dataCotacao) throws CotacaoCulturaException, CotacaoMoedaException {
		
		CotacaoDia cotacaoDia = obterCotacaoBanco(cultura, dataCotacao);
		
		if(cotacaoDia != null) {
			
			return cotacaoDia.getValorCotacao();
		}
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URI_COTACAO + cultura)
				.queryParam("start_date", formatter.format(dataCotacao))
				.queryParam("end_date", formatter.format(dataCotacao))
				.queryParam("api_key", API_KEY);
		
		ResponseEntity<RootCotacao> result = null;
		try {
			result = restTemplate.getForEntity(builder.toUriString(), RootCotacao.class);
			
		} catch (RestClientException e) {
			
			throw e;
		}
		
		if(result != null && result.getBody() != null && result.getBody().getDataset() != null && !result.getBody().getDataset().getData().isEmpty()) {
			
			CotacaoVo cotacaoVo = result.getBody().getDataset().getData().get(0);
			
			if(cotacaoVo.getPrice().compareTo(BigDecimal.ZERO) > 0) {
				
				BigDecimal cotacaoMoeda = obterCotacaoMoeda(dataCotacao);
				
				cotacaoDia = CotacaoDia.builder()
								.culturaCotacao(cultura)	
								.dataCotacao(dataCotacao)	
								.valorCotacao(cotacaoVo.getPrice().multiply(cotacaoMoeda))
								.build();
				
				repository.save(cotacaoDia);
				
				return cotacaoDia.getValorCotacao();
				
			} else {
				
				throw new CotacaoCulturaException("Valor invalido para cotação!");
			}
		}
		
		throw new CotacaoCulturaException("Não há dados com os parametros informados.");
	}
	
	private CotacaoDia obterCotacaoBanco(String cultura, LocalDate dataCotacao) {

		return repository.findByDataCotacaoAndCulturaCotacao(dataCotacao, cultura);
	}

	public BigDecimal obterCotacaoMoeda(LocalDate dataCotacao) throws CotacaoMoedaException {
		
		try {
			
			HttpHeaders headers = new HttpHeaders();
			
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URI_COTACAO_MOEDA+TIPO_MOEDA)
					.queryParam("start_date", formatter.format(dataCotacao))
					.queryParam("end_date", formatter.format(dataCotacao));
			
			ResponseEntity<RootUSD> result = restTemplate.getForEntity(builder.toUriString(), RootUSD.class);
			
			System.out.println(result.getBody());
			
			return result.getBody().getUSD().getBid();
			
		} catch (Exception e) {
			
			throw new CotacaoMoedaException("Falha ao obter cotação da moeda!");
		}
	}
}