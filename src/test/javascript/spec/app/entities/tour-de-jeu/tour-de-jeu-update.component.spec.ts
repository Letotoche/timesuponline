import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TimesuponlineTestModule } from '../../../test.module';
import { TourDeJeuUpdateComponent } from 'app/entities/tour-de-jeu/tour-de-jeu-update.component';
import { TourDeJeuService } from 'app/entities/tour-de-jeu/tour-de-jeu.service';
import { TourDeJeu } from 'app/shared/model/tour-de-jeu.model';

describe('Component Tests', () => {
  describe('TourDeJeu Management Update Component', () => {
    let comp: TourDeJeuUpdateComponent;
    let fixture: ComponentFixture<TourDeJeuUpdateComponent>;
    let service: TourDeJeuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TimesuponlineTestModule],
        declarations: [TourDeJeuUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TourDeJeuUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TourDeJeuUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TourDeJeuService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TourDeJeu(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TourDeJeu();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
