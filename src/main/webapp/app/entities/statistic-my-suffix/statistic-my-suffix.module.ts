import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Hochzeitclick11SharedModule } from '../../shared';
import {
    StatisticMySuffixService,
    StatisticMySuffixPopupService,
    StatisticMySuffixComponent,
    StatisticMySuffixDetailComponent,
    StatisticMySuffixDialogComponent,
    StatisticMySuffixPopupComponent,
    StatisticMySuffixDeletePopupComponent,
    StatisticMySuffixDeleteDialogComponent,
    statisticRoute,
    statisticPopupRoute,
    StatisticMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...statisticRoute,
    ...statisticPopupRoute,
];

@NgModule({
    imports: [
        Hochzeitclick11SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StatisticMySuffixComponent,
        StatisticMySuffixDetailComponent,
        StatisticMySuffixDialogComponent,
        StatisticMySuffixDeleteDialogComponent,
        StatisticMySuffixPopupComponent,
        StatisticMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        StatisticMySuffixComponent,
        StatisticMySuffixDialogComponent,
        StatisticMySuffixPopupComponent,
        StatisticMySuffixDeleteDialogComponent,
        StatisticMySuffixDeletePopupComponent,
    ],
    providers: [
        StatisticMySuffixService,
        StatisticMySuffixPopupService,
        StatisticMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Hochzeitclick11StatisticMySuffixModule {}
