package com.stardust.machine.registry.models;


import java.io.Serializable;

public abstract class DataModel implements Serializable{
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DataModel) {
            return ((DataModel) o).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode(){
        return new Long(getId()).hashCode();
    }

    public boolean validate() {
        return true;
    }
}
