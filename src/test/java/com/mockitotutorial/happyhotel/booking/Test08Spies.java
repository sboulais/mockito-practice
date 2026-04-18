package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

class Test08Spies {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private BookingDAO bookingDAOSpy;
    private MailSender mailSenderMock;

    @BeforeEach
    void setUp() {
        paymentServiceMock = mock(PaymentService.class);
        roomServiceMock = mock(RoomService.class);
        bookingDAOMock = mock(BookingDAO.class);
        bookingDAOSpy = spy(BookingDAO.class);
        mailSenderMock = mock(MailSender.class);
        bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOSpy, mailSenderMock);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void should_MakeBooking_When_InputOK() {

        // given
        BookingRequest bookingRequest = new BookingRequest("2",
                LocalDate.of(2026, Month.MAY,4),
                LocalDate.of(2026, Month.MAY,7),
                2,
                true);

        // when
        String id = bookingService.makeBooking(bookingRequest);

        // then
        // Vérifie que la méthode save du DAO booking est appelée
        verify(bookingDAOSpy).save(bookingRequest);
        System.out.println("Boooking ID = " + id);
    }

    @Test
    void should_CancelBooking_When_InputOK() {

        // given
        BookingRequest bookingRequest = new BookingRequest("2",
                LocalDate.of(2026, Month.MAY,4),
                LocalDate.of(2026, Month.MAY,7),
                2,
                true);

        bookingRequest.setRoomId("1.3");
        String bookingId = "1";

        // Je n'ai rien compris, à revoir !
        doReturn(bookingRequest).when(bookingDAOSpy).get(bookingId);

        // when
        bookingService.cancelBooking(bookingId);

        // then
    }
}