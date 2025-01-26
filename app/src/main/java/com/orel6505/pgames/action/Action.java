package com.orel6505.pgames.action;

import com.orel6505.pgames.entity.Entity;

public class Action implements Entity {
    private String name;

    public Action(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Action other = (Action) obj;
        return name != null ? name.equals(other.name) : other.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
