package org.cheetah.crystal.controllers.auth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.cheetah.crystal.core.services.auth.UserService;
import org.cheetah.crystal.core.utils.CrystalSessionManager;
import org.cheetah.crystal.dtos.auth.User;
import org.cheetah.crystal.dtos.auth.ValidateCodeDto;
import org.cheetah.crystal.rest.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@Autowired
	private GoogleAuthenticator gAuth;
	

	@PostMapping(produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> registerUser(@RequestBody User user, HttpServletResponse response)
			throws WriterException, IOException {
		CrystalSessionManager.setUser(user);
		UserResponse userResponse = new UserResponse();
		userResponse.setUser(user);
		
		String username = user.getUsername();
		final GoogleAuthenticatorKey key = gAuth.createCredentials(username);
		user = userService.registerNewUser(user);

		// I've decided to generate QRCode on backend site
		QRCodeWriter qrCodeWriter = new QRCodeWriter();

		String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("my-demo", username, key);

		BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);

		response.setContentType("image/png");
		// Simple writing to outputstream
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
		outputStream.close();

		user.setPassword(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(outputStream.toByteArray());
	}

	@PostMapping("/validate/key")
	public ResponseEntity<Boolean> validateKey(@RequestBody ValidateCodeDto body) {
		boolean value = gAuth.authorizeUser(body.getUsername(), Integer.parseInt(body.getCode()));
		return ResponseEntity.ok(value);
	}

}