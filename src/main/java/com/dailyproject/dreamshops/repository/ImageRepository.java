package com.dailyproject.dreamshops.repository;

import com.dailyproject.dreamshops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
