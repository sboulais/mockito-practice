package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Test03ReturningCustomValues {

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
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void should_CountAvailablePlaces_When_OneRoomAvailable() {
        // given
        when(roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room 1", 2)));
        int expected = 2;

        // when
        int actual = bookingService.getAvailablePlaceCount();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void should_CountAvailablePlaces_When_MultipleRoomsAvailable() {
        // given
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Room 1", 2));
        rooms.add(new Room("Room 2", 3));
        rooms.add(new Room("Room 3", 1));
        when(roomServiceMock.getAvailableRooms()).thenReturn(rooms);
        int expected = 6;

        // when
        int actual = bookingService.getAvailablePlaceCount();

        // then
        assertEquals(expected, actual);
    }
}