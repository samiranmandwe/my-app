package com.example;

import com.example.dao.PincodeMasterDAO;
import com.example.dao.impl.PincodeMasterDAOImpl;
import com.example.model.PincodeMaster;
import com.example.service.PincodeMasterService;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Demo class to demonstrate the usage of PincodeMasterDAO and PincodeMasterService
 */
public class PincodeMasterDemo {
    
    public static void main(String[] args) {
        System.out.println("=== PincodeMaster DAO and Service Demo ===\n");
        
        // Create service instance
        PincodeMasterService service = new PincodeMasterService();
        
        // Demo 1: Add sample data
        System.out.println("1. Adding sample PIN codes...");
        addSampleData(service);
        System.out.println("Total PIN codes: " + service.getTotalPincodeCount());
        System.out.println("Active PIN codes: " + service.getActivePincodeCount());
        System.out.println("Inactive PIN codes: " + service.getInactivePincodeCount());
        System.out.println();
        
        // Demo 2: Find by PIN code
        System.out.println("2. Finding PIN code details...");
        PincodeMaster pincode = service.getPincodeDetails("110001");
        if (pincode != null) {
            System.out.println("Found: " + pincode);
        } else {
            System.out.println("PIN code 110001 not found");
        }
        System.out.println();
        
        // Demo 3: Find by state
        System.out.println("3. Finding PIN codes by state (Delhi)...");
        List<PincodeMaster> delhiPincodes = service.getPincodesByState("Delhi");
        System.out.println("PIN codes in Delhi: " + delhiPincodes.size());
        delhiPincodes.forEach(pm -> System.out.println("  " + pm.getPincode() + " - " + pm.getOfficeName()));
        System.out.println();
        
        // Demo 4: Find active PIN codes
        System.out.println("4. Finding active PIN codes...");
        List<PincodeMaster> activePincodes = service.getActivePincodes();
        System.out.println("Active PIN codes: " + activePincodes.size());
        activePincodes.forEach(pm -> System.out.println("  " + pm.getPincode() + " - " + pm.getOfficeName() + " (" + pm.getStateName() + ")"));
        System.out.println();
        
        // Demo 5: Deactivate a PIN code
        System.out.println("5. Deactivating PIN code 400001...");
        boolean deactivated = service.deactivatePincode("400001");
        System.out.println("Deactivation successful: " + deactivated);
        System.out.println("Active PIN codes after deactivation: " + service.getActivePincodeCount());
        System.out.println("Inactive PIN codes after deactivation: " + service.getInactivePincodeCount());
        System.out.println();
        
        // Demo 6: Get all states and districts
        System.out.println("6. Getting unique states and districts...");
        Set<String> states = service.getAllStates();
        System.out.println("Available states: " + states);
        
        for (String state : states) {
            Set<String> districts = service.getDistrictsByState(state);
            System.out.println("Districts in " + state + ": " + districts);
        }
        System.out.println();
        
        // Demo 7: Check existence
        System.out.println("7. Checking PIN code existence...");
        System.out.println("110001 exists: " + service.isPincodeExists("110001"));
        System.out.println("999999 exists: " + service.isPincodeExists("999999"));
        System.out.println();
        
        // Demo 8: Direct DAO usage
        System.out.println("8. Direct DAO usage example...");
        PincodeMasterDAO dao = new PincodeMasterDAOImpl();
        
        // Add a new PIN code directly via DAO
        PincodeMaster newPincode = new PincodeMaster("500001", "Hyderabad GPO", "Hyderabad", "Telangana");
        newPincode.setOfficeType("Head Office");
        newPincode.setDeliveryStatus("Delivery");
        dao.save(newPincode);
        
        System.out.println("Added via DAO: " + dao.findByPincode("500001"));
        System.out.println("Total count via DAO: " + dao.count());
        System.out.println();
        
        // Demo 9: Replace all data
        System.out.println("9. Replacing all data with new set...");
        List<PincodeMaster> newData = Arrays.asList(
            new PincodeMaster("700001", "Kolkata GPO", "Kolkata", "West Bengal"),
            new PincodeMaster("600001", "Chennai GPO", "Chennai", "Tamil Nadu")
        );
        
        List<PincodeMaster> replacedData = service.replaceAllPincodes(newData);
        System.out.println("Replaced with " + replacedData.size() + " new records");
        System.out.println("New total count: " + service.getTotalPincodeCount());
        
        // Show final data
        System.out.println("Final data:");
        service.getAllPincodes().forEach(pm -> 
            System.out.println("  " + pm.getPincode() + " - " + pm.getOfficeName() + " (" + pm.getStateName() + ")"));
        
        System.out.println("\n=== Demo completed ===");
    }
    
    private static void addSampleData(PincodeMasterService service) {
        // Create sample PIN code data
        List<PincodeMaster> sampleData = Arrays.asList(
            createPincode("110001", "New Delhi GPO", "New Delhi", "Delhi", "Yes"),
            createPincode("110002", "Indraprastha", "New Delhi", "Delhi", "Yes"),
            createPincode("400001", "Mumbai GPO", "Mumbai", "Maharashtra", "Yes"),
            createPincode("400002", "Mumbai Fort", "Mumbai", "Maharashtra", "Yes"),
            createPincode("560001", "Bangalore GPO", "Bangalore", "Karnataka", "Yes"),
            createPincode("560002", "Bangalore City Market", "Bangalore", "Karnataka", "No"),
            createPincode("201301", "Noida Sector 1", "Gautam Buddha Nagar", "Uttar Pradesh", "Yes")
        );
        
        // Save all sample data
        service.savePincodes(sampleData);
    }
    
    private static PincodeMaster createPincode(String pincode, String officeName, 
                                             String district, String state, String active) {
        PincodeMaster pm = new PincodeMaster(pincode, officeName, district, state);
        pm.setActive(active);
        pm.setOfficeType("Sub Office");
        pm.setDeliveryStatus("Delivery");
        return pm;
    }
}