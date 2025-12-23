package com.bank.Accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "Error Response",
        description = "Schema to hold Error Responses information"
)
public class ErrorResponseDto {

    private String apiPath;

    private HttpStatusCode errorCode;
    private String errorMsg;
    private LocalDateTime errorTime;
}
