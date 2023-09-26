import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class PublicPrivateKeys {


    PublicPrivateKeys(){
        super();
    }
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.ES512);
        String encodedPublicKeyBase64 = Encoders.BASE64.encode(keyPair.getPublic().getEncoded());
        String encodedPrivateKeyBase64 = Encoders.BASE64.encode(keyPair.getPrivate().getEncoded());

        byte[] encodedPublicKeyBytes = Decoders.BASE64.decode(encodedPublicKeyBase64);
        byte[] encodedPrivateKeyBytes = Decoders.BASE64.decode(encodedPrivateKeyBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedPublicKeyBytes));
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedPrivateKeyBytes));

        String jwt = Jwts.builder().setIssuer("example").claim("foo", "bar").signWith(privateKey).compact();
        JwtParser parser = Jwts.parserBuilder().setSigningKey(publicKey).build();
        Jws<Claims> jws = parser.parseClaimsJws(jwt);
        System.out.println(jws); // header={alg=ES512},body={iss=example, foo=bar},signature=AKoAps_bw...
    }
}