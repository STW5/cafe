package com.Cafe.menu.controller;

import com.Cafe.menu.common.IngredientDto;
import com.Cafe.menu.common.MenuDto;
import com.Cafe.menu.common.RecipeDto;
import com.Cafe.menu.entity.Ingredient;
import com.Cafe.menu.entity.LikeMenu;
import com.Cafe.menu.entity.Menu;
import com.Cafe.menu.service.MenuService;
import com.Cafe.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/manage")
    public String manageMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        model.addAttribute("loginUser", loginUser);

        List<Menu> menuList = menuService.getAllMenus();
        model.addAttribute("menuList", menuList);

        List<Ingredient> ingredientList = menuService.getAllIngredients();
        model.addAttribute("ingredientList", ingredientList);

        return "menu/manage";
    }

    @PostMapping("/add")
    public String createMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, MenuDto menuDto){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.createMenu(menuDto);
        return "redirect:/menu/manage";
    }

    @PostMapping("/update")
    public String updateMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, MenuDto menuDto, Long menuId){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.updateMenu(menuId, menuDto);
        return "redirect:/menu/manage";
    }

    @PostMapping("/delete")
    public String deleteMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, long menuId){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.deleteMenu(menuId);
        return "redirect:/menu/manage";
    }

    @GetMapping
    public String menuInfo(@RequestParam("menuId") Long menuId, @SessionAttribute(name = "loginUser", required = false) User loginUser, Model model){
        Menu menu = menuService.getMenuById(menuId);
        LikeMenu likeMenu = menuService.getLikeMenu(menuId, loginUser);
        if(loginUser!=null)model.addAttribute(loginUser);

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("menu", menu);
        model.addAttribute("likeMenu", likeMenu);
        return "menu/info";
    }

    // 재료 처리
    @PostMapping("/ingredient/add")
    public String createIngredient(@SessionAttribute(name = "loginUser", required = false) User loginUser, IngredientDto ingredientDto){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.createIngredient(ingredientDto);
        return "redirect:/menu/manage";
    }

    @PostMapping("/ingredient/update")
    public String updateIngredient(@SessionAttribute(name = "loginUser", required = false) User loginUser, IngredientDto ingredientDto, long ingredientId){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.updateIngredient(ingredientId, ingredientDto);
        return "redirect:/menu/manage";
    }

    @PostMapping("/ingredient/delete")
    public String removeIngredient(@SessionAttribute(name = "loginUser", required = false) User loginUser, long ingredientId){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.deleteIngredient(ingredientId);
        return "redirect:/menu/manage";
    }

    // 레시피 처리
    @GetMapping("/recipe")
    public String recipe(@RequestParam("menuId") Long menuId, @SessionAttribute(name = "loginUser", required = false) User loginUser, Model model){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        model.addAttribute("loginUser", loginUser);

        Menu menu = menuService.getMenuById(menuId);
        if(menu==null) return "redirect:/menu/manage";

        List<Ingredient> ingredientList = menuService.getAllIngredients();

        model.addAttribute("ingredientList", ingredientList);
        model.addAttribute("menu", menu);

        return "menu/recipe";
    }

    @PostMapping("/recipe/add")
    public String createRecipe(@SessionAttribute(name = "loginUser", required = false) User loginUser, RecipeDto recipeDto, HttpServletRequest httpServletRequest){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.createRecipe(recipeDto);
        return "redirect:"+httpServletRequest.getHeader("Referer");
    }

    @PostMapping("/recipe/update")
    public String updateupdateRecipe(@SessionAttribute(name = "loginUser", required = false) User loginUser, long recipeId, long requiredAmount, HttpServletRequest httpServletRequest){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.updateRecipe(recipeId, requiredAmount);
        return "redirect:"+httpServletRequest.getHeader("Referer");
    }

    @PostMapping("/recipe/delete")
    public String removeRecipe(@SessionAttribute(name = "loginUser", required = false) User loginUser, long recipeId, HttpServletRequest httpServletRequest){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        menuService.deleteRecipe(recipeId);
        return "redirect:"+httpServletRequest.getHeader("Referer");
    }

    @PostMapping("/like/toggle")
    public String addLike(@SessionAttribute(name = "loginUser", required = false) User loginUser, long menuId, HttpServletRequest httpServletRequest) {
        if(loginUser == null) {
            return "redirect:/user/login";
        }

        menuService.addLike(loginUser, menuId);
        return "redirect:"+httpServletRequest.getHeader("Referer");
    }

    @GetMapping("/like/list")
    public String addLike(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model, HttpServletRequest httpServletRequest) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        model.addAttribute("loginUser", loginUser);

        List<Menu> menus = menuService.getLikedMenus(loginUser);
        model.addAttribute("menu", menus);

        return "menu/likeList";
    }


}
