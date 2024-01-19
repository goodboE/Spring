package com.ko.tricount.util;

import com.ko.tricount.entity.model.User;

public class UserContext {

    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void setCurrentUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getCurrentUser() {
        return userThreadLocal.get();
    }

    public static void clear() {
        userThreadLocal.remove();
    }

}
