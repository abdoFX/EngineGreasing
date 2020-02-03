package model;

import javax.persistence.PostLoad;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created on 29-11-2016 at 18:49.
 * Project : Engine Greasing application
 * Developper: Bouamer Abdelwaheb
 */
public class Engine {
    private int id;
    private String type;
    private String designation;
    private int frequincy = 0;
    private String lubrifiantConsumation;



    private WorkingTimeType workingTimeType;
    private LocalDateTime startSetupDate;
    private LocalDateTime lastMaintenanceDate;


    private Double workingTime = 0.0;
    private Zone zone;
    private Lubrifiant lubrifiant;

    private Set<WorkingTimeEntry> workingTimeEntries = new HashSet<>(0);
    private Set<maintenance> maintenances = new HashSet<>(0);


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getFrequincy() {
        return frequincy;
    }

    public void setFrequincy(int frequincy) {
        this.frequincy = frequincy;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Lubrifiant getLubrifiant() {
        return lubrifiant;
    }

    public void setLubrifiant(Lubrifiant lubrifiant) {
        this.lubrifiant = lubrifiant;
    }

    public Set<WorkingTimeEntry> getWorkingTimeEntries() {
        return workingTimeEntries;
    }

    public void setWorkingTimeEntries(Set<WorkingTimeEntry> workingTimeEntries) {
        this.workingTimeEntries = workingTimeEntries;
    }

    public Set<maintenance> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(Set<maintenance> maintenances) {
        this.maintenances = maintenances;
    }


    public Double getWorkingTime() {
        if (workingTimeType == WorkingTimeType.HOUR) {
            Double workingTimesHours = (double)
                    workingTimeEntries.stream()
                            .filter(e -> e.getDateTime().isAfter(getLastMaintenanceDate())

                            )
                            .map(workinghour -> workinghour.getWtHours())
                            .collect(Collectors.summingInt(Integer::intValue));
            Double workingTimesMinutes = (double)
                    workingTimeEntries.stream()
                            .filter(e -> e.getDateTime().isAfter(getLastMaintenanceDate())

                            )
                            .map(workinghour -> workinghour.getWtMinutes())
                            .collect(Collectors.summingInt(Integer::intValue));
            System.out.println("");
            if (workingTimesMinutes >= 60) {
                workingTimesHours += workingTimesMinutes / 60;
                workingTime = workingTimesHours;
            } else {

                workingTime = workingTimesHours + (workingTimesMinutes / 60);
            }
            return workingTime;

        } else if (workingTimeType == WorkingTimeType.PERIOD) {

            Period period = Period.between(getLastMaintenanceDate().toLocalDate(), LocalDateTime.now().toLocalDate());
            Double days = ((double) period.getMonths() + ((double) period.getDays() / 30));
            return days;
        }

        return 0.0;
    }

    public void setWorkingTime(Double workingTime) {
        this.workingTime = workingTime;
    }

    public LocalDateTime getLastMaintenanceDate() {
        if (getMaintenances().size() <= 0)
            lastMaintenanceDate = startSetupDate;
        else
            lastMaintenanceDate = getMaintenances().stream().map(e -> e.getMaintDate())
                    .max(LocalDateTime::compareTo).get();

        return lastMaintenanceDate;

    }

    public void setLastMaintenanceDate(LocalDateTime lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public LocalDateTime getStartSetupDate() {
        return startSetupDate;
    }

    public void setStartSetupDate(LocalDateTime startSetupDate) {
        this.startSetupDate = startSetupDate;
    }

    public WorkingTimeType getWorkingTimeType() {
        return workingTimeType;
    }

    public void setWorkingTimeType(WorkingTimeType workingTimeType) {
        this.workingTimeType = workingTimeType;
    }

    @Override
    public String toString() {
        if (designation != null && type != null)
            return type + ":" + designation;
        return super.toString();
    }

    public String getLubrifiantConsumation() {
        return lubrifiantConsumation;
    }

    public void setLubrifiantConsumation(String lubrifiantConsumation) {
        this.lubrifiantConsumation = lubrifiantConsumation;
    }
}
