package com.example.Login.Service.Booking;

import com.example.Login.Dto.Request.Booking.BookingReq;
import com.example.Login.Entity.Booking.BookingEntity;
import com.example.Login.Repository.Booking.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private final BookingRepo bookingRepo;

    public BookingService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public String bookingCode(){
        return UUID.randomUUID().toString();
    }

    public String registerBooking(BookingReq bookingReq) {
        BookingEntity bookingEntity = new BookingEntity();
            bookingEntity.setBookingCode(bookingCode());
            bookingEntity.setAvailability("available");
            bookingEntity.setNumberOfGuest(bookingReq.getNumberOfGuest());
            bookingEntity.setNumberOfChild(bookingReq.getNumberOfChild());
            bookingEntity.setBookingTime(LocalDateTime.now());
            bookingEntity.setGuestName(bookingReq.getGuestName());
            bookingEntity.setGuestEmail(bookingReq.getGuestEmail());
            bookingEntity.setGuestPhNumber(bookingReq.getGuestPhNumber());
            bookingEntity.setCheckInD(bookingReq.getCheckInD());
            bookingEntity.setCheckOutD(bookingReq.getCheckOutD());
            bookingEntity.setState(bookingReq.getState());
            bookingEntity.setRoomId(bookingReq.getRoomId());
            bookingEntity.setCountry(bookingReq.getCountry());
            bookingEntity.setCity(bookingReq.getCity());
            bookingEntity.setZipcode(bookingReq.getZipcode());
            bookingEntity.setArrivalTime(bookingReq.getArrivalTime());
            bookingEntity.setAdditionalRequest(bookingReq.getAdditionalRequest());
            bookingEntity.setFullAddress(bookingReq.getFullAddress());

        bookingRepo.save(bookingEntity);
        return "Successful Booking";
    }
}
