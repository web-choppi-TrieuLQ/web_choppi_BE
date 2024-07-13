package tlq.com.tlq_sale_management.controller;


import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import tlq.com.tlq_sale_management.dto.request.SystemRequest.AuthenticationRequest;
import tlq.com.tlq_sale_management.dto.request.SystemRequest.IntrospectRequest;
import tlq.com.tlq_sale_management.dto.request.SystemRequest.LogoutRequest;
import tlq.com.tlq_sale_management.dto.response.SystemRespone.ApiResponse;
import tlq.com.tlq_sale_management.dto.response.SystemRespone.AuthenticationResponse;
import tlq.com.tlq_sale_management.dto.response.SystemRespone.IntrospectResponse;
import tlq.com.tlq_sale_management.service.AuthenService.AuthenticationService;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("*")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        System.out.println("bat dau logout");
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }
}
