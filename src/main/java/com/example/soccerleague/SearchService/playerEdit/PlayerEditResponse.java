package com.example.soccerleague.SearchService.playerEdit;

import com.example.soccerleague.domain.DataTransferObject;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import lombok.Data;

@Data
public class PlayerEditResponse extends DataTransferObject {
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

    public void fillData(Player player){
        this.setName(player.getName());
        this.setTeamId(player.getTeam().getId());
        this.setPosition(player.getPosition());
        this.setAcceleration(player.getStat().getAcceleration());
        this.setSpeed(player.getStat().getSpeed());
        this.setPass(player.getStat().getPass());
        this.setActiveness(player.getStat().getActiveness());
        this.setBalance(player.getStat().getBalance());
        this.setCrosses(player.getStat().getCrosses());
        this.setBallControl(player.getStat().getBallControl());
        this.setPhysicalFight(player.getStat().getPhysicalFight());
        this.setStamina(player.getStat().getStamina());
        this.setJump(player.getStat().getJump());
        this.setLongPass(player.getStat().getLongPass());
        this.setDefense(player.getStat().getDefense());
        this.setDiving(player.getStat().getDiving());
        this.setDribble(player.getStat().getDribble());
        this.setGoalDetermination(player.getStat().getGoalDetermination());
        this.setMidRangeShot(player.getStat().getMidRangeShot());
        this.setShootPower(player.getStat().getShootPower());
        this.setHeading(player.getStat().getHeading());
        this.setTackle(player.getStat().getTackle());
        this.setIntercepting(player.getStat().getIntercepting());
        this.setSlidingTackle(player.getStat().getSlidingTackle());
        this.setHandling(player.getStat().getHandling());
        this.setGoalKick(player.getStat().getGoalKick());
        this.setSpeedReaction(player.getStat().getSpeedReaction());
        this.setPositioning(player.getStat().getPositioning());
        this.setVisualRange(player.getStat().getVisualRange());
        this.setSense(player.getStat().getSense());
    }
}
