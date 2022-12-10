package com.pwr.inzynierka.service;

import com.pwr.inzynierka.model.Message;
import com.pwr.inzynierka.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {

    private MessageRepository repository;

    public Message save(Message message) {
        return repository.save(message);
    }
}
