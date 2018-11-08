package com.movies.demo.repositories;

import com.movies.demo.domain.Ratings;
import com.movies.demo.domain.Title;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TitleRepositoryIT {

    @Autowired
    TitleRepository titleRepository;

    @Autowired
    RatingsRepository ratingsRepository;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findTypeCasted() throws Exception {

        Iterable<Title> titles = titleRepository.findAllByGenresOrderByRatings_AverageRatingDesc("horror");

        Iterator<Title> it = titles.iterator();
        assertEquals("Primary Title", it.next().getPrimaryTitle());
        assertEquals("Primary Title2", it.next().getPrimaryTitle());
    }

    @Test
    public void findCoincidences() throws Exception {

        Iterable<Title> titles = titleRepository.findAllByPersonCoincidence("Primary Name", "Primary Name2");

        Iterator<Title> it = titles.iterator();
        assertEquals("Primary Title", it.next().getPrimaryTitle());
        assertEquals("Primary Title Coincidence", it.next().getPrimaryTitle());
    }

    @Test
    public void findByPrimaryTitleOrOriginalTitle() throws Exception {

        Title title = titleRepository.findOneByPrimaryTitleOrOriginalTitle("Primary Title", "Original Title");

        assertEquals("Primary Title", title.getPrimaryTitle());
        assertEquals("Original Title", title.getOriginalTitle());

        title = titleRepository.findOneByPrimaryTitleOrOriginalTitle("doesn't exist", "doesn't exist");

        assertNull(null, title);
    }
}
