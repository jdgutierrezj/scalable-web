package com.waes.jgu.service;

import java.util.ArrayList;
import java.util.List;

import com.waes.jgu.domain.EntryData;
import com.waes.jgu.dto.DiffResponse;
import com.waes.jgu.enums.Side;
import com.waes.jgu.exception.EntryIncompleteException;
import com.waes.jgu.exception.EntryNotFoundException;
import com.waes.jgu.exception.InmutableDataException;
import com.waes.jgu.exception.InvalidDataException;
import com.waes.jgu.util.Utils;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the {@code DiffService} interface.
 * 
 * Executes operations to store and compare left and right attributes of an {@code EntryData} against a dummy {@code java.util.List}
 * 
 * @author Jeison Gutierrez jdgutierrezj
 */
@Slf4j
public class DiffDummyServiceImpl implements DiffService {

	/** 
	 * Dummy DataBase
	 * */
	private List<EntryData> dummyDB = new ArrayList<EntryData>();

	/**
	 * @inheritDoc
	 * 
	 * Execute the operation against a dummy Java Collection
	 * */
	@Override
	public EntryData saveData(String id, Side side, String base64Data) throws InmutableDataException, InvalidDataException {		
		Utils.checkBase64Data(base64Data);		
		log.info("Size " + dummyDB.size());
		EntryData entry = dummyDB
				.stream()
				.filter(data -> id.equals(data.getId()))
				.findAny()
				.orElse(null);
		
		if(null != entry) {
			if(Side.LEFT.equals(side)) {
				if(null != entry.getLeft() && !"".equals(entry.getLeft().trim())) {
					throw new InmutableDataException(String.format("The %s side of the comparison was already received", side));
				}
				entry.setLeft(base64Data);
			}
			if(Side.RIGHT.equals(side)) {
				if(null != entry.getRight() && !"".equals(entry.getRight().trim())) {
					throw new InmutableDataException(String.format("The %s side of the comparison was already received", side));
				}
				entry.setRight(base64Data);
			}
			return entry;
		} else {
			entry = new EntryData(id);
			if(Side.LEFT.equals(side)) {
				entry.setLeft(base64Data);
			}
			if(Side.RIGHT.equals(side)) {
				entry.setRight(base64Data);
			}
			dummyDB.add(entry);
			return entry;
		}		
		
	}

	/**
	 * @inheritDoc
	 * 
	 * Execute the operation against a dummy Java Collection
	 * */
	@Override
	public DiffResponse getDiff(String id) throws EntryNotFoundException, EntryIncompleteException {
		
		EntryData entry = dummyDB
				.stream()
				.filter(data -> id.equals(data.getId()))
				.findAny()
				.orElse(null);
		
		return Utils.compare(entry, id);

	}

}
