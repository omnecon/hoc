import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BannerMySuffix } from './banner-my-suffix.model';
import { BannerMySuffixPopupService } from './banner-my-suffix-popup.service';
import { BannerMySuffixService } from './banner-my-suffix.service';

@Component({
    selector: 'jhi-banner-my-suffix-dialog',
    templateUrl: './banner-my-suffix-dialog.component.html'
})
export class BannerMySuffixDialogComponent implements OnInit {

    banner: BannerMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private bannerService: BannerMySuffixService,
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
        if (this.banner.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bannerService.update(this.banner));
        } else {
            this.subscribeToSaveResponse(
                this.bannerService.create(this.banner));
        }
    }

    private subscribeToSaveResponse(result: Observable<BannerMySuffix>) {
        result.subscribe((res: BannerMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: BannerMySuffix) {
        this.eventManager.broadcast({ name: 'bannerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-banner-my-suffix-popup',
    template: ''
})
export class BannerMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bannerPopupService: BannerMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bannerPopupService
                    .open(BannerMySuffixDialogComponent as Component, params['id']);
            } else {
                this.bannerPopupService
                    .open(BannerMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
