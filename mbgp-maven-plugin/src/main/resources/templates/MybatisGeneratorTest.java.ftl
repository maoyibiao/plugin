package ${package.Mapper};

import ${package.Mapper}.${table.mapperName};
import ${package.Entity}.${entity};
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ${table.mapperName}Test {
    @Autowired
    private ${table.mapperName} mapper;
    @Test
    public void test(){
        System.out.println(mapper.selectOne(null));
    }
}
