package com.letotoche.timesuponline.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.letotoche.timesuponline.web.rest.TestUtil;

public class MotTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mot.class);
        Mot mot1 = new Mot();
        mot1.setId(1L);
        Mot mot2 = new Mot();
        mot2.setId(mot1.getId());
        assertThat(mot1).isEqualTo(mot2);
        mot2.setId(2L);
        assertThat(mot1).isNotEqualTo(mot2);
        mot1.setId(null);
        assertThat(mot1).isNotEqualTo(mot2);
    }
}
