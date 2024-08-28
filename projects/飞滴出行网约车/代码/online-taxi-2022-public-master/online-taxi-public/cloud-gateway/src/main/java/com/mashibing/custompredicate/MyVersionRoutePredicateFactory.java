package com.mashibing.custompredicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class MyVersionRoutePredicateFactory extends AbstractRoutePredicateFactory<MyVersionRoutePredicateFactory.MyConfig> {


    public MyVersionRoutePredicateFactory() {
        super(MyConfig.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {

        return Arrays.asList(new String[]{"version"});
    }

    @Override
    public Predicate<ServerWebExchange> apply(MyConfig config) {

        return (ServerWebExchange serverWebExchange )-> {
            String version = serverWebExchange.getRequest().getQueryParams().getFirst("version");
            System.out.println("version:"+version);
            if (version!=null && version.equals(config.getVersion())){
                return true;
            }else {
                return false;
            }

        };
    }

    public static class MyConfig{
        private String version;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
