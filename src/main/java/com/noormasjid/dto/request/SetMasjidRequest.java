package com.noormasjid.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetMasjidRequest {

    @NotNull
    private Long masjidId;
}
