package com.mrdios.foodie.api;

import com.mrdios.foodie.App;
import com.mrdios.foodie.service.TestTranscationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huxiong
 * @date 2020-06-11 10:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TranscationPropagationTest {
    @Autowired
    private TestTranscationService testTranscationService;

    @Test
    public void testTranscationPropagation() {
        testTranscationService.testPropagationTrans();
    }
}