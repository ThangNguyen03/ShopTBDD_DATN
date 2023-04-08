package com.shoptbdddatn.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore // khi chuyen du lieu thanh JSON se khong the
    //them thong tin cua customer vao nham bao mat thong tin ve binh luan
    private Customer customer;

    @OneToMany(targetEntity = Reply.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private List<Reply> reply;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentDetail() {
        return commentDetail;
    }

    public void setCommentDetail(String commentDetail) {
        this.commentDetail = commentDetail;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createdAt) {
        this.createAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Reply> getReply() {
        return reply;
    }

    public void setReply(List<Reply> reply) {
        this.reply = reply;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Comment(int id, String commentDetail, String commentName, int vote, Date createAt, Customer customer,
            List<Reply> reply, Product product) {
        this.id = id;
        this.commentDetail = commentDetail;
        this.commentName = commentName;
        this.vote = vote;
        this.createAt = createAt;
        this.customer = customer;
        this.reply = reply;
        this.product = product;
    }

    public Comment() {
        super();
    }
    
    
    
}
