package com.szypula.github.githubscreen.list.view;

import com.szypula.github.githubscreen.list.view.model.UserViewModel;

import java.util.List;

public class ListIndexCalculator {

    public int indexOfRemoved(List<UserViewModel> users, String code) {
        int index = 0;
        String name = findName(users, code);
        for (UserViewModel user : users) {
            if (user.getName().equals(name)) {
                break;
            }
            index++;
        }
        return index;
    }

    public int indexOf(List<UserViewModel> users, String code) {
        int index = 0;
        String name = findName(users, code);
        for (UserViewModel user : users) {
            if (user.getName().equals(name)) {
                break;
            }
            index++;
        }
        return index;
    }

    private String findName(List<UserViewModel> users, String code) {
        for (UserViewModel user : users) {
            if (user.getCode().equals(code)) {
                return user.getName();
            }
        }
        throw new IllegalArgumentException("Wrong code: " + code);
    }
}
