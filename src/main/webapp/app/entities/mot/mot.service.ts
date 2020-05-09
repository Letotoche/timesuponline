import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMot } from 'app/shared/model/mot.model';

type EntityResponseType = HttpResponse<IMot>;
type EntityArrayResponseType = HttpResponse<IMot[]>;

@Injectable({ providedIn: 'root' })
export class MotService {
  public resourceUrl = SERVER_API_URL + 'api/mots';

  constructor(protected http: HttpClient) {}

  create(mot: IMot): Observable<EntityResponseType> {
    return this.http.post<IMot>(this.resourceUrl, mot, { observe: 'response' });
  }

  update(mot: IMot): Observable<EntityResponseType> {
    return this.http.put<IMot>(this.resourceUrl, mot, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMot>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMot[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
