package com.example.backend.domain.review.exception;

import lombok.Getter;

public class UnauthorizedReviewAccessException extends RuntimeException {

    public UnauthorizedReviewAccessException(ReviewAction action) {
        super("리뷰를 " + action.getActionName() + "할 수 있는 권한이 없습니다.");
    }

    @Getter
    public static enum ReviewAction {
        CREATE("생성"),
        UPDATE("수정"),
        DELETE("삭제");

        private final String actionName;

        ReviewAction(String actionName) {
            this.actionName = actionName;
        }
    }
}
