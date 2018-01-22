import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BannerMySuffixComponent } from './banner-my-suffix.component';
import { BannerMySuffixDetailComponent } from './banner-my-suffix-detail.component';
import { BannerMySuffixPopupComponent } from './banner-my-suffix-dialog.component';
import { BannerMySuffixDeletePopupComponent } from './banner-my-suffix-delete-dialog.component';

@Injectable()
export class BannerMySuffixResolvePagingParams implements Resolve<any> {

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

export const bannerRoute: Routes = [
    {
        path: 'banner-my-suffix',
        component: BannerMySuffixComponent,
        resolve: {
            'pagingParams': BannerMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.banner.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'banner-my-suffix/:id',
        component: BannerMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.banner.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bannerPopupRoute: Routes = [
    {
        path: 'banner-my-suffix-new',
        component: BannerMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.banner.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'banner-my-suffix/:id/edit',
        component: BannerMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.banner.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'banner-my-suffix/:id/delete',
        component: BannerMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.banner.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
