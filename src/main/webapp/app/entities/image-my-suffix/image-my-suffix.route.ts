import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ImageMySuffixComponent } from './image-my-suffix.component';
import { ImageMySuffixDetailComponent } from './image-my-suffix-detail.component';
import { ImageMySuffixPopupComponent } from './image-my-suffix-dialog.component';
import { ImageMySuffixDeletePopupComponent } from './image-my-suffix-delete-dialog.component';

@Injectable()
export class ImageMySuffixResolvePagingParams implements Resolve<any> {

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

export const imageRoute: Routes = [
    {
        path: 'image-my-suffix',
        component: ImageMySuffixComponent,
        resolve: {
            'pagingParams': ImageMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.image.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'image-my-suffix/:id',
        component: ImageMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.image.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const imagePopupRoute: Routes = [
    {
        path: 'image-my-suffix-new',
        component: ImageMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.image.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'image-my-suffix/:id/edit',
        component: ImageMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.image.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'image-my-suffix/:id/delete',
        component: ImageMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.image.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
