package br.com.idtrust.cotacaobmf.resource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import br.com.idtrust.cotacaobmf.exception.CotacaoCulturaException;
import br.com.idtrust.cotacaobmf.exception.CotacaoMoedaException;
import br.com.idtrust.cotacaobmf.service.RestObterCotacaoPorCulturaDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/cotacao")
@Api(value = "Cotações")
@Slf4j
public class cotacaoResource {

	private @Autowired RestObterCotacaoPorCulturaDataService service;
	
	@GetMapping("/{cultura}/{dataPesquisa}")
	@ApiOperation(value = "Consulta cotação de um cultura pela data retornando o valor em R$")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Dados enviados não estão conforme documentação"),
			@ApiResponse(code = 500, message = "Foi gerada um exceção"),
	})
	public ResponseEntity<?> cotacao(@PathVariable String cultura, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPesquisa){
		
		try {
			
			log.debug("Entrou no metodo de consulta");
			
			BigDecimal cotacaoValor = service.cotacaoCultura(cultura, dataPesquisa);
			
			cotacaoValor = cotacaoValor.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
			
			return ResponseEntity.ok(cotacaoValor);
			
		} catch (HttpServerErrorException e) {
			
			log.error(e.getMessage());
			
			return new ResponseEntity<String>(
					e.getMessage(), 
					e.getStatusCode());
			
		} catch (CotacaoCulturaException | CotacaoMoedaException | RestClientException e) {

			log.error(e.getMessage());
			
			return new ResponseEntity<String>(
			          e.getMessage(), 
			          HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {

			log.error(e.getMessage());
			
			return new ResponseEntity<String>(
			          e.getMessage(), 
			          HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
