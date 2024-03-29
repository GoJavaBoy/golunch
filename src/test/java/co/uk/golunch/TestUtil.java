package co.uk.golunch;

import co.uk.golunch.model.Dish;
import co.uk.golunch.model.User;
import co.uk.golunch.to.DishTo;
import co.uk.golunch.web.AuthorizedUser;
import co.uk.golunch.web.json.JsonUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtil {
    public static String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action.andReturn()), clazz);
    }

    public static <T> T readFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(result), clazz);
    }

    public static <T> List<T> readListFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValues(getContent(result), clazz);
    }

    public static void mockAuthorize(User user) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(new AuthorizedUser(user), null, user.getRoles()));
    }

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }

    public static RequestPostProcessor userAuth(User user) {
        return SecurityMockMvcRequestPostProcessors.authentication(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    }

    public static List<DishTo> toDishTo(List<Dish> dishes){
        return dishes.stream()
                .map(DishTo::new)
                .collect(Collectors.toList());
    }

    public static List<Dish> toDish(List<DishTo> dishes){
        return dishes.stream()
                .map(Dish::new)
                .collect(Collectors.toList());
    }

    public static DishTo toDishTo(Dish dish){
        return new DishTo(dish);
    }

    public static Dish toDish(DishTo dishTo){
        return new Dish(dishTo);
    }
}
