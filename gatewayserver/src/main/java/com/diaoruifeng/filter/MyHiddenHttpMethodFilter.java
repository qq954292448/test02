package com.diaoruifeng.filter;

import io.netty.buffer.ByteBufAllocator;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

@Component
public class MyHiddenHttpMethodFilter  extends AbstractNameValueGatewayFilterFactory {
    private static final String X_APP_ID_HEADER = "X-app-id";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    @Override
    public GatewayFilter apply(Consumer<NameValueConfig> consumer) {
        return null;
    }
    @Override
    public GatewayFilter apply(NameValueConfig nameValueConfig) {
        System.out.println("进Filter................................................");
        return (exchange, chain) -> {
            URI uri = exchange.getRequest().getURI();
            URI ex = UriComponentsBuilder.fromUri(uri).build(true).toUri();
            ServerHttpRequest request = exchange.getRequest().mutate().uri(ex).build();
            if("POST".equalsIgnoreCase(request.getMethodValue())){//判断是否为POST请求
                Flux<DataBuffer> body = request.getBody();
                AtomicReference<String> bodyRef = new AtomicReference<>();//缓存读取的request body信息
                body.subscribe(dataBuffer -> {
                    CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
                    DataBufferUtils.release(dataBuffer);
                    bodyRef.set(charBuffer.toString());
                });//读取request body到缓存
                String bodyStr = bodyRef.get();//获取request body
                System.out.println(bodyStr);//这里是我们需要做的操作
                DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
                Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

                request = new ServerHttpRequestDecorator(request){
                    @Override
                    public Flux<DataBuffer> getBody() {
                        return bodyFlux;
                    }
                };//封装我们的request
            }
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    protected DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public ServerHttpRequest.Builder mutate(ServerHttpRequest request) {
        return null;
    }

    @Override
    public ShortcutType shortcutType() {
        return null;
    }

    @Override
    public String shortcutFieldPrefix() {
        return null;
    }
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                return chain.filter(exchange);
            }
        };
    }
}
