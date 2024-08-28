package com.mashibing.internalcommon.request;

import com.mashibing.internalcommon.dto.PriceRule;
import lombok.Data;

@Data
public class ForecastPriceDTO {

    private String depLongitude;

    private String depLatitude;

    private String destLongitude;

    private String destLatitude;

    private String cityCode;
    private String vehicleType;
}
