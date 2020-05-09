import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TimesuponlineTestModule } from '../../../test.module';
import { TourDeJeuComponent } from 'app/entities/tour-de-jeu/tour-de-jeu.component';
import { TourDeJeuService } from 'app/entities/tour-de-jeu/tour-de-jeu.service';
import { TourDeJeu } from 'app/shared/model/tour-de-jeu.model';

describe('Component Tests', () => {
  describe('TourDeJeu Management Component', () => {
    let comp: TourDeJeuComponent;
    let fixture: ComponentFixture<TourDeJeuComponent>;
    let service: TourDeJeuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TimesuponlineTestModule],
        declarations: [TourDeJeuComponent]
      })
        .overrideTemplate(TourDeJeuComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TourDeJeuComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TourDeJeuService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TourDeJeu(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tourDeJeus && comp.tourDeJeus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
