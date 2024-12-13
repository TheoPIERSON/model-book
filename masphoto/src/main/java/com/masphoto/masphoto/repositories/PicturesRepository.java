package com.masphoto.masphoto.repositories;

import com.masphoto.masphoto.entities.Pictures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PicturesRepository extends JpaRepository<Pictures, Long> {
}
