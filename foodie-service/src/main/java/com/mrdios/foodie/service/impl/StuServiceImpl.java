package com.mrdios.foodie.service.impl;

import com.mrdios.foodie.mapper.StuMapper;
import com.mrdios.foodie.pojo.Stu;
import com.mrdios.foodie.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author CodePorter
 * @date 2020-06-13
 */
@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStu(Integer id) {
        return stuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int saveStu(Stu stu) {
        stu = new Stu();
        stu.setName("Tom");
        stu.setAge(19);
        return stuMapper.insert(stu);
    }

    @Override
    public int updateStu(Stu stu) {
        return stuMapper.updateByPrimaryKeySelective(stu);
    }

    @Override
    public int deleteStu(Integer id) {
        return stuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void saveParent() {
        Stu parent = new Stu();
        parent.setAge(18);
        parent.setName("parent");
        stuMapper.insert(parent);
    }

    @Override
//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveChildren() {
        saveChild1();
        int i = 1 / 0;
        saveChild2();
    }

    public void saveChild1() {
        Stu child = new Stu();
        child.setName("child-1");
        child.setAge(11);
        stuMapper.insert(child);
    }

    public void saveChild2() {
        Stu child = new Stu();
        child.setName("child-2");
        child.setAge(11);
        stuMapper.insert(child);
    }
}
