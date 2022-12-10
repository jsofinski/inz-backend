package com.pwr.inzynierka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "users")
public class User {
    @Tolerate
    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String publicKey;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OTPKey> otpKeys;

    @OneToMany(mappedBy = "to", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Message> messages;

    public String toString() {
        return "id: " + id + "; name: " + name + "; publicKey " + publicKey;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("publicKey", publicKey);
        JSONArray otpJson = new JSONArray(otpKeys);
        json.put("otpKeys", otpJson);
        return json;
    }

    public void addMessage(Message message) {
        if (this.messages == null) {
            throw new RuntimeException("user messages is null!; userId: " + this.id);
        }
        this.messages.add(message);
    }
}
