package com.chirag.flex.repository;

import com.chirag.flex.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad,Long> {}