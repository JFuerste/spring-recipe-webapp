package com.jfuerste.recipewebapp.services;

import com.jfuerste.recipewebapp.domain.Recipe;
import com.jfuerste.recipewebapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long id, MultipartFile file) {
        log.debug("New Recipe image");
        Recipe recipe = recipeRepository.findById(id).get();

        try {
            int i = 0;
            Byte[] image = new Byte[file.getBytes().length];
            for (byte b : file.getBytes()){
                image[i] = b;
                i++;
            }

            recipe.setImage(image);
            recipeRepository.save(recipe);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
