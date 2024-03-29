package co.uk.golunch;

import co.uk.golunch.model.Role;
import co.uk.golunch.model.User;
import co.uk.golunch.web.json.JsonUtil;

import java.util.Collections;
import java.util.Date;

import static co.uk.golunch.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator(User.class, "registered", "password");
    public static final int USER_ID = START_SEQ + 5;
    public static final int ADMIN_ID = START_SEQ + 6;
    public static final int NOT_FOUND = 10;

    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User user1 = new User(USER_ID + 2, "User1", "user1@yandex.ru", "password", Role.USER);
    public static final User user2 = new User(USER_ID + 3, "User2", "user2@yandex.ru", "password", Role.USER);
    public static final User user3 = new User(USER_ID + 4, "User3", "user3@yandex.ru", "password", Role.USER);
    public static final User user4 = new User(USER_ID + 5, "User4", "user4@yandex.ru", "password", Role.USER);
    public static final User user5 = new User(USER_ID + 6, "User5", "user5@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setEmail("update@gmail.com");
        updated.setName("UpdatedName");
        updated.setPassword("newPass");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
