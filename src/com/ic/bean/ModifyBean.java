/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.bean;

import com.ic.entity.Modify;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
public class ModifyBean implements Serializable {

    private static Logger logger = null;

    public ModifyBean() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(ModifyBean.class);
    }

    public boolean setRecord(List modifylist) {
        DbBean db = new DbBean();
        boolean b = false;
        String sql = "insert into modify_record(users_id, mdate, mtime, maction, target_data, mnumber, u_ip, id_groups) values(?,?,?,?,?,?,?,?)";
        PreparedStatement preparedstatement = null;
        SimpleDateFormat formatter = null;
        Date date = new Date();
        try {
            db.conn.setAutoCommit(false);
            Iterator it = modifylist.iterator();
            preparedstatement = db.conn.prepareStatement(sql);//*
            while (it.hasNext()) {
                Modify mlist = (Modify) it.next();
                preparedstatement.setInt(1, mlist.getId());
                formatter = new SimpleDateFormat("yyyy/MM/dd");
                preparedstatement.setString(2, formatter.format(date));
                formatter = new SimpleDateFormat("HH:mm:ss");
                preparedstatement.setString(3, formatter.format(date));
                preparedstatement.setString(4, mlist.getMaction());
                preparedstatement.setString(5, mlist.getTarget_data());
                preparedstatement.setInt(6, mlist.getMnumber());
                preparedstatement.setString(7, mlist.getU_ip());
                preparedstatement.setString(8, mlist.getId_groups());
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
