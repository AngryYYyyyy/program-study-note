package com.mashibing;

import com.alibaba.csp.sentinel.cluster.server.ClusterTokenServer;
import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;

public class SentinelTokenServerApplication {

    static {
        // sentinel dashboard
        System.setProperty("csp.sentinel.dashboard.server","localhost:9091");
        System.setProperty("project.name","sentinel-token-server");

    }

    public static void main(String[] args) throws Exception {

        ClusterTokenServer clusterTokenServer = new SentinelDefaultTokenServer();
        ClusterServerConfigManager.loadGlobalTransportConfig(new ServerTransportConfig().setPort(10121));
        clusterTokenServer.start();

    }
}
