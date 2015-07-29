/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.service;

import com.ic.bean.AnswerBean;
import com.ic.bean.CatagorizedBean;
import com.ic.bean.IsoBean;
import com.ic.bean.QuestionBean;
import com.ic.entity.Answer;
import com.ic.entity.Catagorized;
import com.ic.entity.Iso;
import com.ic.entity.Question;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Wei.Cheng
 */
public class AlterDataBase {

    private List l = null;
    private final CatagorizedBean cataBean = new CatagorizedBean();
    private final QuestionBean qBean = new QuestionBean();
    private final AnswerBean ansBean = new AnswerBean();
    private final IsoBean isoBean = new IsoBean();

    public AlterDataBase() {
        l = null;
    }

    public boolean insertCatagorize(String catagorized) {
        if (!catagorized.equals("")) {
            l = new ArrayList();
            Catagorized cata = new Catagorized();
            cata.setName(catagorized);
            l.add(cata);
            return cataBean.insertCatagorized(l);
        } else {
            return false;
        }
    }

    public boolean insertQuestion(String question, int type_no) {
        if (!question.equals("")) {
            l = new ArrayList();
            Question q = new Question();
            q.setName(question);
            q.setTypeNo(type_no);
            l.add(q);
            return qBean.insertQuestion(l);
        } else {
            return false;
        }
    }

    public boolean insertAnswer(String[] ansname, int q_no) {
        l = new ArrayList();
        for (String s : ansname) {
            if (!s.equals("")) {
                Answer ans = new Answer();
                ans.setName(s);
                ans.setQNo(q_no);
                l.add(ans);
            }
        }
        if (!l.isEmpty()) {
            return ansBean.insertAns(l);
        } else {
            return false;
        }
    }

    public boolean insertIso(String[] isoname, int q_no) {
        l = new ArrayList();
        for (String s : isoname) {
            if (!s.equals("")) {
                Iso iso = new Iso();
                iso.setName(s);
                iso.setQNo(q_no);
                l.add(iso);
            }
        }
        if (!l.isEmpty()) {
            return isoBean.insertIso(l);
        } else {
            return false;
        }
    }

    public boolean updateCatagorize(String catagorized, int type_no) {
        if (!catagorized.equals("")) {
            l = new ArrayList();
            Catagorized c = new Catagorized();
            c.setName(catagorized);
            c.setId(type_no);
            l.add(c);
            return cataBean.updateCatagorized(l);
        } else {
            return false;
        }
    }

    public boolean updateQuestion(String question, int q_no) {
        if (!question.equals("")) {
            l = new ArrayList();
            Question q = new Question();
            q.setName(question);
            q.setId(q_no);
            l.add(q);
            return qBean.updateQuestion(l);
        } else {
            return false;
        }
    }

    public boolean updateAnswer(String[] ansname, String[] ansno) {
        l = new ArrayList();
        for (int a = 0; a < ansname.length; a++) {
            if (!ansname[a].equals("")) {
                Answer ans = new Answer();
                ans.setName(ansname[a]);
                ans.setId(Integer.parseInt(ansno[a]));
                l.add(ans);
            }
        }
        if (!l.isEmpty()) {
            return ansBean.updateAns(l);
        } else {
            return false;
        }
    }

    public boolean updateIso(String[] isoname, String[] isono) {
        l = new ArrayList();
        for (int a = 0; a < isoname.length; a++) {
            if (!isoname[a].equals("")) {
                Iso iso = new Iso();
                iso.setName(isoname[a]);
                iso.setId(Integer.parseInt(isono[a]));
                l.add(iso);
            }
        }
        if (!l.isEmpty()) {
            return isoBean.updateIso(l);
        } else {
            return false;
        }
    }

    public boolean deleteCatagorized(String[] catano) {
        l = new ArrayList();
        l.addAll(Arrays.asList(catano));
        return cataBean.deleteCatagorized(l);
    }

    public boolean deleteQuestion(String[] q_no) {
        l = new ArrayList();
        l.addAll(Arrays.asList(q_no));
        return qBean.deleteQuestion(l);
    }

    public boolean deleteAnswer(String[] ans) {
        l = new ArrayList();
        l.addAll(Arrays.asList(ans));
        return ansBean.deleteAnswer(l);
    }

    public boolean deleteIso(String[] ans) {
        l = new ArrayList();
        l.addAll(Arrays.asList(ans));
        return isoBean.deleteIso(l);
    }
}
