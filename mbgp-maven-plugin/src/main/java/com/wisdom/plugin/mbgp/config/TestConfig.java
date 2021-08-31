package com.wisdom.plugin.mbgp.config;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import lombok.Data;

import java.util.HashMap;

@Data
public class TestConfig extends InjectionConfig{

    private String isTest;

    private String junitVersion;

    @Override
    public void initMap() {
        this.setMap(new HashMap<>());
    }
}
