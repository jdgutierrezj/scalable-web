package com.waes.jgu.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

import com.waes.jgu.domain.EntryData;
import com.waes.jgu.dto.DiffResponse;
import com.waes.jgu.dto.DiffResult;
import com.waes.jgu.exception.EntryIncompleteException;
import com.waes.jgu.exception.EntryNotFoundException;
import com.waes.jgu.exception.InvalidDataException;
import com.waes.jgu.external.DiffMatchPatch;

/**
 * Utility class to perform some common operations
 * 
 * @author Jeison Gutierrez jdgutierrezj
 * */
public class Utils {
	/**
	 * @param base64Data sequence to be validated
	 * 
	 * @throws InvalidDataException if the parameter is not a valid base64 sequence
	 * */
	public static void checkBase64Data(String base64Data) throws InvalidDataException {
		Base64.Decoder decoder = Base64.getDecoder();
		try {
		    decoder.decode(base64Data);
		} catch(IllegalArgumentException iae) {
		    throw new InvalidDataException("Invalid Base64 Data", iae);
		}
	}
	
	/**
	 * Performs comparison between both sides using an external class
	 * 
	 * @see <a href="https://github.com/google/diff-match-patch/wiki/Language:-Java">Diff Match Patch is a high-performance library in multiple languages that manipulates plain text.</a>
	 * 
	 * @return list of {@code DiffResult} containing the differences that have been found
	 * */	
	private static List<DiffResult> getDifferences(String left, String right) { 
		List<DiffResult> diffList = new ArrayList<DiffResult>();
        DiffMatchPatch diffMatchPatch = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Diff> diffs = diffMatchPatch.diff_main(left, right, false);
        
        for (DiffMatchPatch.Diff diff : diffs) {
        	DiffResult diffItem = new DiffResult();
            switch (diff.operation) {
                case EQUAL:
                    continue;
                case DELETE:                	
                case INSERT:
                	diffItem.setType(diff.operation);
                	diffItem.setContent(diff.text);
                	diffItem.setPosition(right.indexOf(diff.text));
                	diffItem.setOffset(diff.text.length());
                    break;
            }

            diffList.add(diffItem);
		}
        
        return diffList;
	}
	
	/**
	 * Perform some basic validations and comparisons 
	 * 
	 * @param entry EntryData instance that has been found before
	 * @param id the unique identifier of the entry
	 * 
	 * @return response with the result of the global comparison
	 * 
	 * @throws EntryNotFoundException when the entry was not found
	 * @throws EntryIncompleteException when one or more sides of the comparison have not been received
	 */
	public static DiffResponse compare(EntryData entry, String id) throws EntryNotFoundException, EntryIncompleteException {
		DiffResponse response = new DiffResponse();
		
		if(null == entry) {
			throw new EntryNotFoundException(String.format("The comparison entry identified by the id %s was not found", id));
		}
		if(null == entry.getLeft() 
				|| entry.getLeft().isEmpty() 
				|| null == entry.getRight() 
				|| entry.getRight().isEmpty() ) {
			throw new EntryIncompleteException(String.format("The comparison entry identified by the id %s is incomplete", id));
		}
		
		if(entry.getLeft().equals(entry.getRight())) {
			response.setResult("EQUAL");
		} else if(entry.getLeft().length() == entry.getRight().length()) {
			response.setResult("LENGTH EQUAL");
			response.setDifferences(Utils.getDifferences(entry.getLeft(), entry.getRight()));
		} else {
			response.setResult("NOT EQUAL");
			response.setDifferences(Utils.getDifferences(entry.getLeft(), entry.getRight()));	
		}
		
		return response;
	}
}
