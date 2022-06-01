package cn.raccoon.team.boot.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Qian
 */
public class EncryptionUtils {

  public static String md5Encode(String str) {
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
      md5.update(str.getBytes());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return new BigInteger(1, md5.digest()).toString(16);
  }
}
