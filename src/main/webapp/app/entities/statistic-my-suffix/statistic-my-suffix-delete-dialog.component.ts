import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { StatisticMySuffix } from './statistic-my-suffix.model';
import { StatisticMySuffixPopupService } from './statistic-my-suffix-popup.service';
import { StatisticMySuffixService } from './statistic-my-suffix.service';

@Component({
    selector: 'jhi-statistic-my-suffix-delete-dialog',
    templateUrl: './statistic-my-suffix-delete-dialog.component.html'
})
export class StatisticMySuffixDeleteDialogComponent {

    statistic: StatisticMySuffix;

    constructor(
        private statisticService: StatisticMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.statisticService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'statisticListModification',
                content: 'Deleted an statistic'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-statistic-my-suffix-delete-popup',
    template: ''
})
export class StatisticMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private statisticPopupService: StatisticMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.statisticPopupService
                .open(StatisticMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
