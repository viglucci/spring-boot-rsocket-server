package com.solidice.rsocket.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Component
public class RandomUserEmitter {

    private final UserRepository userRepository;
    private final EmitterProcessor<User> emitter = EmitterProcessor.create(false);

    @Autowired
    public RandomUserEmitter(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.emitRandomUsers();
    }

    private void emitRandomUsers() {
        List<User> users = userRepository.getUsers();

        Stream<User> usersStream = Stream.generate(() -> users.get(new Random().nextInt(users.size())));

        Flux.fromStream(usersStream)
            // arbitrary delay of 250 ms between emissions
            .delayElements(Duration.ofMillis(250))
            .doOnNext(this::emitUser)
            .subscribe();
    }

    private void emitUser(User user) {
        emitter.onNext(user);
    }

    public EmitterProcessor<User> getEmitter() {
        return emitter;
    }
}
