package com.sicnu.gateway.filter;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sicnu.common.utils.R;
import com.sicnu.common.utils.StringUtils;
import com.sicnu.gateway.config.properties.CaptchaProperties;
import com.sicnu.gateway.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * TODO
 *
 * @author 热爱生活の李
 * @version 1.0
 * @since 2022/11/11 21:59
 */
@Component
public class ValidateCodeFilter extends AbstractGatewayFilterFactory<Object>
{
    private final static String[] VALIDATE_URL = new String[] { "/auth/login", "/auth/register" };

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private CaptchaProperties captchaProperties;

    private static final String CODE = "code";

    private static final String UUID = "uuid";

    @Override
    public GatewayFilter apply(Object config)
    {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            boolean flag = false;
            for (int i = 0; i < VALIDATE_URL.length; i++) {
                if(VALIDATE_URL[i].equalsIgnoreCase(request.getURI().getPath())){
                    flag = true;
                    break;
                }
            }
            // 非登录/注册请求或验证码关闭，不处理
            if (flag || !captchaProperties.getEnabled())
            {
                return chain.filter(exchange);
            }

            try
            {
                String rspStr = resolveBodyFromRequest(request);
                JSONObject obj = JSON.parseObject(rspStr);
                validateCodeService.checkCaptcha(obj.getString(CODE), obj.getString(UUID));
            }
            catch (Exception e)
            {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.OK);
                response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                R result = R.error(500, e.getMessage());
                DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
                return response.writeWith(Mono.just(dataBuffer));
            }
            return chain.filter(exchange);
        };
    }

    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest)
    {
        // 获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        return bodyRef.get();
    }
}
