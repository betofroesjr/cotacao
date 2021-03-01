package br.com.idtrust.cotacaobmf.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cotacao_dia_db")
@SequenceGenerator(name="sequence_default", sequenceName = "cotacao_dia_seq", allocationSize = 1, initialValue = 1)
@NoArgsConstructor
public class CotacaoDia extends EntityBase {

	@Column(name = "local_data", columnDefinition = "date")
	private @Getter @Setter LocalDate dataCotacao;

	@Column(name = "cultura")
	private @Getter @Setter String culturaCotacao;
	
	@Column(name = "valor")
	private @Getter @Setter BigDecimal valorCotacao;

	@Builder
	public CotacaoDia(LocalDate dataCotacao, String culturaCotacao, BigDecimal valorCotacao) {
		super();
		this.dataCotacao = dataCotacao;
		this.culturaCotacao = culturaCotacao;
		this.valorCotacao = valorCotacao;
	}	
}
