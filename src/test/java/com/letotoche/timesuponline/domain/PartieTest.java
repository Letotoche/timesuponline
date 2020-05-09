package com.letotoche.timesuponline.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.letotoche.timesuponline.web.rest.TestUtil;

public class PartieTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partie.class);
        Partie partie1 = new Partie();
        partie1.setId(1L);
        Partie partie2 = new Partie();
        partie2.setId(partie1.getId());
        assertThat(partie1).isEqualTo(partie2);
        partie2.setId(2L);
        assertThat(partie1).isNotEqualTo(partie2);
        partie1.setId(null);
        assertThat(partie1).isNotEqualTo(partie2);
    }
}
