package com.letotoche.timesuponline.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Partie.
 */
@Entity
@Table(name = "partie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Partie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "intitule", length = 100, nullable = false)
    private String intitule;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "partie_joueur",
               joinColumns = @JoinColumn(name = "partie_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "joueur_id", referencedColumnName = "id"))
    private Set<User> joueurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public Partie intitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Set<User> getJoueurs() {
        return joueurs;
    }

    public Partie joueurs(Set<User> users) {
        this.joueurs = users;
        return this;
    }

    public Partie addJoueur(User user) {
        this.joueurs.add(user);
        return this;
    }

    public Partie removeJoueur(User user) {
        this.joueurs.remove(user);
        return this;
    }

    public void setJoueurs(Set<User> users) {
        this.joueurs = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partie)) {
            return false;
        }
        return id != null && id.equals(((Partie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Partie{" +
            "id=" + getId() +
            ", intitule='" + getIntitule() + "'" +
            "}";
    }
}
