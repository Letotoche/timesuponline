import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartie, Partie } from 'app/shared/model/partie.model';
import { PartieService } from './partie.service';
import { PartieComponent } from './partie.component';
import { PartieDetailComponent } from './partie-detail.component';
import { PartieUpdateComponent } from './partie-update.component';

@Injectable({ providedIn: 'root' })
export class PartieResolve implements Resolve<IPartie> {
  constructor(private service: PartieService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partie: HttpResponse<Partie>) => {
          if (partie.body) {
            return of(partie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Partie());
  }
}

export const partieRoute: Routes = [
  {
    path: '',
    component: PartieComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'timesuponlineApp.partie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PartieDetailComponent,
    resolve: {
      partie: PartieResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'timesuponlineApp.partie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PartieUpdateComponent,
    resolve: {
      partie: PartieResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'timesuponlineApp.partie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PartieUpdateComponent,
    resolve: {
      partie: PartieResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'timesuponlineApp.partie.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
