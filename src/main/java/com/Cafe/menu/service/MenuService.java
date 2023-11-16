package com.Cafe.menu.service;

import com.Cafe.menu.common.MenuDto;
import com.Cafe.menu.entity.Menu;
import com.Cafe.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;

    public Menu createMenu(MenuDto menuDto) {
        Menu menu = menuDto.toEntity();
        return menuRepository.save(menu);
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAllByActive(true);
    }
}
