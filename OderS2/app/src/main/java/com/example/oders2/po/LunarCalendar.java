package com.example.oders2.po;

import java.util.Calendar;

public class LunarCalendar {
    private int year;
    private int month;
    private int day;
    private int hour;

    public LunarCalendar(Calendar calendar) {
        // 获取公历日期和时辰
        int solarYear = calendar.get(Calendar.YEAR);
        int solarMonth = calendar.get(Calendar.MONTH) + 1;
        int solarDay = calendar.get(Calendar.DAY_OF_MONTH);
        int solarHour = calendar.get(Calendar.HOUR_OF_DAY);

        // TODO: 农历日期的计算逻辑
        // 在这里根据公历日期和时辰计算农历日期信息，并设置给年、月、日和时辰的成员变量

        // 示例：假设年份和月份直接等于公历年份和月份
        this.year = solarYear;
        this.month = solarMonth;
        this.day = solarDay;
        this.hour = solarHour;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }
}
