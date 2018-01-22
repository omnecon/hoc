import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { GalleryMySuffix } from './gallery-my-suffix.model';
import { GalleryMySuffixService } from './gallery-my-suffix.service';

@Component({
    selector: 'jhi-gallery-my-suffix-detail',
    templateUrl: './gallery-my-suffix-detail.component.html'
})
export class GalleryMySuffixDetailComponent implements OnInit, OnDestroy {

    gallery: GalleryMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private galleryService: GalleryMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGalleries();
    }

    load(id) {
        this.galleryService.find(id).subscribe((gallery) => {
            this.gallery = gallery;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGalleries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'galleryListModification',
            (response) => this.load(this.gallery.id)
        );
    }
}
