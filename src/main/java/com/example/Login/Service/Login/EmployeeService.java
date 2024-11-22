package com.example.Login.Service.Login;

import com.example.Login.Entity.Driver.DriverRegistration;
import com.example.Login.Entity.Login.LoginSession;
import com.example.Login.Dto.ChangePassword;
import com.example.Login.Dto.EmployeeDto;
import com.example.Login.Dto.MailBody;
import com.example.Login.Dto.Response.Login.EmployeeRes;
import com.example.Login.Dto.Response.Login.EmployeeResponseMsg;
import com.example.Login.Entity.Login.Employee;
import com.example.Login.Entity.Login.ForgotPassword;
import com.example.Login.Dto.Response.Login.LoginReceive;
import com.example.Login.Repository.Driver.DriverRepo;
import com.example.Login.Repository.Login.EmployeeRepository;
import com.example.Login.Repository.Login.ForgetPasswordRepo;
import com.example.Login.Repository.Login.LoginSessionRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService{

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final DriverRepo driverRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final LoginSessionRepo loginSessionRepo;

    private final EmailService emailService;

    private final ForgetPasswordRepo forgetPasswordRepo;


    public EmployeeService(EmployeeRepository employeeRepository, DriverRepo driverRepo, PasswordEncoder passwordEncoder, EmailService emailService, ForgetPasswordRepo forgetPasswordRepo, LoginSessionRepo loginSessionRepo) {
        this.employeeRepository = employeeRepository;
        this.driverRepo = driverRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.forgetPasswordRepo = forgetPasswordRepo;
        this.loginSessionRepo = loginSessionRepo;
    }

    public void sendRegistrationEmail(String user_id, String firstname){
        MailBody mailBody = MailBody.builder()
                .to(user_id)
                .text("Dear " + firstname + ",\n\n" +
                        "Congratulations and welcome to Anuradhapura Guest House!\n\n" +
                        "We are thrilled to have you on board. Thank you for choosing to register with us. As part of our commitment to providing exceptional hospitality, we are here to ensure your experience with us is both enjoyable and memorable.\n\n" +
                        "If you have any questions or need further assistance, please do not hesitate to reach out to our team. We are here to help!\n\n" +
                        "Once again, welcome to Anuradhapura Guest House. We look forward to serving you.\n\n" +
                        "Best regards,\n\n" +
                        "The Anuradhapura Guest House Team")
                .subject("Welcome to Anuradhapura Guest House!")
                .build();

        emailService.sendSimpleMsg(mailBody);
    }

    public String EmployeeRegistration (EmployeeDto employeeDto){

        Optional<Employee> proveEmployee = employeeRepository.findById(employeeDto.getUser_id());

        if(proveEmployee.isPresent()){
            return "An account with this email already exists.";
        }else{
            String checkEmail = validateEmail(employeeDto.getUser_id());
            if(checkEmail.equals("Valid email address")){
                if(isValidPassword(employeeDto.getPassword())){
                    String designation = designations(employeeDto.getUser_id());

                    Employee employee = new Employee();
                        employee.setUser_id(employeeDto.getUser_id());
                        employee.setFirstname(employeeDto.getFirstname());
                        employee.setLastname(employeeDto.getLastname());
                        employee.setPh_num(employeeDto.getPh_num());
                        employee.setPassword(this.passwordEncoder.encode(employeeDto.getPassword()));
                        employee.setProfile_url("src\\main\\resources\\static\\DefaultProfilePic\\DefaultProfile.jpeg");
                        employee.setDesignation(designation);

                    employeeRepository.save(employee);
                    Optional<Employee> proveEmployee1 = employeeRepository.findById(employeeDto.getUser_id());
                    if(proveEmployee1.isPresent()){
                        Employee employees = proveEmployee1.get();
                        sendRegistrationEmail(employees.getUser_id(),employees.getFirstname());
                        return "user saved successfully";
                    }
                    return "user saved successfully";
                }else{
                    return "Invalid password";
                }

            }else{
                return "Invalid email";
            }
        }
    }

    public String validateEmail(String email){
        if(email == null || email.trim().isEmpty()){
            return "Email cannot be empty";
        }

        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,7}$";

        if (email.matches(emailPattern)) {
            return "Valid email address";
        } else {
            return "Invalid email address";
        }
    }
    //to check the password pattern
    private static final String PASSWORD_PATTERN =
    "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+]).{8,}$";


    // Method to check if the password is valid
    public boolean isValidPassword(String password) {
        if (password == null) {
            return false; // Null password is not valid
        }
        return password.matches(PASSWORD_PATTERN); // Match the password with the regex
    }

    public String designations(String user_id){
        String firstTwoDigits = user_id.substring(0, 2);
        String designation;
        switch (firstTwoDigits) {
            case "sa" -> {
                designation = "Admin";
                return designation;
            }
            case "di" -> {
                designation = "Manager";
                return designation;
            }
            case "95" -> {
                designation = "Driver";
                return designation;
            }
            default -> {
                designation = "customer";
                return designation;
            }
        }
    }

    //to track the device of user login
    public String getCurrentDeviceInfo(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String deviceInfo;

        if (userAgent != null) {
            // Simple device detection logic
            if (userAgent.toLowerCase().contains("mobile")) {
                deviceInfo = "Mobile Device";
            } else if (userAgent.toLowerCase().contains("tablet")) {
                deviceInfo = "Tablet";
            } else {
                deviceInfo = "Desktop";
            }
        } else {
            deviceInfo = "Unknown Device";
        }

        return deviceInfo;
    }
    //retrieve the user
    public EmployeeResponseMsg retEmployee(LoginReceive loginReceive, HttpSession h1, HttpServletRequest request){

        if(designations(loginReceive.getUser_id()).equals("Manager")){
            Optional<Employee> employee = employeeRepository.findById(loginReceive.getUser_id());

            if (employee.isPresent()) {

                String password = loginReceive.getPassword();
                String encodedPassword = employee.get().getPassword();

                boolean isCheck = passwordEncoder.matches(password,encodedPassword);

                if (isCheck) {
                    Employee emp = employee.get();

                    deleteUserSessions(emp.getUser_id());

                    h1.setAttribute("Username", emp.getUser_id());

                    String deviceInfo = getCurrentDeviceInfo(request);

                    String login_session_uuid;

                    LoginSession loginSession = new LoginSession();
                    login_session_uuid = UUID.randomUUID().toString();
                    loginSession.setSessionId(login_session_uuid);
                    loginSession.setUserId(emp.getUser_id());
                    loginSession.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    loginSession.setExpiresAt(new Timestamp(System.currentTimeMillis() + 10 * 3600 * 1000));
                    loginSession.setDevice(deviceInfo);

                    loginSessionRepo.save(loginSession);

                    Optional<LoginSession> loginSession1 = loginSessionRepo.findById(login_session_uuid);
                    if(loginSession1.isPresent()){
                        if(loginSession1.get().getDevice() != null){
                            if(!Objects.equals(deviceInfo, loginSession1.get().getDevice())){
                                MailBody mailBody = MailBody.builder()
                                        .to(emp.getUser_id())
                                        .text("Dear " + emp.getFirstname() + ",\n\n" +
                                                "We detected a login to your account from a new device.\n" +
                                                "Device: " +  deviceInfo + "\n" +
                                                "User Address: " + emp.getUser_id() + "\n\n" +
                                                "If this was you, no further action is needed.\n" +
                                                "If you do not recognize this login, please secure your account immediately.\n\n" +
                                                "Thank you,\n" +
                                                "Your Security Team"
                                        )
                                        .subject("New Login Alert!")
                                        .build();

                                emailService.sendSimpleMsg(mailBody);
                            }

                        }
                    }

                    String dg  = designations(emp.getUser_id());
                    switch (dg) {
                        case "Admin" -> {
                            return new EmployeeResponseMsg("Admin",login_session_uuid ,true);
                        }
                        case "Manager" -> {
                            return new EmployeeResponseMsg("Manager",login_session_uuid,true);
                        }
                        case "customer" -> {
                            return new EmployeeResponseMsg("customer",login_session_uuid,true);
                        }
                    }
                }else{
                    return new EmployeeResponseMsg("Login Failed, check your password","not create the session",false);
                }
            }else {
                return new EmployeeResponseMsg("Incorrect your password or user id","not create the session",false);
            }
        } else if (designations(loginReceive.getUser_id()).equals("Driver")) {
            Optional<DriverRegistration> driver  = driverRepo.findById(loginReceive.getUser_id());

            if (driver.isPresent()) {

                String password = loginReceive.getPassword();
                String encodedPassword = driver.get().getPassword();

                boolean isCheck = passwordEncoder.matches(password,encodedPassword);

                if (isCheck) {
                    DriverRegistration emp = driver.get();

                    deleteUserSessions(emp.getUser_id());

                    h1.setAttribute("Username", emp.getUser_id());

                    String deviceInfo = getCurrentDeviceInfo(request);

                    String login_session_uuid;

                    LoginSession loginSession = new LoginSession();
                    login_session_uuid = UUID.randomUUID().toString();
                    loginSession.setSessionId(login_session_uuid);
                    loginSession.setUserId(emp.getUser_id());
                    loginSession.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    loginSession.setExpiresAt(new Timestamp(System.currentTimeMillis() + 10 * 3600 * 1000));
                    loginSession.setDevice(deviceInfo);

                    loginSessionRepo.save(loginSession);

                    Optional<LoginSession> loginSession1 = loginSessionRepo.findById(login_session_uuid);
                    if(loginSession1.isPresent()){
                        if(loginSession1.get().getDevice() != null){
                            if(!Objects.equals(deviceInfo, loginSession1.get().getDevice())){
                                MailBody mailBody = MailBody.builder()
                                        .to(emp.getUser_id())
                                        .text("Dear " + emp.getFirstname() + ",\n\n" +
                                                "We detected a login to your account from a new device.\n" +
                                                "Device: " +  deviceInfo + "\n" +
                                                "User Address: " + emp.getUser_id() + "\n\n" +
                                                "If this was you, no further action is needed.\n" +
                                                "If you do not recognize this login, please secure your account immediately.\n\n" +
                                                "Thank you,\n" +
                                                "Your Security Team"
                                        )
                                        .subject("New Login Alert!")
                                        .build();

                                emailService.sendSimpleMsg(mailBody);
                            }

                        }
                    }

                    String dg  = designations(emp.getUser_id());
                    if(dg.equals("Driver")){
                        return new EmployeeResponseMsg("Driver",login_session_uuid ,true);
                    }
                }else{
                    return new EmployeeResponseMsg("Login Failed, check your password","not create the session",false);
                }
            }else {
                return new EmployeeResponseMsg("Incorrect your password or user id","not create the session",false);
            }
        }
        return new EmployeeResponseMsg("User id not valid","not create the session",false);
    }

    public InputStream getResourcesFile2(String m_id) throws IOException {

        Optional<Employee> managerOptional = employeeRepository.findById(m_id);
        if (managerOptional.isPresent()) {
            Employee manager = managerOptional.get();

            String filePath = manager.getProfile_url();

            if (filePath != null && !filePath.isEmpty()) {
                File file = new File(filePath);
                if (file.exists()) {
                    return new FileInputStream(file);
                } else {
                    throw new FileNotFoundException("File not found at path: " + filePath);
                }
            } else {
                throw new FileNotFoundException("File path is null or empty.");
            }

        } else {
            throw new EntityNotFoundException("Manager with ID " + m_id + " not found.");
        }
    }
    @Transactional
    public void updateProfilePicture(String sessionId, MultipartFile file) throws IOException {
        // Check file type
        String contentType = file.getContentType();
        if (!"image/jpeg".equalsIgnoreCase(contentType) && !"image/jpg".equalsIgnoreCase(contentType)) {
            throw new IllegalArgumentException("File type must be JPG");
        }

        Optional<LoginSession> loginSession = loginSessionRepo.findById(sessionId);

        if(loginSession.isPresent()){
            String m_id = loginSession.get().getUserId();

            Employee employee = employeeRepository.findById(m_id).orElse(null);
            if (employee != null) {
                //deleteProfilePicture(m_id);
                // Save the new picture and update the employee's profile URL
                String fileName = saveFile(m_id, file);
                employee.setProfile_url(fileName);
                employeeRepository.save(employee);
            }
        }
    }


    @Transactional
    private String saveFile(String m_id, MultipartFile file) {
        // Define the path where the file will be saved
        String folderPath = "src/main/resources/static/profile/";
        File directory = new File(folderPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new RuntimeException("Failed to create directory: " + folderPath);
            }
        }

        // Save the file locally
        String profile_username = m_id.substring(0, m_id.indexOf("@"));
        String fileName = folderPath + profile_username + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(fileName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save the file: " + fileName, e);
        }
        return fileName;
    }

    public void deleteProfilePicture(String m_id) throws IOException {
        Optional<Employee> managerOptional = employeeRepository.findById(m_id);
        if(managerOptional.isPresent()){
            Employee user = managerOptional.get();

            if(!user.getProfile_url().equals("src/main/resources/static/DefaultProfilePic/DefaultProfile.jpeg")){
                Files.delete(Path.of(user.getProfile_url()));
            }else{
                throw new IllegalArgumentException("Cannot delete the default profile picture");
            }
        }else{
            throw new IllegalArgumentException("User not found");
        }
    }

    @Transactional
    public void deleteProfilePictur(String m_id) throws IOException {
        Optional<Employee> managerOptional = employeeRepository.findById(m_id);
        if (managerOptional.isPresent()) {
            Employee user = managerOptional.get();

            // Ensure the profile picture is not the default one
            if (!user.getProfile_url().equals("src/main/resources/static/DefaultProfilePic/DefaultProfile.jpeg")) {
                Path filePath = Paths.get(user.getProfile_url());

                // Check if the file exists before attempting to delete
                if (Files.exists(filePath)) {
                    try {
                        Files.deleteIfExists(Path.of(user.getProfile_url()));
                        // Set profile to the default picture after deletion
                        user.setProfile_url("src/main/resources/static/DefaultProfilePic/DefaultProfile.jpeg");
                        employeeRepository.save(user);
                    } catch (IOException e) {
                        throw new IOException("Error deleting profile picture file", e);
                    }
                } else {
                    throw new IOException("Profile picture file not found");
                }
            } else {
                // Default profile picture cannot be deleted
                throw new IllegalArgumentException("Cannot delete the default profile picture");
            }
        } else {
            // Handle the case where the user is not found
            throw new IllegalArgumentException("User not found");
        }
        /*Optional<Employee> managerOptional = employeeRepository.findById(m_id);
        if (managerOptional.isPresent()) {
            Employee user = managerOptional.get();
            if (!user.getProfile_url().equals("src/main/resources/static/DefaultProfilePic/DefaultProfile.jpeg")) {
                String filePath = user.getProfile_url();
                Path path = Paths.get(filePath.replace("\\", "/"));
                //Path filePath = Paths.get(user.getProfile_url()).toAbsolutePath();
                //System.out.println(Path.of("src/main/resources/static/profile/4478_images.jpg"));
                Files.delete(Path.of("src/main/resources/static/profile/4478_welsh-corgi-8492879_1920.jpg"));
                /*try {
                    boolean isDeleted = Files.deleteIfExists(Path.of(user.getProfile_url()));
                    //Files.delete(Path.of("src/main/resources/static/profile/4478_images.jpg"));
                    if (isDeleted) {
                        user.setProfile_url("src/main/resources/static/DefaultProfilePic/DefaultProfile.jpeg");
                        employeeRepository.save(user);
                    } else {
                        throw new IOException("Failed to delete the profile picture file");
                    }
                } catch (IOException e) {
                    // Log the error if logging is set up
                    throw new IOException("Error deleting profile picture file", e);
                }
            } else {
                // Handle the case where the default profile picture cannot be deleted
                throw new IllegalArgumentException("Cannot delete the default profile picture");
            }
        } else {
            // Handle the case where the user is not found
            throw new IllegalArgumentException("User not found");
        }*/
    }

    @Transactional
    //handel the mail
    public ResponseEntity<String> verifyEmailService(String user_id, HttpSession h2 ,HttpServletRequest request) {

        Optional<Employee> managerOptional = employeeRepository.findById(user_id);
        if (managerOptional.isEmpty()) {
            throw new UsernameNotFoundException("Employee not found with ID " + user_id);
        }

        Employee employee = managerOptional.get();
        Optional<ForgotPassword> fpd = forgetPasswordRepo.findByEmployee(employee);

        h2.setAttribute("FUserId",employee.getUser_id());
        System.out.println(h2.getAttribute("FUserId"));
        List<LoginSession> sessions = loginSessionRepo.getSessionsByUserId(user_id);

        if(!sessions.isEmpty()){
            deleteUserSessions(user_id);
        }

        String deviceInfo = getCurrentDeviceInfo(request);

        LoginSession loginSessionVE = new LoginSession();
            loginSessionVE.setSessionId(UUID.randomUUID().toString());
            loginSessionVE.setUserId(managerOptional.get().getUser_id());
            loginSessionVE.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            loginSessionVE.setExpiresAt(new Timestamp(System.currentTimeMillis() + 10 * 3600 * 1000));
            loginSessionVE.setDevice(deviceInfo);

        loginSessionRepo.save(loginSessionVE);

        if(fpd.isPresent()){
            ForgotPassword fpdo = fpd.get();
            Date now = new Date();
            if (now.after(fpdo.getExpirationTime())) {
                forgetPasswordRepo.delete(fpdo);
                //forgetPasswordRepo.deleteByExpirationTimeBefore(now); // Deletes expired records
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ForgotPassword record is still valid.");
            }
        }
        if (fpd.isEmpty()) {
            Integer otp = otpGenerator();
            MailBody mailBody = MailBody.builder()
                    .to(user_id)
                    .text("This is the OTP for your forget password request " + otp)
                    .subject("OTP forget password request")
                    .build();

            ForgotPassword fp = ForgotPassword.builder()
                    .otp(otp)
                    .expirationTime(new Date(System.currentTimeMillis() + 180 * 1000))
                    .employee(employee)
                    .build();

            emailService.sendSimpleMsg(mailBody);
            forgetPasswordRepo.save(fp);
            return ResponseEntity.ok("Email sent for verification");
        }
        return ResponseEntity.ok("Email sent");
    }

    private Integer otpGenerator(){
        Random random = new Random();
        //here we create the 4 digit number as the otp code
        return random.nextInt(1_000,9_999);
    }

    public ResponseEntity<String> checkEmployee(Integer otp, HttpSession h2){
        String m_id = (String) h2.getAttribute("FUserId");

        System.out.println(h2.getId());
        System.out.println(h2.getAttribute("FUserId"));
        Optional<Employee> managerOptional = employeeRepository.findById(m_id);
        if (managerOptional.isEmpty()) {
            throw new UsernameNotFoundException("Employee not found with ID " + m_id);
        }
        Employee employee = managerOptional.get();
        ForgotPassword fp = forgetPasswordRepo.findByOtpAndEmployee(otp,employee).orElseThrow(() -> new RuntimeException("Invalid otp for email"));

        if(fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgetPasswordRepo.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP has Expired", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP verified");
    }



    public ResponseEntity<String> changePasswordServer(ChangePassword changePassword, HttpSession h2){
        Optional<Employee> managerOptional = employeeRepository.findById((String) h2.getAttribute("FUserId"));
        if (managerOptional.isEmpty()) {
            throw new UsernameNotFoundException("Employee not found with ID " + h2.getAttribute("FUserId"));
        }
        Employee employee = managerOptional.get();
        if(!isValidPassword(changePassword.password())){
            return new ResponseEntity<>("Password must include the capital, simple letters and symbole ",HttpStatus.EXPECTATION_FAILED);
        }
        if(!Objects.equals(changePassword.password(), changePassword.repeatPassword())){
            return new ResponseEntity<>("Please Enter the Password Again",HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        employeeRepository.updatePassword(employee.getUser_id(), encodedPassword);
        deleteUserSessions((String) h2.getAttribute("FUserId"));
        return ResponseEntity.ok("Password has been changed");
    }

    public EmployeeRes getEmployee(String session_id) {
        // Check if the session exists
        Optional<LoginSession> loginSession = loginSessionRepo.findById(session_id);

        if(loginSession.isPresent()){
            String username = loginSession.get().getUserId();
            Optional<Employee> employee = employeeRepository.findById(username);
            Employee employee1;
            if(employee.isPresent()){
                employee1  = employee.get();

                return new EmployeeRes(
                        employee1.getFirstname(),
                        employee1.getLastname(),
                        employee1.getUser_id(),
                        employee1.getDesignation(),
                        employee1.getPh_num()
                );
            }
        }
        return  null;
    }

    //here I use the updated code
    public String expireUserSessions(String sessionId) {
        Optional<LoginSession> loginSession = loginSessionRepo.findById(sessionId);
        if(loginSession.isPresent()){
            String userId = loginSession.get().getUserId();
            List<LoginSession> userSessions = loginSessionRepo.getSessionsByUserId(userId);
            if (!userSessions.isEmpty()) {
                for (LoginSession session : userSessions) {
                    loginSessionRepo.deleteSessionRaw(session.getSessionId());
                }
            }
            return "Successfully deleted";
        }
        return "no session available";
    }

    //check session expiration
    public boolean checkSession(String username) {
        Optional<LoginSession> loginSession = loginSessionRepo.findByUserId(username);
        if (loginSession.isPresent()) {
            if (loginSession.get().getExpiresAt().before(new Timestamp(System.currentTimeMillis()))) {
                loginSessionRepo.deleteExpiredSessions();
                return false;
            }
            return true;
        }
        return false;
    }

    public String getSession_id(String username) {
        Optional<LoginSession> loginSession = loginSessionRepo.findByUserId(username);
        if (loginSession.isPresent()) {
            return loginSession.get().getSessionId();
        }
        return "not found session";
    }

    public String getUsernameFromSession(String Session_Id){
        Optional<LoginSession> loginSession = loginSessionRepo.findById(Session_Id);
        if(loginSession.isPresent()){
            return loginSession.get().getUserId();
        }
        return "user cannot be find";
    }
    //here you should give the username or email address
    public String deleteUserSessions(String username) {

        List<LoginSession> sessions = loginSessionRepo.getSessionsByUserId(username);

        List<String> sessionIds = sessions.stream()
                .map(LoginSession::getSessionId)
                .collect(Collectors.toList());

        if (!sessionIds.isEmpty()) {
            loginSessionRepo.deleteSessionsByIds(sessionIds);
            return "Successfully deleted";
        }
        return "no session available";
    }
}
