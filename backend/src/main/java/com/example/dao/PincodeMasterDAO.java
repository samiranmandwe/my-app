package com.example.dao;

import com.example.model.PincodeMaster;
import java.util.List;

/**
 * Data Access Object interface for PincodeMaster entity
 * Provides CRUD operations and specialized query methods for PIN code data
 */
public interface PincodeMasterDAO {
    
    /**
     * Find all PIN codes
     * @return List of all PincodeMaster entities
     */
    List<PincodeMaster> findAll();
    
    /**
     * Find PIN code by pincode
     * @param pincode PIN code to search
     * @return PincodeMaster entity or null if not found
     */
    PincodeMaster findByPincode(String pincode);
    
    /**
     * Find PIN codes by state
     * @param state State to search
     * @return List of PincodeMaster entities
     */
    List<PincodeMaster> findByState(String state);
    
    /**
     * Find active/inactive PIN codes
     * @param active Active status (Yes/No)
     * @return List of PincodeMaster entities
     */
    List<PincodeMaster> findByActive(String active);
    
    /**
     * Save or update PIN code
     * @param pincodeMaster PincodeMaster entity to save
     * @return Saved PincodeMaster entity
     */
    PincodeMaster save(PincodeMaster pincodeMaster);
    
    /**
     * Save or update multiple PIN codes
     * @param pincodeMasters List of PincodeMaster entities to save
     * @return List of saved PincodeMaster entities
     */
    List<PincodeMaster> saveAll(List<PincodeMaster> pincodeMasters);
    
    /**
     * Delete PIN code by pincode
     * @param pincode PIN code to delete
     * @return true if deleted, false otherwise
     */
    boolean deleteByPincode(String pincode);
    
    /**
     * Delete all PIN codes
     * @return Number of deleted records
     */
    int deleteAll();
    
    /**
     * Count total PIN codes
     * @return Total count
     */
    long count();
    
    /**
     * Check if PIN code exists
     * @param pincode PIN code to check
     * @return true if exists, false otherwise
     */
    boolean existsByPincode(String pincode);
    
    /**
     * Replace all PIN codes with new data
     * @param pincodeMasters New PIN code data
     * @return List of saved PincodeMaster entities
     */
    List<PincodeMaster> replaceAll(List<PincodeMaster> pincodeMasters);
}