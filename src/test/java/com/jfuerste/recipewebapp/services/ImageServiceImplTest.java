package com.jfuerste.recipewebapp.services;

import com.jfuerste.recipewebapp.domain.Recipe;
import com.jfuerste.recipewebapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    ImageServiceImpl imageService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void saveImageFile() throws IOException {
        Long id = 1L;
        MultipartFile file = new MockMultipartFile("imagefile", "test.txt", "text/plain", "teststring".getBytes());
        Recipe recipe = Recipe.builder().id(id).build();

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        ArgumentCaptor<Recipe> captor = ArgumentCaptor.forClass(Recipe.class);

        imageService.saveImageFile(id, file);

        verify(recipeRepository).save(captor.capture());
        Recipe savedRecipe = captor.getValue();
        assertEquals(file.getBytes().length, savedRecipe.getImage().length);
    }
}