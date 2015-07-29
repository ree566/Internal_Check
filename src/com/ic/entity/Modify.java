/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Wei.Cheng
 */
@Entity
@Table(name = "modify_record")
public class Modify implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int user_id;
    @Column(name = "mdate")
    private String mdate;
    @Column(name = "mtime")
    private String mtime;
    @Column(name = "maction")
    private String maction;
    @Column(name = "target_data")
    private String target_data;
    @Column(name = "mnumber")
    private int mnumber;
    @Column(name = "u_ip")
    private String u_ip;
    @Column(name = "id_groups")
    private String id_groups;

    public Modify() {
    }

    public Modify(int user_id, String mdate, String maction, String target_data, int mnumber, String u_ip, String id_groups) {
        this.user_id = user_id;
        this.mdate = mdate;
        this.maction = maction;
        this.target_data = target_data;
        this.mnumber = mnumber;
        this.u_ip = u_ip;
        this.id_groups = id_groups;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getMaction() {
        return maction;
    }

    public void setMaction(String maction) {
        this.maction = maction;
    }

    public String getTarget_data() {
        return target_data;
    }

    public void setTarget_data(String target_data) {
        this.target_data = target_data;
    }

    public int getMnumber() {
        return mnumber;
    }

    public void setMnumber(int mnumber) {
        this.mnumber = mnumber;
    }

    public String getU_ip() {
        return u_ip;
    }

    public void setU_ip(String u_ip) {
        this.u_ip = u_ip;
    }

    public String getId_groups() {
        return id_groups;
    }

    public void setId_groups(String id_groups) {
        this.id_groups = id_groups;
    }

}
