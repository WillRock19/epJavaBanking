package redes;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordManager {
	public String EncodePassword(String password) {
		return Base64.getEncoder().encodeToString(password.getBytes());
	}

	public String DecodePassword(String password) {
		byte[] decodedValue = Base64.getDecoder().decode(password);

		return new String(decodedValue, StandardCharsets.UTF_8);
	}
}