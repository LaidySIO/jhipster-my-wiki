import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategories, Categories } from 'app/shared/model/categories.model';
import { CategoriesService } from './categories.service';
import { CategoriesComponent } from './categories.component';
import { CategoriesDetailComponent } from './categories-detail.component';
import { CategoriesUpdateComponent } from './categories-update.component';

@Injectable({ providedIn: 'root' })
export class CategoriesResolve implements Resolve<ICategories> {
  constructor(private service: CategoriesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategories> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categories: HttpResponse<Categories>) => {
          if (categories.body) {
            return of(categories.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Categories());
  }
}

export const categoriesRoute: Routes = [
  {
    path: '',
    component: CategoriesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterMyWikiApp.categories.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategoriesDetailComponent,
    resolve: {
      categories: CategoriesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterMyWikiApp.categories.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategoriesUpdateComponent,
    resolve: {
      categories: CategoriesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterMyWikiApp.categories.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategoriesUpdateComponent,
    resolve: {
      categories: CategoriesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterMyWikiApp.categories.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
