package com.idocrew.weddingwise.repositories;

import com.idocrew.weddingwise.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestListRepository extends JpaRepository<Guest, Long> {
}
