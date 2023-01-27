package com.sparcs.teamf.api.maincategory.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public record MainCategoryDto(long id, String name) {

}
