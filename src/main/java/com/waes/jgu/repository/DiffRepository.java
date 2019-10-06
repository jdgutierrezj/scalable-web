package com.waes.jgu.repository;

import org.springframework.data.repository.CrudRepository;

import com.waes.jgu.domain.EntryData;

/**
 * Spring Data Crud Repository to handle some basic DB operations
 * 
 * @author Jeison Gutierrez jdgutierrezj
 * */
public interface DiffRepository extends CrudRepository<EntryData, String>{

}
