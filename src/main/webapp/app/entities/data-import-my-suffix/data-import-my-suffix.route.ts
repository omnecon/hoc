import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DataImportMySuffixComponent } from './data-import-my-suffix.component';
import { DataImportMySuffixDetailComponent } from './data-import-my-suffix-detail.component';
import { DataImportMySuffixPopupComponent } from './data-import-my-suffix-dialog.component';
import { DataImportMySuffixDeletePopupComponent } from './data-import-my-suffix-delete-dialog.component';

@Injectable()
export class DataImportMySuffixResolvePagingParams implements Resolve<any> {

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

export const dataImportRoute: Routes = [
    {
        path: 'data-import-my-suffix',
        component: DataImportMySuffixComponent,
        resolve: {
            'pagingParams': DataImportMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.dataImport.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'data-import-my-suffix/:id',
        component: DataImportMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.dataImport.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dataImportPopupRoute: Routes = [
    {
        path: 'data-import-my-suffix-new',
        component: DataImportMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.dataImport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'data-import-my-suffix/:id/edit',
        component: DataImportMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.dataImport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'data-import-my-suffix/:id/delete',
        component: DataImportMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hochzeitclick11App.dataImport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
