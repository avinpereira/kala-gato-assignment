package com.kalagato.TollApplication.loaders;

import com.kalagato.TollApplication.entity.Toll;
import com.kalagato.TollApplication.entity.TollBooth;
import com.kalagato.TollApplication.repository.TollBoothRepository;
import com.kalagato.TollApplication.repository.TollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Component
public class TollRegistry implements CommandLineRunner {
    @Autowired
    private TollRepository tollRepository;
    @Autowired
    private TollBoothRepository tollBoothRepository;
    @Override
    public void run(String... args) throws Exception {
        Toll bangaloreToll = tollRepository.save(Toll.builder()
                .id(1L)
                .name("Bangalore Toll").build());
        Toll hyderabadToll = tollRepository.save(Toll.builder()
                .id(2L)
                .name("Hyderabad Toll").build());
        Toll mangaloreToll = tollRepository.save(Toll.builder()
                .id(3L)
                .name("Mangalore Toll").build());
        List<Long> range = LongStream.range(1, 4).boxed().collect(Collectors.toList());
        range.forEach(id -> {
            tollBoothRepository.save(TollBooth.builder()
                    .parentToll(bangaloreToll)
                    .build());
        });
        range.forEach(id -> {
            tollBoothRepository.save(TollBooth.builder()
                    .parentToll(mangaloreToll)
                    .build());
        });
        range.forEach(id -> {
            tollBoothRepository.save(TollBooth.builder()
                    .parentToll(hyderabadToll)
                    .build());
        });
    }
}
