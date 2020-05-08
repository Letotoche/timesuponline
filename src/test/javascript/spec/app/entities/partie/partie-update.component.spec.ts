import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TimesuponlineTestModule } from '../../../test.module';
import { PartieUpdateComponent } from 'app/entities/partie/partie-update.component';
import { PartieService } from 'app/entities/partie/partie.service';
import { Partie } from 'app/shared/model/partie.model';

describe('Component Tests', () => {
  describe('Partie Management Update Component', () => {
    let comp: PartieUpdateComponent;
    let fixture: ComponentFixture<PartieUpdateComponent>;
    let service: PartieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TimesuponlineTestModule],
        declarations: [PartieUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PartieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Partie(123);
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
        const entity = new Partie();
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
