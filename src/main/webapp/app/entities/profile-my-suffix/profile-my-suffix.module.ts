import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Hochzeitclick11SharedModule } from '../../shared';
import {
    ProfileMySuffixService,
    ProfileMySuffixPopupService,
    ProfileMySuffixComponent,
    ProfileMySuffixDetailComponent,
    ProfileMySuffixDialogComponent,
    ProfileMySuffixPopupComponent,
    ProfileMySuffixDeletePopupComponent,
    ProfileMySuffixDeleteDialogComponent,
    profileRoute,
    profilePopupRoute,
    ProfileMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...profileRoute,
    ...profilePopupRoute,
];

@NgModule({
    imports: [
        Hochzeitclick11SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProfileMySuffixComponent,
        ProfileMySuffixDetailComponent,
        ProfileMySuffixDialogComponent,
        ProfileMySuffixDeleteDialogComponent,
        ProfileMySuffixPopupComponent,
        ProfileMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ProfileMySuffixComponent,
        ProfileMySuffixDialogComponent,
        ProfileMySuffixPopupComponent,
        ProfileMySuffixDeleteDialogComponent,
        ProfileMySuffixDeletePopupComponent,
    ],
    providers: [
        ProfileMySuffixService,
        ProfileMySuffixPopupService,
        ProfileMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Hochzeitclick11ProfileMySuffixModule {}
