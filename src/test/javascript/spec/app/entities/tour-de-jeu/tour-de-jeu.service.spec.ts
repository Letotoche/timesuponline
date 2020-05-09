import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TourDeJeuService } from 'app/entities/tour-de-jeu/tour-de-jeu.service';
import { ITourDeJeu, TourDeJeu } from 'app/shared/model/tour-de-jeu.model';

describe('Service Tests', () => {
  describe('TourDeJeu Service', () => {
    let injector: TestBed;
    let service: TourDeJeuService;
    let httpMock: HttpTestingController;
    let elemDefault: ITourDeJeu;
    let expectedResult: ITourDeJeu | ITourDeJeu[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TourDeJeuService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TourDeJeu(0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            tempsRestant: currentDate.format(DATE_TIME_FORMAT),
            dateDebute: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TourDeJeu', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            tempsRestant: currentDate.format(DATE_TIME_FORMAT),
            dateDebute: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            tempsRestant: currentDate,
            dateDebute: currentDate
          },
          returnedFromService
        );

        service.create(new TourDeJeu()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TourDeJeu', () => {
        const returnedFromService = Object.assign(
          {
            tempsRestant: currentDate.format(DATE_TIME_FORMAT),
            dateDebute: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            tempsRestant: currentDate,
            dateDebute: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TourDeJeu', () => {
        const returnedFromService = Object.assign(
          {
            tempsRestant: currentDate.format(DATE_TIME_FORMAT),
            dateDebute: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            tempsRestant: currentDate,
            dateDebute: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TourDeJeu', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
