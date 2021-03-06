package com.example.soccerleague.support.testData;

import com.example.soccerleague.RegisterService.PlayerEdit.PlayerEditDto;
import com.example.soccerleague.RegisterService.PlayerEdit.PlayerEditRegister;
import com.example.soccerleague.domain.Player.Player;
import com.example.soccerleague.domain.Player.Position;
import com.example.soccerleague.springDataJpa.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PlayerStatConfig {
    private final PlayerRepository playerEntityRepository;
    private final PlayerEditRegister playerEditRegister;
    public void playerStatConfig(){
        playerEntityRepository.findAll()
                .stream()
                .map(ele->(Player)ele)
                .forEach(ele->{
                  statConfig(ele);
                    PlayerEditDto editDto = new PlayerEditDto(ele.getId(),ele.getTeam().getId());
                    editDto.fillData(ele);
                    playerEditRegister.register(editDto);
                });
    }


    private void statConfig(Player player){
        LowerAndHigher arr[] = new LowerAndHigher[27];
        for(int i =0;i<27;i++) arr[i] = new LowerAndHigher();
        if(player.getPosition().equals(Position.ST)
                || player.getPosition().equals(Position.LF)
                || player.getPosition().equals(Position.RF)
                || player.getPosition().equals(Position.CF)
        ){
            settingStriker(player,arr);
        }
        else if(player.getPosition().equals(Position.AM)
                || player.getPosition().equals(Position.LM)
                || player.getPosition().equals(Position.CM)
                || player.getPosition().equals(Position.RM)
                || player.getPosition().equals(Position.DM)
        ){
          settingMid(player,arr);
        }
        else if(player.getPosition().equals(Position.GK)){
            settingGoalKeeper(player,arr);
        }
        else{
            settingDefenser(player,arr);
        }

        //?????????
        player.getStat().setAcceleration((int)(Math.random()*arr[0].higher) + arr[0].lower);
        player.getStat().setSpeed((int)(Math.random()*arr[1].higher) + arr[1].lower);
        player.getStat().setPhysicalFight((int)(Math.random()*arr[2].higher) + arr[2].lower);
        player.getStat().setStamina((int)(Math.random()*arr[3].higher) + arr[3].lower);
        player.getStat().setActiveness((int)(Math.random()*arr[4].higher) + arr[4].lower);
        player.getStat().setJump((int)(Math.random()*arr[5].higher) + arr[5].lower);


        //??????
        player.getStat().setBalance((int)(Math.random()*arr[6].higher) + arr[6].lower);
        player.getStat().setBallControl((int)(Math.random()*arr[7].higher) + arr[7].lower);
        player.getStat().setCrosses((int)(Math.random()*arr[8].higher) + arr[8].lower);
        player.getStat().setPass((int)(Math.random()*arr[9].higher) + arr[9].lower);

        // ?????????
        player.getStat().setLongPass((int)(Math.random()*arr[10].higher) + arr[10].lower);
        player.getStat().setDribble((int)(Math.random()*arr[11].higher) + arr[11].lower);
        player.getStat().setGoalDetermination((int)(Math.random()*arr[12].higher) + arr[12].lower);
        player.getStat().setMidRangeShot((int)(Math.random()*arr[13].higher) + arr[13].lower);
        player.getStat().setShootPower((int)(Math.random()*arr[14].higher) + arr[14].lower);
        player.getStat().setHeading((int)(Math.random()*arr[15].higher) + arr[15].lower);

        //?????????
        player.getStat().setDefense((int)(Math.random()*arr[16].higher) + arr[16].lower);
        player.getStat().setTackle((int)(Math.random()*arr[17].higher) + arr[17].lower);
        player.getStat().setIntercepting((int)(Math.random()*arr[18].higher) + arr[18].lower);
        player.getStat().setSlidingTackle((int)(Math.random()*arr[19].higher) + arr[19].lower);
        // ?????????
        player.getStat().setDiving((int)(Math.random()*arr[20].higher) + arr[20].lower);
        player.getStat().setHandling((int)(Math.random()*arr[21].higher) + arr[21].lower);
        player.getStat().setGoalKick((int)(Math.random()*arr[22].higher) + arr[22].lower);
        player.getStat().setSpeedReaction((int)(Math.random()*arr[23].higher) + arr[23].lower);
        // ??????

        player.getStat().setPositioning((int)(Math.random()*arr[24].higher) + arr[24].lower);
        player.getStat().setVisualRange((int)(Math.random()*arr[25].higher) + arr[25].lower);
        player.getStat().setSense((int)(Math.random()*arr[26].higher) + arr[26].lower);



    }

    private void settingDefenser(Player player , LowerAndHigher []arr){
        /** ????????? **/
        //?????????
        if(player.getPosition().equals(Position.LWB) || player.getPosition().equals(Position.RWB)){
            arr[0].lower = 40;
            arr[0].higher = 30;

        }
        else{
            arr[0].lower = 20;
            arr[0].higher = 30;
        }

        // ??????

        if(player.getPosition().equals(Position.LWB) || player.getPosition().equals(Position.RWB)){
            arr[1].lower = 40;
            arr[1].higher = 30;

        }
        else{
            arr[1].lower = 20;
            arr[1].higher = 30;
        }
        //?????????

        if(player.getPosition().equals(Position.LWB) || player.getPosition().equals(Position.RWB)){
            arr[2].lower = 30;
            arr[2].higher = 30;

        }
        else{
            arr[2].lower = 60;
            arr[2].higher = 25;
        }

        //??????

        arr[3].lower = 50;
        arr[3].higher = 20;


        //?????????

        arr[4].lower = 50;
        arr[4].higher = 20;

        //?????????

        arr[5].lower = 40;
        arr[5].higher = 30;

        // ?????????
        arr[6].lower = 40;
        arr[6].higher = 20;

        /** ?????? **/
        //??????
        arr[7].lower = 30;
        arr[7].higher = 30;
        // ?????????
        if(player.getPosition().equals(Position.LWB) || player.getPosition().equals(Position.RWB)){
            arr[8].lower = 30;
            arr[8].higher = 40;
        }
        else{
            arr[8].lower = 30;
            arr[8].higher = 30;
        }
        // ??????
        arr[9].lower = 30;
        arr[9].higher = 40;
        //?????????
        arr[10].lower = 40;
        arr[10].higher = 30;
        /** ????????? **/

        // ?????????
        arr[11].lower = 30;
        arr[11].higher = 20;


        //?????????
        arr[12].lower = 20;
        arr[12].higher = 30;
        //????????????
        arr[13].lower = 30;
        arr[13].higher = 30;
        //?????????
        arr[14].lower = 20;
        arr[14].higher = 20;
        //??????
        arr[15].lower = 40;
        arr[15].higher = 30;
        /** ?????? **/
        //?????????
        arr[16].lower = 50;
        arr[16].higher = 50;
        // ??????
        arr[17].lower = 50;
        arr[17].higher = 50;
        //????????????
        arr[18].lower = 50;
        arr[18].higher = 50;
        //???????????? ??????
        arr[19].lower = 50;
        arr[19].higher = 50;
        /** ????????? **/
        // ?????????
        arr[20].lower = 0;
        arr[20].higher = 10;
        // ?????????
        arr[21].lower = 0;
        arr[21].higher = 10;
        //??????
        arr[22].lower = 0;
        arr[22].higher = 10;
        // ????????????
        arr[23].lower = 0;
        arr[23].higher = 100;
        /** ?????? **/
        // ????????????
        arr[24].lower = 0;
        arr[24].higher = 100;
        // ??????
        arr[25].lower = 0;
        arr[25].higher = 100;
        //??????
        arr[26].lower = 0;
        arr[26].higher = 100;


    }
    private void settingStriker(Player player , LowerAndHigher [] arr){
        /** ????????? **/
        //?????????

        if(player.getPosition().equals(Position.RF) || player.getPosition().equals(Position.LF)){
            arr[0].lower = 60;
            arr[0].higher = 25;
        }
        else{
            arr[0].lower = 50;
            arr[0].higher = 20;
        }

        // ??????
        if(player.getPosition().equals(Position.RF) || player.getPosition().equals(Position.LF)){
            arr[1].lower = 60;
            arr[1].higher = 25;
        }
        else{
            arr[1].lower = 55;
            arr[1].higher = 20;
        }

        //?????????
        if(player.getPosition().equals(Position.RF) || player.getPosition().equals(Position.LF)){
            arr[2].lower = 40;
            arr[2].higher = 20;
        }
        else{
            arr[2].lower = 50;
            arr[2].higher = 20;
        }
        //??????
        if(player.getPosition().equals(Position.RF) || player.getPosition().equals(Position.LF)){
            arr[3].lower = 40;
            arr[3].higher = 20;
        }
        else{
            arr[3].lower = 40;
            arr[3].higher = 20;
        }

        //?????????
        if(player.getPosition().equals(Position.RF) || player.getPosition().equals(Position.LF)){
            arr[4].lower = 40;
            arr[4].higher = 20;
        }
        else{
            arr[4].lower = 40;
            arr[4].higher = 20;
        }
        //?????????
        if(player.getPosition().equals(Position.RF) || player.getPosition().equals(Position.LF)){
            arr[5].lower = 30;
            arr[5].higher = 30;
        }
        else{
            arr[5].lower = 40;
            arr[5].higher = 30;
        }
        // ?????????
        if(player.getPosition().equals(Position.RF) || player.getPosition().equals(Position.LF)){
            arr[6].lower = 40;
            arr[6].higher = 20;
        }
        else{
            arr[6].lower = 50;
            arr[6].higher = 20;
        }
        /** ?????? **/
        //??????
        arr[7].lower = 55;
        arr[7].higher = 20;
        // ?????????
        arr[8].lower = 40;
        arr[8].higher = 20;
        // ??????
        arr[9].lower = 40;
        arr[9].higher = 30;
        //?????????
        arr[10].lower = 30;
        arr[10].higher = 20;
        /** ????????? **/

        // ?????????
        if(player.getPosition().equals(Position.RF) || player.getPosition().equals(Position.LF)){
            arr[11].lower = 40;
            arr[11].higher = 30;
        }
        else{
            arr[11].lower = 30;
            arr[11].higher = 30;
        }


        //?????????
        if(player.getPosition().equals(Position.RF) || player.getPosition().equals(Position.LF)){
            arr[12].lower = 30;
            arr[12].higher = 30;
        }
        else{
            arr[12].lower = 40;
            arr[12].higher = 30;
        }

        //????????????
        arr[13].lower = 20;
        arr[13].higher = 30;

        //?????????
        arr[14].lower = 40;
        arr[14].higher = 30;

        //??????
        arr[15].lower = 20;
        arr[15].higher = 40;

        /** ?????? **/
        //?????????
        arr[16].lower = 10;
        arr[16].higher = 40;
        // ??????
        arr[17].lower = 10;
        arr[17].higher = 40;
        //????????????
        arr[18].lower = 10;
        arr[18].higher = 40;
        //???????????? ??????
        arr[19].lower = 10;
        arr[19].higher = 40;
        /** ????????? **/
        // ?????????
        arr[20].lower = 0;
        arr[20].higher = 10;
        // ?????????
        arr[21].lower = 0;
        arr[21].higher = 10;
        //??????
        arr[22].lower = 0;
        arr[22].higher = 10;
        // ????????????
        arr[23].lower = 0;
        arr[23].higher = 100;
        /** ?????? **/
        // ????????????
        arr[24].lower = 0;
        arr[24].higher = 100;
        // ??????
        arr[25].lower = 0;
        arr[25].higher = 100;
        //??????
        arr[26].lower = 0;
        arr[26].higher = 100;
    }
    private void settingMid(Player player , LowerAndHigher [] arr){
        /** ????????? **/
        //?????????
        if(player.getPosition().equals(Position.RM) || player.getPosition().equals(Position.LM)){
            arr[0].lower = 60;
            arr[0].higher = 25;
        }
        else{
            arr[0].lower = 30;
            arr[0].higher = 25;
        }

        // ??????
        if(player.getPosition().equals(Position.RM) || player.getPosition().equals(Position.LM)){
            arr[1].lower = 60;
            arr[1].higher = 25;
        }
        else{
            arr[1].lower = 30;
            arr[1].higher = 25;
        }
        //?????????

        if(player.getPosition().equals(Position.RM) || player.getPosition().equals(Position.LM)){
            arr[2].lower = 40;
            arr[2].higher = 15;
        }
        else{
            arr[2].lower = 50;
            arr[2].higher = 25;
        }

        //??????
        if(player.getPosition().equals(Position.RM) || player.getPosition().equals(Position.LM)){
            arr[3].lower = 50;
            arr[3].higher = 25;
        }
        else{
            arr[3].lower = 30;
            arr[3].higher = 20;
        }

        //?????????

        arr[4].lower = 50;
        arr[4].higher = 20;


        //?????????


        arr[5].lower = 40;
        arr[5].higher = 20;

        // ?????????

        arr[6].lower = 40;
        arr[6].higher = 25;



        /** ?????? **/
        //??????

        arr[7].lower = 40;
        arr[7].higher = 30;

        // ?????????
        if(player.getPosition().equals(Position.RM) || player.getPosition().equals(Position.LM)){
            arr[8].lower = 50;
            arr[8].higher = 20;
        }
        else{
            arr[8].lower = 20;
            arr[8].higher = 30;
        }

        // ??????

        arr[9].lower = 40;
        arr[9].higher = 30;

        //?????????
        arr[10].lower = 40;
        arr[10].higher = 20;
        /** ????????? **/

        // ?????????

        arr[11].lower = 30;
        arr[11].higher = 30;

        //?????????
        arr[12].lower = 20;
        arr[12].higher = 30;


        //????????????
        arr[13].lower = 40;
        arr[13].higher = 23;
        //?????????

        arr[14].lower = 20;
        arr[14].higher = 40;

        //??????

        arr[15].lower = 20;
        arr[15].higher = 40;

        /** ?????? **/
        //?????????

        if(player.getPosition().equals(Position.RM) || player.getPosition().equals(Position.LM)){
            arr[16].lower = 20;
            arr[16].higher = 30;
        }
        else{
            arr[16].lower = 40;
            arr[16].higher = 30;
        }
        // ??????
        if(player.getPosition().equals(Position.RM) || player.getPosition().equals(Position.LM)){
            arr[17].lower = 20;
            arr[17].higher = 30;
        }
        else{
            arr[17].lower = 40;
            arr[17].higher = 30;
        }
        //????????????
        if(player.getPosition().equals(Position.RM) || player.getPosition().equals(Position.LM)){
            arr[18].lower = 20;
            arr[18].higher = 30;
        }
        else{
            arr[18].lower = 40;
            arr[18].higher = 30;
        }
        //???????????? ??????
        if(player.getPosition().equals(Position.RM) || player.getPosition().equals(Position.LM)){
            arr[19].lower = 20;
            arr[19].higher = 30;
        }
        else{
            arr[19].lower = 40;
            arr[19].higher = 30;
        }
        /** ????????? **/
        // ?????????
        arr[20].lower = 0;
        arr[20].higher = 10;
        // ?????????
        arr[21].lower = 0;
        arr[21].higher = 10;
        //??????
        arr[22].lower = 0;
        arr[22].higher = 10;
        // ????????????
        arr[23].lower = 0;
        arr[23].higher = 100;
        /** ?????? **/
        // ????????????
        arr[24].lower = 0;
        arr[24].higher = 100;
        // ??????
        arr[25].lower = 0;
        arr[25].higher = 100;
        //??????
        arr[26].lower = 0;
        arr[26].higher = 100;
    }
    private  void settingGoalKeeper(Player player , LowerAndHigher [] arr){
        for(int i =0;i<20;i++){
            arr[i].higher = 10;
            arr[i].lower = 0;
        }

        /** ????????? **/
        // ?????????
        arr[20].lower = 50;
        arr[20].higher = 50;
        // ?????????
        arr[21].lower = 50;
        arr[21].higher = 50;
        //??????
        arr[22].lower = 50;
        arr[22].higher = 50;
        // ????????????
        arr[23].lower = 0;
        arr[23].higher = 100;
        /** ?????? **/
        // ????????????
        arr[24].lower = 0;
        arr[24].higher = 100;
        // ??????
        arr[25].lower = 0;
        arr[25].higher = 100;
        //??????
        arr[26].lower = 0;
        arr[26].higher = 100;
    }

}

class LowerAndHigher{
     int lower;
     int higher;
}