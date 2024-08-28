package com.mashibing.serviceorder.service.impl;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.DriverGrabRequest;
import com.mashibing.serviceorder.service.GrabService;
import com.mashibing.serviceorder.service.OrderInfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service("grabByZookeeperDiyService")
public class GrabByZookeeperDiyServiceImpl implements GrabService {

    @Autowired
    OrderInfoService orderInfoService;

    @Value("${zookeeper.address}")
    private String address;

    @Value("${zookeeper.timeout}")
    private int timeout;

    @Override
    public ResponseResult grab(DriverGrabRequest driverGrabRequest) {
        ResponseResult grab = null;
        try {
            // 获取锁
            // 连接Zookeeper客户端
            CountDownLatch countDownLatch = new CountDownLatch(1);

            ZooKeeper zooKeeper = new ZooKeeper(address, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (Event.KeeperState.SyncConnected==watchedEvent.getState()){
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();

            // 创建 持久节点
            Long orderId = driverGrabRequest.getOrderId();
            String parentNode = "/order-"+orderId;

            Stat exists = zooKeeper.exists(parentNode, false);
            if (exists == null){
                zooKeeper.create(parentNode,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
            // 创建临时节点
            String seq = "/seq";
            String s = zooKeeper.create(parentNode + seq, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            // 判断我的节点是不是 所有子节点中最小的
            boolean ifLock = false;
            // /order-138/seq0000000000
            if (StringUtils.isNotBlank(s)){
                List<String> children = zooKeeper.getChildren(parentNode, false);
                Collections.sort(children);
                if ((children.size()>0) && ((parentNode + "/" + children.get(0)).equals(s))){
                    ifLock = true;
                    // 执行业务逻辑
                    System.out.println("开始锁 zookeeper diy");
                    grab = orderInfoService.grab(driverGrabRequest);
                    System.out.println("结束锁 zookeeper diy");
                }

            }
            if (!ifLock){
                grab = ResponseResult.fail(CommonStatusEnum.ORDER_CAN_NOT_GRAB.getCode(),CommonStatusEnum.ORDER_CAN_NOT_GRAB.getValue());
            }

            // 释放锁
            // 关闭链接
            zooKeeper.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return grab;
    }
}
