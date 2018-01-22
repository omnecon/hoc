import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Hochzeitclick11SharedModule } from '../../shared';
import {
    DataImportMySuffixService,
    DataImportMySuffixPopupService,
    DataImportMySuffixComponent,
    DataImportMySuffixDetailComponent,
    DataImportMySuffixDialogComponent,
    DataImportMySuffixPopupComponent,
    DataImportMySuffixDeletePopupComponent,
    DataImportMySuffixDeleteDialogComponent,
    dataImportRoute,
    dataImportPopupRoute,
    DataImportMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...dataImportRoute,
    ...dataImportPopupRoute,
];

@NgModule({
    imports: [
        Hochzeitclick11SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DataImportMySuffixComponent,
        DataImportMySuffixDetailComponent,
        DataImportMySuffixDialogComponent,
        DataImportMySuffixDeleteDialogComponent,
        DataImportMySuffixPopupComponent,
        DataImportMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        DataImportMySuffixComponent,
        DataImportMySuffixDialogComponent,
        DataImportMySuffixPopupComponent,
        DataImportMySuffixDeleteDialogComponent,
        DataImportMySuffixDeletePopupComponent,
    ],
    providers: [
        DataImportMySuffixService,
        DataImportMySuffixPopupService,
        DataImportMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Hochzeitclick11DataImportMySuffixModule {}
