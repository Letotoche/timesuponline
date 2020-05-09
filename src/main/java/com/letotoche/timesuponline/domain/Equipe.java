package com.letotoche.timesuponline.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * The Entity entity.\n@author Antoine
 */
@ApiModel(description = "The Entity entity.\n@author Antoine")
@Entity
@Table(name = "equipe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * fieldName
     */
    @NotNull
    @ApiModelProperty(value = "fieldName", required = true)
    @Column(name = "nom", nullable = false)
    private String nom;

    /**
     * scores
     */
    @ApiModelProperty(value = "scores")
    @Column(name = "score_1")
    private Integer score1;

    @Column(name = "score_2")
    private Integer score2;

    @Column(name = "score_3")
    private Integer score3;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "equipe_membre",
               joinColumns = @JoinColumn(name = "equipe_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "membre_id", referencedColumnName = "id"))
    private Set<User> membres = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("equipes")
    private Partie partie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Equipe nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getScore1() {
        return score1;
    }

    public Equipe score1(Integer score1) {
        this.score1 = score1;
        return this;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public Equipe score2(Integer score2) {
        this.score2 = score2;
        return this;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public Integer getScore3() {
        return score3;
    }

    public Equipe score3(Integer score3) {
        this.score3 = score3;
        return this;
    }

    public void setScore3(Integer score3) {
        this.score3 = score3;
    }

    public Set<User> getMembres() {
        return membres;
    }

    public Equipe membres(Set<User> users) {
        this.membres = users;
        return this;
    }

    public Equipe addMembre(User user) {
        this.membres.add(user);
        return this;
    }

    public Equipe removeMembre(User user) {
        this.membres.remove(user);
        return this;
    }

    public void setMembres(Set<User> users) {
        this.membres = users;
    }

    public Partie getPartie() {
        return partie;
    }

    public Equipe partie(Partie partie) {
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
        if (!(o instanceof Equipe)) {
            return false;
        }
        return id != null && id.equals(((Equipe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Equipe{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", score1=" + getScore1() +
            ", score2=" + getScore2() +
            ", score3=" + getScore3() +
            "}";
    }
}
