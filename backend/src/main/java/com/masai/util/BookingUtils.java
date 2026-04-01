package com.masai.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class BookingUtils {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private BookingUtils() {}

    public static String generateBookingNumber() {
        String datePart = LocalDate.now().format(DATE_FMT);
        String randomPart = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return "BMB-" + datePart + "-" + randomPart;
    }
}
