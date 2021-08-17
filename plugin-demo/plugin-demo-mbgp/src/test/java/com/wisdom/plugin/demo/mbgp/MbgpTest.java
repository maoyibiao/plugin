package com.wisdom.plugin.demo.mbgp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wisdom.plugin.demo.mbgp.entity.OmsOrder;
import com.wisdom.plugin.demo.mbgp.mapper.CmsHelpCategoryMapper;
import com.wisdom.plugin.demo.mbgp.mapper.OmsOrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Wrapper;

@SpringBootTest
public class MbgpTest {
    @Autowired
    private OmsOrderMapper omsOrderMapper;
    @Test
    public void test(){
        QueryWrapper qw = new QueryWrapper();
        qw.isNotNull("coupon_id");
        System.out.println(omsOrderMapper.selectPage(new Page<OmsOrder>(1,5),qw));
    }
}
