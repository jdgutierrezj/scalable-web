package com.waes.jgu.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.waes.jgu.domain.EntryData;
import com.waes.jgu.dto.DiffResponse;
import com.waes.jgu.enums.Side;
import com.waes.jgu.exception.EntryIncompleteException;
import com.waes.jgu.exception.EntryNotFoundException;
import com.waes.jgu.exception.InmutableDataException;
import com.waes.jgu.exception.InvalidDataException;
import com.waes.jgu.repository.DiffRepository;
import com.waes.jgu.util.Utils;

/**
 * Implementation of the {@code DiffService} interface.
 * 
 * Executes operations to store and compare left and right attributes of an {@code EntryData} against an in memory H2 DataBase
 * 
 * @author Jeison Gutierrez jdgutierrezj
 */
public class DiffH2ServiceImpl implements DiffService {
	
	/**
	 * JPA Repository that handles some basic CRUD operations
	 * */
	@Autowired
	private DiffRepository repository;
	
	/**
	 * @throws InvalidDataException, InmutableDataException 
	 * @inheritDoc
	 * 
	 * Execute the operation against an in memory H2 DataBase
	 * */
	@Override
	public EntryData saveData(String id, Side side, String base64Data) throws InvalidDataException, InmutableDataException {
		Utils.checkBase64Data(base64Data);		
		
		EntryData entryData = repository
									.findById(id)
									.orElse(new EntryData(id));
		
		if(Side.LEFT.equals(side)) {
			if(null != entryData.getLeft() && !"".equals(entryData.getLeft().trim())) {
				throw new InmutableDataException(String.format("The %s side of the comparison was already received", side));
			}
			entryData.setLeft(base64Data);
		} else if(Side.RIGHT.equals(side)) {
			if(null != entryData.getRight() && !"".equals(entryData.getRight().trim())) {
				throw new InmutableDataException(String.format("The %s side of the comparison was already received", side));
			}
			entryData.setRight(base64Data);
		}
		
		return repository.save(entryData);
	}

	/**
	 * @inheritDoc
	 * 
	 * Execute the operation against an in memory H2 DataBase
	 * */
	@Override
	public DiffResponse getDiff(String id) throws EntryNotFoundException, EntryIncompleteException {
		EntryData entry = repository
				.findById(id)
				.orElse(null);
		
		return Utils.compare(entry, id);
	}
	
	
}
