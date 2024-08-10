package com.practice.security.user;

import java.util.Arrays;
import java.util.List;

public enum Role {

    CUSTOMER(Arrays.asList(Permisssion.READ_ALL_PRODUCTS)),

    ADMIN(Arrays.asList(Permisssion.READ_ALL_PRODUCTS,Permisssion.SAVE_ONE_PRODUCT));

    private List<Permisssion> permisssions;

    Role(List<Permisssion> permisssions) {
        this.permisssions = permisssions;
    }

    public List<Permisssion> getPermisssions() {
        return permisssions;
    }

    public void setPermisssions(List<Permisssion> permisssions) {
        this.permisssions = permisssions;
    }
}
