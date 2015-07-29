/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.bean;

import com.ic.entity.Answer;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
public class AnswerBean implements Serializable {

    private DbBean db = null;
    private PreparedStatement preparedstatement = null;
    private static Logger logger = null;
    
    public AnswerBean(){
        BasicConfigurator.configure();
        logger = Logger.getLogger(AnswerBean.class);
    }

    //查詢所有解答
    public List getAnswer() {

        db = new DbBean();
        List answerlist = null;
        try {
            ResultSet rs = db.search("select * from answer");
            answerlist = new ArrayList();
            while (rs.next()) {
                Answer ans = new Answer();
                ans.setId(rs.getInt("id"));
                ans.setName(rs.getString("name"));
                ans.setQNo(rs.getInt("q_no"));
                answerlist.add(ans);
            }
        } catch (Exception ex) {
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
        return answerlist;
    }

    //查詢解答by問題
    public List getAnswerByQuestion(int q_no) {
        List list = getAnswer();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Answer answerlist = (Answer) it.next();
            if (answerlist.getQNo() != q_no) {
                it.remove();
            }
        }
        return list;
    }

    //新增解答
    public boolean insertAns(List ans) {
        String sql = "insert into answer(name,q_no) values (?,?)";
        boolean b = alterAnswer(ans, sql, 0);
        return b;
    }

    //修改ans
    public boolean updateAns(List ans) {
        String sql = "update answer set name=? where id=?";
        boolean b = alterAnswer(ans, sql, 1);
        return b;
    }

    //刪除ans
    public boolean deleteAnswer(List ans_num) {
        //delete from answer where id=?
        String sql = "delete from answer where id=?";
        boolean b = alterAnswer(ans_num, sql, 2);
        return b;
    }

    public boolean alterAnswer(List ans, String sql, int type) {
        db = new DbBean();
        boolean b = false;
        Answer anslist = null;
        try {
            db.conn.setAutoCommit(false);
            Iterator it = ans.iterator();
            preparedstatement = db.conn.prepareStatement(sql);
            while (it.hasNext()) {
                if (type < 2) {
                    anslist = (Answer) it.next();
                }
                switch (type) {
                    case 0:
                        preparedstatement.setString(1, anslist.getName());
                        preparedstatement.setInt(2, anslist.getQNo());
                        break;
                    case 1:
                        preparedstatement.setString(1, anslist.getName());
                        preparedstatement.setInt(2, anslist.getId());
                        break;
                    case 2:
                        preparedstatement.setString(1, (String) it.next());
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
