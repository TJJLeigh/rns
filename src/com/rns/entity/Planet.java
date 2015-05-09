package com.rns.entity;

public enum Planet {

    MERCURY("mercury", .38f),
    VENUS("venus", 0.91f),
    EARTH("earth", 1f),
    MARS("mars",.38f),
    JUPITER("jupiter", 2.4f),
    SATURN("saturn", .92f),
    URANUS("uranus", .89f),
    NEPTUNE("neptune", 1.1f),
    NOT_A_PLANET("", -1f);

    private Planet(String name, float gravity){
        _Gravity = gravity;
        _Name = name;
    }

    private float _Gravity;
    private String _Name;

    public String getName(){
        return _Name;
    }
    public float getGravity(){
        return _Gravity;
    }

}
