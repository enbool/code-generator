package io.github.enbool.generator;

import io.github.enbool.generator.config.OutputFile;
import io.github.enbool.generator.config.builder.CustomFile;
import io.github.enbool.generator.config.rules.DbColumnType;
import io.github.enbool.generator.engine.EnjoyTemplateEngine;
import io.github.enbool.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.*;

/**
 * @Description:
 * @Author: wumin2
 * @Date: 2023/4/14 12:32
 */
public class Application {
    public static void main(String[] args) {
        String author = "wumin";


        // todo config mysql
        String url = "jdbc:mysql://localhost:3306/dict?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "123456";
        // todo config output
        String outputDir = "D://dict";
        String packageName = "io.github.enbool";
        String moduleName = "dict";

        // todo config table
        String[] includeTables = new String[]{"new_word"};
        String[] excludeTables = new String[]{};
        String[] tablePrefix = new String[]{};

        // 自定义配置
        Map<String, Object> customMap = new HashMap<>();
        customMap.put("pkgRepository", packageName + "." + moduleName + ".repository");
        customMap.put("pkgRepositoryImpl", packageName + "." + moduleName + ".repository.impl");
        customMap.put("pkgModel", packageName + "." + moduleName + ".model");

        // entity config
        String superEntityClass = "io.github.enbool.dict.model.entity.BaseEntity";
        String superControllerClass = "io.github.enbool.dict.controller.BaseController";

        List<CustomFile> customFiles = new ArrayList<>();
        customFiles.add(new CustomFile.Builder()
                .fileName("Repository.java")
                .packageName("repository")
                .templatePath("/templates/repository.java.ej")
                .build()
        );
        customFiles.add(new CustomFile.Builder()
                .fileName("RepositoryImpl.java")
                .packageName("repository.impl")
                .templatePath("/templates/repositoryImpl.java.ej")
                .build()
        );

        customFiles.add(new CustomFile.Builder()
                .fileName("Form.java")
                .packageName("model.form")
                .templatePath("/templates/form.java.ej")
                .build()
        );

        customFiles.add(new CustomFile.Builder()
                .fileName("Query.java")
                .packageName("model.query")
                .templatePath("/templates/query.java.ej")
                .build()
        );

        customFiles.add(new CustomFile.Builder()
                .fileName("VO.java")
                .packageName("model.vo")
                .templatePath("/templates/vo.java.ej")
                .build()
        );

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author)
                            // .enableKotlin()
                            .enableSpringdoc()
                            .fileOverride()
                            // output directory
                            .outputDir(outputDir)
                            .commentDate("yyyy-MM-dd HH:mm:ss");
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent(packageName)
                            .moduleName(moduleName)
                            .entity("model.entity")
                            // 设置mapperXml生成路径
                            .pathInfo(Collections.singletonMap(OutputFile.xml, outputDir));
                })
                .strategyConfig(builder -> {
                    // 设置需要生成的表名
                    builder
                            .addInclude(includeTables)
                            // .addExclude(excludeTables)
                            .addTablePrefix(tablePrefix);

                    builder.entityBuilder()
                            .superClass(superEntityClass)
                            .addSuperEntityColumns("id", "created_time", "updated_time", "created_by", "updated_by")
                            .enableLombok();

                    builder.serviceBuilder()
                                    .formatServiceFileName("%sService");

                    builder.controllerBuilder()
                            .superClass(superControllerClass)
                            .enableRestStyle()
                            .enableHyphenStyle();
                    ;

                })
                .injectionConfig(builder -> {
                    // 自定义配置
                    builder.customMap(customMap)
                            .customFile(customFiles);
                })
                // use enjoy template engine
                .templateEngine(new EnjoyTemplateEngine())
                .execute();
    }
}
