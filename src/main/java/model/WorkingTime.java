package model;

/**
 * Created on 23-12-2016 at 11:54.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class WorkingTime {
    private int wtHours;
    private int wtMinutes;
    private int wtSeconds;

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


    @Override
    public String toString() {
        return wtHours +
                ":" + wtMinutes +
                ":" + wtSeconds;
    }
}
