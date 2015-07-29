/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.service;

import com.ic.bean.ModifyBean;
import com.ic.entity.Modify;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wei.Cheng
 */
public class ModifyRecord {

    private StringBuffer success;
    private List modifylist;

    public ModifyRecord() {
        success = new StringBuffer();
        modifylist = new ArrayList();
    }

    public void appendMessage(int user_id, String action, String name, boolean msg, int count, String ip) {
        //以msg 的 boolean判斷修改成敗並記錄在sb
        if (msg) {
            success.append(action);
            success.append(name);
            success.append(" 成功");
            success.append("\\n");
            add_modify_list(user_id, action, name, count, ip);
        }
    }

    public void add_modify_list(int user_id, String action, String dataname, int datacount, String ip) {
        //將修改動作儲存在list之中
        Modify m = new Modify();
        m.setId(user_id);
        m.setMaction(action);
        m.setTarget_data(dataname);
        m.setMnumber(datacount);
        m.setU_ip(ip);
        modifylist.add(m);
    }

    public boolean record_modify() {
        //將記錄儲存到資料庫
        ModifyBean mBean = new ModifyBean();
        Boolean b = mBean.setRecord(modifylist);
        return b;
    }

    public StringBuffer getSuccess() {
        return success;
    }

    public void setSuccess(StringBuffer success) {
        this.success = success;
    }

    public List getModifylist() {
        return modifylist;
    }

    public void setModifylist(List modifylist) {
        this.modifylist = modifylist;
    }

}
