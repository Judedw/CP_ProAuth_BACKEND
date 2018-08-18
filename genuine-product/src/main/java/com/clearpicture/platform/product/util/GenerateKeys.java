package com.clearpicture.platform.product.util;


import org.springframework.stereotype.Component;

@Component
public class GenerateKeys {

    /*@Autowired
    private UserKeyService userKeyService;

    @Autowired
    private PlatformConfigProperties configs;

	private KeyPairGenerator keyGen;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private static final String PUBLICKEY_PREFIX    = "-----BEGIN PUBLIC KEY-----";
	private static final String PUBLICKEY_POSTFIX   = "-----END PUBLIC KEY-----";
	private static final String PRIVATEKEY_PREFIX   = "-----BEGIN RSA PRIVATE KEY-----";
	private static final String PRIVATEKEY_POSTFIX  = "-----END RSA PRIVATE KEY-----";


	public GenerateKeys(){}
	private void generateSecureKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
		this.keyGen = KeyPairGenerator.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");//Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		this.keyGen.initialize(1024);
	}

	private void createKeys() {
		KeyPair pair = this.keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	private PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	private PublicKey getPublicKey() {
		return this.publicKey;
	}


	public String keyGenerateAndReturnPublicKey(String appId) {
		String publicKeyPEM = null;
		String privateKeyPEM;
		System.out.println("main method of generator");
		try {
			this.generateSecureKeys();
			this.createKeys();

			// THIS IS PEM:
	        publicKeyPEM = DatatypeConverter.printBase64Binary(this.getPublicKey().getEncoded()).replaceAll("(.{64})", "$1\n") ;
	        privateKeyPEM = DatatypeConverter.printBase64Binary(this.getPrivateKey().getEncoded()).replaceAll("(.{64})", "$1\n");
            userKeyService.saveKeysInDb(appId,publicKeyPEM,privateKeyPEM);
	        //this.saveKeysInDb(appId,publicKeyPEM,privateKeyPEM);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return publicKeyPEM;
	}
	public PrivateKey readPrivateKey(String appId)
			throws IOException, GeneralSecurityException {
		PrivateKey key = null;
		UserKey userKey = userKeyService.getPrivateKeyForAppId(configs.getKeyFile().getAppId());
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(userKey.getPrivateKey());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        key = kf.generatePrivate(spec);
		return key;
	}*/

}
