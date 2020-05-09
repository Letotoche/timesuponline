package com.letotoche.timesuponline.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.letotoche.timesuponline.domain.enumeration.EtatMot;

/**
 * The Mot entity.\n@author A true hipster
 */
@ApiModel(description = "The Mot entity.\n@author A true hipster")
@Entity
@Table(name = "mot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * mot
     */
    @NotNull
    @ApiModelProperty(value = "mot", required = true)
    @Column(name = "mot", nullable = false)
    private String mot;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private EtatMot etat;

    @OneToOne
    @JoinColumn(unique = true)
    private User auteur;

    @ManyToOne
    @JsonIgnoreProperties("paquets")
    private Partie partie;

    @OneToOne(mappedBy = "mot")
    @JsonIgnore
    private Reclamation reclamation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMot() {
        return mot;
    }

    public Mot mot(String mot) {
        this.mot = mot;
        return this;
    }

    public void setMot(String mot) {
        this.mot = mot;
    }

    public EtatMot getEtat() {
        return etat;
    }

    public Mot etat(EtatMot etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(EtatMot etat) {
        this.etat = etat;
    }

    public User getAuteur() {
        return auteur;
    }

    public Mot auteur(User user) {
        this.auteur = user;
        return this;
    }

    public void setAuteur(User user) {
        this.auteur = user;
    }

    public Partie getPartie() {
        return partie;
    }

    public Mot partie(Partie partie) {
        this.partie = partie;
        return this;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public Mot reclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
        return this;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mot)) {
            return false;
        }
        return id != null && id.equals(((Mot) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Mot{" +
            "id=" + getId() +
            ", mot='" + getMot() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
