package com.example.schoolupdaterapp.menu;

public class EntityModelClass {

    private String entityName;
    private String firstEntityValue;
    private String secondEntityValue;

    public EntityModelClass() {
    }

    public EntityModelClass(String entityName, String firstEntityValue, String secondEntityValue) {
        this.entityName = entityName;
        this.firstEntityValue = firstEntityValue;
        this.secondEntityValue = secondEntityValue;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getFirstEntityValue() {
        return firstEntityValue;
    }

    public void setFirstEntityValue(String firstEntityValue) {
        this.firstEntityValue = firstEntityValue;
    }

    public String getSecondEntityValue() {
        return secondEntityValue;
    }

    public void setSecondEntityValue(String secondEntityValue) {
        this.secondEntityValue = secondEntityValue;
    }
}
