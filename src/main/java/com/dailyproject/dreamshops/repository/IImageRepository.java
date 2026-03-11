package com.dailyproject.dreamshops.repository;

import com.dailyproject.dreamshops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
