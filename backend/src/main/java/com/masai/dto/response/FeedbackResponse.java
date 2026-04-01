package com.masai.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FeedbackResponse {

    private Long id;
    private Long bookingId;
    private String bookingNumber;
    private Long busId;
    private String busName;
    private Long userId;
    private String username;
    private Integer overallRating;
    private Integer driverRating;
    private Integer serviceRating;
    private String comment;
    private LocalDateTime createdAt;
}
