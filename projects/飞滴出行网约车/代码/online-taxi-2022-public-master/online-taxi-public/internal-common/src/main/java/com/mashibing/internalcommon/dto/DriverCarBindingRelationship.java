package com.mashibing.internalcommon.dto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 晁鹏飞
 * @since 2022-08-13
 */
@Data
public class DriverCarBindingRelationship implements Serializable {

    private Long id;

    /**
     * 司机ID
     */
    private Long driverId;

    /**
     * 车辆Id
     */
    private Long carId;

    /**
     * 绑定状态：1：绑定，2：解绑
     */
    private Integer bindState;

    /**
     * 绑定时间
     */
    private LocalDateTime bindingTime;

    /**
     * 解绑时间
     */
    private LocalDateTime unBindingTime;

}
