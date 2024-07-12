package com.insignia.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insignia.entity.OrderAndProductLink;

public interface OrderAndProductLinkRepository extends JpaRepository<OrderAndProductLink, Serializable> {

}
