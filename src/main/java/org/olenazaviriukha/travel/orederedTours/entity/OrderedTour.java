package org.olenazaviriukha.travel.orederedTours.entity;

import org.olenazaviriukha.travel.tours.entity.Tour;
import org.olenazaviriukha.travel.users.entity.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class OrderedTour implements Serializable {
    private Integer id;
    private Integer tourId;
    private Tour tour;
    private Integer userId;
    private User user;
    private Integer discount;
    private BigDecimal fixedPrice;
    private Status status;
    private String annotation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
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

    public BigDecimal getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(BigDecimal fixedPrice) {
        this.fixedPrice = fixedPrice;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getFinalPrice() {
        float percent = 100 - discount;
        percent = percent / 100;
        BigDecimal finalPrice = fixedPrice.multiply(BigDecimal.valueOf(percent));
        return finalPrice;
    }

    public enum Status {
        REGISTERED,
        PAID,
        CANCELLED,
        COMPLETED
    }
}
