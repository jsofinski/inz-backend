package com.pwr.inzynierka.service;


import org.springframework.stereotype.Service;

@Service
public class cryptoService {

    public static String encrypt(String publicKey, String message) {
        return "";
    }

    public static boolean verifySignature(String publicKey, String signature, String message) {
        return true;
    }

}
