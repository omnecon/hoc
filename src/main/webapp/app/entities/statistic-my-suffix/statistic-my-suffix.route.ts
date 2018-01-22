import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { StatisticMySuffixComponent } from './statistic-my-suffix.component';
import { StatisticMySuffixDetailComponent } from './statistic-my-suffix-detail.component';
import { StatisticMySuffixPopupComponent } from './statistic-my-suffix-dialog.component';
import { StatisticMySuffixDeletePopupComponent } from './statistic-my-suffix-delete-dialog.component';

@Injectable()
export class StatisticMySuffixResolvePagingParams implements Resolve<any> {

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

export const statisticRoute: Routes = [
    {
        path: 'statistic-my-suffix',
        component: StatisticMySuffixComponent,
        resolve: {
            'pagingParams': StatisticMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.statistic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'statistic-my-suffix/:id',
        component: StatisticMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.statistic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const statisticPopupRoute: Routes = [
    {
        path: 'statistic-my-suffix-new',
        component: StatisticMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.statistic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'statistic-my-suffix/:id/edit',
        component: StatisticMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.statistic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'statistic-my-suffix/:id/delete',
        component: StatisticMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.statistic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
