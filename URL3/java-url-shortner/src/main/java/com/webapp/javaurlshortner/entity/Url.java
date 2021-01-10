package com.webapp.javaurlshortner.entity;

import javax.persistence.*;
import java.util.Date;

// An entity is a row of table in database--> Each row is an entity

@Entity
public class Url {

    /*
    The @Id annotation is inherited from javax.persistence.Id， indicating the member field below is the primary key of current entity.
    Hence Hibernate and spring framework as well as we can do some reflect works based on this annotation
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /*
    The @GeneratedValue annotation is to configure the way of increment of the specified column(field).
    For example when using Mysql, we can specify auto_increment in the definition of table to make it self-incremental
     */
    @Column(nullable = false)
    private String longUrl; // Long Url Passed by User

    /*
    in the Java code to denote that you also acknowledged to use this database server side strategy.
    Also, we can change the value in this annotation to fit different requirements
     */
    @Column(nullable = false)
    private Date createdDate;

    @Column(nullable = true)
    private Date expiresDate; // Only required if user wishes to delete the row after n days

    // Auto Generated Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Date expiresDate) {
        this.expiresDate = expiresDate;
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", longUrl='" + longUrl + '\'' +
                ", createdDate=" + createdDate +
                ", expiresDate=" + expiresDate +
                '}';
    }
}
