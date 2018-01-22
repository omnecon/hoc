import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FeatureMySuffixComponent } from './feature-my-suffix.component';
import { FeatureMySuffixDetailComponent } from './feature-my-suffix-detail.component';
import { FeatureMySuffixPopupComponent } from './feature-my-suffix-dialog.component';
import { FeatureMySuffixDeletePopupComponent } from './feature-my-suffix-delete-dialog.component';

@Injectable()
export class FeatureMySuffixResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const featureRoute: Routes = [
    {
        path: 'feature-my-suffix',
        component: FeatureMySuffixComponent,
        resolve: {
            'pagingParams': FeatureMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.feature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'feature-my-suffix/:id',
        component: FeatureMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.feature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const featurePopupRoute: Routes = [
    {
        path: 'feature-my-suffix-new',
        component: FeatureMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.feature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'feature-my-suffix/:id/edit',
        component: FeatureMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.feature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'feature-my-suffix/:id/delete',
        component: FeatureMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.feature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
