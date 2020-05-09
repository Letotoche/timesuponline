package com.letotoche.timesuponline.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.letotoche.timesuponline.domain.enumeration.PhasePartie;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.letotoche.timesuponline.domain.Partie} entity. This class is used
 * in {@link com.letotoche.timesuponline.web.rest.PartieResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /parties?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PartieCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PhasePartie
     */
    public static class PhasePartieFilter extends Filter<PhasePartie> {

        public PhasePartieFilter() {
        }

        public PhasePartieFilter(PhasePartieFilter filter) {
            super(filter);
        }

        @Override
        public PhasePartieFilter copy() {
            return new PhasePartieFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter intitule;

    private LocalDateFilter dateCreation;

    private PhasePartieFilter phase;

    private IntegerFilter nbMots;

    private IntegerFilter tempsSablier;

    private LongFilter equipeId;

    private LongFilter masterId;

    private LongFilter joueurId;

    private LongFilter paquetId;

    public PartieCriteria() {
    }

    public PartieCriteria(PartieCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.intitule = other.intitule == null ? null : other.intitule.copy();
        this.dateCreation = other.dateCreation == null ? null : other.dateCreation.copy();
        this.phase = other.phase == null ? null : other.phase.copy();
        this.nbMots = other.nbMots == null ? null : other.nbMots.copy();
        this.tempsSablier = other.tempsSablier == null ? null : other.tempsSablier.copy();
        this.equipeId = other.equipeId == null ? null : other.equipeId.copy();
        this.masterId = other.masterId == null ? null : other.masterId.copy();
        this.joueurId = other.joueurId == null ? null : other.joueurId.copy();
        this.paquetId = other.paquetId == null ? null : other.paquetId.copy();
    }

    @Override
    public PartieCriteria copy() {
        return new PartieCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIntitule() {
        return intitule;
    }

    public void setIntitule(StringFilter intitule) {
        this.intitule = intitule;
    }

    public LocalDateFilter getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateFilter dateCreation) {
        this.dateCreation = dateCreation;
    }

    public PhasePartieFilter getPhase() {
        return phase;
    }

    public void setPhase(PhasePartieFilter phase) {
        this.phase = phase;
    }

    public IntegerFilter getNbMots() {
        return nbMots;
    }

    public void setNbMots(IntegerFilter nbMots) {
        this.nbMots = nbMots;
    }

    public IntegerFilter getTempsSablier() {
        return tempsSablier;
    }

    public void setTempsSablier(IntegerFilter tempsSablier) {
        this.tempsSablier = tempsSablier;
    }

    public LongFilter getEquipeId() {
        return equipeId;
    }

    public void setEquipeId(LongFilter equipeId) {
        this.equipeId = equipeId;
    }

    public LongFilter getMasterId() {
        return masterId;
    }

    public void setMasterId(LongFilter masterId) {
        this.masterId = masterId;
    }

    public LongFilter getJoueurId() {
        return joueurId;
    }

    public void setJoueurId(LongFilter joueurId) {
        this.joueurId = joueurId;
    }

    public LongFilter getPaquetId() {
        return paquetId;
    }

    public void setPaquetId(LongFilter paquetId) {
        this.paquetId = paquetId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PartieCriteria that = (PartieCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(intitule, that.intitule) &&
            Objects.equals(dateCreation, that.dateCreation) &&
            Objects.equals(phase, that.phase) &&
            Objects.equals(nbMots, that.nbMots) &&
            Objects.equals(tempsSablier, that.tempsSablier) &&
            Objects.equals(equipeId, that.equipeId) &&
            Objects.equals(masterId, that.masterId) &&
            Objects.equals(joueurId, that.joueurId) &&
            Objects.equals(paquetId, that.paquetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        intitule,
        dateCreation,
        phase,
        nbMots,
        tempsSablier,
        equipeId,
        masterId,
        joueurId,
        paquetId
        );
    }

    @Override
    public String toString() {
        return "PartieCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (intitule != null ? "intitule=" + intitule + ", " : "") +
                (dateCreation != null ? "dateCreation=" + dateCreation + ", " : "") +
                (phase != null ? "phase=" + phase + ", " : "") +
                (nbMots != null ? "nbMots=" + nbMots + ", " : "") +
                (tempsSablier != null ? "tempsSablier=" + tempsSablier + ", " : "") +
                (equipeId != null ? "equipeId=" + equipeId + ", " : "") +
                (masterId != null ? "masterId=" + masterId + ", " : "") +
                (joueurId != null ? "joueurId=" + joueurId + ", " : "") +
                (paquetId != null ? "paquetId=" + paquetId + ", " : "") +
            "}";
    }

}
