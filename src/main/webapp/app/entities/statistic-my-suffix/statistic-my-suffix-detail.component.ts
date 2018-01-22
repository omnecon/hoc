import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { StatisticMySuffix } from './statistic-my-suffix.model';
import { StatisticMySuffixService } from './statistic-my-suffix.service';

@Component({
    selector: 'jhi-statistic-my-suffix-detail',
    templateUrl: './statistic-my-suffix-detail.component.html'
})
export class StatisticMySuffixDetailComponent implements OnInit, OnDestroy {

    statistic: StatisticMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private statisticService: StatisticMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStatistics();
    }

    load(id) {
        this.statisticService.find(id).subscribe((statistic) => {
            this.statistic = statistic;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStatistics() {
        this.eventSubscriber = this.eventManager.subscribe(
            'statisticListModification',
            (response) => this.load(this.statistic.id)
        );
    }
}
