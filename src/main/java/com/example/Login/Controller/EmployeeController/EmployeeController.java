package com.example.Login.Controller.EmployeeController;

import com.example.Login.Dto.ChangePassword;
import com.example.Login.Dto.EmployeeDto;
import com.example.Login.Dto.Response.Login.EmployeeRes;
import com.example.Login.Dto.Response.Login.EmployeeResponseMsg;
import com.example.Login.Dto.Response.Login.LoginReceive;
import com.example.Login.Service.Login.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/login")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     *
     * @param employeeDto
     * @return
     */
    @PostMapping("/Registration")
    public String saveEmployee(@RequestBody EmployeeDto employeeDto){
        String registry = employeeService.EmployeeRegistration(employeeDto);
        return switch (registry) {
            case "An account with this email already exists." -> "An account with this email already exists.";
            case "Invalid email" -> "Invalid email";
            case "Invalid password" -> "Invalid password";
            default -> "user saved successfully";
        };
    }

    /**
     *
     * @param h1
     * @return
     */

    @GetMapping("/CheckLogin")
    public ResponseEntity<?> checkAuthentication(HttpSession h1) {
        if (h1.getAttribute("Username") != null) {
            return ResponseEntity.ok().body("User is authenticated");
        }
        return ResponseEntity.ok().body("User is not authenticated");
    }

    /**
     *
     * @param loginReceive
     * @param h1
     * @param request
     * @return
     */

    @PostMapping("/profile")
    public EmployeeResponseMsg displayEmployee(@RequestBody LoginReceive loginReceive, HttpSession h1, HttpServletRequest request){
        return employeeService.retEmployee(loginReceive,h1, request);
    }

    /**
     *
     * @param request
     * @return
     */

    @PostMapping("/getEmployee")
    public ResponseEntity<EmployeeRes> getEmployee(@RequestBody Map<String, String> request) {
        String uniqueId = request.get("unique_id");
        EmployeeRes employeeRes = employeeService.getEmployee(uniqueId);

        if (employeeRes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employeeRes);
    }

    /**
     *
     * @param username
     * @return
     */
    @GetMapping("/getSession")
    public ResponseEntity<String> getSession(@RequestParam String username){
        String sessionId = employeeService.getSession_id(username);
        return ResponseEntity.ok(sessionId);
    }

    /**
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
          String message = employeeService.deleteUserSessions(username);// Invalidate user's sessions
        if(Objects.equals(message, "Successfully deleted")){
            return ResponseEntity.ok("{\"message\": \"Logout successful\"}");
        }
        return ResponseEntity.ok("{\"message\": \"no session available\"}");
    }
    //here after control the profile of the manager
    //update the profile picture
    /*@PostMapping("/updateProfilePicture")
    public ResponseEntity<Void> updateProfilePicture(@RequestParam Integer m_id, @RequestParam("file") MultipartFile file) throws IOException {
        employeeService.updateProfilePicture(m_id, file);
        return ResponseEntity.ok().build();
    }*/

    /**
     *
     * @param sessionId
     * @param file
     * @return
     */

    @PostMapping("/updateProfilePicture")
    public ResponseEntity<String> updateProfilePicture(@RequestParam String sessionId, @RequestParam("file") MultipartFile file) {
        //@RequestParam Integer m_id
        try {
            employeeService.updateProfilePicture(sessionId, file);
            return ResponseEntity.ok("Profile picture updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("file type should be the jpg");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    /**
     *
     * @param sessionId
     * @param response
     * @throws IOException
     */
    @GetMapping("/profileimage")
    public void serveFileHandle (@RequestParam String sessionId, HttpServletResponse response) throws IOException {

        String m_id =  employeeService.getUsernameFromSession(sessionId);
        if(!Objects.equals(m_id, "user cannot be find")){
            try {
                InputStream resourceFile = employeeService.getResourcesFile2(m_id);
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                StreamUtils.copy(resourceFile, response.getOutputStream());
            } catch (EntityNotFoundException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Manager not found");
            } catch (FileNotFoundException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            } catch (IOException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching file");
            }
        }else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"user cannot be found");
        }

    }

    /**
     *
     * @param h1
     * @return
     */
    @PostMapping("/deleteProfile")
    public ResponseEntity<Void> deleteProfile(HttpSession h1){
        try{

            employeeService.deleteProfilePicture((String) h1.getAttribute("Username"));
            return ResponseEntity.ok().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // Handle specific case
        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     *
     * @param user_id
     * @return
     */
    @PostMapping("/emailValidation")
    public ResponseEntity<String> validEmail(@RequestBody String user_id){
        String validationResult = employeeService.validateEmail(user_id);

        if (validationResult.equals("Valid email address")) {
            return ResponseEntity.ok("Valid email address");
        } else {
            return ResponseEntity.ok("Invalid email address");
        }

    }

    /**
     *
     * @param user_id
     * @param h2
     * @param request
     * @return
     */
    //here we create the email verification
    @PostMapping("/verifyMail/{user_id}")
    public ResponseEntity<String> verifyEmail(@PathVariable String user_id, HttpSession h2, HttpServletRequest request){
            return employeeService.verifyEmailService(user_id,h2,request);
    }

    /**
     *
     * @param requestBody
     * @param h2
     * @return
     */
    @PostMapping("/verifyOTP")
    public ResponseEntity<String> verifyOTP(@RequestBody Map<String, Integer> requestBody, HttpSession h2){
        Integer otp = requestBody.get("otp");
        return employeeService.checkEmployee(otp,h2);
    }

    /**
     *
     * @param changePassword
     * @param h2
     * @return
     */
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePasswordHandel(@RequestBody ChangePassword changePassword,HttpSession h2){
        ResponseEntity<String> response = employeeService.changePasswordServer(changePassword, h2);

        if (Objects.equals(response.getBody(), "Password has been changed")) {
            employeeService.checkSession((String) h2.getAttribute("FUserId"));

        }
        return response;
    }
}