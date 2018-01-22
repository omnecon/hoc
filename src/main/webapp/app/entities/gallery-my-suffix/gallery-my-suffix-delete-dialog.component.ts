import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GalleryMySuffix } from './gallery-my-suffix.model';
import { GalleryMySuffixPopupService } from './gallery-my-suffix-popup.service';
import { GalleryMySuffixService } from './gallery-my-suffix.service';

@Component({
    selector: 'jhi-gallery-my-suffix-delete-dialog',
    templateUrl: './gallery-my-suffix-delete-dialog.component.html'
})
export class GalleryMySuffixDeleteDialogComponent {

    gallery: GalleryMySuffix;

    constructor(
        private galleryService: GalleryMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.galleryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'galleryListModification',
                content: 'Deleted an gallery'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-gallery-my-suffix-delete-popup',
    template: ''
})
export class GalleryMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private galleryPopupService: GalleryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.galleryPopupService
                .open(GalleryMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
