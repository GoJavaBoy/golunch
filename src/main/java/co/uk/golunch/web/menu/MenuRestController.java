package co.uk.golunch.web.menu;

import co.uk.golunch.model.MenuItem;
import co.uk.golunch.service.MenuService;
import co.uk.golunch.to.MenuItemTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static co.uk.golunch.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuService menuService;

    static final String REST_URL = "/admin/restaurants";

    public MenuRestController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/{restaurantId}/menu")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMenu(@PathVariable int restaurantId, @RequestBody @Valid MenuItemTo... menu) {
        log.info("add menu for restaurant {}", restaurantId);
        menuService.addMenu(restaurantId, menu);
    }

    @GetMapping("/{userId}/menu")
    public List<MenuItem> getTodayMenu(@PathVariable int userId) {
        log.info("get menu of the day for restaurant {}", userId);
        return menuService.getTodayMenu(userId);
    }

    @PutMapping("/{restaurantId}/menu/{menuItemId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMenuItem(@PathVariable int restaurantId, @PathVariable int menuItemId, @RequestBody @Valid MenuItemTo menuItemTo) {
        log.info("update menuItem with id {}", restaurantId);
        assureIdConsistent(menuItemTo, menuItemId);
        menuService.updateMenuItem(restaurantId, menuItemTo);
    }

    @DeleteMapping("/{restaurantId}/menu/{menuItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable int restaurantId, @PathVariable int menuItemId) {
        log.info("delete menuItem with id {}", menuItemId);
        menuService.deleteMenuItem(restaurantId, menuItemId);
    }

}
