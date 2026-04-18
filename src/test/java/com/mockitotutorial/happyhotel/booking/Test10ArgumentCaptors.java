package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class Test10ArgumentCaptors {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;
    private ArgumentCaptor<Double> doubleCaptor;
    private ArgumentCaptor<BookingRequest> bookingRequestCaptor;

    @BeforeEach
    void setUp() {
        paymentServiceMock = mock(PaymentService.class);
        roomServiceMock = mock(RoomService.class);
        bookingDAOMock = mock(BookingDAO.class);
        mailSenderMock = mock(MailSender.class);
        bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
        doubleCaptor = ArgumentCaptor.forClass(Double.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void should_PayCorrectPrice_When_InputOK() {

        // given
        BookingRequest bookingRequest = new BookingRequest("2",
                LocalDate.of(2026, Month.MAY,4),
                LocalDate.of(2026, Month.MAY,7),
                2,
                true);

        // when
        bookingService.makeBooking(bookingRequest);

        // then
        verify(paymentServiceMock, times(1)).pay(eq(bookingRequest), doubleCaptor.capture());
        double capturedArgument = doubleCaptor.getValue();
        assertEquals(300.0, capturedArgument);
    }

    @Test
    void should_PayCorrectPrices_When_MultipleCalls() {

        // given
        BookingRequest bookingRequest1 = new BookingRequest("2",
                LocalDate.of(2026, Month.MAY,4),
                LocalDate.of(2026, Month.MAY,7),
                1,
                true);

        BookingRequest bookingRequest2 = new BookingRequest("2",
                LocalDate.of(2026, Month.MAY,4),
                LocalDate.of(2026, Month.MAY,5),
                2,
                true);

        List<Double> expected = Arrays.asList(3 * 1 * 50.0, 1 * 2 * 50.0);

        // when
        bookingService.makeBooking(bookingRequest1);
        bookingService.makeBooking(bookingRequest2);

        // then
        verify(paymentServiceMock, times(2)).pay(any(), doubleCaptor.capture());
        assertEquals(expected, doubleCaptor.getAllValues());
    }
}