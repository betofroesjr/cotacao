package br.com.idtrust.cotacaobmf.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_default")
	private @Getter @Setter Long id;

}