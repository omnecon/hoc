import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { GalleryMySuffixComponent } from './gallery-my-suffix.component';
import { GalleryMySuffixDetailComponent } from './gallery-my-suffix-detail.component';
import { GalleryMySuffixPopupComponent } from './gallery-my-suffix-dialog.component';
import { GalleryMySuffixDeletePopupComponent } from './gallery-my-suffix-delete-dialog.component';

@Injectable()
export class GalleryMySuffixResolvePagingParams implements Resolve<any> {

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

export const galleryRoute: Routes = [
    {
        path: 'gallery-my-suffix',
        component: GalleryMySuffixComponent,
        resolve: {
            'pagingParams': GalleryMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.gallery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'gallery-my-suffix/:id',
        component: GalleryMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.gallery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const galleryPopupRoute: Routes = [
    {
        path: 'gallery-my-suffix-new',
        component: GalleryMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.gallery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gallery-my-suffix/:id/edit',
        component: GalleryMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.gallery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gallery-my-suffix/:id/delete',
        component: GalleryMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.gallery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
