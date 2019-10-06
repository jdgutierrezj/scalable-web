package com.waes.jgu.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity that stores both sides of the comparison on a DB or dummy Java Collection
 *  
 * @author Jeison Gutierrez jdgutierrezj
 *
 */
@Data
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@Entity
public class EntryData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3754090053840884359L;
	
	public EntryData(String id) {
		this.id = id;
	}
	
	@Id
	private String id;
	@Lob
	@Column( length = 100000 )
	private String left;
	@Lob
	@Column( length = 100000 )
	private String right;
}
