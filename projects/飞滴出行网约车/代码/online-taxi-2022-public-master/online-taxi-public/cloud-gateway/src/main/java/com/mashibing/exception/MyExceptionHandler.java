package com.mashibing.exception;

import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MyExceptionHandler implements WebExceptionHandler {
    /**
     * 在这个方法中定义了遇到不同异常时候的处理办法
     * @param exchange
     * @param ex
     * @return
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ResponseResult responseResult = null;

        if (ex instanceof FlowException){
            responseResult = ResponseResult.fail(429,"自定义流控异常");
        }

        return writeResponse(exchange,responseResult);
    }

    private Mono<Void> writeResponse(ServerWebExchange exchange, ResponseResult responseResult){

        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("Content-Type","application/json;charset=utf-8");

        String resultString = "";
        try {
            resultString = new ObjectMapper().writeValueAsString(responseResult);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        DataBuffer wrap = response.bufferFactory().wrap(resultString.getBytes());

        return response.writeWith(Flux.just(wrap));

    }
}
