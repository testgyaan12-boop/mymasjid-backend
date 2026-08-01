package com.noormasjid.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRoleRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String roleName;
}
