package jmg.UserStructurecode;
public class Scout {
    private int id;
    private String name;
    private String skill;
    private String location;
    private int totalPlayersScouted;
    private int successfulScouts;

    public Scout(int id, String name, String skill, String location) {
        this.id = id;
        this.name = name;
        this.skill = skill;
        this.location = location;
        this.totalPlayersScouted = 0;
        this.successfulScouts = 0;
    }

    public double getSuccessRate() {
        if (totalPlayersScouted == 0) return 0;
        return (double) successfulScouts / totalPlayersScouted * 100;
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

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalPlayersScouted() {
        return totalPlayersScouted;
    }

    public void setTotalPlayersScouted(int totalPlayersScouted) {
        this.totalPlayersScouted = totalPlayersScouted;
    }

    public int getSuccessfulScouts() {
        return successfulScouts;
    }

    public void setSuccessfulScouts(int successfulScouts) {
        this.successfulScouts = successfulScouts;
    }

    
}
