package com.crossover.trial.journals.dto;

import com.crossover.trial.journals.model.Category;

public class SubscriptionDTO {

    private long id;

    private String name;

    private boolean active;

    public SubscriptionDTO(Category c) {
        name = c.getName();
        id = c.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubscriptionDTO that = (SubscriptionDTO) o;

        return id == that.id;

    }
}
