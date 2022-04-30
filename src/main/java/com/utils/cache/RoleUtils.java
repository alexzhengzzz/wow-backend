package com.utils.cache;

import com.entity.User;
import com.enums.Role;
import com.enums.RoleType;

import java.util.HashMap;

public class RoleUtils {
    private static HashMap<Character, Role> roleUserHashMap = new HashMap<>();
    private static HashMap<Character, RoleType> roleTypeHashMap = new HashMap<>();

    static {
        roleUserHashMap.put('0', Role.ADMIN);
        roleUserHashMap.put('1', Role.USER);
        roleUserHashMap.put('2', Role.USER);
        roleUserHashMap.put('3', Role.ANONYMOUS);

        roleTypeHashMap.put('0', RoleType.ADMIN);
        roleTypeHashMap.put('1', RoleType.INDIVIDUAL);
        roleTypeHashMap.put('2', RoleType.CORPORATION);
        roleTypeHashMap.put('3', RoleType.GUEST);
    }

    public static Role getRole(User user) {
        return roleUserHashMap.get(user.getRoleType());
    }

    public static RoleType getRoleType(User user) {
        return roleTypeHashMap.get(user.getRoleType());
    }

}
