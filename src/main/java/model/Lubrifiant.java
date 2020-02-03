package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 27-11-2016 at 22:34.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class Lubrifiant {
    private Integer id;
    private String name;
    private Set<Engine> engines = new HashSet<Engine>(0);


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
