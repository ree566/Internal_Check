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
@Table(name = "catagorized")
public class Catagorized implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;

    public Catagorized() {
    }

    public Catagorized(String name) {
        this.name = name;
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
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {  
            return false;  
        }  
        if (obj == this) {  
            return true;  
        }  
        if (!(obj instanceof Catagorized)) {  
            return false;  
        }  
        Catagorized c = (Catagorized) obj;  
        if (Objects.equals(c.getId(), this.getId()) || Objects.equals(c.getName(), this.getName())) {  
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
