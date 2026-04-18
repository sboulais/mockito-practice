package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class Test15Answers {

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
    void should_CalculateCorrectPrice() {

        // Nouveau depuis mockito 3.4
        try (MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)) {

            // given
            BookingRequest bookingRequest = new BookingRequest("2",
                    LocalDate.of(2026, Month.MAY,4),
                    LocalDate.of(2026, Month.MAY,7),
                    2,
                    false);

            Double expected = 300.0 * 0.8;

            // Patch la méthode toEuro() pour qu'elle retourne 80% de la valeur.
            mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble()))
                    .thenAnswer(invocation -> (double) invocation.getArgument(0) * 0.8);

            // when
            double actual = bookingService.calculatePriceEuro(bookingRequest);

            // then
            assertEquals(expected, actual);
        }
    }
}