package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 29-11-2016 at 18:46.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class Base {
    private int id;
    private String name;
    private Set<Zone> zones = new HashSet<>(0);

    public Base() {
    }

    public Base(int id, String name) {
        this.id = id;
        this.name = name;
    }

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

    @Override
    public String toString() {
        if (name != null)
            return name;
        return super.toString();
    }
}
