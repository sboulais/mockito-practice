package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Test05ThrowingException {

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
    void should_ThrowException_When_NoRoomAvailable() {

        // given
        BookingRequest bookingRequest = new BookingRequest("1",
                LocalDate.of(2026, Month.MAY,4),
                LocalDate.of(2026, Month.MAY,7),
                2,
                false);

        // La méthode findAvailableRoomId lancera une exception
        // pour cette demande de réservation :
        when(roomServiceMock.findAvailableRoomId(bookingRequest))
                .thenThrow(BusinessException.class);

        // when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        // then
        assertThrows(BusinessException.class, executable);
    }
}