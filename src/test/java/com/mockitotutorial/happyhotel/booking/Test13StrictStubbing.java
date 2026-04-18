package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test13StrictStubbing {

    @InjectMocks
    private BookingService bookingService;
    @Mock
    private PaymentService paymentServiceMock;
    @Mock
    private RoomService roomServiceMock;
    @Mock
    private BookingDAO bookingDAOMock;
    @Mock
    private MailSender mailSenderMock;
    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

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
                false);

        // Un stub est un remplaçant fictif d'une méthode réelle (un patch) :
        // when(paymentServiceMock.pay(any(), anyDouble())).thenReturn("1");
        lenient().when(paymentServiceMock.pay(any(), anyDouble())).thenReturn("1");

        // when
        bookingService.makeBooking(bookingRequest);

        // then

        // Si la méthode pay() n'est pas appelée, une exeception UnnecessaryStubbingException
        // est déclenchée. Le mode strict stubbing est activé par défaut avec
        // @ExtendWith(MockitoExtension.class).

        // On peut contourner l'exception avec lenient() devant la méthode when()
        // mais il ne faut pas en abuser non plus.
    }
}