/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.bean;

import com.ic.entity.Iso;
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
public class IsoBean implements Serializable {

    private DbBean db = null;
    private PreparedStatement preparedstatement = null;
    private static Logger logger = null;
    
    public IsoBean(){
        BasicConfigurator.configure();
        logger = Logger.getLogger(IsoBean.class);
    }

    //查詢所有iso資料
    public List getIso() {

        db = new DbBean();
        List isolist = null;
        try {
            ResultSet rs = db.search("select * from iso");
            isolist = new ArrayList();
            while (rs.next()) {
                Iso iso = new Iso();
                iso.setId(rs.getInt("id"));
                iso.setName(rs.getString("name"));
                iso.setQNo(rs.getInt("q_no"));
                isolist.add(iso);
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
        return isolist;
    }

    //查詢iso資料by問題
    public List getIsoByQuestion(int q_no) {
        List list = getIso();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Iso isolist = (Iso) it.next();
            if (isolist.getQNo() != q_no) {
                it.remove();
            }
        }
        return list;
    }

    //新增iso  //0新增 1修改 2刪除
    public boolean insertIso(List iso) {
        String sql = "insert into iso(name,q_no) values (?,?)";
        boolean b = alterIso(iso, sql, 0);
        return b;
    }

    //修改iso
    public boolean updateIso(List iso) {
        String sql = "update iso set name=? where id=?";
        boolean b = alterIso(iso, sql, 1);
        return b;
    }

    //刪除iso
    public boolean deleteIso(List iso_num) {
        String sql = "delete from iso where id=?";
        boolean b = alterIso(iso_num, sql, 2);
        return b;
    }

    public boolean alterIso(List iso, String sql, int type) {
        db = new DbBean();
        boolean b = false;
        Iso isolist = null;
        try {
            db.conn.setAutoCommit(false);
            Iterator it = iso.iterator();
            preparedstatement = db.conn.prepareStatement(sql);//*
            while (it.hasNext()) {
                if (type < 2) {
                    isolist = (Iso) it.next();
                }
                switch (type) {
                    case 0:
                        preparedstatement.setString(1, isolist.getName());
                        preparedstatement.setInt(2, isolist.getQNo());
                        break;
                    case 1:
                        preparedstatement.setString(1, isolist.getName());
                        preparedstatement.setInt(2, isolist.getId());
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
