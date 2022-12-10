package com.pwr.inzynierka.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "message")
public class Message {
    @Tolerate
    Message() {
    }

    @Id
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, insertable = false, updatable = false)
    private User to;

    private String fromPublicKey;
    private String rawData;
}
