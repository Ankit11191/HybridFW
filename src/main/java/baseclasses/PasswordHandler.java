package baseclasses;

import java.util.Base64;

public class PasswordHandler {
	
	public static String decode(String ePassword)
	{
		ePassword=ePassword.replaceAll("[^\\w]", "").replace("EncryptedPassword", "");
		byte[] decodedBytes = Base64.getDecoder().decode(ePassword.getBytes());
		return new String(decodedBytes);
	}
}
