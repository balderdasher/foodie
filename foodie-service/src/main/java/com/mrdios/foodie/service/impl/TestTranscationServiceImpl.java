package com.mrdios.foodie.service.impl;

import com.mrdios.foodie.service.StuService;
import com.mrdios.foodie.service.TestTranscationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author CodePorter
 * @date 2020-06-13
 */
@Service
public class TestTranscationServiceImpl implements TestTranscationService {
    @Autowired
    private StuService stuService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void testPropagationTrans() {
        stuService.saveParent();
        stuService.saveChildren();
    }
}
