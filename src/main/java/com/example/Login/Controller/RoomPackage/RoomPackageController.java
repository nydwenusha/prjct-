package com.example.Login.Controller.RoomPackage;

import com.example.Login.Dto.Request.RoomPackageReq.RoomPackageReq;
import com.example.Login.Dto.Response.Property.Room.RoomRes;
import com.example.Login.Dto.Response.RoomPackageRes.RoomPackageRes;
import com.example.Login.Service.RoomPackage.RoomPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/RoomPackage")
@CrossOrigin
public class RoomPackageController {

    @Autowired
    private final RoomPackageService roomPackageService;

    public RoomPackageController(RoomPackageService roomPackageService) {
        this.roomPackageService = roomPackageService;
    }

    @PostMapping("/addRoomPackage")
    public ResponseEntity<String> addRoomPackage(@RequestBody RoomPackageReq roomPackageReq, MultipartFile file){
        String check = roomPackageService.addRoomPackage(roomPackageReq, file);
        if(check.equals("Successful")){
            return ResponseEntity.ok("added successful");
        }
        return ResponseEntity.ok("Error Has been occurred");
    }

    @PostMapping("/removeRoomPackage")
    public ResponseEntity<String> removeRoomPackage(){
        return ResponseEntity.ok("removed");
    }

    @PostMapping("/updateRoomPackage")
    public ResponseEntity<String> updateRoomPackage(){
        return ResponseEntity.ok("successfully update the room");
    }

    @GetMapping("/getAllRoomPackage")
    public ResponseEntity<List<RoomPackageRes>> getAllImages() {
        List<RoomPackageRes> images = roomPackageService.getAllRooms();
        return ResponseEntity.ok(images);
    }
}
