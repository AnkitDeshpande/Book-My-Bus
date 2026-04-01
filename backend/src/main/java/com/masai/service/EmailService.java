package com.masai.service;

public interface EmailService {

    void sendActivationEmail(String to, String firstName, String activationToken);

    void sendPasswordResetEmail(String to, String firstName, String resetToken);

    void sendBookingConfirmationEmail(String to, String firstName, String bookingNumber,
                                     String busName, String source, String destination,
                                     String journeyDate, int seatCount, String totalFare);

    void sendBookingCancellationEmail(String to, String firstName, String bookingNumber,
                                      String busName, String journeyDate, String totalFare);
}
