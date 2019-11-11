package com.androidx.jetpack.main;

/**
 * Created by meikai on 2019/11/11.
 */
public class TestCase {

    Class<?> activityClass;
    String desc;

    public TestCase(Class<?> activityClass, String desc) {
        this.activityClass = activityClass;
        this.desc = desc;
    }
}
