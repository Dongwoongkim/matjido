package com.example.backend.domain.review.entity;

import com.example.backend.domain.review.exception.InvalidReviewScoreException;
import lombok.Getter;

@Getter
public enum ReviewScore {
    
    ONE(1, "재방문 의사 없음"),
    TWO(2, ""),
    THREE(3, ""),
    FOUR(4, ""),
    FIVE(5, "재방문 의사 있음");

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
