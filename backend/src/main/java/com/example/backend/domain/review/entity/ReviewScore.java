package com.example.backend.domain.review.entity;

import com.example.backend.domain.review.exception.InvalidReviewScoreException;
import lombok.Getter;

@Getter
public enum ReviewScore {

    ONE(1, "매우 불만족"),
    TWO(2, "불만족"),
    THREE(3, "보통"),
    FOUR(4, "만족"),
    FIVE(5, "매우 만족");
    
    private final int score;
    private final String description;

    ReviewScore(int score, String description) {
        this.score = score;
        this.description = description;
    }

    public static ReviewScore fromScore(int score) {
        for (ReviewScore reviewScore : values()) {
            if (reviewScore.getScore() == score) {
                return reviewScore;
            }
        }
        throw new InvalidReviewScoreException();
    }
}
