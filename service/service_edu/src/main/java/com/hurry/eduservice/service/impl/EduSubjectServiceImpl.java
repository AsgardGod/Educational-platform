package com.hurry.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hurry.eduservice.entity.EduSubject;
import com.hurry.eduservice.entity.excel.ExcelSubjectData;
import com.hurry.eduservice.entity.subject.OneSubject;
import com.hurry.eduservice.entity.subject.TwoSubject;
import com.hurry.eduservice.listener.SubjectExcelListener;
import com.hurry.eduservice.mapper.EduSubjectMapper;
import com.hurry.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-26
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            InputStream is = file.getInputStream();
            EasyExcel.read(is, ExcelSubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        //查询出所有父id值相同的值，封装到list集合中
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        //id值等于0的是一级分类，不等于0的是二级分类
        twoWrapper.ne("parent_id",0);
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);
        //创建一个集合用来存放返回的数据，类型为OneSubject
        List<OneSubject> finalSubject = new ArrayList<>();
        //对得到的一级分类的集合进行遍历，得到每一个一级分类
        for (EduSubject eduSubject : oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
            //使用spring框架提供的方法，将eduSubject与oneSubject进行比对，将前者的数据对应的赋值给后者
            //相当于oneSubject.setId(eduSubject.getId());
            BeanUtils.copyProperties(eduSubject,oneSubject);
            //用来存放被赋值后的二级分类
            List<TwoSubject> twoFinaList = new ArrayList<>();
            for (EduSubject subject : twoSubjectList) {
                //如果二级分类的父id和一级分类的id相同
                if (subject.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    //将得到的二级分类数据中对应的属性赋值给二级分类的实体类
                    BeanUtils.copyProperties(subject,twoSubject);
                    //将赋值之后的二级分类的实体类放入集合
                    twoFinaList.add(twoSubject);
                }
            }
            //将存放赋值后的二级分类的集合放入到一级分类的实体类中
            oneSubject.setChildren(twoFinaList);
            //将赋值完成的数据放入最终需要返回的集合
            finalSubject.add(oneSubject);
        }
        return finalSubject;
    }
}
