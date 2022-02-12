package org.olenazaviriukha.travel.hotels.model;

import java.io.Serializable;

public class Hotel implements Serializable {
    private Integer id;
    private String name;
    private Integer hotelType;
    private String description;
    private String image;

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

    public Integer getHotelType() {
        return hotelType;
    }

    public void setHotelType(Integer hotelType) {
        this.hotelType = hotelType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hotelType=" + hotelType +
                ", description='" + description + '\'' +
                '}';
    }
}
