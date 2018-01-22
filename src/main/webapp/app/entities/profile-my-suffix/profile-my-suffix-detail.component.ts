import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ProfileMySuffix } from './profile-my-suffix.model';
import { ProfileMySuffixService } from './profile-my-suffix.service';

@Component({
    selector: 'jhi-profile-my-suffix-detail',
    templateUrl: './profile-my-suffix-detail.component.html'
})
export class ProfileMySuffixDetailComponent implements OnInit, OnDestroy {

    profile: ProfileMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private profileService: ProfileMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProfiles();
    }

    load(id) {
        this.profileService.find(id).subscribe((profile) => {
            this.profile = profile;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProfiles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'profileListModification',
            (response) => this.load(this.profile.id)
        );
    }
}
