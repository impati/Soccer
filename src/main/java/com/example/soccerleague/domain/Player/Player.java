package com.example.soccerleague.domain.Player;


import com.example.soccerleague.domain.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;


@Entity
@Getter@Setter
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="player_id")
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Position position;

    private int rating;

    @ManyToOne
    @JoinColumn(name ="team_id")
    private Team team;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="stat_id")
    private  Stat stat;


    /***
     * when : 선수를 생성할 떄 레이팅은 '0'
     * @param name
     * @param position
     * @param team
     * @param stat
     * @return
     */
    public static Player createPlayer(String name,Position position,Team team,Stat stat){
        Player player = new Player();
        player.setName(name);
        player.setPosition(position);
        player.setRating(0);
        player.setTeam(team);
        player.setStat(stat);
        return player;
    }
    /**
     * when : 선수 수정 페이지에서 할 떄!
     * do: player update
     */
    public void update(String name,Position position ,Team team,
                       int acceleration,int speed,int physicalFight,
                       int stamina,int activeness,int jump,
                       int balance,int ballControl,int crosses,
                       int pass,int longPass,int dribble,
                       int goalDetermination,int midRangeShot,int shootPower,
                       int heading,int defense,int tackle,
                       int intercepting,int slidingTackle,int diving,
                       int handling,int goalKick,int speedReaction,
                       int positioning, int visualRange, int sense
                       ){
        this.setName(name);
        this.setPosition(position);
        this.setTeam(team);
        this.getStat().update(
                acceleration,speed,physicalFight,
                stamina,activeness,jump,balance,ballControl,
                crosses,pass,longPass,dribble,goalDetermination,midRangeShot,
                shootPower,heading,defense,
                tackle,intercepting,slidingTackle,diving,
                handling,goalKick,speedReaction,positioning,visualRange,sense);
    }

}
