package com.example.soccerleague.Web.newDto.cmp;

import com.example.soccerleague.Web.newDto.record.LeaguePlayerRecordDto;
import com.example.soccerleague.domain.Direction;
import com.example.soccerleague.domain.SortType;

import java.util.Comparator;

public class LeaguePlayerRecordCmp implements Comparator<LeaguePlayerRecordDto> {
    @Override
    public int compare(LeaguePlayerRecordDto o1, LeaguePlayerRecordDto o2) {
        if(o1.getSortType().equals(SortType.GOAL)){
            if(o1.getDirection().equals(Direction.ASC)){
                if(o1.getGoal() > o2.getGoal())return 1;
                else return -1;
            }
            else{
                if(o1.getGoal() > o2.getGoal())return -1;
                else return 1;
            }
        }
        else if(o1.getSortType().equals(SortType.ASSIST)){
            if(o1.getDirection().equals(Direction.ASC)){
                if(o1.getAssist() > o2.getAssist())return 1;
                else return -1;
            }
            else{
                if(o1.getAssist() > o2.getAssist())return -1;
                else return 1;
            }
        }
        else if(o1.getSortType().equals(SortType.ATTACKPOINT)){
            if(o1.getDirection().equals(Direction.ASC)){
                if(o1.getAttackPoint() > o2.getAttackPoint())return 1;
                else return -1;
            }
            else{
                if(o1.getAttackPoint() > o2.getAttackPoint())return -1;
                else return 1;
            }
        }
        else if(o1.getSortType().equals(SortType.SHOOTING)){
            if(o1.getDirection().equals(Direction.ASC)){
                if(o1.getShooting() > o2.getShooting())return 1;
                else return -1;
            }
            else{
                if(o1.getShooting() > o2.getShooting())return -1;
                else return 1;
            }
        }
        else if(o1.getSortType().equals(SortType.VALIDSHOOTING)){
            if(o1.getDirection().equals(Direction.ASC)){
                if(o1.getValidShooting() > o2.getValidShooting())return 1;
                else return -1;
            }
            else{
                if(o1.getValidShooting() > o2.getValidShooting())return -1;
                else return 1;
            }
        }
        else if(o1.getSortType().equals(SortType.FOUL)){
            if(o1.getDirection().equals(Direction.ASC)){
                if(o1.getFoul() > o2.getFoul())return 1;
                else return -1;
            }
            else{
                if(o1.getFoul() > o2.getFoul())return -1;
                else return 1;
            }
        }
        else if(o1.getSortType().equals(SortType.PASS)){
            if(o1.getDirection().equals(Direction.ASC)){
                if(o1.getPass() > o2.getPass())return 1;
                else return -1;
            }
            else{
                if(o1.getPass() > o2.getPass())return -1;
                else return 1;
            }
        }
        else{
            if(o1.getDirection().equals(Direction.ASC)){
                if(o1.getDefense() > o2.getDefense())return 1;
                else return -1;
            }
            else{
                if(o1.getDefense() > o2.getDefense())return -1;
                else return 1;
            }
        }
    }
}
