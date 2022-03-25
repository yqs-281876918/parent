package org.mixed.exam.bank.service;

import org.mixed.exam.admin.api.pojo.Classification;
import org.mixed.exam.bank.dao.PaperDao;
import org.mixed.exam.bank.pojo.po.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditService {
    @Autowired
    private PaperDao paperDao;

    public void getFrom(String id,
                        String foreWord,
                        List<List<String>> subjectIDs,
                        String courseID,
                        boolean open,
                        Integer difficulty){
        List<Classification> tmp=paperDao.getCourseid(courseID);
        Paper p=paperDao.getOne(id);
        p.setForeWord(foreWord);
        p.setSubjectIDs(subjectIDs);
        p.setCourseID(tmp.get(0).getId());
        p.setOpen(open);
        p.setDifficulty(difficulty);
        paperDao.savePaper(p);
    }

    public List<Classification> getAllCourse() {
        return paperDao.getAllCourse();
    }

    public Classification getCourse(String id) {
        return paperDao.getCourse(id);
    }
}
