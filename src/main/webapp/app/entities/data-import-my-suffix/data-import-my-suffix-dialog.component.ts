import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DataImportMySuffix } from './data-import-my-suffix.model';
import { DataImportMySuffixPopupService } from './data-import-my-suffix-popup.service';
import { DataImportMySuffixService } from './data-import-my-suffix.service';

@Component({
    selector: 'jhi-data-import-my-suffix-dialog',
    templateUrl: './data-import-my-suffix-dialog.component.html'
})
export class DataImportMySuffixDialogComponent implements OnInit {

    dataImport: DataImportMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataImportService: DataImportMySuffixService,
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
        if (this.dataImport.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dataImportService.update(this.dataImport));
        } else {
            this.subscribeToSaveResponse(
                this.dataImportService.create(this.dataImport));
        }
    }

    private subscribeToSaveResponse(result: Observable<DataImportMySuffix>) {
        result.subscribe((res: DataImportMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: DataImportMySuffix) {
        this.eventManager.broadcast({ name: 'dataImportListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-data-import-my-suffix-popup',
    template: ''
})
export class DataImportMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dataImportPopupService: DataImportMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dataImportPopupService
                    .open(DataImportMySuffixDialogComponent as Component, params['id']);
            } else {
                this.dataImportPopupService
                    .open(DataImportMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
