import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { FeatureMySuffix } from './feature-my-suffix.model';
import { FeatureMySuffixService } from './feature-my-suffix.service';

@Component({
    selector: 'jhi-feature-my-suffix-detail',
    templateUrl: './feature-my-suffix-detail.component.html'
})
export class FeatureMySuffixDetailComponent implements OnInit, OnDestroy {

    feature: FeatureMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private featureService: FeatureMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFeatures();
    }

    load(id) {
        this.featureService.find(id).subscribe((feature) => {
            this.feature = feature;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFeatures() {
        this.eventSubscriber = this.eventManager.subscribe(
            'featureListModification',
            (response) => this.load(this.feature.id)
        );
    }
}
