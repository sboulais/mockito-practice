package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.mockito.Mockito.*;

/**
 * Contrairement à un mock, qui est une coquille vide ne contenant aucune logique réelle, un spy (espion)
 * est un "wrapper" autour d'une instance réelle d'une classe.
 * - Un mock renvoie des valeurs par défaut (ex: null ou 0) à moins que vous ne définissiez un comportement.
 * - Un spy appelle les vraies méthodes de l'objet, à moins que vous ne décidiez d'en simuler certaines
 */
class Test08Spies {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOSpy;
    private MailSender mailSenderMock;

    @BeforeEach
    void setUp() {
        paymentServiceMock = mock(PaymentService.class);
        roomServiceMock = mock(RoomService.class);
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
        // Vérifie que la méthode save de la classe BookingDAO est bien appelée
        // par makeBooking
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

        /**
         * La méthode cancelBooking essaiera d'obtenir réellement un objet
         * BookingRequest. Ce n'est pas ce que nous voulons, il faut
         * donc changer le comportement de l'espion BookingDAO :
         **/
        doReturn(bookingRequest).when(bookingDAOSpy).get(bookingId);

        // when
        bookingService.cancelBooking(bookingId);

        // then
        verify(bookingDAOSpy).delete(bookingId);
    }
}