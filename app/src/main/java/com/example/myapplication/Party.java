package com.example.myapplication;

public class Party {
    private String partyId;

    public Party() {
        // Default constructor required for calls to DataSnapshot.getValue(Party.class)
    }

    public Party(String partyId) {
        this.partyId = partyId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
}
