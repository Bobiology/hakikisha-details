package io.mglobe.customerdetails.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class DarajaAuth {
public static void mainAA(String []a){
    String SessionID="20010202049";
    String TransactionReference="qwedfsa5051";
    String Password="Test";
    DarajaAuth d5 =new DarajaAuth();
   //System.err.println(d5.Auth(TransactionReference,SessionID,Password));\
    String BillRefNumber ="";
    if(BillRefNumber.equalsIgnoreCase("") || BillRefNumber.equalsIgnoreCase("null") || BillRefNumber.equalsIgnoreCase(null)) {
		BillRefNumber="N";
	}
    System.out.println("ERRORS = "+BillRefNumber);
}
public String Auth(String transactioncode, String SessionID, String password) {
        String rawPassYangu = "";
        try {
            String billerPassword = password;
            if (billerPassword != null && !billerPassword.isEmpty()) {
                rawPassYangu = Auth2(transactioncode, SessionID, billerPassword.trim());
                //System.out.println("PASSWORD: "+rawPassYangu);
               
            }else{
                System.out.println("TXN ID : " + transactioncode + " : Utils | Auth | Password NOT found");
            }
        } catch (Exception e) {
            System.out.println("TXN ID : " + transactioncode + " : Utils | Auth | " + e);
            e.printStackTrace();
        }
        return rawPassYangu;
    }
    private String Auth2(String transactioncode, String SessionID, String Password) {
        String sstring = "nomatenasanah";
        try {
            String md5Hashed = md5Hash(Password,transactioncode);
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(SessionID.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(md5Hashed.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            sstring = sb.toString();
        } catch (Exception e) {
            System.out.println("TXN ID : " + transactioncode + " : Utils | Auth2 | " + e);
            e.printStackTrace();
        }
        return sstring;
    }
    private String md5Hash(String input,String transactioncode){
         String hashtext = "nomatenaSanah";
         try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
             hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
        } catch (Exception e) {
            System.out.println("TXN ID : " + transactioncode + " : Utils | md5Hash | Exception " + e);
            e.printStackTrace();
            return hashtext.toUpperCase();
        }
    }
}

