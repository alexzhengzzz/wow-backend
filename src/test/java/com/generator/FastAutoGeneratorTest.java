package com.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

import java.sql.SQLException;

/**
 * <p>
 * 快速生成
 * </p>
 *
 * @author lanjerry
 * @since 2021-09-16
 */
public class FastAutoGeneratorTest {
    private static final String username = "root";
    private static final String password = "123456a";
    /**
     * 执行初始化数据库脚本
     */
//    public static void before() throws SQLException {
//        Connection conn = DATA_SOURCE_CONFIG.build().getConn();
//        InputStream inputStream = H2CodeGeneratorTest.class.getResourceAsStream("/sql/init.sql");
//        ScriptRunner scriptRunner = new ScriptRunner(conn);
//        scriptRunner.setAutoCommit(true);
//        scriptRunner.runScript(new InputStreamReader(inputStream));
//        conn.close();
//    }



    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://localhost:3306/wow?characterEncoding=utf-8&userSSL=false", username, password);

    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {
//        before();
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author(scanner.apply("请输入作者名称")))
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名")))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(scanner.apply("请输入表名，多个表名用,隔开")))
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
                .execute();
    }
}