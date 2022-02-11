package org.olenazaviriukha.travel.entity;

import java.io.Serializable;

public class OrderedTour implements Serializable {
    private Integer id;
    private Integer tourId;
    private Integer userId;
    private Integer discount;
    private Status status;
    private String annotation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTourId() {
        return tourId;
    }

    public void setTourId(Integer tourId) {
        this.tourId = tourId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public enum Status {
        REGISTERED,
        PAID,
        CANCELLED,
        COMPLETED
    }
}
