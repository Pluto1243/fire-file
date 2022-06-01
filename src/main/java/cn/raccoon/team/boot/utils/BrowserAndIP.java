package cn.raccoon.team.boot.utils;

import javax.servlet.http.HttpServletRequest;


/**
 * @author Qian
 */
public class BrowserAndIP {

  private static final String UNKNOW = "unknow";

  public static String getBrowserAndIP(HttpServletRequest request) {
    String browser = request.getHeader("User-Agent");
    String loginIp = request.getHeader("X-Forwarded-For");
    if (loginIp == null || loginIp.isEmpty() || UNKNOW == loginIp){
      loginIp = request.getHeader("PRoxy-Client-IP");
    }
    if (loginIp == null || loginIp.isEmpty() || UNKNOW == loginIp){
      loginIp = request.getHeader("WL-Proxy-Client-IP");
    }
    if (loginIp == null || loginIp.isEmpty() || UNKNOW == loginIp){
      loginIp = request.getHeader("X-Real-IP");
    }
    if (loginIp == null || loginIp.isEmpty() || UNKNOW == loginIp){
      loginIp = request.getHeader("HTTP_CLIENT_IP");
    }
    if (loginIp == null || loginIp.isEmpty() || UNKNOW == loginIp){
      loginIp = request.getRemoteAddr();
    }
    return EncryptionUtils.md5Encode(loginIp+browser);
  }
}
