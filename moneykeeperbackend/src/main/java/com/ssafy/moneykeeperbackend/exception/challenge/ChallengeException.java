package com.ssafy.moneykeeperbackend.exception.challenge;

public class ChallengeException extends Exception {
    public ChallengeException(String message, Exception e) {
        super(message, e);
    }
}
