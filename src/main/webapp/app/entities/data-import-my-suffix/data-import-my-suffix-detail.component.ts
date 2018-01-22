import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DataImportMySuffix } from './data-import-my-suffix.model';
import { DataImportMySuffixService } from './data-import-my-suffix.service';

@Component({
    selector: 'jhi-data-import-my-suffix-detail',
    templateUrl: './data-import-my-suffix-detail.component.html'
})
export class DataImportMySuffixDetailComponent implements OnInit, OnDestroy {

    dataImport: DataImportMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataImportService: DataImportMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDataImports();
    }

    load(id) {
        this.dataImportService.find(id).subscribe((dataImport) => {
            this.dataImport = dataImport;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDataImports() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dataImportListModification',
            (response) => this.load(this.dataImport.id)
        );
    }
}
