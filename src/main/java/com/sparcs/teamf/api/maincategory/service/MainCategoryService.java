package com.sparcs.teamf.api.maincategory.service;

import com.sparcs.teamf.domain.maincategory.MainCategory;
import com.sparcs.teamf.domain.maincategory.MainCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainCategoryService {

    private final MainCategoryRepository mainCategoryRepository;

    public List<MainCategory> getMainCategory() {
        List<MainCategory> mainCategories = mainCategoryRepository.findAll();
        System.out.println(mainCategories);
        return mainCategories;
    }
}
