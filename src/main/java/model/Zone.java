package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 29-11-2016 at 18:47.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class Zone {
    private int id;
    private String name;
    private Base base;
    private Set<Engine> engines = new HashSet<>(0);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Set<Engine> getEngines() {
        return engines;
    }

    public void setEngines(Set<Engine> engines) {
        this.engines = engines;
    }


    @Override
    public String toString() {
        if (name != null)
            return name;
        return super.toString();
    }
}
