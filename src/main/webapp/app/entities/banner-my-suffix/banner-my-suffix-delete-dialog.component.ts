import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BannerMySuffix } from './banner-my-suffix.model';
import { BannerMySuffixPopupService } from './banner-my-suffix-popup.service';
import { BannerMySuffixService } from './banner-my-suffix.service';

@Component({
    selector: 'jhi-banner-my-suffix-delete-dialog',
    templateUrl: './banner-my-suffix-delete-dialog.component.html'
})
export class BannerMySuffixDeleteDialogComponent {

    banner: BannerMySuffix;

    constructor(
        private bannerService: BannerMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bannerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bannerListModification',
                content: 'Deleted an banner'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-banner-my-suffix-delete-popup',
    template: ''
})
export class BannerMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bannerPopupService: BannerMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bannerPopupService
                .open(BannerMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
