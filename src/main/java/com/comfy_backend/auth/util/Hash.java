package com.comfy_backend.auth.util;


import at.favre.lib.crypto.bcrypt.BCrypt;

public class Hash {

    public String createHash(String cipherText){
    return BCrypt
            .withDefaults()
            .hashToString(12, cipherText.toCharArray());
    }

    public boolean verifyHash (String cipherText, String hash){
        return BCrypt
                .verifyer()
                .verify(cipherText.toCharArray(), hash)
                .verified;
    }
}
