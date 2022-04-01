package com.example.soccerleague.Service;

import com.example.soccerleague.Repository.TeamLeagueRecordRepository;
import com.example.soccerleague.domain.record.TeamLeagueRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamLeagueRecordServiceImpl implements TeamLeagueRecordService {
    private TeamLeagueRecordRepository teamLeagueRecordRepository;

    @Override
    public void save(TeamLeagueRecord teamLeagueRecord) {
        teamLeagueRecordRepository.save(teamLeagueRecord);
    }

    @Override
    public List<TeamLeagueRecord> searchRoundId(Long roundId) {
        return teamLeagueRecordRepository.findByRoundId(roundId);
    }
}
