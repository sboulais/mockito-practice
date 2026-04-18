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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test12BDD {

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
    void should_CountAvailablePlaces_When_OneRoomAvailable() {
        // given
        // la méthode given est équivalente à when, pour des raisons de lisibilité et
        // respecter le pattern given/when/then
        given(roomServiceMock.getAvailableRooms())
                .willReturn(Collections.singletonList(new Room("Room 1", 2)));
        int expected = 2;

        // when
        int actual = bookingService.getAvailablePlaceCount();

        // then
        assertEquals(expected, actual);
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

        // then (== verify)
        then(paymentServiceMock)
                .should(times(1))
                .pay(bookingRequest, 3 * 2 * 50);
        verifyNoMoreInteractions(paymentServiceMock);
    }
}