/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ic.entity;

import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "iso")
public class Iso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "q_no")
    private int qNo;

    public Iso() {
    }

    public Iso(String name, int qNo) {
        this.name = name;
        this.qNo = qNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQNo() {
        return qNo;
    }

    public void setQNo(int qNo) {
        this.qNo = qNo;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {  
            return false;  
        }  
        if (obj == this) {  
            return true;  
        }  
        if (!(obj instanceof Iso)) {  
            return false;  
        }  
        Iso i = (Iso) obj;  
        if (Objects.equals(i.getId(), this.getId()) || Objects.equals(i.getName(), this.getName())) {  
            return true;  
        }  
        return false; 
    }

    @Override
    @SuppressWarnings("empty-statement")
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id) + Objects.hashCode(this.name);
        return hash;
    }
    
}
