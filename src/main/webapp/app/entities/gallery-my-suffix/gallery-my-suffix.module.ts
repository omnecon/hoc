import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Hochzeitclick11SharedModule } from '../../shared';
import {
    GalleryMySuffixService,
    GalleryMySuffixPopupService,
    GalleryMySuffixComponent,
    GalleryMySuffixDetailComponent,
    GalleryMySuffixDialogComponent,
    GalleryMySuffixPopupComponent,
    GalleryMySuffixDeletePopupComponent,
    GalleryMySuffixDeleteDialogComponent,
    galleryRoute,
    galleryPopupRoute,
    GalleryMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...galleryRoute,
    ...galleryPopupRoute,
];

@NgModule({
    imports: [
        Hochzeitclick11SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GalleryMySuffixComponent,
        GalleryMySuffixDetailComponent,
        GalleryMySuffixDialogComponent,
        GalleryMySuffixDeleteDialogComponent,
        GalleryMySuffixPopupComponent,
        GalleryMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        GalleryMySuffixComponent,
        GalleryMySuffixDialogComponent,
        GalleryMySuffixPopupComponent,
        GalleryMySuffixDeleteDialogComponent,
        GalleryMySuffixDeletePopupComponent,
    ],
    providers: [
        GalleryMySuffixService,
        GalleryMySuffixPopupService,
        GalleryMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Hochzeitclick11GalleryMySuffixModule {}
