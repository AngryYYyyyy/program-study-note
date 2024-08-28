package com.mashibing.serviceorder.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * 自动生成代码工具类
 */
public class MysqlGenerator {
    public static void main(String[] args) {

        FastAutoGenerator.create("jdbc:mysql://localhost:3306/service-order?characterEncoding=utf-8&serverTimezone=GMT%2B8",
                "root","root")
                .globalConfig(builder -> {
                    builder.author("cpf").fileOverride().outputDir("D:\\00-项目\\gitee\\online-taxi-2022-public\\online-taxi-public\\service-order\\src\\main\\java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.mashibing.serviceorder").pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "D:\\01-项目\\gitee\\online-taxi-2022-public\\online-taxi-public\\service-order\\src\\main\\java\\com\\mashibing\\serviceorder\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("driver_order_statistics");

                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
