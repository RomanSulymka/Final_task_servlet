package com.travel_agency.entity;

import com.travel_agency.exceptions.TravelAgencyDataWrongException;

import java.io.Serializable;
import java.util.Objects;


public abstract class Entity implements Serializable, Cloneable {

    private int id;

    public Entity() {
    }

    public Entity(int id) {
        if (id > 0) {
            this.id = id;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws TravelAgencyDataWrongException {
        if (id > 0) {
            this.id = id;
        } else {
            throw new TravelAgencyDataWrongException("Incorrect id value.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "id=" + id + ", ";
    }
}
