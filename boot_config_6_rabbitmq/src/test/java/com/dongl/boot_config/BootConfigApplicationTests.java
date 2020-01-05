package com.dongl.boot_config;

import com.dongl.boot_config_rabbitmq.BootConfigRabbitMQApplication;
import com.dongl.boot_config_rabbitmq.common.Provider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootConfigRabbitMQApplication.class)
public class BootConfigApplicationTests {

    @Autowired
    Provider provider;

    @Test
    public void provider() {
        provider.provider();
    }
}
