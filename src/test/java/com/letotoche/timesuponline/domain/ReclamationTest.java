package com.letotoche.timesuponline.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.letotoche.timesuponline.web.rest.TestUtil;

public class ReclamationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reclamation.class);
        Reclamation reclamation1 = new Reclamation();
        reclamation1.setId(1L);
        Reclamation reclamation2 = new Reclamation();
        reclamation2.setId(reclamation1.getId());
        assertThat(reclamation1).isEqualTo(reclamation2);
        reclamation2.setId(2L);
        assertThat(reclamation1).isNotEqualTo(reclamation2);
        reclamation1.setId(null);
        assertThat(reclamation1).isNotEqualTo(reclamation2);
    }
}
