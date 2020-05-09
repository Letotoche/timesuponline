import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TimesuponlineTestModule } from '../../../test.module';
import { TourDeJeuDetailComponent } from 'app/entities/tour-de-jeu/tour-de-jeu-detail.component';
import { TourDeJeu } from 'app/shared/model/tour-de-jeu.model';

describe('Component Tests', () => {
  describe('TourDeJeu Management Detail Component', () => {
    let comp: TourDeJeuDetailComponent;
    let fixture: ComponentFixture<TourDeJeuDetailComponent>;
    const route = ({ data: of({ tourDeJeu: new TourDeJeu(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TimesuponlineTestModule],
        declarations: [TourDeJeuDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TourDeJeuDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TourDeJeuDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tourDeJeu on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tourDeJeu).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
