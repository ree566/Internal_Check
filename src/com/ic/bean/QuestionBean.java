/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.bean;

import com.ic.entity.Question;
import java.io.*;
import java.util.*;
import java.sql.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
public class QuestionBean implements Serializable {

    private DbBean db = null;
    private PreparedStatement preparedstatement = null;
    private static Logger logger = null;
    
    public QuestionBean(){
        BasicConfigurator.configure();
        logger = Logger.getLogger(QuestionBean.class);
    }

    //查詢所有問題
    public List getQuestion() {

        db = new DbBean();
        List question = null;
        try {
            ResultSet rs = db.search("select * from question");
            question = new ArrayList();
            while (rs.next()) {
                Question que = new Question();
                que.setName(rs.getString("name"));
                que.setId(rs.getInt("id"));
                que.setTypeNo(rs.getInt("type_no"));
                question.add(que);
            }
        } catch (SQLException ex) {
            logger.error(ex);
        } finally {
            try {
                db.result.close();
                db.stat.close();
                db.conn.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        return question;
    }

    //查詢問題by類型
    public List getQuestionByType(int type_no) {
        List list = getQuestion();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Question questionlist = (Question) it.next();
            if (questionlist.getTypeNo() != type_no) {
                it.remove();
            }
        }
        return list;
    }

    //查詢最新的問題id(answer & iso 新增用)
    public int getnewest_question() {
        db = new DbBean();
        int id = 0;
        try {
            ResultSet rs = db.search("select max(id) id from question");
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            try {
                db.stat.close();
                db.conn.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        return id;
    }

    //新增問題
    public boolean insertQuestion(List question) {
        String sql = "insert into question(name,type_no) values (?,?)";
        boolean b = alterQuestion(question, sql, 0);
        return b;
    }

    //修改問題
    public boolean updateQuestion(List question) {
        String sql = "update question set name=? where id=?";
        boolean b = alterQuestion(question, sql, 1);
        return b;
    }

    //刪除問題
    public boolean deleteQuestion(List question_num) {
        String sql = "delete from question where id=?";
        boolean b = alterQuestion(question_num, sql, 2);
        return b;
    }

    public boolean alterQuestion(List question, String sql, int type) {
        db = new DbBean();
        boolean b = false;
        Question qlist = null;
        try {
            db.conn.setAutoCommit(false);
            Iterator it = question.iterator();
            preparedstatement = db.conn.prepareStatement(sql);
            while (it.hasNext()) {
                if(type < 2){
                    qlist = (Question) it.next();
                }
                switch (type) {
                    case 0:
                        preparedstatement.setString(1, qlist.getName());
                        preparedstatement.setInt(2, qlist.getTypeNo());
                        break;
                    case 1:
                        preparedstatement.setString(1, qlist.getName());
                        preparedstatement.setInt(2, qlist.getId());
                        break;
                    case 2:
                        preparedstatement.setString(1, (String)it.next());
                        break;
                }
                preparedstatement.addBatch();
            }
            preparedstatement.executeBatch();
            db.conn.commit();
            b = true;
        } catch (SQLException ex) {
            logger.error(ex);
            try {
                db.conn.rollback();
            } catch (SQLException ex1) {
                logger.error(ex1);
            }
        } finally {
            try {
                db.conn.setAutoCommit(true);
                preparedstatement.close();
                db.conn.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        return b;
    }
}
