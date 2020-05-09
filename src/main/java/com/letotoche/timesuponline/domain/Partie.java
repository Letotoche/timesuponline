package com.letotoche.timesuponline.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.letotoche.timesuponline.domain.enumeration.PhasePartie;

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

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "phase", nullable = false)
    private PhasePartie phase;

    /**
     * options
     */
    @ApiModelProperty(value = "options")
    @Column(name = "nb_mots")
    private Integer nbMots;

    @Column(name = "temps_sablier")
    private Integer tempsSablier;

    @OneToMany(mappedBy = "partie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Equipe> equipes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("parties")
    private User master;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "partie_joueur",
               joinColumns = @JoinColumn(name = "partie_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "joueur_id", referencedColumnName = "id"))
    private Set<User> joueurs = new HashSet<>();

    @OneToMany(mappedBy = "partie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Mot> paquets = new HashSet<>();

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

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Partie dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public PhasePartie getPhase() {
        return phase;
    }

    public Partie phase(PhasePartie phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(PhasePartie phase) {
        this.phase = phase;
    }

    public Integer getNbMots() {
        return nbMots;
    }

    public Partie nbMots(Integer nbMots) {
        this.nbMots = nbMots;
        return this;
    }

    public void setNbMots(Integer nbMots) {
        this.nbMots = nbMots;
    }

    public Integer getTempsSablier() {
        return tempsSablier;
    }

    public Partie tempsSablier(Integer tempsSablier) {
        this.tempsSablier = tempsSablier;
        return this;
    }

    public void setTempsSablier(Integer tempsSablier) {
        this.tempsSablier = tempsSablier;
    }

    public Set<Equipe> getEquipes() {
        return equipes;
    }

    public Partie equipes(Set<Equipe> equipes) {
        this.equipes = equipes;
        return this;
    }

    public Partie addEquipe(Equipe equipe) {
        this.equipes.add(equipe);
        equipe.setPartie(this);
        return this;
    }

    public Partie removeEquipe(Equipe equipe) {
        this.equipes.remove(equipe);
        equipe.setPartie(null);
        return this;
    }

    public void setEquipes(Set<Equipe> equipes) {
        this.equipes = equipes;
    }

    public User getMaster() {
        return master;
    }

    public Partie master(User user) {
        this.master = user;
        return this;
    }

    public void setMaster(User user) {
        this.master = user;
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

    public Set<Mot> getPaquets() {
        return paquets;
    }

    public Partie paquets(Set<Mot> mots) {
        this.paquets = mots;
        return this;
    }

    public Partie addPaquet(Mot mot) {
        this.paquets.add(mot);
        mot.setPartie(this);
        return this;
    }

    public Partie removePaquet(Mot mot) {
        this.paquets.remove(mot);
        mot.setPartie(null);
        return this;
    }

    public void setPaquets(Set<Mot> mots) {
        this.paquets = mots;
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
            ", dateCreation='" + getDateCreation() + "'" +
            ", phase='" + getPhase() + "'" +
            ", nbMots=" + getNbMots() +
            ", tempsSablier=" + getTempsSablier() +
            "}";
    }
}
