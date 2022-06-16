package com.example.soccerleague.SearchService.Round.LineUp;

import java.util.Comparator;

public class LineUpPlayerCmpByPosition implements Comparator<LineUpPlayer> {
    @Override
    public int compare(LineUpPlayer o1, LineUpPlayer o2) {
        if(o1.getPosition().ordinal() > o2.getPosition().ordinal()) return 1;
        else if (o1.getPosition().ordinal() < o2.getPosition().ordinal())return -1;
        else return 0;
    }
}
