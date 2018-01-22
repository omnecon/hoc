import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { InquiryMySuffixComponent } from './inquiry-my-suffix.component';
import { InquiryMySuffixDetailComponent } from './inquiry-my-suffix-detail.component';
import { InquiryMySuffixPopupComponent } from './inquiry-my-suffix-dialog.component';
import { InquiryMySuffixDeletePopupComponent } from './inquiry-my-suffix-delete-dialog.component';

@Injectable()
export class InquiryMySuffixResolvePagingParams implements Resolve<any> {

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

export const inquiryRoute: Routes = [
    {
        path: 'inquiry-my-suffix',
        component: InquiryMySuffixComponent,
        resolve: {
            'pagingParams': InquiryMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.inquiry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inquiry-my-suffix/:id',
        component: InquiryMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.inquiry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inquiryPopupRoute: Routes = [
    {
        path: 'inquiry-my-suffix-new',
        component: InquiryMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.inquiry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inquiry-my-suffix/:id/edit',
        component: InquiryMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.inquiry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inquiry-my-suffix/:id/delete',
        component: InquiryMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.inquiry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
