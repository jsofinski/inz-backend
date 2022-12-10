package com.pwr.inzynierka.controller;

import com.pwr.inzynierka.dto.UserDTO;
import com.pwr.inzynierka.model.Message;
import com.pwr.inzynierka.model.OTPKey;
import com.pwr.inzynierka.model.User;
import com.pwr.inzynierka.service.MessageService;
import com.pwr.inzynierka.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final MessageService messageService;


    @GetMapping(value = "/test")
    public String test() {
        return "ok";
    }

    @PostMapping(value = "/testBody")
    public String testBody(@RequestBody String rawBody) {
        JSONObject body = new JSONObject(rawBody);
        String data = body.getString("testData");
        return "Received in spring: " + data;
    }

    @GetMapping(value = "/all")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping(value = "/id")
    public String getById(@RequestParam Long id) {
        Optional<User> user = userService.getById(id);
        if (user.isPresent()) {
            return user.get().toString();
        }
        return "Nie ma takiego usera :(";
    }

    @PostMapping(value = "/new")
    public User post(@RequestBody String rawBody) {
        JSONObject body = new JSONObject(rawBody);
        String name = body.getString("name");
        String publicKey = body.getString("publicKey");

        System.out.println(name);
        System.out.println(publicKey);
        return userService.save(new UserDTO(null, name, publicKey));
    }


    @PostMapping(value = "/otpkeys")
    public User postOtpKeys(@RequestBody String rawBody) {
        System.out.println(rawBody);

        JSONObject body = new JSONObject(rawBody);
        JSONArray otpkeys = body.getJSONArray("otpKeys");
        String userPublicKey = body.getString("publicKey");

        // TODO
        // verificiation of otpkeys sender
        Optional<User> optionalUser = userService.getByPublicKey(userPublicKey);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Wrong user public for otpkeys!: " + userPublicKey);
        }

        List<OTPKey> keys = new ArrayList<>();

        System.out.println("Przed petlo w 81");
        otpkeys.toList().forEach(rawKey -> {
            keys.add(new OTPKey(0L, rawKey.toString(), false, optionalUser.get()));
        });
        System.out.println("Przed petli w 81");

        optionalUser.get().getOtpKeys().addAll(keys);

        String publicKey = body.getString("publicKey");

        System.out.println("New opt keys for user: ");
        System.out.println(optionalUser.get());
        keys.forEach(key -> System.out.println(key.getId() + "; " + key.getOtpKey()));

        return userService.update(optionalUser.get());
    }


    @GetMapping(value = "/")
    public String getByPublicKey(@RequestBody String rawBody) {
        JSONObject body = new JSONObject(rawBody);
        System.out.println(rawBody);
        String publicKey = body.getString("publicKey");

        Optional<User> user = userService.getByPublicKey(publicKey);
        if (user.isPresent()) {
            return user.get().toString();
        }
        return "Nie ma takiego usera :( " + publicKey;
    }


    @GetMapping(value = "/name{name}")
    public User getUserByName(@RequestParam String name) {
        Optional<User> user = userService.getByName(name);
        System.out.println(user.isPresent());

        if (user.isPresent()) {
            return user.get();
        }
        throw new RuntimeException("Nie ma takiego usera :( " + name);
    }

    @PostMapping("/message")
    public Message postMessage(@RequestBody String rawBody) {
        JSONObject body = new JSONObject(rawBody);
        System.out.println(rawBody);

        String toPublicKey = body.getString("toUser");
        String fromPublicKey = body.getString("toUser");
        String rawData = body.getString("rawData");

        User toUser = userService.getByPublicKey(toPublicKey).orElseThrow(() -> new RuntimeException("user not found :( (postMessage)"));
        Message message = Message.builder().to(toUser).fromPublicKey(fromPublicKey).rawData(rawData).build();

        toUser.addMessage(message);
        return messageService.save(message);
    }

    @GetMapping("/message/{publicKey}")
    public List<Message> getMessages(@RequestParam String rawBody) {
        JSONObject body = new JSONObject(rawBody);
        String publicKey = body.getString("publicKey");
        User user = userService.getByPublicKey(publicKey).orElseThrow(() -> new RuntimeException("user not found :( (getMessages)"));
        Set<Message> messageSet = user.getMessages();

        return messageSet.stream().sorted(Comparator.comparing(Message::getId)).toList();
    }
}
