import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMot, Mot } from 'app/shared/model/mot.model';
import { MotService } from './mot.service';
import { MotComponent } from './mot.component';
import { MotDetailComponent } from './mot-detail.component';
import { MotUpdateComponent } from './mot-update.component';

@Injectable({ providedIn: 'root' })
export class MotResolve implements Resolve<IMot> {
  constructor(private service: MotService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMot> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mot: HttpResponse<Mot>) => {
          if (mot.body) {
            return of(mot.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Mot());
  }
}

export const motRoute: Routes = [
  {
    path: '',
    component: MotComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'timesuponlineApp.mot.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MotDetailComponent,
    resolve: {
      mot: MotResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'timesuponlineApp.mot.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MotUpdateComponent,
    resolve: {
      mot: MotResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'timesuponlineApp.mot.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MotUpdateComponent,
    resolve: {
      mot: MotResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'timesuponlineApp.mot.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
