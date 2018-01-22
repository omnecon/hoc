import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Hochzeitclick11SharedModule } from '../../shared';
import {
    InquiryMySuffixService,
    InquiryMySuffixPopupService,
    InquiryMySuffixComponent,
    InquiryMySuffixDetailComponent,
    InquiryMySuffixDialogComponent,
    InquiryMySuffixPopupComponent,
    InquiryMySuffixDeletePopupComponent,
    InquiryMySuffixDeleteDialogComponent,
    inquiryRoute,
    inquiryPopupRoute,
    InquiryMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...inquiryRoute,
    ...inquiryPopupRoute,
];

@NgModule({
    imports: [
        Hochzeitclick11SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InquiryMySuffixComponent,
        InquiryMySuffixDetailComponent,
        InquiryMySuffixDialogComponent,
        InquiryMySuffixDeleteDialogComponent,
        InquiryMySuffixPopupComponent,
        InquiryMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        InquiryMySuffixComponent,
        InquiryMySuffixDialogComponent,
        InquiryMySuffixPopupComponent,
        InquiryMySuffixDeleteDialogComponent,
        InquiryMySuffixDeletePopupComponent,
    ],
    providers: [
        InquiryMySuffixService,
        InquiryMySuffixPopupService,
        InquiryMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Hochzeitclick11InquiryMySuffixModule {}
