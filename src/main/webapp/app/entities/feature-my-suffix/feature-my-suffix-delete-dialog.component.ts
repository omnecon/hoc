import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FeatureMySuffix } from './feature-my-suffix.model';
import { FeatureMySuffixPopupService } from './feature-my-suffix-popup.service';
import { FeatureMySuffixService } from './feature-my-suffix.service';

@Component({
    selector: 'jhi-feature-my-suffix-delete-dialog',
    templateUrl: './feature-my-suffix-delete-dialog.component.html'
})
export class FeatureMySuffixDeleteDialogComponent {

    feature: FeatureMySuffix;

    constructor(
        private featureService: FeatureMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.featureService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'featureListModification',
                content: 'Deleted an feature'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-feature-my-suffix-delete-popup',
    template: ''
})
export class FeatureMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private featurePopupService: FeatureMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.featurePopupService
                .open(FeatureMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
