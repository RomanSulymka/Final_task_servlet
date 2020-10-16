package com.travel_agency.entity;

public enum TransportType {

    PLAIN(1), TRAIN(2), BUS(3), AUTO(4);

    private int id;

    private TransportType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static TransportType getValue(int id) {
        TransportType transportType = null;
        if (id == 1) {
            transportType = PLAIN;
        } else if (id == 2) {
            transportType = TRAIN;
        } else if (id == 3) {
            transportType = BUS;
        } else if (id == 4) {
            transportType = AUTO;
        }
        return transportType;
    }
}
