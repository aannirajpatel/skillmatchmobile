package com.aanpatel.skillmatch3;

/**
 * Created by Gamer Patel on 12/11/18.
 */

public class Skill {
    private String skillName, skillLevel;

    public Skill(String skillName, String skillLevel) {
        this.skillName = skillName;
        this.skillLevel = skillLevel;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }
}
