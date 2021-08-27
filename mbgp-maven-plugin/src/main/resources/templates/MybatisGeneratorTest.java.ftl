package ${package.Mapper};

import ${package.Mapper}.${table.mapperName};
import ${package.Entity}.${entity};
<#if cfg.testConfig.junitVersion == "5">
import org.junit.jupiter.api.Test;
</#if>
<#if cfg.testConfig.junitVersion == "4">
import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
<#if cfg.testConfig.junitVersion == "4">
@RunWith(SpringRunner.class)
</#if>
public class ${table.mapperName}Test {
    @Autowired
    private ${table.mapperName} mapper;
    @Test
    public void test(){
        System.out.println(mapper.selectOne(null));
    }
}
