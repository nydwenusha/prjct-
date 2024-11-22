package com.example.Login.Service.Property.Room;

import com.example.Login.Dto.Request.Property.Room.RoomReq;
import com.example.Login.Dto.Response.Property.Room.RoomRes;
import com.example.Login.Entity.Property.Room.Room;
import com.example.Login.Repository.Property.Room.RoomRepo;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private final RoomRepo roomRepo;

    String baseUrl = "http://localhost:8080/";

    public RoomService(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;

    }
    public String saveFile( MultipartFile file, String folderPath) {
        // Define the path where the file will be saved

        File directory = new File(folderPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new RuntimeException("Failed to create directory: " + folderPath);
            }
        }

        // Save the file locally
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String fileName = folderPath + timestamp + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(fileName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save the file: " + fileName, e);
        }
        return fileName;
    }

    public String addRooms(RoomReq roomReq, MultipartFile file){

        if (file.isEmpty()) {
            return "File is missing. Please upload an image.";
        }
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            return "Invalid file type. Please upload an image.";
        }
        //String[] parts = saveFile(file).split("rooms/");
        if(roomObj(roomReq.getProperty_id()).isEmpty()){
            Room room = new Room();
                room.setPropertyId(roomReq.getProperty_id());
                room.setProperty_name(roomReq.getProperty_name());
                room.setProperty_desc(roomReq.getProperty_desc());
                room.setImage_link(saveFile(file, "src/main/resources/static/rooms/"));
            roomRepo.save(room);
            return "Successful";
        }
        return "Id Exist";
    }

    public Optional<Room> roomObj(String property_id){
        return roomRepo.findById(property_id);

    }

    public String updateRoomDetails(RoomReq roomReq, MultipartFile file){
        if(roomObj(roomReq.getProperty_id()).isPresent()){
            Room room = new Room();
            room.setPropertyId(roomReq.getProperty_id());
            room.setProperty_name(roomReq.getProperty_name());
            room.setProperty_desc(roomReq.getProperty_desc());
            room.setImage_link(saveFile(file, "src/main/resources/static/rooms/"));
            roomRepo.save(room);
            return "successfully updated the room";
        }
        return "cannot find the room by id";
    }

    public String deleteRoom(String propertyId){
        Optional<Room> room = roomRepo.findById(propertyId);
        if(room.isPresent()){
            roomRepo.deleteRoom(propertyId);
            deleteFile(propertyId);
            return "successfully delete the room";
        }
        return "false";
    }

    public boolean deleteFile(String propertyId) {
        Room room = roomRepo.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Room not found with property_id: " + propertyId));

        String filePath = room.getImage_link();
        File file = new File(filePath);

        if (file.exists() && file.delete()) {
            System.out.println("File deleted successfully: " + filePath);
            return true;
        } else {
            System.out.println("File not found or failed to delete: " + filePath);
            return false;
        }
    }

    public List<RoomRes> getAllRooms() {
        List<Room> rooms = (List<Room>) roomRepo.findAll();
        return rooms.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private RoomRes convertToDTO(Room room) {
        RoomRes dto = new RoomRes();
        dto.setRoomId(room.getPropertyId());
        dto.setProperty_desc(room.getProperty_desc());
        dto.setProperty_name(room.getProperty_name());

        // Convert file path to URL
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rooms/") // Direct path to rooms folder
                .path(new File(room.getImage_link()).getName()) // Get only the filename
                .toUriString();
        dto.setImageStream(imageUrl);

        return dto;
    }

    public Room getRoomById(String propertyId) {
        Optional<Room> room = roomRepo.findById(propertyId);
        if (room.isPresent()) {
            return room.get();
        } else {
            throw new RuntimeException("Room not found with ID: " + propertyId);
        }
    }

}
