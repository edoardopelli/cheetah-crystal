package org.cheetah.crystal.core.services.auth;

import java.security.SecureRandom;

public class ServicesUtil {

	
    public static String generatePin() {
        SecureRandom random = new SecureRandom();
        int pin = random.nextInt(900000) + 100000; // Genera un PIN a 6 cifre
        return String.valueOf(pin);
    }

}
