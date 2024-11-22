package com.example.Login.Service.RoomPackage;

import com.example.Login.Dto.Request.RoomPackageReq.RoomPackageReq;
import com.example.Login.Dto.Response.RoomPackageRes.RoomPackageRes;
import com.example.Login.Entity.RoomPackages.RoomPackages;
import com.example.Login.Repository.RoomPackage.RoomPackageRepo;
import com.example.Login.Service.Property.Room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomPackageService {

    @Autowired
    private final RoomPackageRepo roomPackageRepo;

    private final RoomService roomService;

    public RoomPackageService(RoomPackageRepo roomPackageRepo, RoomService roomService) {
        this.roomPackageRepo = roomPackageRepo;
        this.roomService = roomService;
    }

    public String addRoomPackage(RoomPackageReq roomPackageReq, MultipartFile file){
        if (file.isEmpty()) {
            return "File is missing. Please upload an image.";
        }
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            return "Invalid file type. Please upload an image.";
        }
        if(roomPackageReq  != null){
            RoomPackages roomPackages = new RoomPackages();
                roomPackages.setPackageName(roomPackageReq.getPackageName());
                roomPackages.setStartDate(roomPackageReq.getStartDate());
                roomPackages.setEndDate(roomPackageReq.getEndDate());
                roomPackages.setDescription(roomPackageReq.getDescription());
                roomPackages.setRoomPackageImageLink(roomService.saveFile(file, "src/main/resources/static/roomsPackages/"));
            roomPackageRepo.save(roomPackages);
            return "Successful";
        }
        return "Id Exist";
    }

    public String deleteRoomPackage(Integer propertyId){
        Optional<RoomPackages> roomPackages = roomPackageRepo.findById(propertyId);
        if(roomPackages.isPresent()){
            roomPackageRepo.deleteRoom(propertyId);
            if(deleteFileRoomPackage(propertyId)){
                return "successfully delete the room";
            }
        }
        return "false";
    }

    public boolean deleteFileRoomPackage(Integer propertyId) {
        RoomPackages roomPackages = roomPackageRepo.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Room not found with property_id: " + propertyId));

        String filePath = roomPackages.getRoomPackageImageLink();
        File file = new File(filePath);

        if (file.exists() && file.delete()) {
            System.out.println("File deleted successfully: " + filePath);
            return true;
        } else {
            System.out.println("File not found or failed to delete: " + filePath);
            return false;
        }
    }

    public List<RoomPackageRes> getAllRooms() {
        List<RoomPackages> roomPackages = roomPackageRepo.findAll();
        return roomPackages.stream().map(this::convertToRoomPackageRes).collect(Collectors.toList());
    }

    private RoomPackageRes convertToRoomPackageRes(RoomPackages roomPackages) {
        RoomPackageRes roomPackageRes = new RoomPackageRes();
            roomPackageRes.setPackageName(roomPackages.getPackageName());
            roomPackageRes.setStartDate(roomPackages.getStartDate() );
            roomPackageRes.setEndDate(roomPackages.getEndDate());
            roomPackageRes.setDescription(roomPackages.getDescription());
            roomPackageRes.setDiscountPrice(roomPackages.getDiscountPrice());
            roomPackageRes.setRoomPackageImageLink(roomPackages.getRoomPackageImageLink());

        // Convert file path to URL
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/roomsPackage/") // Direct path to rooms folder
                .path(new File(roomPackages.getRoomPackageImageLink()).getName()) // Get only the filename
                .toUriString();
        roomPackageRes.setRoomPackageImageLink(imageUrl);

        return roomPackageRes;
    }
}
