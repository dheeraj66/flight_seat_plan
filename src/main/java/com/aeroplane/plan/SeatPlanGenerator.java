package com.aeroplane.plan;

import java.util.*;

public class SeatPlanGenerator {

    public static final String  WINDOW="W";
    public static final String  AISLE="A";
    public static final String  MIDDLE="M";
    public static final String  SEPARATOR="_";

    public String[][] generateSeatPlan(int[][]currentSeatPlanMetaData,int totalPassengersCount) {
        if(currentSeatPlanMetaData!=null&&totalPassengersCount>0) {
            //Step 1:- Determine Seat Plan Row,Column Count & Get Initial Seat Plan
            int metaRowCount = currentSeatPlanMetaData.length;
            String[][] seatPlan = getInitialSeatPlan(currentSeatPlanMetaData);
            int seatMapRowCount = seatPlan.length;
            int seatMapColumnCount = seatPlan[0].length;

            //Step 2:- Process Each chunk and Update Labelling such as W, M & A
            Set<Integer> chunkGapIndices = chunkProcessing(currentSeatPlanMetaData, seatPlan);

            //Step 3:- Mapping Seat Map with Specified Seat Type
            Map<String, Integer> windowSeatMap = new LinkedHashMap<>();
            Map<String, Integer> aisleSeatMap = new LinkedHashMap<>();
            Map<String, Integer> middleSeatMap = new LinkedHashMap<>();
            generateSeatTypeMap(seatPlan, windowSeatMap, aisleSeatMap, middleSeatMap);

            //Step 4:- Update Seat Plan with Passenger Mapping
            updateSeatPlan(seatPlan, windowSeatMap, aisleSeatMap, middleSeatMap, totalPassengersCount);

            //Step 5:- Print the Seat Plan
            printSeatPlan(seatPlan, chunkGapIndices);

            return seatPlan;
        }
        else
        {
            return null;
        }
    }

    public String[][] getInitialSeatPlan(int[][]currentSeatPlanMetaData) {
        int seatMapColumnCount = 0;
        int seatMapRowCount = currentSeatPlanMetaData[0][1];
        for (int i = 0; i < currentSeatPlanMetaData.length; i++) {
            for (int j = 0; j < 2; j++) {
                if (j == 0) {
                    seatMapColumnCount += currentSeatPlanMetaData[i][j];
                } else {
                    if (seatMapRowCount < currentSeatPlanMetaData[i][j]) {
                        seatMapRowCount = currentSeatPlanMetaData[i][j];
                    }
                }
            }
        }
        return new String[seatMapRowCount][seatMapColumnCount];
    }

    public Set<Integer> chunkProcessing(int[][]currentSeatPlanMetaData,String[][] seatPlan) {
        Set<Integer> chunkGapIndices=new HashSet<>();
        int prevColumnCount=0;
        for (int i = 0; i < currentSeatPlanMetaData.length; i++) {
            int chunkSeatMapColumnCount = currentSeatPlanMetaData[i][0];
            int chunkSeatMapRowCount = currentSeatPlanMetaData[i][1];
            updateSeatPlanNaming(seatPlan,chunkSeatMapRowCount,chunkSeatMapColumnCount,prevColumnCount,i==0?true:false,i==currentSeatPlanMetaData.length-1?true:false,chunkGapIndices);
            prevColumnCount+=chunkSeatMapColumnCount;
        }
        return chunkGapIndices;
    }
    public void updateSeatPlanNaming(String[][] seatPlan,int chunkSeatMapRowCount, int chunkSeatMapColumnCount, int prevColumnCount, boolean isFirstChunk, boolean isLastChunk,Set<Integer> chunkGapIndices) {
        for(int i=0;i<chunkSeatMapRowCount;i++)
        {
            for(int j=0;j<chunkSeatMapColumnCount;j++)
            {
                int newColumnIndex=j+prevColumnCount;
                if(isFirstChunk&&j==0 || isLastChunk&&j==chunkSeatMapColumnCount-1)
                {
                    seatPlan[i][newColumnIndex]=WINDOW;
                }
                else if(j==0||j==chunkSeatMapColumnCount-1)
                {
                    seatPlan[i][newColumnIndex]=AISLE;
                }
                else
                {
                    seatPlan[i][newColumnIndex]=MIDDLE;
                }

                if(j==chunkSeatMapColumnCount-1&&!isLastChunk)
                {
                    chunkGapIndices.add(newColumnIndex);
                }
            }
        }
    }

    public Integer generateSeatTypeMap(String[][] seatPlan,Map<String,Integer> windowSeatMap,Map<String,Integer> aisleSeatMap,Map<String,Integer> middleSeatMap) {

        for (int i = 0; i < seatPlan.length; i++) {
            for (int j = 0; j < seatPlan[0].length; j++) {
                if(seatPlan[i][j]!=null)
                {
                    switch(seatPlan[i][j])
                    {
                        case WINDOW:
                            windowSeatMap.put(i+SEPARATOR+j,null);
                            break;
                        case AISLE:
                            aisleSeatMap.put(i+SEPARATOR+j,null);
                            break;
                        default:
                            middleSeatMap.put(i+SEPARATOR+j,null);
                    }
                }
            }
        }
        return windowSeatMap.size()+aisleSeatMap.size()+middleSeatMap.size();
    }

    public String[][] updateSeatPlan(String[][] seatPlan, Map<String, Integer> windowSeatMap, Map<String, Integer> aisleSeatMap, Map<String, Integer> middleSeatMap,int totalPassengersCount) {
        int totalAisleSeats=aisleSeatMap.size();
        int totalWindowSeats=windowSeatMap.size();
        int totalMiddleSeats=middleSeatMap.size();
        int nextWaitingSlot=updateSeatMap(1,totalAisleSeats>totalPassengersCount?totalPassengersCount:totalAisleSeats,aisleSeatMap);
        if(nextWaitingSlot<=totalPassengersCount)
        {
            nextWaitingSlot=updateSeatMap(nextWaitingSlot,totalWindowSeats>totalPassengersCount-totalAisleSeats?nextWaitingSlot+totalPassengersCount-totalAisleSeats-1:totalAisleSeats+totalWindowSeats,windowSeatMap);
        }
        if(nextWaitingSlot<=totalPassengersCount)
        {
            updateSeatMap(nextWaitingSlot,totalMiddleSeats>totalPassengersCount-totalAisleSeats-totalWindowSeats?nextWaitingSlot+totalPassengersCount-totalAisleSeats-totalWindowSeats-1:totalAisleSeats+totalWindowSeats+totalMiddleSeats,middleSeatMap);
        }

        for (int i = 0; i < seatPlan.length; i++) {
            for (int j = 0; j < seatPlan[0].length; j++) {
                if(seatPlan[i][j]!=null)
                {
                    switch(seatPlan[i][j])
                    {
                        case WINDOW:
                            seatPlan[i][j]=windowSeatMap.get(i+SEPARATOR+j)!=null?seatPlan[i][j]+SEPARATOR+windowSeatMap.get(i+SEPARATOR+j):seatPlan[i][j];
                            break;
                        case AISLE:
                            seatPlan[i][j]=aisleSeatMap.get(i+SEPARATOR+j)!=null?seatPlan[i][j]+SEPARATOR+aisleSeatMap.get(i+SEPARATOR+j):seatPlan[i][j];
                            break;
                        default:
                            seatPlan[i][j]=middleSeatMap.get(i+SEPARATOR+j)!=null?seatPlan[i][j]+SEPARATOR+middleSeatMap.get(i+SEPARATOR+j):seatPlan[i][j];
                    }
                }
            }
        }
        return seatPlan;
    }

    public int updateSeatMap(int start, int end, Map<String, Integer> seatMap) {
        for (Map.Entry<String, Integer> entry : seatMap.entrySet()) {
            entry.setValue(start++);
            if(start>end)
                break;
        }
        return start;
    }

    public  void printSeatPlan(String[][] updatedSeatPlan, Set<Integer> chunkGapIndices) {
        if(updatedSeatPlan!=null)
        {
            int columns=updatedSeatPlan[0].length;
            for (int i = 0; i < updatedSeatPlan.length; i++)
            {
                for (int j = 0; j < columns; j++) {
                    if(updatedSeatPlan[i][j]!=null) {
                        System.out.print(String.format("%-7s", updatedSeatPlan[i][j]));
                    }
                    else
                    {
                        System.out.print(String.format("%-7s"," "));
                    }
                    if(chunkGapIndices.contains(j))
                    {
                        System.out.print("\t\t");
                    }
                }
                System.out.println();
            }
        }
    }
}
