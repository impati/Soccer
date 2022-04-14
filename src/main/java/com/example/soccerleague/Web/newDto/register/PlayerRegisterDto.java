package com.example.soccerleague.Web.newDto.register;

import com.example.soccerleague.Web.dto.Player.PlayerSaveDto;
import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.domain.Player.Stat;
import com.example.soccerleague.domain.Team;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlayerRegisterDto extends DataTransferObject {
    private Long playerId;
    // 받을 데이터
    private String name;
    private Long teamId;
    private Position position;
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

    public static PlayerRegisterDto PlayerRegisterDto(
            String name,Long teamId,Position position,
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
        PlayerRegisterDto saveDto = new PlayerRegisterDto();
        saveDto.setName(name);
        saveDto.setTeamId(teamId);
        saveDto.setPosition(position);
        saveDto.setAcceleration(acceleration);
        saveDto.setSpeed(speed);
        saveDto.setPass(pass);
        saveDto.setActiveness(activeness);
        saveDto.setBalance(balance);
        saveDto.setCrosses(crosses);
        saveDto.setBallControl(ballControl);
        saveDto.setPhysicalFight(physicalFight);
        saveDto.setStamina(stamina);
        saveDto.setJump(jump);
        saveDto.setLongPass(longPass);
        saveDto.setDefense(defense);
        saveDto.setDiving(diving);
        saveDto.setDribble(dribble);
        saveDto.setGoalDetermination(goalDetermination);
        saveDto.setMidRangeShot(midRangeShot);
        saveDto.setShootPower(shootPower);
        saveDto.setHeading(heading);
        saveDto.setTackle(tackle);
        saveDto.setIntercepting(intercepting);
        saveDto.setSlidingTackle(slidingTackle);
        saveDto.setHandling(handling);
        saveDto.setGoalKick(goalKick);
        saveDto.setSpeedReaction(speedReaction);
        saveDto.setPositioning(positioning);
        saveDto.setVisualRange(visualRange);
        saveDto.setSense(sense);
        return saveDto;
    }



}
