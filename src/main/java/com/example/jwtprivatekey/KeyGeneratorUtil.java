package com.example.jwtprivatekey;


import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class KeyGeneratorUtil {

    @Value("classpath:rsa_private_key_pkcs8.pem")
    private Resource privateKeyResource;
    @Value("classpath:rsa_public_key.pem")
    private Resource publicKeyResource;
//for RSA
//    @Bean
//    public PublicKey publicKeyString() throws IOException {
//        try (InputStream inputStream = publicKeyResource.getInputStream()) {
//            // Read the public key content from the input stream
//            byte[] keyBytes = inputStream.readAllBytes();
//
//            // Remove the header and footer lines if present
//            String publicKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
//                    .replace("-----BEGIN PUBLIC KEY-----", "")
//                    .replaceAll(System.lineSeparator(), "")
//                    .replace("-----END PUBLIC KEY-----", "");
//
//            // Decode the Base64 encoded bytes
//            byte[] decodedBytes = Base64.getDecoder().decode(publicKeyPEM);
//
//            // Create PublicKey instance
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedBytes);
//            return keyFactory.generatePublic(keySpec);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (InvalidKeySpecException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Bean
    public PublicKey publicKeyString() throws IOException {
        try (InputStream inputStream = publicKeyResource.getInputStream()) {
            // Read the public key content from the input stream
            byte[] keyBytes = inputStream.readAllBytes();

            // Remove the header and footer lines if present
            String publicKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", ""); // Remove all whitespace characters

            // Decode the Base64 encoded bytes
            byte[] decodedBytes = Base64.getDecoder().decode(publicKeyPEM);

            // Create PublicKey instance
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedBytes);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such algorithm: EC", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Invalid key spec for EC public key", e);
        }
    }

    //    @Bean
//    public PrivateKey privateKeyString() throws IOException {
//        try (InputStream inputStream = privateKeyResource.getInputStream()) {
//            // Read the private key content from the input stream
//            byte[] keyBytes = inputStream.readAllBytes();
//
//            // Remove the header and footer lines if present
//            String privateKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
//                    .replace("-----BEGIN PRIVATE KEY-----", "")
//                    .replaceAll(System.lineSeparator(), "")
//                    .replace("-----END PRIVATE KEY-----", "");
//
//            // Decode the Base64 encoded bytes
//            byte[] decodedBytes = Base64.getDecoder().decode(privateKeyPEM);
//
//            // Create PrivateKey instance
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedBytes);
//            return keyFactory.generatePrivate(keySpec);
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    @Bean
//    public PrivateKey privateKeyString() throws IOException {
//        try (InputStream inputStream = privateKeyResource.getInputStream()) {
//            // Read the private key content from the input stream
//            byte[] keyBytes = inputStream.readAllBytes();
//
//            // Remove the header and footer lines if present
//            String privateKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
//                    .replace("-----BEGIN PRIVATE KEY-----", "")
//                    .replace("-----END PRIVATE KEY-----", "")
//                    .replaceAll("\\s", ""); // Remove all whitespace characters
//
//            // Decode the Base64 encoded bytes
//            byte[] decodedBytes = Base64.getDecoder().decode(privateKeyPEM);
//
//            // Create PrivateKey instance
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedBytes);
//            return keyFactory.generatePrivate(keySpec);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("No such algorithm: EC", e);
//        } catch (InvalidKeySpecException e) {
//            throw new RuntimeException("Invalid key spec for EC private key", e);
//        }
//    }


    public PrivateKey privateKeyString() throws IOException {
        try (InputStream inputStream = privateKeyResource.getInputStream()) {
            byte[] keyBytes = inputStream.readAllBytes();

            // Extract the private key content between the headers and decode Base64
            String privateKeyPEM = new String(keyBytes, StandardCharsets.UTF_8);
            String privateKeyPEMContent = privateKeyPEM
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decodedBytes = Base64.getDecoder().decode(privateKeyPEMContent);

            // Create the EC PrivateKey instance
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedBytes, SignatureAlgorithm.PS384.getJcaName());
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such algorithm: EC", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Invalid key spec for EC private key", e);
        }
    }
}

