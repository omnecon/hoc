import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BannerMySuffix } from './banner-my-suffix.model';
import { BannerMySuffixService } from './banner-my-suffix.service';

@Component({
    selector: 'jhi-banner-my-suffix-detail',
    templateUrl: './banner-my-suffix-detail.component.html'
})
export class BannerMySuffixDetailComponent implements OnInit, OnDestroy {

    banner: BannerMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bannerService: BannerMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBanners();
    }

    load(id) {
        this.bannerService.find(id).subscribe((banner) => {
            this.banner = banner;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBanners() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bannerListModification',
            (response) => this.load(this.banner.id)
        );
    }
}
