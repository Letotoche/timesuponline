package com.letotoche.timesuponline.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A TourDeJeu.
 */
@Entity
@Table(name = "tour_de_jeu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TourDeJeu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "temps_restant")
    private Instant tempsRestant;

    @Column(name = "date_debute")
    private ZonedDateTime dateDebute;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "tourDeJeu")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Mot> mots = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("tourDeJeus")
    private Partie partie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTempsRestant() {
        return tempsRestant;
    }

    public TourDeJeu tempsRestant(Instant tempsRestant) {
        this.tempsRestant = tempsRestant;
        return this;
    }

    public void setTempsRestant(Instant tempsRestant) {
        this.tempsRestant = tempsRestant;
    }

    public ZonedDateTime getDateDebute() {
        return dateDebute;
    }

    public TourDeJeu dateDebute(ZonedDateTime dateDebute) {
        this.dateDebute = dateDebute;
        return this;
    }

    public void setDateDebute(ZonedDateTime dateDebute) {
        this.dateDebute = dateDebute;
    }

    public User getUser() {
        return user;
    }

    public TourDeJeu user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Mot> getMots() {
        return mots;
    }

    public TourDeJeu mots(Set<Mot> mots) {
        this.mots = mots;
        return this;
    }

    public TourDeJeu addMot(Mot mot) {
        this.mots.add(mot);
        mot.setTourDeJeu(this);
        return this;
    }

    public TourDeJeu removeMot(Mot mot) {
        this.mots.remove(mot);
        mot.setTourDeJeu(null);
        return this;
    }

    public void setMots(Set<Mot> mots) {
        this.mots = mots;
    }

    public Partie getPartie() {
        return partie;
    }

    public TourDeJeu partie(Partie partie) {
        this.partie = partie;
        return this;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TourDeJeu)) {
            return false;
        }
        return id != null && id.equals(((TourDeJeu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TourDeJeu{" +
            "id=" + getId() +
            ", tempsRestant='" + getTempsRestant() + "'" +
            ", dateDebute='" + getDateDebute() + "'" +
            "}";
    }
}
