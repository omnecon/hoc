import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ProfileMySuffixComponent } from './profile-my-suffix.component';
import { ProfileMySuffixDetailComponent } from './profile-my-suffix-detail.component';
import { ProfileMySuffixPopupComponent } from './profile-my-suffix-dialog.component';
import { ProfileMySuffixDeletePopupComponent } from './profile-my-suffix-delete-dialog.component';

@Injectable()
export class ProfileMySuffixResolvePagingParams implements Resolve<any> {

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

export const profileRoute: Routes = [
    {
        path: 'profile-my-suffix',
        component: ProfileMySuffixComponent,
        resolve: {
            'pagingParams': ProfileMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.profile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'profile-my-suffix/:id',
        component: ProfileMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.profile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const profilePopupRoute: Routes = [
    {
        path: 'profile-my-suffix-new',
        component: ProfileMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.profile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profile-my-suffix/:id/edit',
        component: ProfileMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.profile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profile-my-suffix/:id/delete',
        component: ProfileMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.profile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
