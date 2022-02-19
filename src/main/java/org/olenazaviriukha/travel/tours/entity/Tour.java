package org.olenazaviriukha.travel.tours.entity;

import org.olenazaviriukha.travel.hotels.dao.HotelDAO;
import org.olenazaviriukha.travel.hotels.entity.Hotel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Tour implements Serializable {
    private Integer id;
    private String name;
    private TourType tourType;
    private Hotel hotel = null;
    private Integer hotelId;
    private Integer guestsNumber;
    private LocalDate startDay;
    private LocalDate endDay;
    private BigDecimal price;
    private Integer maxDiscount;
    private Integer discountStep;
    private Boolean hot;
    private String description;

    public enum TourType{
        RELAX,
        EXCURSION,
        SHOPPING;

        public String getFriendlyName() {
            return name().toLowerCase();
        }
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

    public void setHotelId(Integer hotelId){
        this.hotelId = hotelId;
    }

    public Integer getHotelId() {
        if (this.hotelId == null && this.hotel != null)
            return this.hotel.getId();
        return this.hotelId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TourType getTourType() {
        return tourType;
    }

    public void setTourType(TourType tourType) {
        this.tourType = tourType;
    }

    public Hotel getHotel() {
        if (this.hotel == null && this.hotelId != null)
            return HotelDAO.getHotelById(this.hotelId);
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Integer getGuestsNumber() {
        return guestsNumber;
    }

    public void setGuestsNumber(Integer guestsNumber) {
        this.guestsNumber = guestsNumber;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Integer maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public Integer getDiscountStep() {
        return discountStep;
    }

    public void setDiscountStep(Integer discountStep) {
        this.discountStep = discountStep;
    }

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tourType=" + tourType +
                ", hotel=" + hotel +
                ", guestsNumber=" + guestsNumber +
                ", startDay=" + startDay +
                ", endDay=" + endDay +
                ", price=" + price +
                ", maxDiscount=" + maxDiscount +
                ", discountStep=" + discountStep +
                ", hot=" + hot +
                ", description='" + description + '\'' +
                '}';
    }
}
