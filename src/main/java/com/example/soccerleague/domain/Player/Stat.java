package com.example.soccerleague.domain.Player;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id")
    private Long id;

    //피지컬
    private int acceleration; // 가속력
    private int speed;//속력
    private int physicalFight;  //몸싸움

    private int stamina ;// 체력
    private int activeness; // 적극성
    private int jump; //점프력
    private int balance; // 밸런스 - > 넘어지냐 안넘어지냐


    // 패스
    private int ballControl; // 볼컨
    private int crosses;// 크로스

    private int pass; // 패스
    private int longPass; //롱 패스

    //공격력
    private int dribble; //
    private int goalDetermination; // 결정력
    private int midRangeShot; // 중거리슛
    private int shootPower; // 슛파워
    private int heading; // 헤딩

    //수비
    private int defense; //수비력
    private int tackle; // 태클
    private int intercepting; // 가로채기
    private int slidingTackle; // 슬라이딩 태클

    //골기퍼
    private int diving; // 다이빙
    private int handling; // 핸들링
    private int goalKick; //골킥
    private int speedReaction; // 반응속도

    //기타
    private int positioning; // 위치선정
    private int visualRange; // 시야
    private int sense; // 센스

    /**
     *
     * when:player 생성시
     * do : stat 정보 생성
     */
    public static Stat createStat(
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
        Stat stat = new Stat();
        stat.setAcceleration(acceleration);
        stat.setSpeed(speed);
        stat.setPass(pass);
        stat.setActiveness(activeness);
        stat.setBalance(balance);
        stat.setCrosses(crosses);
        stat.setBallControl(ballControl);
        stat.setPhysicalFight(physicalFight);
        stat.setStamina(stamina);
        stat.setJump(jump);
        stat.setLongPass(longPass);
        stat.setDefense(defense);
        stat.setDiving(diving);
        stat.setDribble(dribble);
        stat.setGoalDetermination(goalDetermination);
        stat.setMidRangeShot(midRangeShot);
        stat.setShootPower(shootPower);
        stat.setHeading(heading);
        stat.setTackle(tackle);
        stat.setIntercepting(intercepting);
        stat.setSlidingTackle(slidingTackle);
        stat.setHandling(handling);
        stat.setGoalKick(goalKick);
        stat.setSpeedReaction(speedReaction);
        stat.setPositioning(positioning);
        stat.setVisualRange(visualRange);
        stat.setSense(sense);
        return stat;
    }

    /**
     *
     * when : 업데이트 호출로써 player update 에서 호출함.
     * do : 업데이트
     */
    public void update(
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
        this.setAcceleration(acceleration);
        this.setSpeed(speed);
        this.setPass(pass);
        this.setActiveness(activeness);
        this.setBalance(balance);
        this.setCrosses(crosses);
        this.setBallControl(ballControl);
        this.setPhysicalFight(physicalFight);
        this.setStamina(stamina);
        this.setJump(jump);
        this.setLongPass(longPass);
        this.setDefense(defense);
        this.setDiving(diving);
        this.setDribble(dribble);
        this.setGoalDetermination(goalDetermination);
        this.setMidRangeShot(midRangeShot);
        this.setShootPower(shootPower);
        this.setHeading(heading);
        this.setTackle(tackle);
        this.setIntercepting(intercepting);
        this.setSlidingTackle(slidingTackle);
        this.setHandling(handling);
        this.setGoalKick(goalKick);
        this.setSpeedReaction(speedReaction);
        this.setPositioning(positioning);
        this.setVisualRange(visualRange);
        this.setSense(sense);

    }
}
