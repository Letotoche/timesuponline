package com.letotoche.timesuponline.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.letotoche.timesuponline.web.rest.TestUtil;

public class TourDeJeuTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TourDeJeu.class);
        TourDeJeu tourDeJeu1 = new TourDeJeu();
        tourDeJeu1.setId(1L);
        TourDeJeu tourDeJeu2 = new TourDeJeu();
        tourDeJeu2.setId(tourDeJeu1.getId());
        assertThat(tourDeJeu1).isEqualTo(tourDeJeu2);
        tourDeJeu2.setId(2L);
        assertThat(tourDeJeu1).isNotEqualTo(tourDeJeu2);
        tourDeJeu1.setId(null);
        assertThat(tourDeJeu1).isNotEqualTo(tourDeJeu2);
    }
}
