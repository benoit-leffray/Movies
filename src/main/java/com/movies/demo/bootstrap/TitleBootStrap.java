package com.movies.demo.bootstrap;

import com.movies.demo.domain.Person;
import com.movies.demo.domain.Principal;
import com.movies.demo.domain.Ratings;
import com.movies.demo.domain.Title;
import com.movies.demo.repositories.PersonRepository;
import com.movies.demo.repositories.PrincipalRepository;
import com.movies.demo.repositories.RatingsRepository;
import com.movies.demo.repositories.TitleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Slf4j
@Component
public class TitleBootStrap implements ApplicationListener<ContextRefreshedEvent> {

    private final TitleRepository titleRepository;
    private final PrincipalRepository principalRepository;
    private final PersonRepository personRepository;
    private final RatingsRepository ratingsRepository;

    public TitleBootStrap(TitleRepository titleRepository, PrincipalRepository principalRepository, PersonRepository personRepository, RatingsRepository ratingsRepository) {
        this.titleRepository = titleRepository;
        this.principalRepository = principalRepository;
        this.personRepository = personRepository;
        this.ratingsRepository = ratingsRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Person person = new Person("Primary Name", 1900, 2020);
        person = personRepository.save(person);

        Person person2 = new Person("Primary Name2", 1901, 2021);
        personRepository.save(person2);

        Person person3 = new Person("Primary Name3", 1902, 2022);
        personRepository.save(person3);

        Person person4 = new Person("Primary Name4", 1903, 2023);
        personRepository.save(person4);

        Person personAlone = new Person("Name Alone", 1904, 2024);
        personRepository.save(personAlone);

        Title title1 = new Title("type", "Primary Title", "Original Title", false, 1928, 1929, 90, "horror");
        titleRepository.save(title1);

        Title title2 = new Title("type", "Primary Title2", "Original Title2", false, 1930, 1931, 120, "horror");
        titleRepository.save(title2);

        Title title3 = new Title("type", "Primary Title3", "Original Title3", false, 1931, 1932, 121, "romance");
        titleRepository.save(title3);

        Title titleAlone = new Title("type", "Primary Title alone", "Original Title alone", false, 1932, 1933, 124, "romance");
        titleRepository.save(titleAlone);

        Title titleCommon = new Title("type", "Primary Title Coincidence", "Original Title Coincidence", false, 1933, 1934, 125, "romance");
        titleRepository.save(titleCommon);

        Title title = titleRepository.findOneByPrimaryTitleOrOriginalTitle("Primary Title", "Primary Title");

        person.getKnownForTitles().add(title);
        person.getKnownForTitles().add(title2);
        person.getKnownForTitles().add(title3);
        person.getKnownForTitles().add(titleCommon);
        personRepository.save(person);

        person2.getKnownForTitles().add(title);
        person2.getKnownForTitles().add(titleCommon);
        personRepository.save(person2);

        person3.getKnownForTitles().add(title2);
        personRepository.save(person3);

        personAlone.getKnownForTitles().add(titleAlone);
        personRepository.save(personAlone);

        Principal principal = new Principal("category", "characters", "job", person);
        Principal principal2 = new Principal("category2", "characters2", "job1", person);
        Principal principal3 = new Principal("category3", "characters3", "job2", person);

        title.addPrincipal(principal);
        title2.addPrincipal(principal2);
        title3.addPrincipal(principal3);

        title = titleRepository.save(title);
        title2 = titleRepository.save(title2);
        title3 = titleRepository.save(title3);

        Ratings ratings = new Ratings(title, 78.1, 200);
        ratings = ratingsRepository.save(ratings);

        Ratings ratings2 = new Ratings(title2, 77.7, 199);
        ratings2 = ratingsRepository.save(ratings2);

        title.setRatings(ratings);
        title = titleRepository.save(title);

        title2.setRatings(ratings2);
        title2 = titleRepository.save(title2);

        log.info("Saved title: " + title.toString());
        log.info("Saved title2: " + title2.toString());
    }
}