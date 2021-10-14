package com.heifeng.utils.common.utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XLF
 */
public class DateUtil {

    public static Date addDay(Date start, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    public static Date twoDaysAfter(Date createTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createTime);
        calendar.add(Calendar.DATE, 2);
        return calendar.getTime();
    }

    public static Date addSecond(Date start, Integer second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static Date addHour(Date start, Integer hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    public static String dateToRange(Date startTime, Date endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        String start = sdf.format(startTime);
        String end = sdf.format(endTime);
        return start + "至" + end;
    }

    public static Date addMonth(Date now, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static int getYear(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.get(Calendar.YEAR);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        return calendar.get(Calendar.MONTH);
    }

    public static int getDay(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        return calendar.get(Calendar.DATE);
    }

    public static Date rangeToDate(int range) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date startTime = null;
        switch (range) {
            //近一天
            case 0:
                calendar.add(Calendar.DATE, -1);
                startTime = calendar.getTime();
                break;
            //近一周
            case 1:
                calendar.add(Calendar.WEEK_OF_MONTH, -1);
                startTime = calendar.getTime();
                break;
            //近两周
            case 2:
                calendar.add(Calendar.WEEK_OF_MONTH, -2);
                startTime = calendar.getTime();
                break;
            //近一月
            case 3:
                calendar.add(Calendar.MONTH, -1);
                startTime = calendar.getTime();
                break;
            //近三月
            case 4:
                calendar.add(Calendar.MONTH, -3);
                startTime = calendar.getTime();
                break;
            //近半年
            case 5:
                calendar.add(Calendar.MONTH, -6);
                startTime = calendar.getTime();
                break;
            //近一年
            case 6:
                calendar.add(Calendar.YEAR, -1);
                startTime = calendar.getTime();
                break;
            default:
                break;
        }
        return startTime;
    }

    public static long daysBetween(Date one, Date two) {
        long difference = (one.getTime() - two.getTime()) / 86400000;
        return Math.abs(difference);
    }

    public static Date getTimeByFieldWithoutSeconds(Date originTime, int field, int diff) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(originTime);
        if (field != 0 && diff != 0) {
            calendar.add(field, diff);
        }
        // 设置秒初始
        calendar.set(Calendar.SECOND, 0);
        // 设置毫秒初始
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 数据库查询出来的统计数据有时候日期是不连续的.
     * 但是前端展示要补全缺失的日期.
     * 此方法返回一个给定日期期间的所有日期字符串列表.
     * 具体在业务逻辑中去判断补全.
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static List<String> completionDate(
            LocalDateTime startDate,
            LocalDateTime endDate) {
        //日期格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> dateList = new ArrayList<>();
        //遍历给定的日期期间的每一天
        for (int i = 0; !Duration.between(startDate.plusDays(i), endDate).isNegative(); i++) {
            //添加日期
            dateList.add(startDate.plusDays(i).format(formatter));
        }
        return dateList;
    }


    /**
     * 获得当天零时零分零秒
     *
     * @return
     */
    public static Date today() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得昨天零时零分零秒
     *
     * @return
     */
    public static Date yesterday() {
        Date today = today();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        List<Integer> sortLists = new ArrayList<>();
        sortLists.add(1);
        sortLists.add(4);
        sortLists.add(6);
        sortLists.add(3);
        sortLists.add(2);
        sortLists.stream().sorted((In1,In2)->
                In1-In2).forEach(s -> System.out.println(s));
    }

}

