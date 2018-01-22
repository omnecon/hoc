import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Hochzeitclick11SharedModule } from '../../shared';
import {
    BannerMySuffixService,
    BannerMySuffixPopupService,
    BannerMySuffixComponent,
    BannerMySuffixDetailComponent,
    BannerMySuffixDialogComponent,
    BannerMySuffixPopupComponent,
    BannerMySuffixDeletePopupComponent,
    BannerMySuffixDeleteDialogComponent,
    bannerRoute,
    bannerPopupRoute,
    BannerMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...bannerRoute,
    ...bannerPopupRoute,
];

@NgModule({
    imports: [
        Hochzeitclick11SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BannerMySuffixComponent,
        BannerMySuffixDetailComponent,
        BannerMySuffixDialogComponent,
        BannerMySuffixDeleteDialogComponent,
        BannerMySuffixPopupComponent,
        BannerMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        BannerMySuffixComponent,
        BannerMySuffixDialogComponent,
        BannerMySuffixPopupComponent,
        BannerMySuffixDeleteDialogComponent,
        BannerMySuffixDeletePopupComponent,
    ],
    providers: [
        BannerMySuffixService,
        BannerMySuffixPopupService,
        BannerMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Hochzeitclick11BannerMySuffixModule {}
