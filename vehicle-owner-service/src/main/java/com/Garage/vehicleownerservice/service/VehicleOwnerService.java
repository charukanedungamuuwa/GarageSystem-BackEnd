package com.Garage.vehicleownerservice.service;

import com.Garage.vehicleownerservice.Exeptions.UserAlreadyExistsException;
import com.Garage.vehicleownerservice.model.VehicleOwner;
import com.Garage.vehicleownerservice.repository.VehicleOwnerRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VehicleOwnerService {
    @Autowired
    private VehicleOwnerRepository vehicleOwnerRepository;







    //
    public int generateUserId() {
        VehicleOwner lastvehicleOwner = vehicleOwnerRepository.findFirstByOrderByUserIdDesc();
        if (lastvehicleOwner != null) {
            int lastUserId = lastvehicleOwner.getUserId();

            // Convert int to String
            String lastUserIdStr = String.valueOf(lastUserId);

            // Extract numeric part using regex
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(lastUserIdStr);

            if (matcher.find()) {
                int lastNumericUserId = Integer.parseInt(matcher.group());
                return lastNumericUserId + 1;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

//


    // Helper method to extract token from request headers
//    private String extractTokenFromHeader(HttpServletRequest request) {
//        final String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            return authHeader.substring(7);
//        }
//        return null;
//    }




    //




    public List<VehicleOwner> getAllVehicleOwners() {
        return vehicleOwnerRepository.findAll();
    }

    public VehicleOwner getByEmail(String email) {
        return vehicleOwnerRepository.findByEmail(email);
    }



//    public String getUserEmailFromToken(String token) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(token);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.exchange(
//                "http://localhost:8083/auth/extractUserEmail",
//                HttpMethod.GET,
//                entity,
//                String.class
//        );
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return response.getBody(); // Email retrieved successfully
//        } else {
//            return null; // Token validation failed or other error
//        }
//    }



//    public VehicleOwner addVehicleOwner(VehicleOwner vehicleOwner) {
//        return vehicleOwnerRepository.save(vehicleOwner);
//    }



    public ResponseEntity<String> addVehicleOwner(VehicleOwner vehicleOwner) {
        try {
            VehicleOwner existingOwner = vehicleOwnerRepository.findByEmail(vehicleOwner.getEmail());
            vehicleOwner.setUserId(generateUserId());
            if (existingOwner != null) {
                throw new UserAlreadyExistsException("Email: " + vehicleOwner.getEmail() + " is already exist");
            }
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.getForEntity(

                    "http://localhost:8083/auth/checkEmailExists/{email}",
                    String.class,
                    vehicleOwner.getEmail()
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                String requestJson = "{" +
                        "\"userId\":\"" + vehicleOwner.getUserId() + "\"," +
                        "\"firstName\":\"" + vehicleOwner.getFirstName() + "\"," +
                        "\"lastName\":\"" + vehicleOwner.getLastName() + "\"," +
                        "\"email\":\"" + vehicleOwner.getEmail() + "\"," +
                        "\"contactNumber\":\"" + vehicleOwner.getContactNumber() + "\"," +  // Add contact number
                        "\"address\":\"" + vehicleOwner.getAddress() + "\"," +              // Add address
                        "\"password\":\"" + vehicleOwner.getPassword() + "\"," +
                        "\"role\":\"CUSTOMER\"" +
                        "}";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);
                ResponseEntity<String> registerResponse = restTemplate.postForEntity(
                        "http://localhost:8083//auth/register",
                        requestEntity,
                        String.class
                );

                if (registerResponse.getStatusCode() == HttpStatus.OK) {
//                    customer.setOrderStatus(Customer.OrderStatus.NOT_APPLICABLE);
                    vehicleOwner.setPassword(null);
                    vehicleOwnerRepository.save(vehicleOwner);
                    return ResponseEntity.ok("Owner saved successfully");
                } else {
                    throw new UserAlreadyExistsException("Error registering user");
                }
            } else {
                throw new UserAlreadyExistsException("Email: " + vehicleOwner.getEmail() + " is already exist");
            }
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }



    public String deleteOwnerById(Integer id) {
        vehicleOwnerRepository.deleteById(id);
        return "Deleted Successfully";
    }





//    public VehicleOwner getbyid(Long id) {
//        Optional<VehicleOwner> ownerOptional = vehicleOwnerRepository.findById(id);
//        return ownerOptional.orElse(null);
//    }

//    public VehicleOwner getbyid(Long id) {
//        Optional<VehicleOwner> ownerOptional = vehicleOwnerRepository.findById(id);
//        if (ownerOptional.isPresent()) {
//            return ownerOptional.get();
//        } else {
//            throw new RuntimeException("Owner not found with id: " + id);
//        }
//    }
public Optional<VehicleOwner> getbyid(Integer userId) {
    return vehicleOwnerRepository.findById(userId);
}

    //
    public VehicleOwner editOwner(Integer id, VehicleOwner newOwner) {
        VehicleOwner existingOwner = vehicleOwnerRepository.findById(id).orElse(null);
        if (existingOwner != null) {
            existingOwner.setFirstName(newOwner.getFirstName());
            existingOwner.setLastName(newOwner.getLastName());
            existingOwner.setEmail(newOwner.getEmail());
            existingOwner.setContactNumber(newOwner.getContactNumber());
            existingOwner.setAddress(newOwner.getAddress());
            return vehicleOwnerRepository.save(existingOwner);
        }
        return null;
    }


}
