package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class Test02DefaultReturnValues {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setUp() {
        paymentServiceMock = mock(PaymentService.class);
        roomServiceMock = mock(RoomService.class);
        bookingDAOMock = mock(BookingDAO.class);
        mailSenderMock = mock(MailSender.class);
        bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
        System.out.println("getAvailableRooms : " + roomServiceMock.getAvailableRooms());
        System.out.println("findAvailableRoomId : " + roomServiceMock.findAvailableRoomId(null));
        System.out.println("getRoomCount : " + roomServiceMock.getRoomCount());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void should_CountAvailablePlaces() {
        // given
        int expected = 0;

        // when
        int actual = bookingService.getAvailablePlaceCount();

        // then
        assertEquals(expected, actual);
    }
}