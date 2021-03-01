package br.com.idtrust.cotacaobmf.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.idtrust.cotacaobmf.config.CustomLocalDateTimeDeserializer;
import br.com.idtrust.cotacaobmf.config.CustomLocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

public class RootUSD{
	
    @JsonProperty("USD") 
    private @Getter @Setter USD uSD;

    public class USD{
    	
    	private @Getter @Setter String code;
    	private @Getter @Setter String codein;
    	private @Getter @Setter String name;
    	private @Getter @Setter BigDecimal high;
    	private @Getter @Setter BigDecimal low;
    	private @Getter @Setter BigDecimal varBid;
    	private @Getter @Setter BigDecimal pctChange;
    	private @Getter @Setter BigDecimal bid;
    	private @Getter @Setter BigDecimal ask;
    	private @Getter @Setter Long timestamp;
    	
    	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    	private @Getter @Setter LocalDateTime create_date;
		
    	@Override
		public String toString() {
			return "USD [code=" + code + ", codein=" + codein + ", name=" + name + ", high=" + high + ", low=" + low
					+ ", varBid=" + varBid + ", pctChange=" + pctChange + ", bid=" + bid + ", ask=" + ask
					+ ", timestamp=" + timestamp + ", create_date=" + create_date + "]";
		}
    }

	@Override
	public String toString() {
		return "RootUSD [" + uSD + "]";
	}
}

