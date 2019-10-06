package com.waes.jgu.controller.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.waes.jgu.domain.EntryData;
import com.waes.jgu.repository.DiffRepository;

/**
 * Unit test
 * 
 * @see <a href="https://www.baeldung.com/spring-boot-testing">Spring boot testing</a>
 * 
 * @author Jeison Gutierrez jdgutierrezj
 * */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DiffRepositoryTest {
	
	@Autowired
	private DiffRepository repository;

	@Test
	public void when_thenReturnEntry() {
	    // given
	    EntryData entry = new EntryData("1");
	    entry.setLeft("TESTLEFT");
	    entry.setRight("TESTRIGHT");
	    repository.save(entry);
	 
	    // when
	    EntryData found = repository
	    		.findById("1")
	    		.orElse(new EntryData("2"));
	 
	    // then
	    assertThat(found.getLeft()).isEqualTo(entry.getLeft());
	}
	
}
