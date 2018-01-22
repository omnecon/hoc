import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ImageMySuffix } from './image-my-suffix.model';
import { ImageMySuffixPopupService } from './image-my-suffix-popup.service';
import { ImageMySuffixService } from './image-my-suffix.service';

@Component({
    selector: 'jhi-image-my-suffix-dialog',
    templateUrl: './image-my-suffix-dialog.component.html'
})
export class ImageMySuffixDialogComponent implements OnInit {

    image: ImageMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private imageService: ImageMySuffixService,
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
        if (this.image.id !== undefined) {
            this.subscribeToSaveResponse(
                this.imageService.update(this.image));
        } else {
            this.subscribeToSaveResponse(
                this.imageService.create(this.image));
        }
    }

    private subscribeToSaveResponse(result: Observable<ImageMySuffix>) {
        result.subscribe((res: ImageMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ImageMySuffix) {
        this.eventManager.broadcast({ name: 'imageListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-image-my-suffix-popup',
    template: ''
})
export class ImageMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imagePopupService: ImageMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.imagePopupService
                    .open(ImageMySuffixDialogComponent as Component, params['id']);
            } else {
                this.imagePopupService
                    .open(ImageMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
