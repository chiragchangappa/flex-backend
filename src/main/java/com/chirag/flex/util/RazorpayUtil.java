package com.chirag.flex.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class RazorpayUtil {

    public static boolean verify(String orderId,String paymentId,String signature,String secret){
        try{
            String payload=orderId+"|"+paymentId;

            Mac mac=Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(),"HmacSHA256"));

            String generated=Base64.getEncoder().encodeToString(mac.doFinal(payload.getBytes()));

            return generated.equals(signature);
        }catch(Exception e){
            return false;
        }
    }
}
