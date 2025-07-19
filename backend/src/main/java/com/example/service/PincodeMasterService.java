package com.example.service;

import com.example.dao.PincodeMasterDAO;
import com.example.dao.impl.PincodeMasterDAOImpl;
import com.example.model.PincodeMaster;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for PincodeMaster business logic
 * Provides a higher-level interface over the DAO layer with validation and business rules
 */
public class PincodeMasterService {
    
    private final PincodeMasterDAO pincodeMasterDAO;
    
    /**
     * Constructor with dependency injection support
     * @param pincodeMasterDAO DAO implementation to use
     */
    public PincodeMasterService(PincodeMasterDAO pincodeMasterDAO) {
        this.pincodeMasterDAO = pincodeMasterDAO;
    }
    
    /**
     * Default constructor using the default implementation
     */
    public PincodeMasterService() {
        this.pincodeMasterDAO = new PincodeMasterDAOImpl();
    }
    
    /**
     * Get all PIN codes
     * @return List of all PIN codes
     */
    public List<PincodeMaster> getAllPincodes() {
        return pincodeMasterDAO.findAll();
    }
    
    /**
     * Get all active PIN codes
     * @return List of active PIN codes
     */
    public List<PincodeMaster> getActivePincodes() {
        return pincodeMasterDAO.findByActive("Yes");
    }
    
    /**
     * Get all inactive PIN codes
     * @return List of inactive PIN codes
     */
    public List<PincodeMaster> getInactivePincodes() {
        return pincodeMasterDAO.findByActive("No");
    }
    
    /**
     * Find PIN code details by PIN code
     * @param pincode PIN code to search
     * @return PincodeMaster or null if not found
     */
    public PincodeMaster getPincodeDetails(String pincode) {
        if (!isValidPincode(pincode)) {
            throw new IllegalArgumentException("Invalid PIN code format. PIN code must be 6 digits.");
        }
        return pincodeMasterDAO.findByPincode(pincode);
    }
    
    /**
     * Get all PIN codes for a state
     * @param state State name
     * @return List of PIN codes in the state
     */
    public List<PincodeMaster> getPincodesByState(String state) {
        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("State name cannot be null or empty");
        }
        return pincodeMasterDAO.findByState(state);
    }
    
    /**
     * Get all active PIN codes for a state
     * @param state State name
     * @return List of active PIN codes in the state
     */
    public List<PincodeMaster> getActivePincodesByState(String state) {
        return getPincodesByState(state)
                .stream()
                .filter(PincodeMaster::isActive)
                .collect(Collectors.toList());
    }
    
    /**
     * Create or update a PIN code
     * @param pincodeMaster PIN code data to save
     * @return Saved PIN code data
     */
    public PincodeMaster savePincode(PincodeMaster pincodeMaster) {
        validatePincodeMaster(pincodeMaster);
        return pincodeMasterDAO.save(pincodeMaster);
    }
    
    /**
     * Create or update multiple PIN codes
     * @param pincodeMasters List of PIN code data to save
     * @return List of saved PIN code data
     */
    public List<PincodeMaster> savePincodes(List<PincodeMaster> pincodeMasters) {
        if (pincodeMasters == null || pincodeMasters.isEmpty()) {
            throw new IllegalArgumentException("PIN code list cannot be null or empty");
        }
        
        // Validate all records before saving
        for (PincodeMaster pm : pincodeMasters) {
            validatePincodeMaster(pm);
        }
        
        return pincodeMasterDAO.saveAll(pincodeMasters);
    }
    
    /**
     * Activate a PIN code
     * @param pincode PIN code to activate
     * @return true if activated successfully
     */
    public boolean activatePincode(String pincode) {
        PincodeMaster pm = getPincodeDetails(pincode);
        if (pm == null) {
            return false;
        }
        
        pm.markAsActive();
        pincodeMasterDAO.save(pm);
        return true;
    }
    
    /**
     * Deactivate a PIN code
     * @param pincode PIN code to deactivate
     * @return true if deactivated successfully
     */
    public boolean deactivatePincode(String pincode) {
        PincodeMaster pm = getPincodeDetails(pincode);
        if (pm == null) {
            return false;
        }
        
        pm.markAsInactive();
        pincodeMasterDAO.save(pm);
        return true;
    }
    
    /**
     * Delete a PIN code
     * @param pincode PIN code to delete
     * @return true if deleted successfully
     */
    public boolean deletePincode(String pincode) {
        if (!isValidPincode(pincode)) {
            throw new IllegalArgumentException("Invalid PIN code format");
        }
        return pincodeMasterDAO.deleteByPincode(pincode);
    }
    
    /**
     * Delete all PIN codes
     * @return Number of deleted records
     */
    public int deleteAllPincodes() {
        return pincodeMasterDAO.deleteAll();
    }
    
    /**
     * Check if a PIN code exists
     * @param pincode PIN code to check
     * @return true if exists
     */
    public boolean isPincodeExists(String pincode) {
        if (!isValidPincode(pincode)) {
            return false;
        }
        return pincodeMasterDAO.existsByPincode(pincode);
    }
    
    /**
     * Get total count of PIN codes
     * @return Total count
     */
    public long getTotalPincodeCount() {
        return pincodeMasterDAO.count();
    }
    
    /**
     * Get count of active PIN codes
     * @return Active PIN codes count
     */
    public long getActivePincodeCount() {
        return getActivePincodes().size();
    }
    
    /**
     * Get count of inactive PIN codes
     * @return Inactive PIN codes count
     */
    public long getInactivePincodeCount() {
        return getInactivePincodes().size();
    }
    
    /**
     * Replace all PIN code data with new data
     * @param pincodeMasters New PIN code data
     * @return List of saved PIN code data
     */
    public List<PincodeMaster> replaceAllPincodes(List<PincodeMaster> pincodeMasters) {
        if (pincodeMasters != null) {
            // Validate all records before replacing
            for (PincodeMaster pm : pincodeMasters) {
                validatePincodeMaster(pm);
            }
        }
        return pincodeMasterDAO.replaceAll(pincodeMasters);
    }
    
    /**
     * Get all unique states
     * @return Set of state names
     */
    public Set<String> getAllStates() {
        if (pincodeMasterDAO instanceof PincodeMasterDAOImpl) {
            return ((PincodeMasterDAOImpl) pincodeMasterDAO).getAllStates();
        }
        
        // Fallback implementation
        return getAllPincodes()
                .stream()
                .map(PincodeMaster::getStateName)
                .filter(state -> state != null && !state.trim().isEmpty())
                .collect(Collectors.toSet());
    }
    
    /**
     * Get all districts for a given state
     * @param state State name
     * @return Set of district names
     */
    public Set<String> getDistrictsByState(String state) {
        if (pincodeMasterDAO instanceof PincodeMasterDAOImpl) {
            return ((PincodeMasterDAOImpl) pincodeMasterDAO).getDistrictsByState(state);
        }
        
        // Fallback implementation
        return getPincodesByState(state)
                .stream()
                .map(PincodeMaster::getDistrictName)
                .filter(district -> district != null && !district.trim().isEmpty())
                .collect(Collectors.toSet());
    }
    
    // Private helper methods
    
    /**
     * Validate PIN code format
     * @param pincode PIN code to validate
     * @return true if valid
     */
    private boolean isValidPincode(String pincode) {
        if (pincode == null || pincode.trim().isEmpty()) {
            return false;
        }
        
        String cleanPincode = pincode.trim();
        return cleanPincode.length() == 6 && cleanPincode.matches("\\d{6}");
    }
    
    /**
     * Validate PincodeMaster object
     * @param pincodeMaster Object to validate
     */
    private void validatePincodeMaster(PincodeMaster pincodeMaster) {
        if (pincodeMaster == null) {
            throw new IllegalArgumentException("PincodeMaster cannot be null");
        }
        
        if (!isValidPincode(pincodeMaster.getPincode())) {
            throw new IllegalArgumentException("Invalid PIN code format. Must be 6 digits.");
        }
        
        if (pincodeMaster.getOfficeName() == null || pincodeMaster.getOfficeName().trim().isEmpty()) {
            throw new IllegalArgumentException("Office name cannot be null or empty");
        }
        
        if (pincodeMaster.getStateName() == null || pincodeMaster.getStateName().trim().isEmpty()) {
            throw new IllegalArgumentException("State name cannot be null or empty");
        }
        
        if (pincodeMaster.getDistrictName() == null || pincodeMaster.getDistrictName().trim().isEmpty()) {
            throw new IllegalArgumentException("District name cannot be null or empty");
        }
        
        // Validate active status
        String active = pincodeMaster.getActive();
        if (active != null && !active.equalsIgnoreCase("Yes") && !active.equalsIgnoreCase("No")) {
            throw new IllegalArgumentException("Active status must be 'Yes' or 'No'");
        }
    }
}