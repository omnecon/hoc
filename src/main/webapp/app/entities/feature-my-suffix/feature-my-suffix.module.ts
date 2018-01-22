import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Hochzeitclick11SharedModule } from '../../shared';
import {
    FeatureMySuffixService,
    FeatureMySuffixPopupService,
    FeatureMySuffixComponent,
    FeatureMySuffixDetailComponent,
    FeatureMySuffixDialogComponent,
    FeatureMySuffixPopupComponent,
    FeatureMySuffixDeletePopupComponent,
    FeatureMySuffixDeleteDialogComponent,
    featureRoute,
    featurePopupRoute,
    FeatureMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...featureRoute,
    ...featurePopupRoute,
];

@NgModule({
    imports: [
        Hochzeitclick11SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FeatureMySuffixComponent,
        FeatureMySuffixDetailComponent,
        FeatureMySuffixDialogComponent,
        FeatureMySuffixDeleteDialogComponent,
        FeatureMySuffixPopupComponent,
        FeatureMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        FeatureMySuffixComponent,
        FeatureMySuffixDialogComponent,
        FeatureMySuffixPopupComponent,
        FeatureMySuffixDeleteDialogComponent,
        FeatureMySuffixDeletePopupComponent,
    ],
    providers: [
        FeatureMySuffixService,
        FeatureMySuffixPopupService,
        FeatureMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Hochzeitclick11FeatureMySuffixModule {}
