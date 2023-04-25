package com.sicnu.gateway.handler;

import com.sicnu.common.utils.R;
import com.sicnu.gateway.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


import java.io.IOException;

/**
 * 验证码获取
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/11 21:51
 */
@Component
public class ValidateCodeHandler implements HandlerFunction<ServerResponse> {
    @Autowired
    private ValidateCodeService validateCodeService;

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        R r = null;
        try {
            r = validateCodeService.createCaptcha();
        } catch (IOException e) {
            e.printStackTrace();
            return Mono.error(e);
        }
        return ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(r));
    }
}
