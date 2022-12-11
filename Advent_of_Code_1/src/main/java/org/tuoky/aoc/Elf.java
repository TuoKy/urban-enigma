package org.tuoky.aoc;

public class Elf implements Comparable<Elf>{

    private int calories;

    public Elf (String data){
        calories = 0;

        for (String s : data.split(":")){
            this.calories += Integer.parseInt(s);
        }
    }

    public Elf() {
        this.calories = 0;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public int compareTo(Elf elf) {
        return Integer.compare(getCalories(), elf.getCalories());
    }
}
