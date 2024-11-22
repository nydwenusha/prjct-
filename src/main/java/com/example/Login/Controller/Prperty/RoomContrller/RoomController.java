package com.example.Login.Controller.Prperty.RoomContrller;

import com.example.Login.Dto.Request.Property.Room.RoomReq;
import com.example.Login.Dto.Response.Property.Room.RoomRes;
import com.example.Login.Entity.Property.Room.Room;
import com.example.Login.Service.Property.Room.RoomService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(value = "/api/Property")
@CrossOrigin
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping(value = "/add/Room", consumes = "multipart/form-data")
    public ResponseEntity<String> addRoom(@ModelAttribute RoomReq roomReq, @RequestParam("file") MultipartFile file) {
        String result = roomService.addRooms(roomReq, file);
        if(result.equals("Successful")){
            return ResponseEntity.ok("Successful add the room");
        }
        return ResponseEntity.ok("Successful add");
    }

    @GetMapping("/deleteRoomImage/{id}")
    public ResponseEntity<String> remove_image(@PathVariable("id") String property_id) {
        boolean check = roomService.deleteFile(property_id);
        if(check){
          return ResponseEntity.ok("Successfully removed");
        }
        return ResponseEntity.ok("Error has been occur");
    }

    @GetMapping("/deleteRoom/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable("id")  String property_id){
        String check = roomService.deleteRoom(property_id);
        if(check.equals("successfully delete the room")){
            return ResponseEntity.ok("delete successful");
        }
        return  ResponseEntity.ok("cannot delete the room or not found by the id ");
    }

    @PostMapping("/updateRoom")
    public ResponseEntity<String> updateRoom(@ModelAttribute RoomReq roomReq, @RequestParam("file") MultipartFile file) {
        String check = roomService.updateRoomDetails(roomReq,file);
        if(check.equals("successfully updated the room")){
            return ResponseEntity.ok("successfully updated the room");
        }
        return ResponseEntity.ok("cannot update the data");
    }


    @GetMapping("/getAllRooms")
    public ResponseEntity<List<RoomRes>> getAllImages() {
        List<RoomRes> images = roomService.getAllRooms();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable("id") String propertyId) {
        return roomService.getRoomById(propertyId);
    }

}
