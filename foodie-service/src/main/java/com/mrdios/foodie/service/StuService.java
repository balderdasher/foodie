package com.mrdios.foodie.service;

import com.mrdios.foodie.pojo.Stu;

/**
 * @author CodePorter
 * @date 2020-06-13
 */
public interface StuService {
    Stu getStu(Integer id);

    int saveStu(Stu stu);

    int updateStu(Stu stu);

    int deleteStu(Integer id);


    // For testing transaction
    void saveParent();

    void saveChildren();
}
