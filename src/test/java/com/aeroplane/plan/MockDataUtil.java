package com.aeroplane.plan;

import java.util.*;

public class MockDataUtil {

    public static final int TOTAL_PASSENGER_COUNT=30;

    public static final int[][] CURR_SEAT_PLAN_META_DATA = {
            {3, 2},
            {4, 3},
            {2, 3},
            {3, 4}
    };

    public static final String[][] SEAT_PLAN_NAME_MAPPING = {
            {"W","M","A","A","M","M","A","A","A","A","M","W"},
            {"W","M","A","A","M","M","A","A","A","A","M","W"},
            {null,null,null,"A","M","M","A","A","A","A","M","W"},
            {null,null,null,null,null,null,null,null,null,"A","M","W"}
    };

    public static final Set<Integer> CHUNK_INDICES = new HashSet<>(Arrays.asList(new Integer[]{2,6,8}));

    public static final Map<String,Integer> WINDOW_INITIAL_SEAT_MAP=new LinkedHashMap<String, Integer>() {{
        put("0_0", null);
        put("0_11", null);
        put("1_0", null);
        put("1_11", null);
        put("2_11", null);
        put("3_11", null);
    }};
    public static final Map<String,Integer> AISLE_INITIAL_SEAT_MAP=new LinkedHashMap<String, Integer>() {{
        put("0_2", null);
        put("0_3", null);
        put("0_6", null);
        put("0_7", null);
        put("0_8", null);
        put("0_9", null);
        put("1_2", null);
        put("1_3", null);
        put("1_6", null);
        put("1_7", null);
        put("1_8", null);
        put("1_9", null);
        put("2_3", null);
        put("2_6", null);
        put("2_7", null);
        put("2_8", null);
        put("2_9", null);
        put("3_9", null);
    }};
    public static final Map<String,Integer> MIDDLE_INITIAL_SEAT_MAP=new LinkedHashMap<String, Integer>() {{
        put("0_1", null);
        put("0_4", null);
        put("0_5", null);
        put("0_10", null);
        put("1_1", null);
        put("1_4", null);
        put("1_5", null);
        put("1_10", null);
        put("2_4", null);
        put("2_5", null);
        put("2_10", null);
        put("3_10", null);
    }};

    public static final String[][] SEAT_PLAN = {
            {"W_19","M_25","A_1","A_2","M_26","M_27","A_3","A_4","A_5","A_6","M_28","W_20"},
            {"W_21","M_29","A_7","A_8","M_30","M","A_9","A_10","A_11","A_12","M","W_22"},
            {null,null,null,"A_13","M","M","A_14","A_15","A_16","A_17","M","W_23"},
            {null,null,null,null,null,null,null,null,null,"A_18","M","W_24"}
    };

}
