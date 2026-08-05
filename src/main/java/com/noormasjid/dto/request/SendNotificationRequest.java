package com.noormasjid.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendNotificationRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String message;

    private String type;

    private Long userId;

    private Long masjidId;
}
