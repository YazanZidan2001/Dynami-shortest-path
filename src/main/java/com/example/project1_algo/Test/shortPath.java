package com.example.project1_algo.Test;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class shortPath {

    private int n;
    private String start = "";
    private String end = "";
    private List<List<Integer>>  matrix = new ArrayList<>();
    private Map<String, Integer> cityIndexMap = new HashMap<>();

    private int [][] spArray;

    public shortPath(File file) throws Exception {

        Scanner myReader = new Scanner(file);
        String data = myReader.nextLine();
        n = Integer.valueOf(data);
        spArray = new int[n][n];
        int counter = 1;
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
            if(counter == 1) {
                String[] arr = data.split(",");
                if(arr.length != 2) {
                    throw new Exception(String.format("Invalid start/end line, val = %s", data));
                }
                start = arr[0].replaceAll(" ", "");
                end = arr[1].replaceAll(" ", "");
                cityIndexMap.put(start, 0);
                cityIndexMap.put(end, n-1);
            } else {


                List<String> list = parseLine(data).stream().filter(p->!p.isBlank()).collect(Collectors.toList());

                String sourceCityName = list.get(0);
                registerCity(sourceCityName);

                for(int i =1; i< list.size(); i++) {

                    if(list.get(i).isBlank()) {
                        throw new Exception("Desitnation cannot be empty");
                    }
                    String[] arr = list.get(i).substring(1, list.get(i).length()-1).split(",");
                    if(arr.length != 3) {
                        throw new Exception(String.format("Invalid destination data %s", list.get(i)));
                    }
                    String destinationCityName = arr[0];
                    registerCity(destinationCityName);
                    spArray[cityIndexMap.get(sourceCityName)][cityIndexMap.get(destinationCityName)] = Integer.valueOf(arr[2].replaceAll(" ", ""))+Integer.valueOf(arr[1].replaceAll(" ", ""));

                }


            }
            counter++;

        }

        tag:for(int i =0; i < n; i++) {

            for(int j =0; j < n; j++) {
                if(spArray[j][i] !=0) {
                    continue tag;
                }
                int tempMin = Integer.MAX_VALUE;
                for(int u = j; u < n; u++) {

                    if (spArray[u][i] == 0) {
                        continue;
                    }
                    tempMin = Math.min(tempMin, spArray[u][i] + spArray[j][u]);
                }
                spArray[j][i] = tempMin;
            }

        }
        myReader.close();


    }

    public int getShortestPath(String source, String destination) {
        if(!cityIndexMap.containsKey(source)) {
            throw new RuntimeException("Invalid source "+source);
        }

        if(!cityIndexMap.containsKey(destination)) {
            throw new RuntimeException("Invalid destination "+destination);
        }
        int sourceIndex = cityIndexMap.get(source);
        int destinationIndex = cityIndexMap.get(destination);

        return spArray[sourceIndex][destinationIndex];
    }



    private void registerCity(String cityName) {
        if(cityIndexMap.get(cityName) == null) {
            cityIndexMap.put(cityName, cityIndexMap.keySet().size()-1);
        }
    }

    private static List<String> parseLine(String line) {
        List<String> segments = new ArrayList<>();

        int start = 0;
        int end = 0;
        boolean inSegment = false;
        int bracketsCount = 0;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '[') {
                bracketsCount++;
                inSegment = true;
            } else if (c == ']') {
                bracketsCount--;
            }

            if (!inSegment && (c == ',' || c == '[')) {
                end = i;
                segments.add(line.substring(start, end).trim());
                start = end + 1;
            }

            if (bracketsCount == 0 && inSegment && c == ']') {
                segments.add(line.substring(start, i + 1).trim());
                inSegment = false;
                start = i + 1;
            }
        }

        if (start < line.length()) {
            segments.add(line.substring(start).trim());
        }

        return segments;
    }

//    @Override
//    public String toString() {
//        return "DPShortestPath{" +
//                "n=" + n +
//                ", start='" + start + '\'' +
//                ", end='" + end + '\'' +
//                ", pathList=" + pathList +
//                ", matrix=" + matrix +
//                '}';
//    }
}
