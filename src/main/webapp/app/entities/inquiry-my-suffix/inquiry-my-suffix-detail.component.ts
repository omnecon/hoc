import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { InquiryMySuffix } from './inquiry-my-suffix.model';
import { InquiryMySuffixService } from './inquiry-my-suffix.service';

@Component({
    selector: 'jhi-inquiry-my-suffix-detail',
    templateUrl: './inquiry-my-suffix-detail.component.html'
})
export class InquiryMySuffixDetailComponent implements OnInit, OnDestroy {

    inquiry: InquiryMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private inquiryService: InquiryMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInquiries();
    }

    load(id) {
        this.inquiryService.find(id).subscribe((inquiry) => {
            this.inquiry = inquiry;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInquiries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inquiryListModification',
            (response) => this.load(this.inquiry.id)
        );
    }
}
