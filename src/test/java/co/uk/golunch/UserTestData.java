package co.uk.golunch;

import co.uk.golunch.model.Role;
import co.uk.golunch.model.User;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;

import static co.uk.golunch.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator(User.class, "registered", "restaurant");
    public static TestMatcher<User> USER_WITH_RESTAURANT_MATCHER =
            TestMatcher.usingAssertions(User.class,
//     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    new BiConsumer<User, User>() {
                        @Override
                        public void accept(User a, User e) {
                            assertThat(a).usingRecursiveComparison()
                                    .ignoringFields("registered", "meals.user").isEqualTo(e);
                        }
                    },
                    new BiConsumer<>() {
                        @Override
                        public void accept(Iterable<User> a, Iterable<User> e) {
                            throw new UnsupportedOperationException();
                        }
                    });

    public static final int USER_ID = START_SEQ + 5;
    public static final int ADMIN_ID = START_SEQ + 6;
    public static final int NOT_FOUND = 10;

    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

//    static {
//        user.setMeals(meals);
//        admin.setMeals(List.of(adminMeal2, adminMeal1));
//    }

//    public static User getNew() {
//        return new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), Collections.singleton(Role.USER));
//    }

//    public static User getUpdated() {
//        User updated = new User(user);
//        updated.setEmail("update@gmail.com");
//        updated.setName("UpdatedName");
//        updated.setCaloriesPerDay(330);
//        updated.setPassword("newPass");
//        updated.setEnabled(false);
//        updated.setRoles(Collections.singletonList(Role.ADMIN));
//        return updated;
//    }
}