package cn.raccoon.team.boot.exception;

/**
 * @author Qian
 */
public interface CommonError {

  public int getErrCode();

  public String getErrMsg();

  public CommonError setErrMsg(String errMsg);
}
