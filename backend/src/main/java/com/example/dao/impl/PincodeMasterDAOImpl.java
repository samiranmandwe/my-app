package com.example.dao.impl;

import com.example.dao.PincodeMasterDAO;
import com.example.model.PincodeMaster;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of PincodeMasterDAO
 * This implementation uses a ConcurrentHashMap for thread-safe operations
 * Can be easily adapted for database persistence (JPA, MyBatis, etc.)
 */
public class PincodeMasterDAOImpl implements PincodeMasterDAO {
    
    // Thread-safe in-memory storage
    private final Map<String, PincodeMaster> pincodeMasterMap = new ConcurrentHashMap<>();
    
    @Override
    public List<PincodeMaster> findAll() {
        return new ArrayList<>(pincodeMasterMap.values());
    }
    
    @Override
    public PincodeMaster findByPincode(String pincode) {
        if (pincode == null || pincode.trim().isEmpty()) {
            return null;
        }
        return pincodeMasterMap.get(pincode.trim());
    }
    
    @Override
    public List<PincodeMaster> findByState(String state) {
        if (state == null || state.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return pincodeMasterMap.values()
                .stream()
                .filter(pm -> state.trim().equalsIgnoreCase(pm.getStateName()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PincodeMaster> findByActive(String active) {
        if (active == null || active.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return pincodeMasterMap.values()
                .stream()
                .filter(pm -> active.trim().equalsIgnoreCase(pm.getActive()))
                .collect(Collectors.toList());
    }
    
    @Override
    public PincodeMaster save(PincodeMaster pincodeMaster) {
        if (pincodeMaster == null || pincodeMaster.getPincode() == null) {
            throw new IllegalArgumentException("PincodeMaster and pincode cannot be null");
        }
        
        String pincode = pincodeMaster.getPincode().trim();
        
        // Check if updating existing record
        PincodeMaster existing = pincodeMasterMap.get(pincode);
        if (existing != null) {
            // Update modification date for existing records
            pincodeMaster.setModifiedDate(LocalDateTime.now());
            // Preserve creation date
            pincodeMaster.setCreatedDate(existing.getCreatedDate());
        } else {
            // Set creation date for new records
            if (pincodeMaster.getCreatedDate() == null) {
                pincodeMaster.setCreatedDate(LocalDateTime.now());
            }
            if (pincodeMaster.getModifiedDate() == null) {
                pincodeMaster.setModifiedDate(LocalDateTime.now());
            }
        }
        
        pincodeMasterMap.put(pincode, pincodeMaster);
        return pincodeMaster;
    }
    
    @Override
    public List<PincodeMaster> saveAll(List<PincodeMaster> pincodeMasters) {
        if (pincodeMasters == null) {
            return new ArrayList<>();
        }
        
        List<PincodeMaster> savedList = new ArrayList<>();
        for (PincodeMaster pm : pincodeMasters) {
            try {
                PincodeMaster saved = save(pm);
                savedList.add(saved);
            } catch (IllegalArgumentException e) {
                // Log error and continue with next record
                System.err.println("Error saving pincode master: " + e.getMessage());
            }
        }
        return savedList;
    }
    
    @Override
    public boolean deleteByPincode(String pincode) {
        if (pincode == null || pincode.trim().isEmpty()) {
            return false;
        }
        
        PincodeMaster removed = pincodeMasterMap.remove(pincode.trim());
        return removed != null;
    }
    
    @Override
    public int deleteAll() {
        int count = pincodeMasterMap.size();
        pincodeMasterMap.clear();
        return count;
    }
    
    @Override
    public long count() {
        return pincodeMasterMap.size();
    }
    
    @Override
    public boolean existsByPincode(String pincode) {
        if (pincode == null || pincode.trim().isEmpty()) {
            return false;
        }
        return pincodeMasterMap.containsKey(pincode.trim());
    }
    
    @Override
    public List<PincodeMaster> replaceAll(List<PincodeMaster> pincodeMasters) {
        if (pincodeMasters == null) {
            pincodeMasters = new ArrayList<>();
        }
        
        // Clear existing data
        pincodeMasterMap.clear();
        
        // Save new data
        return saveAll(pincodeMasters);
    }
    
    // Additional utility methods for the implementation
    
    /**
     * Find PIN codes by district
     * @param district District name to search
     * @return List of PincodeMaster entities
     */
    public List<PincodeMaster> findByDistrict(String district) {
        if (district == null || district.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return pincodeMasterMap.values()
                .stream()
                .filter(pm -> district.trim().equalsIgnoreCase(pm.getDistrictName()))
                .collect(Collectors.toList());
    }
    
    /**
     * Find PIN codes by office name
     * @param officeName Office name to search
     * @return List of PincodeMaster entities
     */
    public List<PincodeMaster> findByOfficeName(String officeName) {
        if (officeName == null || officeName.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return pincodeMasterMap.values()
                .stream()
                .filter(pm -> pm.getOfficeName() != null && 
                             pm.getOfficeName().toLowerCase().contains(officeName.trim().toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Find PIN codes within a state and district combination
     * @param state State name
     * @param district District name
     * @return List of PincodeMaster entities
     */
    public List<PincodeMaster> findByStateAndDistrict(String state, String district) {
        if (state == null || state.trim().isEmpty() || 
            district == null || district.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return pincodeMasterMap.values()
                .stream()
                .filter(pm -> state.trim().equalsIgnoreCase(pm.getStateName()) &&
                             district.trim().equalsIgnoreCase(pm.getDistrictName()))
                .collect(Collectors.toList());
    }
    
    /**
     * Get all unique states
     * @return Set of unique state names
     */
    public Set<String> getAllStates() {
        return pincodeMasterMap.values()
                .stream()
                .map(PincodeMaster::getStateName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
    
    /**
     * Get all unique districts for a given state
     * @param state State name
     * @return Set of unique district names
     */
    public Set<String> getDistrictsByState(String state) {
        if (state == null || state.trim().isEmpty()) {
            return new HashSet<>();
        }
        
        return pincodeMasterMap.values()
                .stream()
                .filter(pm -> state.trim().equalsIgnoreCase(pm.getStateName()))
                .map(PincodeMaster::getDistrictName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}