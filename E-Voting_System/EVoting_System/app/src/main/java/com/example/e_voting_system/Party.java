package com.example.e_voting_system;

public class Party {
    private boolean selected;
    private int logo;
    private String name;

    public Party(boolean selected, int logo, String name) {
        this.selected = selected;
        this.logo = logo;
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
