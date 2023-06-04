package com.example.oders2.Gongju;

import com.example.oders2.po.LunarCalendar;

import java.util.Calendar;

public class LunarCalendarUtil {
    // 农历月份名称
    private static final String[] LUNAR_MONTHS = {
            "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"
    };

    // 农历日名称
    private static final String[] LUNAR_DAYS = {
            "初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十",
            "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
            "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"
    };

    // 农历时辰名称
    private static final String[] LUNAR_HOURS = {
            "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"
    };

    public static String solarToLunarWithTime(int year, int month, int day, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, 0); // 设置公历日期和时辰

        LunarCalendar lunarCalendar = new LunarCalendar(calendar);
        int lunarYear = lunarCalendar.getYear();
        int lunarMonth = lunarCalendar.getMonth();
        int lunarDay = lunarCalendar.getDay();
        int lunarHour = lunarCalendar.getHour();

        String lunarMonthName = LUNAR_MONTHS[lunarMonth - 1];
        String lunarDayName = LUNAR_DAYS[lunarDay - 1];
        String lunarHourName = LUNAR_HOURS[lunarHour];

        return "农历日期：" + lunarYear + "年" + lunarMonthName + "月" + lunarDayName +
                " " + lunarHourName + "时";
    }
}
