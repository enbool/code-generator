package io.github.enbool.generator.config.converts;

import io.github.enbool.generator.config.builder.GeneratorBuilder;
import io.github.enbool.generator.config.GlobalConfig;
import io.github.enbool.generator.config.rules.DateType;
import io.github.enbool.generator.config.rules.DbColumnType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author lanjerry 2020/10/23.
 */
public class SqlServerTypeConvertTest {

    @Test
    void processTypeConvertTest() {
        // 常用格式
        GlobalConfig globalConfig = GeneratorBuilder.globalConfig();
        SqlServerTypeConvert convert = SqlServerTypeConvert.INSTANCE;
        Assertions.assertEquals(DbColumnType.STRING, convert.processTypeConvert(globalConfig, "char"));
        Assertions.assertEquals(DbColumnType.STRING, convert.processTypeConvert(globalConfig, "xml"));
        Assertions.assertEquals(DbColumnType.STRING, convert.processTypeConvert(globalConfig, "text"));
        Assertions.assertEquals(DbColumnType.LONG, convert.processTypeConvert(globalConfig, "bigint"));
        Assertions.assertEquals(DbColumnType.INTEGER, convert.processTypeConvert(globalConfig, "int"));
        Assertions.assertEquals(DbColumnType.BOOLEAN, convert.processTypeConvert(globalConfig, "bit"));
        Assertions.assertEquals(DbColumnType.DOUBLE, convert.processTypeConvert(globalConfig, "decimal"));
        Assertions.assertEquals(DbColumnType.DOUBLE, convert.processTypeConvert(globalConfig, "numeric"));
        Assertions.assertEquals(DbColumnType.BIG_DECIMAL, convert.processTypeConvert(globalConfig, "money"));
        Assertions.assertEquals(DbColumnType.BYTE_ARRAY, convert.processTypeConvert(globalConfig, "binary"));
        Assertions.assertEquals(DbColumnType.BYTE_ARRAY, convert.processTypeConvert(globalConfig, "image"));
        Assertions.assertEquals(DbColumnType.FLOAT, convert.processTypeConvert(globalConfig, "float"));
        Assertions.assertEquals(DbColumnType.FLOAT, convert.processTypeConvert(globalConfig, "real"));

        // 日期格式
        globalConfig = GeneratorBuilder.globalConfigBuilder().dateType(DateType.SQL_PACK).build();
        Assertions.assertEquals(DbColumnType.DATE_SQL, convert.processTypeConvert(globalConfig, "date"));
        Assertions.assertEquals(DbColumnType.TIME, convert.processTypeConvert(globalConfig, "time"));
        Assertions.assertEquals(DbColumnType.TIMESTAMP, convert.processTypeConvert(globalConfig, "timestamp"));
        Assertions.assertEquals(DbColumnType.TIMESTAMP, convert.processTypeConvert(globalConfig, "datetime"));

        globalConfig = GeneratorBuilder.globalConfigBuilder().dateType(DateType.TIME_PACK).build();
        Assertions.assertEquals(DbColumnType.LOCAL_DATE, convert.processTypeConvert(globalConfig, "date"));
        Assertions.assertEquals(DbColumnType.LOCAL_TIME, convert.processTypeConvert(globalConfig, "time"));
        Assertions.assertEquals(DbColumnType.LOCAL_DATE_TIME, convert.processTypeConvert(globalConfig, "timestamp"));
        Assertions.assertEquals(DbColumnType.LOCAL_DATE_TIME, convert.processTypeConvert(globalConfig, "datetime"));

        globalConfig = GeneratorBuilder.globalConfigBuilder().dateType(DateType.ONLY_DATE).build();
        Assertions.assertEquals(DbColumnType.DATE, convert.processTypeConvert(globalConfig, "date"));
        Assertions.assertEquals(DbColumnType.DATE, convert.processTypeConvert(globalConfig, "time"));
        Assertions.assertEquals(DbColumnType.DATE, convert.processTypeConvert(globalConfig, "timestamp"));
        Assertions.assertEquals(DbColumnType.DATE, convert.processTypeConvert(globalConfig, "datetime"));
    }
}
