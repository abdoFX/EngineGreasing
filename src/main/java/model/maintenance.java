package model;

import java.time.LocalDateTime;

/**
 * Created on 27-11-2016 at 22:34.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class maintenance {
    private Integer id;
    private LocalDateTime maintDate;
    private Engine engine;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getMaintDate() {
        return maintDate;
    }

    public void setMaintDate(LocalDateTime maintDate) {
        this.maintDate = maintDate;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}