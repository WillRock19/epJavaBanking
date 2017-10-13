package redes;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/*USED AS INFORMATIONAL: 
 * 	https://stackoverflow.com/questions/18787419/ssl-socket-connection
 *	https://www.javacodegeeks.com/2013/06/java-security-tutorial-step-by-step-guide-to-create-ssl-connection-and-certificates.html
*/

public class SSLConnection 
{
	  public  static SSLContext createSSLContext(String keyStoreFile, String keyStoreFilePassword) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException, KeyManagementException, NoSuchProviderException 
      {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(keyStoreFile), keyStoreFilePassword.toCharArray());

        KeyManager[] km = createKeyManager(keyStore, keyStoreFilePassword);
        
        TrustManager[] tm = createTrustManager(keyStore);

        // Initialize SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(km, tm, null);

        return sslContext;
      }
	  
	  private static KeyManager[] createKeyManager(KeyStore keyStore, String keyStoreFilePassword) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException
	  {
		  	KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
	        keyManagerFactory.init(keyStore, keyStoreFilePassword.toCharArray());
	        
	        return keyManagerFactory.getKeyManagers();
	  }
	  
	  private static TrustManager[] createTrustManager(KeyStore keyStore) throws NoSuchAlgorithmException, KeyStoreException
	  {
		  	TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
	        trustManagerFactory.init(keyStore);
	        
	        return trustManagerFactory.getTrustManagers();
	  }
}