package com.aeroplane.plan;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;


public class SeatPlanGeneratorTests {

    SeatPlanGenerator seatPlanGenerator=new SeatPlanGenerator();

    @Test
    public void generateSeatPlanTestPositive() {
        assertTrue(Arrays.deepEquals(
                MockDataUtil.SEAT_PLAN,
                seatPlanGenerator.generateSeatPlan(MockDataUtil.CURR_SEAT_PLAN_META_DATA,
                        MockDataUtil.TOTAL_PASSENGER_COUNT)));
    }

    @Test
    public void generateSeatPlanTestNegative() {
        assertNull(
                seatPlanGenerator.generateSeatPlan(null,
                        MockDataUtil.TOTAL_PASSENGER_COUNT));
        assertNull(
                seatPlanGenerator.generateSeatPlan(MockDataUtil.CURR_SEAT_PLAN_META_DATA,
                        -1));
    }

    @Test
    public void getInitialSeatPlanTest() {
        String[][] initialSeatPlan=seatPlanGenerator.getInitialSeatPlan(MockDataUtil.CURR_SEAT_PLAN_META_DATA);
        assertEquals(MockDataUtil.SEAT_PLAN_NAME_MAPPING.length,initialSeatPlan.length);
        assertEquals(MockDataUtil.SEAT_PLAN_NAME_MAPPING[0].length,initialSeatPlan[0].length);
    }

    @Test
    public void chunkProcessing() {
        assertTrue(MockDataUtil.CHUNK_INDICES
                .equals(seatPlanGenerator.chunkProcessing(
                        MockDataUtil.CURR_SEAT_PLAN_META_DATA,
                        new String[MockDataUtil.SEAT_PLAN_NAME_MAPPING.length]
                                [MockDataUtil.SEAT_PLAN_NAME_MAPPING[0].length])));
    }

    @Test
    public void generateSeatTypeMap() {
        assertTrue(MockDataUtil.AISLE_INITIAL_SEAT_MAP.size()
                        +MockDataUtil.WINDOW_INITIAL_SEAT_MAP.size()
                        +MockDataUtil.MIDDLE_INITIAL_SEAT_MAP.size()
                ==seatPlanGenerator.generateSeatTypeMap(
                MockDataUtil.SEAT_PLAN_NAME_MAPPING,
                new LinkedHashMap<>(), new LinkedHashMap<>(),new LinkedHashMap<>()));
    }

    @Test
    public void updateSeatPlan() {
        String[][] seatPlan=seatPlanGenerator.updateSeatPlan(MockDataUtil.SEAT_PLAN_NAME_MAPPING,
                MockDataUtil.WINDOW_INITIAL_SEAT_MAP,
                MockDataUtil.AISLE_INITIAL_SEAT_MAP,
                MockDataUtil.MIDDLE_INITIAL_SEAT_MAP,
                MockDataUtil.TOTAL_PASSENGER_COUNT);
        assertTrue(Arrays.deepEquals(seatPlan,MockDataUtil.SEAT_PLAN));
    }

}
