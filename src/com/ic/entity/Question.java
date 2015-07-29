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
@Table(name = "question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "type_no")
    private int typeNo;

    public Question() {
    }

    public Question(String name, int typeNo) {
        this.name = name;
        this.typeNo = typeNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(int typeNo) {
        this.typeNo = typeNo;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {  
            return false;  
        }  
        if (obj == this) {  
            return true;  
        }  
        if (!(obj instanceof Question)) {  
            return false;  
        }  
        Question q = (Question) obj;  
        if (Objects.equals(q.getId(), this.getId()) || Objects.equals(q.getName(), this.getName())) {  
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
