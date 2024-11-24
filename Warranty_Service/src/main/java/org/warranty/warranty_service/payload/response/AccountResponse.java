package org.warranty.warranty_service.payload.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String userId;

//    private String password;

    private LocalDateTime creationDateTime;

    private LocalDateTime expirationDateTime;

    private boolean active;

    private String region;

    private Long contactNumber;
}
