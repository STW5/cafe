package com.Cafe.supply.controller;

import com.Cafe.menu.entity.Ingredient;
import com.Cafe.menu.service.MenuService;
import com.Cafe.supply.common.SupplierDto;
import com.Cafe.supply.common.SupplyIngredientDto;
import com.Cafe.supply.entity.Supplier;
import com.Cafe.supply.entity.Supply;
import com.Cafe.supply.service.SupplyService;
import com.Cafe.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("supply")
@RequiredArgsConstructor
public class SupplyController {
    private final SupplyService supplyService;
    private final MenuService menuService;

    @GetMapping("supplier")
    public String supplier(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        model.addAttribute("loginUser", loginUser);

        List<Supplier> supplierList = supplyService.getAllSuppliers();
        model.addAttribute("supplierList", supplierList);

        List<Supply> supplyList = supplyService.getAllSupplies();
        model.addAttribute("supplyList", supplyList);

        return "supply/supplier";
    }

    @PostMapping("/add")
    public String createSupplier(@SessionAttribute(name = "loginUser", required = false) User loginUser, SupplierDto supplierDto){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        supplyService.addSupplier(supplierDto);
        return "redirect:/supply/supplier";
    }

    @PostMapping("/update")
    public String updateSupplier(@SessionAttribute(name = "loginUser", required = false) User loginUser, SupplierDto supplierDto, long supplierId){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        supplyService.updateSupplier(supplierId, supplierDto);
        return "redirect:/supply/supplier";
    }

    @PostMapping("/delete")
    public String deleteSupplier(@SessionAttribute(name = "loginUser", required = false) User loginUser, long supplierId){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";
        supplyService.deActivateSupplier(supplierId);
        return "redirect:/supply/supplier";
    }

    @GetMapping
    public String supply(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model, long supplierId){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";

        model.addAttribute("loginUser", loginUser);

        Supplier supplier = supplyService.getSupplier(supplierId);
        model.addAttribute("supplier", supplier);

        Supply supply = supplyService.getSupply(supplierId);
        model.addAttribute("supply", supply);

        List<Ingredient> ingredientList = menuService.getAllIngredients();
        model.addAttribute("ingredientList", ingredientList);

        return "supply/supply";
    }

    @PostMapping("/purchaseIngredient")
    public String addItem(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                          Model model, long supplierId,
                          SupplyIngredientDto supplyIngredientDto,
                          HttpServletRequest httpServletRequest){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";

        supplyService.purchaseSupplyIngredient(supplierId, supplyIngredientDto);
        return "redirect:"+httpServletRequest.getHeader("Referer");
    }

    @PostMapping("/confirm")
    public String confirmSupply(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                                Model model, long supplierId,
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime suppliedDate){
        if(loginUser==null)return "redirect:/user/login";
        if(!loginUser.isAdmin()) return "redirect:/";

        supplyService.confirmSupply(supplierId, suppliedDate);
        return "redirect:/supply/supplier";
    }


}
