package br.com.idtrust.cotacaobmf.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.idtrust.cotacaobmf.exception.CotacaoCulturaException;
import lombok.Getter;
import lombok.Setter;

/**
 * @author José Humberto Fróes Júnior
 *
 */
/**
 * @author Beto
 *
 */
public class RootCotacao {

	private @Getter @Setter Dataset dataset;
	
	@Override
	public String toString() {
		return "RootCotacao [" + dataset + "]";
	}
	
	public void validar() throws CotacaoCulturaException {
		
		if(getDataset() != null) {
			
			getDataset().validar();			
		}
	}

	public class Dataset {

		 private @Getter @Setter Integer id;
		 
		 private @Getter @Setter String dataset_code;
		 
		 private @Getter @Setter String database_code;
		 
		 private @Getter @Setter String name;
		 
		 private @Getter @Setter String description;
		 
		 private @Getter @Setter LocalDateTime refreshed_at;
		 
		 private @Getter @Setter LocalDate newest_available_date;
		 
		 private @Getter @Setter LocalDate oldest_available_date;
		 
		 private @Getter @Setter String[] column_names;
		 
		 private @Getter @Setter String frequency;
		 
		 private @Getter @Setter String type;
		 
		 private @Getter @Setter boolean premium;
		 
		 private @Getter @Setter String limit = null;
		 
		 private @Getter @Setter String transform = null;
		 
		 private @Getter @Setter float column_index;
		 
		 private @Getter @Setter LocalDate start_date;
		 
		 private @Getter @Setter LocalDate end_date;
		 
		 @JsonProperty(value = "data")
		 private @Getter @Setter List<CotacaoVo> data;
		 
		 private @Getter @Setter String collapse = null;
		 
		 private @Getter @Setter String order = null;
		 
		 private @Getter @Setter Integer database_id;

		public void validar() throws CotacaoCulturaException {
				
			if(getDataset() == null || getDataset().getColumn_names() == null || getDataset().getData() == null) {
					
				throw new CotacaoCulturaException("Dados não estão de acorodo com a documentação da consulta! Verificar alteração na documentação!");
			}
		} 
		 
		@Override
		public String toString() {
			return "Dataset [id=" + id + ", dataset_code=" + dataset_code + ", database_code=" + database_code + ", name="
					+ name + ", description=" + description + ", refreshed_at=" + refreshed_at + ", newest_available_date="
					+ newest_available_date + ", oldest_available_date=" + oldest_available_date + ", column_names="
					+ column_names + ", frequency=" + frequency + ", type=" + type + ", premium=" + premium + ", limit="
					+ limit + ", transform=" + transform + ", column_index=" + column_index + ", start_date=" + start_date
					+ ", end_date=" + end_date + ", data=" + data + ", collapse=" + collapse + ", order=" + order
					+ ", database_id=" + database_id + "]";
		}
	}
}
