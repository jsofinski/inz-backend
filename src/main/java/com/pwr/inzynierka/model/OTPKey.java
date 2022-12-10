package com.pwr.inzynierka.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "opt_keys")
public class OTPKey {
    @Tolerate
    public OTPKey() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String otpKey;

    @Setter
    private Boolean used = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
