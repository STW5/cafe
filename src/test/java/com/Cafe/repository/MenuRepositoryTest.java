package com.Cafe.repository;

import com.Cafe.menu.entity.Menu;
import com.Cafe.menu.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MenuRepositoryTest {
    @Autowired
    MenuRepository menuRepository;

    @Test
    @DisplayName("메뉴 저장 테스트")
    public void saveMenu(){
        Menu menu = new Menu();
        menu.setName("테스트메뉴");
        menu.setPrice(111);
        menu.setSpecial(true);
        Menu savedMenu = menuRepository.save(menu);

        System.out.println(savedMenu.toString());
    }
}
