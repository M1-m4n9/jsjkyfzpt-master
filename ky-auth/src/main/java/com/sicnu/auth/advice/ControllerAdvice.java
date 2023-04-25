package com.sicnu.auth.advice;

import com.sicnu.common.exception.CodeEnume;
import com.sicnu.common.exception.KyException;
import com.sicnu.common.utils.R;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/10/23 13:35
 */
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R BindExceptionHandler(MethodArgumentNotValidException e){
        Map<String,String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(item->{
            // 错误信息
            String message = item.getDefaultMessage();
            // 错误字段
            String field = item.getField();
            map.put(field,message);
        });
        return R.error(CodeEnume.VALID_EXCEPTION).put("data",map);
    }

    @ExceptionHandler(KyException.class)
    public R KyExceptionHandler(KyException e){
        return R.error(e.getCode(),e.getMessage());
    }
}
