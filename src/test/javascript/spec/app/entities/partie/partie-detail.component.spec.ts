import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TimesuponlineTestModule } from '../../../test.module';
import { PartieDetailComponent } from 'app/entities/partie/partie-detail.component';
import { Partie } from 'app/shared/model/partie.model';

describe('Component Tests', () => {
  describe('Partie Management Detail Component', () => {
    let comp: PartieDetailComponent;
    let fixture: ComponentFixture<PartieDetailComponent>;
    const route = ({ data: of({ partie: new Partie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TimesuponlineTestModule],
        declarations: [PartieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PartieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load partie on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
