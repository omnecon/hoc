import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ImageMySuffix } from './image-my-suffix.model';
import { ImageMySuffixPopupService } from './image-my-suffix-popup.service';
import { ImageMySuffixService } from './image-my-suffix.service';

@Component({
    selector: 'jhi-image-my-suffix-delete-dialog',
    templateUrl: './image-my-suffix-delete-dialog.component.html'
})
export class ImageMySuffixDeleteDialogComponent {

    image: ImageMySuffix;

    constructor(
        private imageService: ImageMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.imageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'imageListModification',
                content: 'Deleted an image'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-image-my-suffix-delete-popup',
    template: ''
})
export class ImageMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private imagePopupService: ImageMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.imagePopupService
                .open(ImageMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
