package com.example.project1_algo.Test;

import com.example.project1_algo.DPCell;
import com.example.project1_algo.DPPath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DPShortestPathWithAlternatives {

    private int n;
    private String start = "";
    private String end = "";
    private List<List<Integer>> matrix = new ArrayList<>();
    private Map<String, Integer> cityIndexMap = new HashMap<>();

    private DPCell[][] spArray;

    public DPShortestPathWithAlternatives(File file) throws Exception {

        Scanner myReader = new Scanner(file);
        String data = myReader.nextLine();
        n = Integer.valueOf(data);
        spArray = new DPCell[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                spArray[j][i] = new DPCell();
            }
        }


        int counter = 1;
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
            if (counter == 1) {
                String[] arr = data.split(",");
                if (arr.length != 2) {
                    throw new Exception(String.format("Invalid start/end line, val = %s", data));
                }
                start = arr[0].replaceAll(" ", "");
                end = arr[1].replaceAll(" ", "");
                cityIndexMap.put(start, 0);
                cityIndexMap.put(end, n - 1);
            } else {


                List<String> list = parseLine(data).stream().filter(p -> !p.isBlank()).collect(Collectors.toList());

                String sourceCityName = list.get(0);
                registerCity(sourceCityName);

                for (int i = 1; i < list.size(); i++) {

                    if (list.get(i).isBlank()) {
                        throw new Exception("Desitnation cannot be empty");
                    }
                    String[] arr = list.get(i).substring(1, list.get(i).length() - 1).split(",");
                    if (arr.length != 3) {
                        throw new Exception(String.format("Invalid destination data %s", list.get(i)));
                    }
                    String destinationCityName = arr[0];
                    registerCity(destinationCityName);
                    spArray[cityIndexMap.get(sourceCityName)][cityIndexMap.get(destinationCityName)].setValue(Integer.valueOf(arr[2].replaceAll(" ", "")) + Integer.valueOf(arr[1].replaceAll(" ", "")));
                    spArray[cityIndexMap.get(sourceCityName)][cityIndexMap.get(destinationCityName)].setCurrentCity(destinationCityName);
                    spArray[cityIndexMap.get(sourceCityName)][cityIndexMap.get(destinationCityName)].getPathList().add(new DPPath());

                    DPPath dpPath = spArray[cityIndexMap.get(sourceCityName)][cityIndexMap.get(destinationCityName)].getPathList().get(0);
                    dpPath.setPath(sourceCityName + "->" + destinationCityName);
                    dpPath.setValue(Integer.valueOf(arr[2].replaceAll(" ", "")) + Integer.valueOf(arr[1].replaceAll(" ", "")));

                }


            }
            counter++;

        }

        tag:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!spArray[j][i].getPathList().isEmpty()) {
                    continue tag;
                }
                int tempMin = Integer.MAX_VALUE;
                String tempPath = "";
                String tempCurrentCity = "";
                for (int u = j; u < n; u++) {

                    if (spArray[u][i].getPathList().isEmpty() && !tempPath.isBlank()) {
                        break;
                    }
                    if (spArray[u][i].getPathList().isEmpty()) {
                        continue;
                    }
                    if (tempCurrentCity.isBlank()) {
                        tempCurrentCity = spArray[u][i].getCurrentCity();
                    }
                    List<DPPath> pathList = spArray[j][u].getPathList();
                    for (int k = 0; k < pathList.size(); k++) {
                        spArray[j][i].getPathList().add(new DPPath(spArray[u][i].getValue() + pathList.get(k).getValue(), pathList.get(k).getPath() + "->" + tempCurrentCity, tempCurrentCity));
                    }

                }
                //tempMin = Math.min(tempMin, spArray[u][i] + spArray[j][u]);
                Collections.sort(spArray[j][i].getPathList(), Comparator.comparingInt(DPPath::getValue));
            }
        }
        myReader.close();

    }

    public int getShortestPathCost(String source, String destination, int degree) {
        if (!cityIndexMap.containsKey(source)) {
            throw new RuntimeException("Invalid source " + source);
        }

        if (!cityIndexMap.containsKey(destination)) {
            throw new RuntimeException("Invalid destination " + destination);
        }
        int sourceIndex = cityIndexMap.get(source);
        int destinationIndex = cityIndexMap.get(destination);

        if (degree > spArray[sourceIndex][destinationIndex].getPathList().size()) {
            throw new RuntimeException("Cant found path between this two cities");
        }

        return spArray[sourceIndex][destinationIndex].getPathList().get(degree - 1).getValue();
    }


    public String getShortestPath(String source, String destination, int degree) {
        if (!cityIndexMap.containsKey(source)) {
            throw new RuntimeException("Invalid source " + source);
        }

        if (!cityIndexMap.containsKey(destination)) {
            throw new RuntimeException("Invalid destination " + destination);
        }

        int sourceIndex = cityIndexMap.get(source);
        int destinationIndex = cityIndexMap.get(destination);

        if (degree > spArray[sourceIndex][destinationIndex].getPathList().size()) {
            throw new RuntimeException("Cant found path between this two cities");
        }

        return spArray[sourceIndex][destinationIndex].getPathList().get(degree - 1).getPath();
    }


    private void registerCity(String cityName) {
        if (cityIndexMap.get(cityName) == null) {
            cityIndexMap.put(cityName, cityIndexMap.keySet().size() - 1);
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
    public String getDynamicTable() {
        StringBuilder tableBuilder = new StringBuilder();

        // Calculate the maximum length of the values in each column
        int[] columnWidths = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (!spArray[i][j].getPathList().isEmpty()) {
                    String value = spArray[i][j].getPathList().get(0).getValue() + " ";
                    columnWidths[j] = Math.max(columnWidths[j], value.length());
                }
            }
        }

        // Print the table with proper formatting
        for (int i = 0; i < n-1; i++) {
            for (int j = 1; j < n; j++) {
                if (spArray[i][j].getPathList().isEmpty()) {
                    tableBuilder.append(String.format("%-" + columnWidths[j] + "s", "0"));
                } else {
                    String value = spArray[i][j].getPathList().get(0).getValue() + " ";
                    tableBuilder.append(String.format("%-" + columnWidths[j] + "s", value));
                }
                tableBuilder.append("\t");
            }
            tableBuilder.append("\n");
        }

        return tableBuilder.toString();
    }

    public String getDynamicTable3(String filePath) {
        StringBuilder tableBuilder = new StringBuilder();

        // Calculate the maximum length of the values in each column
        int[] columnWidths = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (!spArray[i][j].getPathList().isEmpty()) {
                    String value = spArray[i][j].getPathList().get(0).getValue() + " ";
                    columnWidths[j] = Math.max(columnWidths[j], value.length());
                }
            }
        }

        List<String> starts = new ArrayList<>();
        List<String> ends = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            List<String> startPoints = new ArrayList<>();
            List<String> letters = new ArrayList<>();
            List<String> endPoints = new ArrayList<>();

            String line;
            int lineNumber = 0;
            String[] stringArray = new String[2];
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 2) {
                    stringArray = line.split(", ");
                }

                String[] parts = line.split(",");
                String task = parts[0].trim();

                if (task.equals(stringArray[0])) {
                    startPoints.add(task);
                } else if (task.equals(stringArray[1])) {
                    endPoints.add(task);
                } else {
                    starts.add(task);
                    ends.add(task);
                    letters.add(task);
                }
            }

            letters.remove(0);
            starts.add(0, stringArray[0]);
            starts.remove(1);
            ends.add(stringArray[1]);
            ends.remove(0);

            // Calculate the maximum length of the y-axis labels
            int maxLabelWidth = Math.max(stringArray[0].length(), stringArray[1].length());
            for (int i = 0; i < n; i++) {
                String label = "" + i;
                maxLabelWidth = Math.max(maxLabelWidth, label.length());
            }

            // Add y-axis labels


            // Print the table with proper formatting
            tableBuilder.append(String.format("%-" + maxLabelWidth + "s", "")).append("\t");
            for (String label : ends) {
                tableBuilder.append(String.format("%-" + maxLabelWidth + "s", label)).append("\t");
            }
            tableBuilder.append("\n");

            for (int i = 0; i < starts.size(); i++) {
                String label = starts.get(i);
                tableBuilder.append(String.format("%-" + maxLabelWidth + "s", label)).append("\t");
                for (int j = 1; j < n; j++) {
                    if (spArray[i][j].getPathList().isEmpty()) {
                        tableBuilder.append(String.format("%-" + columnWidths[j] + "s", "0"));
                    } else {
                        String value = spArray[i][j].getPathList().get(0).getValue() + " ";
                        tableBuilder.append(String.format("%-" + columnWidths[j] + "s", value));
                    }
                    tableBuilder.append("\t");
                }
                tableBuilder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tableBuilder.toString();
    }




}
