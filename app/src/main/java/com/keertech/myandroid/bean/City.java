package com.keertech.myandroid.bean;

import com.yftools.db.annotation.Table;

@Table(name = "t_city")
public class City {

    public String id;
    public String name;
    public String pyf;
    public String pys;
    public Boolean hot;
}
