import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITourDeJeu, TourDeJeu } from 'app/shared/model/tour-de-jeu.model';
import { TourDeJeuService } from './tour-de-jeu.service';
import { TourDeJeuComponent } from './tour-de-jeu.component';
import { TourDeJeuDetailComponent } from './tour-de-jeu-detail.component';
import { TourDeJeuUpdateComponent } from './tour-de-jeu-update.component';

@Injectable({ providedIn: 'root' })
export class TourDeJeuResolve implements Resolve<ITourDeJeu> {
  constructor(private service: TourDeJeuService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITourDeJeu> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tourDeJeu: HttpResponse<TourDeJeu>) => {
          if (tourDeJeu.body) {
            return of(tourDeJeu.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TourDeJeu());
  }
}

export const tourDeJeuRoute: Routes = [
  {
    path: '',
    component: TourDeJeuComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'timesuponlineApp.tourDeJeu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TourDeJeuDetailComponent,
    resolve: {
      tourDeJeu: TourDeJeuResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'timesuponlineApp.tourDeJeu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TourDeJeuUpdateComponent,
    resolve: {
      tourDeJeu: TourDeJeuResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'timesuponlineApp.tourDeJeu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TourDeJeuUpdateComponent,
    resolve: {
      tourDeJeu: TourDeJeuResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'timesuponlineApp.tourDeJeu.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
