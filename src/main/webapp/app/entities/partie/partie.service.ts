import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPartie } from 'app/shared/model/partie.model';

type EntityResponseType = HttpResponse<IPartie>;
type EntityArrayResponseType = HttpResponse<IPartie[]>;

@Injectable({ providedIn: 'root' })
export class PartieService {
  public resourceUrl = SERVER_API_URL + 'api/parties';

  constructor(protected http: HttpClient) {}

  create(partie: IPartie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partie);
    return this.http
      .post<IPartie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(partie: IPartie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partie);
    return this.http
      .put<IPartie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPartie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPartie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(partie: IPartie): IPartie {
    const copy: IPartie = Object.assign({}, partie, {
      dateCreation: partie.dateCreation && partie.dateCreation.isValid() ? partie.dateCreation.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreation = res.body.dateCreation ? moment(res.body.dateCreation) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((partie: IPartie) => {
        partie.dateCreation = partie.dateCreation ? moment(partie.dateCreation) : undefined;
      });
    }
    return res;
  }
}
