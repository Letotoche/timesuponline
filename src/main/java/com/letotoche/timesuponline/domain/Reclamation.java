package com.letotoche.timesuponline.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.letotoche.timesuponline.domain.enumeration.EtatReclamation;

/**
 * The Reclamation entity.\n@author A true hipster
 */
@ApiModel(description = "The Reclamation entity.\n@author A true hipster")
@Entity
@Table(name = "reclamation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reclamation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private EtatReclamation etat;

    @OneToOne
    @JoinColumn(unique = true)
    private User auteur;

    @OneToOne
    @JoinColumn(unique = true)
    private Mot mot;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtatReclamation getEtat() {
        return etat;
    }

    public Reclamation etat(EtatReclamation etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(EtatReclamation etat) {
        this.etat = etat;
    }

    public User getAuteur() {
        return auteur;
    }

    public Reclamation auteur(User user) {
        this.auteur = user;
        return this;
    }

    public void setAuteur(User user) {
        this.auteur = user;
    }

    public Mot getMot() {
        return mot;
    }

    public Reclamation mot(Mot mot) {
        this.mot = mot;
        return this;
    }

    public void setMot(Mot mot) {
        this.mot = mot;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reclamation)) {
            return false;
        }
        return id != null && id.equals(((Reclamation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
            "id=" + getId() +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
