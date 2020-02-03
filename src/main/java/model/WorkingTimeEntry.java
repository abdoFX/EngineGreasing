package model;

import java.time.LocalDateTime;

/**
 * Created on 30-11-2016 at 20:09.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class WorkingTimeEntry {
    private int id;
    private LocalDateTime dateTime;
    private int wtHours;
    private int wtMinutes;
    private int wtSeconds;


    private Engine engine;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getWtHours() {
        return wtHours;
    }

    public void setWtHours(int wtHours) {
        this.wtHours = wtHours;
    }

    public int getWtMinutes() {
        return wtMinutes;
    }

    public void setWtMinutes(int wtMinutes) {
        this.wtMinutes = wtMinutes;
    }

    public int getWtSeconds() {
        return wtSeconds;
    }

    public void setWtSeconds(int wtSeconds) {
        this.wtSeconds = wtSeconds;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
