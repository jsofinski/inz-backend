package com.pwr.inzynierka.repository;

import com.pwr.inzynierka.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
