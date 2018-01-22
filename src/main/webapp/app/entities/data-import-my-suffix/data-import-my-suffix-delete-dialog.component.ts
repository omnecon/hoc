import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DataImportMySuffix } from './data-import-my-suffix.model';
import { DataImportMySuffixPopupService } from './data-import-my-suffix-popup.service';
import { DataImportMySuffixService } from './data-import-my-suffix.service';

@Component({
    selector: 'jhi-data-import-my-suffix-delete-dialog',
    templateUrl: './data-import-my-suffix-delete-dialog.component.html'
})
export class DataImportMySuffixDeleteDialogComponent {

    dataImport: DataImportMySuffix;

    constructor(
        private dataImportService: DataImportMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dataImportService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dataImportListModification',
                content: 'Deleted an dataImport'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-data-import-my-suffix-delete-popup',
    template: ''
})
export class DataImportMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dataImportPopupService: DataImportMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dataImportPopupService
                .open(DataImportMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
