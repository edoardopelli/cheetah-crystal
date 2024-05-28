package org.cheetah.crystal.controllers.auth;
import org.cheetah.crystal.core.services.auth.UserService;
import org.cheetah.crystal.dtos.auth.User;
import org.cheetah.crystal.rest.requests.PinConfirmationRequest;
import org.cheetah.crystal.rest.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/confirm-pin")
public class PinConfirmationController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> confirmPin(@RequestBody PinConfirmationRequest request) {
        User user = userService.confirmPin(request.getPin());
        UserResponse response = new UserResponse();
        response.setUser(user);
        return ResponseEntity.ok(response);
    }
}