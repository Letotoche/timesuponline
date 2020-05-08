package com.letotoche.timesuponline.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter intitule;

    private LongFilter joueurId;

    public PartieCriteria() {
    }

    public PartieCriteria(PartieCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.intitule = other.intitule == null ? null : other.intitule.copy();
        this.joueurId = other.joueurId == null ? null : other.joueurId.copy();
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

    public LongFilter getJoueurId() {
        return joueurId;
    }

    public void setJoueurId(LongFilter joueurId) {
        this.joueurId = joueurId;
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
            Objects.equals(joueurId, that.joueurId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        intitule,
        joueurId
        );
    }

    @Override
    public String toString() {
        return "PartieCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (intitule != null ? "intitule=" + intitule + ", " : "") +
                (joueurId != null ? "joueurId=" + joueurId + ", " : "") +
            "}";
    }

}
