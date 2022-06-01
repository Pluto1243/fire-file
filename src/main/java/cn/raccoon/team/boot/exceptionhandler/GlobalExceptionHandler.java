package cn.raccoon.team.boot.exceptionhandler;

import cn.raccoon.team.boot.exception.CommonException;
import cn.raccoon.team.boot.exception.EmError;
import cn.raccoon.team.boot.exception.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Qian
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public R doError(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Exception ex) {
    Map<String, Object> responseData = new HashMap<>(16);
    ex.printStackTrace();
    if (ex instanceof CommonException) {
      CommonException commonException = (CommonException) ex;
      responseData.put("errCode", commonException.getErrCode());
      responseData.put("errMsg", commonException.getErrMsg());

    } else {
      responseData.put("errCode", EmError.UNKNOWN_ERROR.getErrCode());
      responseData.put("errMsg", EmError.UNKNOWN_ERROR.getErrMsg());
    }

    return R.error(responseData);
  }
}
