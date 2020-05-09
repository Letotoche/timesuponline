import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TimesuponlineTestModule } from '../../../test.module';
import { MotDetailComponent } from 'app/entities/mot/mot-detail.component';
import { Mot } from 'app/shared/model/mot.model';

describe('Component Tests', () => {
  describe('Mot Management Detail Component', () => {
    let comp: MotDetailComponent;
    let fixture: ComponentFixture<MotDetailComponent>;
    const route = ({ data: of({ mot: new Mot(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TimesuponlineTestModule],
        declarations: [MotDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MotDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MotDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mot on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mot).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
