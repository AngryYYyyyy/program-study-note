package com.mashibing.serviceorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author cpf
 * @since 2023-08-29
 */
@TableName("driver_order_statistics")
public class DriverOrderStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Long driverId;

    /**
     * 司机抢单成功的数量
     */
    private Integer grabOrderSuccessCount;

    private LocalDate grabOrderDate;

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getGrabOrderSuccessCount() {
        return grabOrderSuccessCount;
    }

    public void setGrabOrderSuccessCount(Integer grabOrderSuccessCount) {
        this.grabOrderSuccessCount = grabOrderSuccessCount;
    }
    public LocalDate getGrabOrderDate() {
        return grabOrderDate;
    }

    public void setGrabOrderDate(LocalDate grabOrderDate) {
        this.grabOrderDate = grabOrderDate;
    }

    @Override
    public String toString() {
        return "DriverOrderStatistics{" +
            "id=" + id +
            ", grabOrderSuccessCount=" + grabOrderSuccessCount +
            ", grabOrderDate=" + grabOrderDate +
        "}";
    }
}
