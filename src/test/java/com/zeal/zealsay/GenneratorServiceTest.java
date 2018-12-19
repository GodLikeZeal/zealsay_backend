package com.zeal.zealsay;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

/**
 * 测试mybatis-plus代码生成.
 *
 * @author zhanglei
 * @date 2018/9/7  下午3:25
 */
public class GenneratorServiceTest {

  public void generateCode() {
    String packageName = "com.zeal.zealsay";
    boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
    generateByTables(serviceNameStartWithI, packageName, "article");
  }

  private void generateByTables(boolean serviceNameStartWithI,
                                String packageName, String... tableNames) {
    GlobalConfig config = new GlobalConfig();
    String dbUrl = "jdbc:mysql://localhost:3306/"
        + "zealsay?useUnicode=true&characterEncoding=UTF-8"
        + "&allowMultiQueries=true&useSSL=false";
    DataSourceConfig dataSourceConfig = new DataSourceConfig();
    dataSourceConfig.setDbType(DbType.MYSQL)
        .setUrl(dbUrl)
        .setUsername("")
        .setPassword("")
        .setDriverName("com.mysql.jdbc.Driver");
    StrategyConfig strategyConfig = new StrategyConfig();
    strategyConfig
        .setCapitalMode(true)
        .setEntityLombokModel(false)
        .setTablePrefix("sys_")  //表前缀
        .setNaming(NamingStrategy.underline_to_camel)
        .setEntityBuilderModel(true)  //使用建造者模式
        .setEntityLombokModel(true)  //lombok
        .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
    config.setActiveRecord(false)
        .setAuthor("zhanglei")
        .setSwagger2(true)
        .setIdType(IdType.ID_WORKER)
        .setOutputDir("/Users/zhanglei/Documents/my/code/zealsay_backend/src/main/java")
        .setFileOverride(true);
    if (!serviceNameStartWithI) {
      config.setServiceName("%sService");
    }
    new AutoGenerator().setGlobalConfig(config)
        .setDataSource(dataSourceConfig)
        .setStrategy(strategyConfig)
        .setPackageInfo(
            new PackageConfig()
                .setParent(packageName)
                .setController("controller")
                .setEntity("entity")
        ).execute();
  }
}
