package com.masai.service.impl;

import com.masai.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendActivationEmail(String to, String firstName, String activationToken) {
        String link = frontendUrl + "/auth/activate?token=" + activationToken;
        String html = "<div style='font-family:Arial,sans-serif;max-width:600px;margin:0 auto'>"
                + "<h2 style='color:#1a73e8'>Welcome to Book My Bus, " + firstName + "!</h2>"
                + "<p>Thank you for registering. Please click the button below to activate your account.</p>"
                + "<p>This link will expire in <strong>24 hours</strong>.</p>"
                + "<a href='" + link + "' style='display:inline-block;background:#1a73e8;color:#ffffff;"
                + "padding:12px 28px;text-decoration:none;border-radius:4px;font-size:15px;margin:16px 0'>"
                + "Activate Account</a>"
                + "<p style='color:#666;font-size:13px'>If the button doesn't work, copy and paste this link:</p>"
                + "<p style='color:#1a73e8;font-size:13px'>" + link + "</p>"
                + "<hr style='border:none;border-top:1px solid #eee;margin:24px 0'>"
                + "<p style='color:#999;font-size:12px'>If you did not create an account, please ignore this email.</p>"
                + "</div>";
        sendEmail(to, "Activate your Book My Bus account", html);
    }

    @Override
    public void sendPasswordResetEmail(String to, String firstName, String resetToken) {
        String link = frontendUrl + "/auth/reset-password?token=" + resetToken;
        String html = "<div style='font-family:Arial,sans-serif;max-width:600px;margin:0 auto'>"
                + "<h2 style='color:#1a73e8'>Password Reset Request</h2>"
                + "<p>Hi " + firstName + ", we received a request to reset your password.</p>"
                + "<p>Click the button below to set a new password. This link expires in <strong>1 hour</strong>.</p>"
                + "<a href='" + link + "' style='display:inline-block;background:#e53935;color:#ffffff;"
                + "padding:12px 28px;text-decoration:none;border-radius:4px;font-size:15px;margin:16px 0'>"
                + "Reset Password</a>"
                + "<p style='color:#666;font-size:13px'>If the button doesn't work, copy and paste this link:</p>"
                + "<p style='color:#1a73e8;font-size:13px'>" + link + "</p>"
                + "<hr style='border:none;border-top:1px solid #eee;margin:24px 0'>"
                + "<p style='color:#999;font-size:12px'>If you did not request a password reset, please ignore this email. Your password will remain unchanged.</p>"
                + "</div>";
        sendEmail(to, "Reset your Book My Bus password", html);
    }

    @Override
    public void sendBookingConfirmationEmail(String to, String firstName, String bookingNumber,
                                             String busName, String source, String destination,
                                             String journeyDate, int seatCount, String totalFare) {
        String html = "<div style='font-family:Arial,sans-serif;max-width:600px;margin:0 auto'>"
                + "<h2 style='color:#1a73e8'>Booking Confirmed!</h2>"
                + "<p>Hi " + firstName + ", your booking has been confirmed.</p>"
                + "<div style='background:#f5f5f5;border-radius:8px;padding:20px;margin:16px 0'>"
                + "<table style='width:100%;border-collapse:collapse'>"
                + "<tr><td style='padding:6px 0;color:#666'>Booking Number</td>"
                + "    <td style='padding:6px 0;font-weight:bold'>" + bookingNumber + "</td></tr>"
                + "<tr><td style='padding:6px 0;color:#666'>Bus</td>"
                + "    <td style='padding:6px 0'>" + busName + "</td></tr>"
                + "<tr><td style='padding:6px 0;color:#666'>Route</td>"
                + "    <td style='padding:6px 0'>" + source + " → " + destination + "</td></tr>"
                + "<tr><td style='padding:6px 0;color:#666'>Journey Date</td>"
                + "    <td style='padding:6px 0'>" + journeyDate + "</td></tr>"
                + "<tr><td style='padding:6px 0;color:#666'>Seats</td>"
                + "    <td style='padding:6px 0'>" + seatCount + "</td></tr>"
                + "<tr><td style='padding:6px 0;color:#666'>Total Fare</td>"
                + "    <td style='padding:6px 0;font-weight:bold;color:#1a73e8'>₹" + totalFare + "</td></tr>"
                + "</table></div>"
                + "<p style='color:#666;font-size:13px'>Please carry a valid ID on your journey. Have a safe trip!</p>"
                + "<hr style='border:none;border-top:1px solid #eee;margin:24px 0'>"
                + "<p style='color:#999;font-size:12px'>Book My Bus — Comfortable journeys, every time.</p>"
                + "</div>";
        sendEmail(to, "Booking Confirmed — " + bookingNumber, html);
    }

    @Override
    public void sendBookingCancellationEmail(String to, String firstName, String bookingNumber,
                                              String busName, String journeyDate, String totalFare) {
        String html = "<div style='font-family:Arial,sans-serif;max-width:600px;margin:0 auto'>"
                + "<h2 style='color:#e53935'>Booking Cancelled</h2>"
                + "<p>Hi " + firstName + ", your booking has been cancelled and a refund has been initiated.</p>"
                + "<div style='background:#f5f5f5;border-radius:8px;padding:20px;margin:16px 0'>"
                + "<table style='width:100%;border-collapse:collapse'>"
                + "<tr><td style='padding:6px 0;color:#666'>Booking Number</td>"
                + "    <td style='padding:6px 0;font-weight:bold'>" + bookingNumber + "</td></tr>"
                + "<tr><td style='padding:6px 0;color:#666'>Bus</td>"
                + "    <td style='padding:6px 0'>" + busName + "</td></tr>"
                + "<tr><td style='padding:6px 0;color:#666'>Journey Date</td>"
                + "    <td style='padding:6px 0'>" + journeyDate + "</td></tr>"
                + "<tr><td style='padding:6px 0;color:#666'>Refund Amount</td>"
                + "    <td style='padding:6px 0;font-weight:bold;color:#43a047'>₹" + totalFare + "</td></tr>"
                + "</table></div>"
                + "<p style='color:#666;font-size:13px'>Refunds are typically processed within 5-7 business days.</p>"
                + "<hr style='border:none;border-top:1px solid #eee;margin:24px 0'>"
                + "<p style='color:#999;font-size:12px'>Book My Bus — Comfortable journeys, every time.</p>"
                + "</div>";
        sendEmail(to, "Booking Cancelled — " + bookingNumber, html);
    }

    private void sendEmail(String to, String subject, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            mailSender.send(message);
            log.info("Email sent to {}: {}", to, subject);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
