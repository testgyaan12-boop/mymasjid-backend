package com.noormasjid.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {

    private String name;
    private String phone;
    private String avatar;
}
