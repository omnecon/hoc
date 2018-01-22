import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InquiryMySuffix } from './inquiry-my-suffix.model';
import { InquiryMySuffixPopupService } from './inquiry-my-suffix-popup.service';
import { InquiryMySuffixService } from './inquiry-my-suffix.service';

@Component({
    selector: 'jhi-inquiry-my-suffix-dialog',
    templateUrl: './inquiry-my-suffix-dialog.component.html'
})
export class InquiryMySuffixDialogComponent implements OnInit {

    inquiry: InquiryMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private inquiryService: InquiryMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.inquiry.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inquiryService.update(this.inquiry));
        } else {
            this.subscribeToSaveResponse(
                this.inquiryService.create(this.inquiry));
        }
    }

    private subscribeToSaveResponse(result: Observable<InquiryMySuffix>) {
        result.subscribe((res: InquiryMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: InquiryMySuffix) {
        this.eventManager.broadcast({ name: 'inquiryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-inquiry-my-suffix-popup',
    template: ''
})
export class InquiryMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inquiryPopupService: InquiryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inquiryPopupService
                    .open(InquiryMySuffixDialogComponent as Component, params['id']);
            } else {
                this.inquiryPopupService
                    .open(InquiryMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
