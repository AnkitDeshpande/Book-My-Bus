package com.masai.controller;

import com.masai.dto.ApiResponse;
import com.masai.dto.request.FeedbackRequest;
import com.masai.dto.response.FeedbackResponse;
import com.masai.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Post-journey feedback management")
@SecurityRequirement(name = "bearerAuth")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    @Operation(summary = "Submit feedback for a completed booking")
    public ResponseEntity<ApiResponse<FeedbackResponse>> submitFeedback(@Valid @RequestBody FeedbackRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Feedback submitted successfully", feedbackService.submitFeedback(request)));
    }

    @GetMapping("/my")
    @Operation(summary = "Get current user's feedbacks")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getMyFeedbacks() {
        return ResponseEntity.ok(ApiResponse.success("Feedbacks fetched", feedbackService.getMyFeedbacks()));
    }

    @GetMapping("/bus/{busId}")
    @Operation(summary = "Get all feedbacks for a bus")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getFeedbacksByBus(@PathVariable Long busId) {
        return ResponseEntity.ok(ApiResponse.success("Feedbacks fetched", feedbackService.getFeedbacksByBus(busId)));
    }
}
