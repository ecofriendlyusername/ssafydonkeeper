package com.ssafy.moneykeeperbackend.challenge.repository;

import com.ssafy.moneykeeperbackend.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findAllByInProgressAndIsFinished(boolean inProgress, boolean isFinished);
    Challenge findById(long id);

    int countById(long id);
}
