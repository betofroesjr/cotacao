package br.com.idtrust.cotacaobmf.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class CotacaoVo {

	private @Getter @Setter LocalDate date;
	
	@JsonAlias({"Price US$"})
	private @Getter @Setter BigDecimal price;
	
	@JsonAlias({"Daily %"})
	private @Getter @Setter BigDecimal daily;

	@JsonAlias({"Monthly %"})
	private @Getter @Setter BigDecimal monthly;
}
