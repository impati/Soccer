package com.example.soccerleague.domain.director;

import com.example.soccerleague.domain.BaseEntity;
import com.example.soccerleague.domain.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Director extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="director_id")
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="team_Id")
    private Team team;

    // 연관관계 편의 메서드
    public void setTeam(Team team){

        if(this.team != null) this.team.setDirector(null);
        this.team = team;

        if(team != null)
            team.setDirector(this);

    }

    public Director(String name) {
        this.name = name;
    }
    public void edit(String name,Team team){
        this.name =  name;
        setTeam(team);
    }
}
