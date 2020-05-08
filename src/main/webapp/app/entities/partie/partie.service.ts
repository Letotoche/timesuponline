import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<IPartie>(this.resourceUrl, partie, { observe: 'response' });
  }

  update(partie: IPartie): Observable<EntityResponseType> {
    return this.http.put<IPartie>(this.resourceUrl, partie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
