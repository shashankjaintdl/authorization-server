//package com.commerxo.authserver.authserver.domain;
//
//
//import jakarta.persistence.*;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.io.Serializable;
//import java.util.Date;
//
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
//public abstract class Auditable<U> implements Serializable {
//
//    private U createdBy;
//
//    private Date creationDate;
//
//    private U lastModifiedBy;
//
//    private Date lastModifiedDate;
//
//    @CreatedBy
//    public U getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(U createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    @CreatedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_at")
//    public Date getCreationDate() {
//        return creationDate;
//    }
//
//    public void setCreationDate(Date creationDate) {
//        this.creationDate = creationDate;
//    }
//
//    @LastModifiedBy
//    public U getLastModifiedBy() {
//        return lastModifiedBy;
//    }
//
//    public void setLastModifiedBy(U lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }
//
//    @LastModifiedDate
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "updated_at")
//    public Date getLastModifiedDate() {
//        return lastModifiedDate;
//    }
//
//    public void setLastModifiedDate(Date lastModifiedDate) {
//        this.lastModifiedDate = lastModifiedDate;
//    }
//}
