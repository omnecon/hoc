import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InquiryMySuffix } from './inquiry-my-suffix.model';
import { InquiryMySuffixPopupService } from './inquiry-my-suffix-popup.service';
import { InquiryMySuffixService } from './inquiry-my-suffix.service';

@Component({
    selector: 'jhi-inquiry-my-suffix-delete-dialog',
    templateUrl: './inquiry-my-suffix-delete-dialog.component.html'
})
export class InquiryMySuffixDeleteDialogComponent {

    inquiry: InquiryMySuffix;

    constructor(
        private inquiryService: InquiryMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inquiryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inquiryListModification',
                content: 'Deleted an inquiry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inquiry-my-suffix-delete-popup',
    template: ''
})
export class InquiryMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inquiryPopupService: InquiryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.inquiryPopupService
                .open(InquiryMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
