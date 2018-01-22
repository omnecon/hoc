import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GalleryMySuffix } from './gallery-my-suffix.model';
import { GalleryMySuffixPopupService } from './gallery-my-suffix-popup.service';
import { GalleryMySuffixService } from './gallery-my-suffix.service';

@Component({
    selector: 'jhi-gallery-my-suffix-dialog',
    templateUrl: './gallery-my-suffix-dialog.component.html'
})
export class GalleryMySuffixDialogComponent implements OnInit {

    gallery: GalleryMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private galleryService: GalleryMySuffixService,
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
        if (this.gallery.id !== undefined) {
            this.subscribeToSaveResponse(
                this.galleryService.update(this.gallery));
        } else {
            this.subscribeToSaveResponse(
                this.galleryService.create(this.gallery));
        }
    }

    private subscribeToSaveResponse(result: Observable<GalleryMySuffix>) {
        result.subscribe((res: GalleryMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: GalleryMySuffix) {
        this.eventManager.broadcast({ name: 'galleryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-gallery-my-suffix-popup',
    template: ''
})
export class GalleryMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private galleryPopupService: GalleryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.galleryPopupService
                    .open(GalleryMySuffixDialogComponent as Component, params['id']);
            } else {
                this.galleryPopupService
                    .open(GalleryMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
