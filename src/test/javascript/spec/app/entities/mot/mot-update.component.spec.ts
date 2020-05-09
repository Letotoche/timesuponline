import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TimesuponlineTestModule } from '../../../test.module';
import { MotUpdateComponent } from 'app/entities/mot/mot-update.component';
import { MotService } from 'app/entities/mot/mot.service';
import { Mot } from 'app/shared/model/mot.model';

describe('Component Tests', () => {
  describe('Mot Management Update Component', () => {
    let comp: MotUpdateComponent;
    let fixture: ComponentFixture<MotUpdateComponent>;
    let service: MotService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TimesuponlineTestModule],
        declarations: [MotUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MotUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MotUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MotService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Mot(123);
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
        const entity = new Mot();
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
