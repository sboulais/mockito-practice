package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

class Test07VerifyingBehaviour {

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
    void should_InvokePayment_When_Prepaid() {

        // Dans ce test, on vérifie que la méthode pay() est bien appelée une
        // seule fois depuis makeBooking. Le prix à payer est égal à :
        // 3 nuits * 2 personnes * 50 USD par nuit

        // given
        BookingRequest bookingRequest = new BookingRequest("2",
                LocalDate.of(2026, Month.MAY,4),
                LocalDate.of(2026, Month.MAY,7),
                2,
                true);

        // when
        bookingService.makeBooking(bookingRequest);

        // then
        verify(paymentServiceMock, times(1)).pay(bookingRequest, 3 * 2 * 50);
        verifyNoMoreInteractions(paymentServiceMock);
    }

    @Test
    void should_NotInvokePayment_When_NotPrepaid() {

        // Dans ce test, on vérifie que la méthode pay() n'est jamais
        // appelée depuis makeBooking.

        // given
        BookingRequest bookingRequest = new BookingRequest("2",
                LocalDate.of(2026, Month.MAY,4),
                LocalDate.of(2026, Month.MAY,7),
                2,
                false);

        // when
        bookingService.makeBooking(bookingRequest);

        // then
        verify(paymentServiceMock, never()).pay(any(), anyDouble());
    }
}