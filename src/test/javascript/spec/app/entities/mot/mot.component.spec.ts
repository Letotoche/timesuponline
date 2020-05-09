import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TimesuponlineTestModule } from '../../../test.module';
import { MotComponent } from 'app/entities/mot/mot.component';
import { MotService } from 'app/entities/mot/mot.service';
import { Mot } from 'app/shared/model/mot.model';

describe('Component Tests', () => {
  describe('Mot Management Component', () => {
    let comp: MotComponent;
    let fixture: ComponentFixture<MotComponent>;
    let service: MotService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TimesuponlineTestModule],
        declarations: [MotComponent]
      })
        .overrideTemplate(MotComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MotComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MotService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Mot(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.mots && comp.mots[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
