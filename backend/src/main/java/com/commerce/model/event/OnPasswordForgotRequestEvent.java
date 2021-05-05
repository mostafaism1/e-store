package com.commerce.model.event;

import com.commerce.model.entity.User;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnPasswordForgotRequestEvent extends ApplicationEvent {

    private User user;
    private String token;

    public OnPasswordForgotRequestEvent(User user, String token) {
        super(user);
        this.user = user;
        this.token = token;
    }

}
