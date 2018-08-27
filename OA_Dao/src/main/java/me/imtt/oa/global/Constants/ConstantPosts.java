package me.imtt.oa.global.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 职位相关常量
 */
public class ConstantPosts {
    public static final String POST_STAFF = "员工";

    public static final String POST_FM = "部门经理";

    public static final String POST_GM = "总经理";

    public static final String POST_CASHIER = "财务";

    public static List<String> getPosts() {
        List<String> posts = new ArrayList<String>();
        posts.add(POST_STAFF);
        posts.add(POST_FM);
        posts.add(POST_GM);
        posts.add(POST_CASHIER);

        return posts;
    }
}
