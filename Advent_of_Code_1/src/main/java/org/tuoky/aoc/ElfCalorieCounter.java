package org.tuoky.aoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ElfCalorieCounter {

    private static final String fileName = "input.txt";
    private List<Elf> lisOfElves;

    public List<Elf> getLisOfElves() {
        return lisOfElves;
    }

    ElfCalorieCounter(){
        InputStream is = this.getFileFromResourceAsStream(fileName);
        this.lisOfElves = new ArrayList<>();
        this.setUpElves(is);
        //printInputStream(is);
    }

    private void setUpElves(InputStream is) {
        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if(line.equals("")){
                    this.lisOfElves.add(new Elf(sb.toString()));
                    sb.setLength(0);
                } else if (sb.isEmpty()) {
                    sb.append(line);
                } else {
                    sb.append(String.format(":%s",line));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }


    private static void printInputStream(InputStream is) {

        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getMostCalories() {
        int calories = 0;
        for (Elf e : lisOfElves){
            if (e.getCalories() >= calories){
                calories = e.getCalories();
            }
        }
        return calories;
    }

    public int get3MostCalories() {
        Elf[] temp = {new Elf(), new Elf(), new Elf()};
        for (Elf e : lisOfElves){
            if(e.getCalories() > temp[0].getCalories()){
                temp[0] = e;
            }
            Collections.sort(Arrays.asList(temp));
        }
        return Arrays.asList(temp).stream().mapToInt(Elf::getCalories).sum();
    }


}
