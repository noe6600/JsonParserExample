package com.kiory.jsonparserexample.datastructure;

import com.kiory.jsonparserexample.utils.AlternativeName;

import java.util.List;

public class GameCharacter {

    private String character;
    @AlternativeName("class")
    private String classCharacter;
    private int level;
    private String race;
    private long gold;
    private List<Armor> armor;

    public GameCharacter(){}

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getClassCharacter() {
        return classCharacter;
    }

    public void setClassCharacter(String classCharacter) {
        this.classCharacter = classCharacter;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public List<Armor> getArmor() {
        return armor;
    }

    public void setArmor(List<Armor> armor) {
        this.armor = armor;
    }
}
