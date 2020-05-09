import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PartieService } from 'app/entities/partie/partie.service';
import { IPartie, Partie } from 'app/shared/model/partie.model';
import { PhasePartie } from 'app/shared/model/enumerations/phase-partie.model';

describe('Service Tests', () => {
  describe('Partie Service', () => {
    let injector: TestBed;
    let service: PartieService;
    let httpMock: HttpTestingController;
    let elemDefault: IPartie;
    let expectedResult: IPartie | IPartie[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PartieService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Partie(0, 'AAAAAAA', currentDate, PhasePartie.CREEE, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateCreation: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Partie', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateCreation: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreation: currentDate
          },
          returnedFromService
        );

        service.create(new Partie()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Partie', () => {
        const returnedFromService = Object.assign(
          {
            intitule: 'BBBBBB',
            dateCreation: currentDate.format(DATE_FORMAT),
            phase: 'BBBBBB',
            nbMots: 1,
            tempsSablier: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreation: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Partie', () => {
        const returnedFromService = Object.assign(
          {
            intitule: 'BBBBBB',
            dateCreation: currentDate.format(DATE_FORMAT),
            phase: 'BBBBBB',
            nbMots: 1,
            tempsSablier: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateCreation: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Partie', () => {
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
