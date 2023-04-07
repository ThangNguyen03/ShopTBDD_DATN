package com.shoptbdddatn.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="comment_detail",length = 500)
    private String commentDetail;

    @Column(name="comment_name",length =50)
    private String commentName;

    @Column(name="vote")
    private int vote;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date createdAt;
    
}
