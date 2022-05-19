package com.example.soccerleague.domain.director;

import com.example.soccerleague.domain.BaseEntity;
import com.example.soccerleague.domain.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @OneToOne(mappedBy = "director",fetch = FetchType.LAZY)
    @JoinColumn(name ="team_Id")
    private Team team;


}
