package com.shoptbdddatn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_token")
public class Token extends BaseEntity{
    @Column(length = 1000)
    private String token;

    private Date tokenExpDate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenExpDate() {
        return tokenExpDate;
    }

    public void setTokenExpDate(Date tokenExpDate) {
        this.tokenExpDate = tokenExpDate;
    }

}
