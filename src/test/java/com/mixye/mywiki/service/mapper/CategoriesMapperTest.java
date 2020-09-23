package com.mixye.mywiki.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoriesMapperTest {

    private CategoriesMapper categoriesMapper;

    @BeforeEach
    public void setUp() {
        categoriesMapper = new CategoriesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(categoriesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(categoriesMapper.fromId(null)).isNull();
    }
}
