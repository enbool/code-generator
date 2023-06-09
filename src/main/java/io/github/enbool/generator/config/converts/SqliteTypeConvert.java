/*
 * Copyright (c) 2011-2022, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.enbool.generator.config.converts;

import io.github.enbool.generator.config.GlobalConfig;
import io.github.enbool.generator.config.ITypeConvert;
import io.github.enbool.generator.config.rules.DbColumnType;
import io.github.enbool.generator.config.rules.IColumnType;

/**
 * SQLite 字段类型转换
 *
 * @author chen_wj, hanchunlin
 * @since 2019-05-08
 */
public class SqliteTypeConvert implements ITypeConvert {
    public static final SqliteTypeConvert INSTANCE = new SqliteTypeConvert();

    /**
     * @inheritDoc
     * @see MySqlTypeConvert#toDateType(GlobalConfig, String)
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(TypeConverts.contains("bigint").then(DbColumnType.LONG))
            .test(TypeConverts.containsAny("tinyint(1)", "boolean").then(DbColumnType.BOOLEAN))
            .test(TypeConverts.contains("int").then(DbColumnType.INTEGER))
            .test(TypeConverts.containsAny("text", "char", "enum").then(DbColumnType.STRING))
            .test(TypeConverts.containsAny("decimal", "numeric").then(DbColumnType.BIG_DECIMAL))
            .test(TypeConverts.contains("clob").then(DbColumnType.CLOB))
            .test(TypeConverts.contains("blob").then(DbColumnType.BLOB))
            .test(TypeConverts.contains("float").then(DbColumnType.FLOAT))
            .test(TypeConverts.contains("double").then(DbColumnType.DOUBLE))
            .test(TypeConverts.containsAny("date", "time", "year").then(t -> MySqlTypeConvert.toDateType(config, t)))
            .or(DbColumnType.STRING);
    }

}
