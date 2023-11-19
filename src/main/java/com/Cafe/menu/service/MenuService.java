package com.Cafe.menu.service;

import com.Cafe.menu.common.Category;
import com.Cafe.menu.common.IngredientDto;
import com.Cafe.menu.common.MenuDto;
import com.Cafe.menu.common.RecipeDto;
import com.Cafe.menu.entity.Ingredient;
import com.Cafe.menu.entity.Menu;
import com.Cafe.menu.entity.Recipe;
import com.Cafe.menu.repository.IngredientRepository;
import com.Cafe.menu.repository.MenuRepository;
import com.Cafe.menu.repository.RecipeRepository;
import com.Cafe.order.entity.OrderMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    public Menu createMenu(MenuDto menuDto) {
        Menu menu = menuDto.toEntity();
        return menuRepository.save(menu);
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAllByActive(true);
    }

    @Transactional
    public void updateMenu(Long menuId, MenuDto menuDto) {
        Optional<Menu> optionalMenu = menuRepository.findById(menuId);
        if(optionalMenu.isEmpty()) return;
        Menu menu = optionalMenu.get();
        menu.setName(menuDto.getName());
        menu.setPrice(menuDto.getPrice());
        menu.setCategory(menuDto.getCategory());
        menu.setSpecial(menuDto.isSpecial());
    }

    @Transactional
    public void deleteMenu(long menuId) {
        menuRepository.findById(menuId).ifPresent(menu -> menu.setActive(false));
    }

    public Menu getMenuById(long menuId) {
        return menuRepository.findById(menuId).orElse(null);
    }

    public List<Menu> getAllSearchedMenu(String keyword, Category category){
        if ((keyword == null || keyword.isEmpty()) && (category == null || category == Category.ALL)) {
            return menuRepository.findAllByActive(true);
        } else if(keyword!=null && (category!=null && category != Category.ALL)){
            return menuRepository.findAllByNameContainingAndCategoryAndActive(keyword, category, true);
        } else if (keyword!=null){
            return menuRepository.findAllByNameContainingAndActive(keyword, true);
        }else {
            return menuRepository.findAllByCategoryAndActive(category, true);
        }
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAllByActive(true);
    }

    public Ingredient createIngredient(IngredientDto ingredientDto) {
        if(ingredientRepository.existsByNameAndActive(ingredientDto.getName(), true)) return null;
        return ingredientRepository.save(ingredientDto.toEntity());

    }

    @Transactional
    public void updateIngredient(long ingredientId, IngredientDto ingredientDto) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredientId);
        if (optionalIngredient.isEmpty()) return;
        Ingredient ingredient = optionalIngredient.get();
        ingredient.setName(ingredientDto.getName());
        ingredient.setStock(ingredientDto.getStock());
        ingredient.setOrderUnit(ingredientDto.getOrderUnit());
    }
    @Transactional
    public void deleteIngredient(long ingredientId) {
        ingredientRepository.findById(ingredientId).ifPresent(ingredient -> ingredient.setActive(false));
    }

    public Recipe createRecipe(RecipeDto recipeDto) {
        Optional<Menu> optionalMenu = menuRepository.findById(recipeDto.getMenuId());
        if (optionalMenu.isEmpty()) {
            return null;
        }
        Menu menu = optionalMenu.get();
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(recipeDto.getIngredientId());
        if (optionalIngredient.isEmpty()) {
            return null;
        }
        Ingredient ingredient = optionalIngredient.get();

        Recipe recipe = new Recipe();
        recipe.setRequiredAmount(recipeDto.getRequiredAmount());
        recipe.setMenu(menu);
        recipe.setIngredient(ingredient);
        return recipeRepository.save(recipe);
    }

    @Transactional
    public void updateRecipe(long recipeId, long requiredAmount) {
        recipeRepository.findById(recipeId).ifPresent(recipe -> recipe.setRequiredAmount(requiredAmount));
    }

    @Transactional
    public void deleteRecipe(long recipeId) {
        recipeRepository.deleteById(recipeId);
    }
    //주문할때 마다 재료 재고량 소진되는 기능 구현 메서드
    public void subIngredientStock(OrderMenu orderMenu) {
        for (Recipe recipe : orderMenu.getMenu().getRecipes()) {
            Ingredient ingredient = recipe.getIngredient();
            ingredient.setStock(ingredient.getStock() - recipe.getRequiredAmount() * orderMenu.getQuantity());
        }
    }
}
