package io.github.enbool.generator.samples;

import io.github.enbool.generator.AutoGenerator;
import io.github.enbool.generator.config.DataSourceConfig;
import org.junit.jupiter.api.Test;

/**
 * PostgreSQL 代码生成
 *
 * @author lanjerry
 * @since 3.5.3
 */
public class PostgreSQLGeneratorTest extends BaseGeneratorTest {

    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
        .Builder("jdbc:postgresql://xxxx:5432/postgres", "postgres", "postgres")
        .build();

    @Test
    public void testSimple() {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.global(globalConfig().build());
        generator.execute();
    }
}
